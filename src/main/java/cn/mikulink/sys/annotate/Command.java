package cn.mikulink.sys.annotate;


import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 自定义注解
 * 用来标记指令类,然后直接扫描该标记的指令类，以实现自动注册
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Component
public @interface Command {

}
