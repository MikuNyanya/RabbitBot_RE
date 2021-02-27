package cn.mikulink.rabbitbot.test.utils;

import cn.mikulink.rabbitbot.utils.FileUtil;
import org.junit.Test;

import java.io.File;

/**
 * @author MikuLink
 * @date 2021/2/3 21:32
 * for the Reisen
 */
public class FileUtilTest {

    @Test
    public void fileCheckTest() {
        try {
            FileUtil.fileCheck("src/main/resources/files/groups/123/switch");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    @Test
    public void getListFiles() {
        try {
            File[] files = FileUtil.getListFiles("D:/");
            System.out.println("");
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
