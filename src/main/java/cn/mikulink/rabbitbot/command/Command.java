package cn.mikulink.rabbitbot.command;


import cn.mikulink.rabbitbot.entity.CommandProperties;

/**
* @author MikuLink
* @date 2020/8/31 10:50
* for the Reisen
* <p>
* 指令接口
* 当接收到 指令(properties) 执行动作
*/
public interface Command {

   /**
    * 构造指令名称以及别称
    * 不区分大小写
    * @return 指令名称对象
    */
   CommandProperties properties();

}
