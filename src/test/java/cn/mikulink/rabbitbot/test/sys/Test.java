package cn.mikulink.rabbitbot.test.sys;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

public class Test {

    private void test(){
        /**
         * 1.上边界通配符，Number与Number子类
         */
        List<? extends Number> numberListFour = new ArrayList<Number>();
        numberListFour = new ArrayList<Integer>();
        numberListFour = new ArrayList<Double>();
        numberListFour = new ArrayList<Long>();

/**
 * 2.下边界通配符,Integer与Integer父类
 */
        List<? super Integer> integerList = new ArrayList<Integer>();
        integerList = new ArrayList<Number>();
        integerList = new ArrayList<Object>();

/**
 * 3. 无界通配符,类型不确定，任意类型
 */
        List<?> list = new ArrayList<Integer>();
        list = new ArrayList<Number>();
        list = new ArrayList<Object>();
        list = new ArrayList<String>();
    }


    /**
     * 获取集合长度
     */
    public static <T> int size(Collection<T> list){
        return list.size();
    }

    /**
     * 获取集合长度-2
     */
    public static int sizeTwo(Collection<?> list){
        return list.size();
    }


    /**
     * 获取任意Set两个集合交集数量
     */
    public static <T,T2> int beMixedSum(Set<T> s1, Set<T2> s2){
        int i = 0;
        for (T t : s1) {
            if (s2.contains(t)) {
                i++;
            }
        }
        return i;
    }

    /**
     * 获取任意两个Set集合交集数量-2
     */
    public static  int beMixedSumTwo(Set<?> s1,Set<?> s2){
        int i = 0;
        for (Object o : s1) {
            if (s2.contains(o)) {
                i++;
            }
        }
        return i;
    }
}
