package cn.mikulink.filemanage;

import cn.mikulink.utils.FileUtil;

import java.io.*;

/**
 * create by MikuLink on 2019/12/5 15:44
 * for the Reisen
 * <p>
 * logo文件
 */
public class FileManagerLogo {

    /**
     * 控制台打印logo
     */
    public static void printLogo() {
        try {
            File logoFile = FileUtil.fileCheck("src/main/resources/rabbit_logo");

            //创建读取器
            BufferedReader reader = new BufferedReader(new FileReader(logoFile));
            //逐行读取文件
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
            //关闭读取器
            reader.close();
        } catch (Exception ex) {
            //吃掉所有异常，不管它，这里不重要
            ex.printStackTrace();
        }
    }

}
