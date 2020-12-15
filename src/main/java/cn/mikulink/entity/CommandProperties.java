package cn.mikulink.entity;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @Description: 指令名称以及别称
 * @author: MikuLink
 * @date: 2020/12/14 18:30
 **/
@Getter
@Setter
public class CommandProperties {
    /**
     * 指令名称
     */
    public String name;
    /**
     * 指令别称
     */
    public ArrayList<String> alias;


    /**
     * 指令属性构造器
     *
     * @param name  指令名
     * @param alias 其他指令名
     */
    public CommandProperties(String name, ArrayList<String> alias) {
        this.name = name;
        this.alias = alias;
    }

    /**
     * 指令属性构造器封装
     *
     * @param name 指令名
     */
    public CommandProperties(String name) {
        this(name, new ArrayList<>());
    }

    /**
     * 指令属性构造器封装,这个用的比较多
     *
     * @param name  指令名
     * @param alias 其他指令名
     */
    public CommandProperties(String name, String... alias) {
        this(name, new ArrayList<>(Arrays.asList(alias)));
    }
}
