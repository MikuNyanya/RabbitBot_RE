package cn.mikulink.rabbitbot.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * create by MikuLink on 2019/12/5 16:00
 * for the Reisen
 * <p>
 * 文件工具
 */
public class FileUtil {
    /**
     * 检验文件，不存在则创建
     *
     * @param filePath 文件路径
     * @return 指定路径的文件
     * @throws IOException 文件处理异常
     */
    public static File fileCheck(String filePath) throws IOException {
        File file = new File(filePath);

        //存在则直接返回
        if (file.exists()) {
            return file;
        }
        //不存在的时候，先创建目录
        File parentFile = file.getParentFile();
        if (null != parentFile && !parentFile.exists()) {
            //可创建多级目录，并且只能创建目录，无法用来创建文件
            fileDirsCheck(parentFile);
        }
        file.createNewFile();

        return file;
    }

    //检验文件夹，不存在则创建 方法重载
    public static void fileDirsCheck(File file) {
        fileDirsCheck(file, null);

    }

    //检验文件夹，不存在则创建 方法重载
    public static void fileDirsCheck(String filePath) {
        fileDirsCheck(null, filePath);
    }

    /**
     * 检验文件夹，不存在则创建
     * 参数二选一，优先使用file
     *
     * @param file     文件
     * @param filePath 文件夹路径
     */
    public static void fileDirsCheck(File file, String filePath) {
        File fileDirs = null;
        fileDirs = null == file ? new File(filePath) : file.getParentFile();

        if (null != fileDirs && !fileDirs.exists()) {
            //可创建多级目录，并且只能创建目录，无法用来创建文件
            fileDirs.mkdirs();
        }
    }

    /**
     * 检验文件
     *
     * @param filePath 文件路径
     * @return 指定路径的文件
     */
    public static boolean exists(String filePath) {
        File file = new File(filePath);

        //存在则直接返回
        return file.exists();
    }

    /**
     * 删除文件
     *
     * @param filePath 文件路径
     * @return 是否删除成功
     */
    public static boolean delete(String filePath) {
        File file = new File(filePath);
        //存在则删除
        if (file.exists()) {
            return file.delete();
        }
        return true;
    }

    /**
     * 复制文件
     *
     * @param srcPathStr 原文件完整路径
     * @param desPathStr 目标文件夹路径
     * @throws IOException 文件处理异常
     */
    public static void copy(String srcPathStr, String desPathStr) throws IOException {
        //获取源文件的名称
        String newFileName = getFileName(srcPathStr);
        //目标文件地址
        desPathStr = desPathStr + File.separator + newFileName;

        //创建输入流对象
        FileInputStream inputStream = new FileInputStream(srcPathStr);
        //创建输出流对象
        FileOutputStream outputStream = new FileOutputStream(desPathStr);
        //创建搬运工具
        byte[] tempDatas = new byte[1024 * 8];

        //创建长度
        int len = 0;
        //向目标路径写入数据
        while ((len = inputStream.read(tempDatas)) != -1) {
            outputStream.write(tempDatas, 0, len);
        }
        //关闭流
        outputStream.close();
        inputStream.close();
    }

    /**
     * 移动文件
     * 原理是直接修改文件完整路径，以达到移动的目的
     *
     * @param srcPathStr 原文件完整路径
     * @param desPathStr 目标文件夹路径
     * @throws IOException 文件处理异常
     */
    public static boolean move(String srcPathStr, String desPathStr) throws IOException {
        if (StringUtil.isEmpty(srcPathStr)) {
            throw new IOException("原文件路径不能为空");
        }
        if (StringUtil.isEmpty(desPathStr)) {
            throw new IOException("目标路径不能为空");
        }
        File srcFile = new File(srcPathStr);
        if (!srcFile.exists()) {
            throw new IOException("原文件不存在");
        }
        //获取原文件名称+后缀
        String srcFileName = srcPathStr.substring(srcPathStr.lastIndexOf(File.separator) + 1);

        return srcFile.renameTo(new File(desPathStr + File.separator + srcFileName));
    }

    /**
     * 根据文件路径获取文件名称
     *
     * @param filePath 文件路径
     * @return 文件名称
     */
    public static String getFileName(String filePath) {
        if (StringUtil.isEmpty(filePath)) {
            return "";
        }
        if (!filePath.contains(File.separator)) {
            return filePath;
        }
        //截取最后一个分隔符后面的字段
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 根据文件路径获取目录
     *
     * @param fileFullPath 文件路径
     * @return 文件夹路径
     */
    public static String getFilePath(String fileFullPath) {
        if (StringUtil.isEmpty(fileFullPath)) {
            return "";
        }
        if (!fileFullPath.contains(File.separator)) {
            return fileFullPath;
        }
        //截取最后一个分隔符前面的字段
        return fileFullPath.substring(0, fileFullPath.lastIndexOf(File.separator));
    }

    /**
     * 读取指定目录下所有文件，文件夹以及文件夹下的子文件的列表
     *
     * @param path 指定目录
     * @return 文件对象列表
     */
    public static File[] getListFiles(String path) {
        if (exists(path)) {
            return null;
        }
        return new File(path).listFiles();
    }

    /**
     * 读取指定目录下所有文件，文件夹的名称
     *
     * @param path 指定目录
     * @return 文件，文件夹名称列表
     */
    public static String[] getList(String path) {
        if (!exists(path)) {
            return null;
        }
        return new File(path).list();
    }

    /**
     * 获取文件后缀
     *
     * @param fileName 文件名
     * @return 文件后缀
     */
    public static String getFileSuffix(String fileName) {
        if (StringUtil.isEmpty(fileName)) {
            return "";
        }
        if (!fileName.contains(".")) {
            return "";
        }
        return fileName.substring(fileName.lastIndexOf("."));
    }
}
