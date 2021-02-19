package cn.mikulink.rabbitbot.filemanage;

import cn.mikulink.rabbitbot.constant.ConstantFile;
import cn.mikulink.rabbitbot.utils.FileUtil;
import com.alibaba.fastjson.JSONObject;
import org.springframework.util.StringUtils;

import java.io.*;
import java.util.HashMap;

/**
 * create by MikuLink on 2021/2/3 16:37
 * for the Reisen
 * <p>
 * 开关文件专用管理器
 */
public class FileManagerSwitch {
    /**
     * 覆写开关配置
     *
     * @param switchName  开关名称
     * @param switchValue 开关值
     * @param groupId     群id,如果没有群id，覆写默认配置
     * @throws IOException 文件操作异常
     */
    public static void setSwitch(String switchName, String switchValue, Long groupId) throws IOException {
        String filePath = ConstantFile.SWITCH_FILE_PATH;
        if (null != groupId) {
            filePath = filePath.replace("switch", "groups/" + groupId + "/switch");
        }
        //读取文件
        File switchFile = FileUtil.fileCheck(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(switchFile));
        //只读取第一行
        String switchJson = reader.readLine();
        HashMap<String, String> switchMapTamp = null;
        if (null == switchJson || switchJson.length() <= 0) {
            switchMapTamp = new HashMap<>();
        } else {
            switchMapTamp = JSONObject.parseObject(switchJson, HashMap.class);
        }

        //添加或覆盖开关配置
        switchMapTamp.put(switchName, switchValue);
        BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePath, false)));
        out.write(JSONObject.toJSONString(switchMapTamp));

        //关闭写入流
        out.close();
        //关闭读取器
        reader.close();
    }


    public static String getSwitch(String switchName) throws IOException {
        return getSwitch(switchName, null);
    }

    /**
     * 读取开关配置
     *
     * @param switchName 开关名称
     * @param groupId    群id,如果没有群id，读取默认配置
     * @throws IOException 文件操作异常
     */
    public static String getSwitch(String switchName, Long groupId) throws IOException {
        String filePath = ConstantFile.SWITCH_FILE_PATH;
        if (null != groupId) {
            filePath = filePath.replace("switch", "groups/" + groupId + "/switch");
        }
        //读取文件
        File switchFile = FileUtil.fileCheck(filePath);
        BufferedReader reader = new BufferedReader(new FileReader(switchFile));
        //只读取第一行
        String switchJson = reader.readLine();
        HashMap<String, String> switchMapTamp = null;
        if (StringUtils.isEmpty(switchJson)) {
            switchMapTamp = new HashMap<>();
        } else {
            switchMapTamp = JSONObject.parseObject(switchJson, HashMap.class);
        }

        //关闭读取器
        reader.close();

        return switchMapTamp.get(switchName);
    }
}
