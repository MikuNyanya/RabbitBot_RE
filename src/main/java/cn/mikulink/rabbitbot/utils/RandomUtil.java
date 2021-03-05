package cn.mikulink.rabbitbot.utils;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * create by MikuLink on 2019/12/3 14:47
 * for the Reisen
 * <p>
 * 随机数工具
 */
public class RandomUtil {
    /**
     * 随机一个正整数
     * 最小为0
     * 最大以传参为准
     *
     * @param maxNum 随机出的最大数
     * @return 一个符合条件的随机数
     */
    public static int roll(int maxNum) {
        //由于不会随机到入参本身，所以需要+1
        return new Random().nextInt(maxNum + 1);
    }

    /**
     * 随机多个正整数
     * 最小为0
     * 最大以传参为准
     *
     * @param maxNum 随机出的最大数
     * @param count  返回元素数量
     * @return 一个符合条件的随机数
     */
    public static List<Integer> roll(int maxNum, int count) {
        //返回的元素数量不能大于可随机的元素数量
        if (count > maxNum) {
            return null;
        }
        List<Integer> randNumList = new ArrayList<>();
        int i = 1;
        do {
            do {
                //由于不会随机到入参本身，所以需要+1
                Integer randNum = roll(maxNum);
                if (!randNumList.contains(randNum)) {
                    randNumList.add(randNum);
                    break;
                }
            } while (true);
            i++;
        } while (count >= i);
        return randNumList;
    }

    /**
     * 重载 随机一个正整数
     * 0-100 包含0和100
     *
     * @return 一个0-100的随机数
     */
    public static int roll() {
        return roll(100);
    }

    /**
     * 随机是或否
     * 可以使用参数调整权重
     *
     * @param addition 加成，取值±100，100必定为true，-100必定为false
     * @return 是或否
     */
    public static boolean rollBoolean(int addition) {
        //随机0-199整数(加上0一共200个数字)
        int randNum = roll(200);
        //把范围限制在-100~100之间
        randNum -= 100;
        //两个绝对的结果
        if (randNum == 100) return true;
        if (randNum == -100) return false;

        //算上加成
        randNum += addition;
        return randNum >= 0;
    }

    /**
     * 返回列表中的一条随机字符串
     *
     * @param strList 目标列表
     * @return 列表中的随机一条
     */
    public static String rollStrFromList(List<String> strList) {
        if (null == strList || strList.size() <= 0) {
            return "";
        }
        //获取消息
        String msg = strList.get(RandomUtil.roll(strList.size() - 1));
        //针对换行符做处理
        return msg.replaceAll("\\\\n", "\n");
    }

    /**
     * 返回列表中的一条随机元素
     *
     * @param strList 目标列表
     * @return 列表中的随机一条
     */
    public static <T> T rollObjFromList(List<T> strList) {
        if (null == strList || strList.size() <= 0) {
            return null;
        }
        //获取随机元素
        return strList.get(RandomUtil.roll(strList.size() - 1));
    }

    /**
     * 返回列表中的一条随机字符串，并从列表中移除这条
     *
     * @param strList 目标列表
     * @return 列表中的随机一条
     */
    public static String rollAndDelStrFromList(List<String> strList) {
        if (null == strList || strList.size() <= 0) {
            return "";
        }
        //随机信息
        int randomNum = RandomUtil.roll(strList.size() - 1);
        //获取消息
        String msg = strList.get(randomNum);
        //移除信息
        strList.remove(randomNum);
        //针对换行符做处理
        return msg.replaceAll("\\\\n", "\n");
    }

    /**
     * 根据权重随机出一个元素
     *
     * @param objAdditionMap key为要随机的元素，value为权重 单个元素的权重最小0.01 权重可以总和突破100%，但建议控制在正常综合100%的范围内
     * @return 根据权重随机出的元素
     */
    public static Object rollObjByAddition(Map<Object, Double> objAdditionMap) {
        if (null == objAdditionMap || objAdditionMap.size() <= 0) {
            return null;
        }
        //对象都是引用传递，放心搞
        List<Object> objectList = new ArrayList<>();
        //根据权重铺开list
        for (Object obj : objAdditionMap.keySet()) {
            //获取权重
            Double addition = objAdditionMap.get(obj);
            //扩大100倍，用以增加精度，并转为整数
            Integer additionNum = NumberUtil.toInt(addition * 100);
            for (int i = 1; i <= additionNum; i++) {
                objectList.add(obj);
            }
        }

        //根据list中的元素数量，随机出一个数字
        int listCount = objectList.size();
        //防止下标溢出要-1
        int rollNum = roll(listCount - 1);

        return objectList.get(rollNum);
    }

    /**
     * 该方法用于在制定范围即（min~max）内对min到max的所有数字进行重新随机排列，使他们形成一组随机数。
     * 1、首先先初始化一个长度为(max-min+1)的数组source，数组的元素按照数字从大到小的顺序分别为min~max；
     * 2、每次取一个随机数index，0<=index<=(数组长度len-1)，同时len自减1，
     * 取出source数组中对应在index位置上的元素依次放进最终数组result，并且将source[index]替换为source[len]。
     **/

    public static <T> T[] randomArray(Class<T> componentType,  T[] sourceArray) {
        int len = sourceArray.length;

        T[] result = (T[]) Array.newInstance(componentType, len);
        Random rd = new Random();
        int index = 0;
        for (int i = 0; i < result.length; i++) {
            //待选数组0到(len-2)随机一个下标
            index = Math.abs(rd.nextInt() % len--);
            //将随机到的数放入结果集
            result[i] = sourceArray[index];
            //将待选数组中被随机到的数，用待选数组(len-1)下标对应的数替换
            sourceArray[index] = sourceArray[len];
        }
        return result;
    }


}
