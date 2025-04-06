package cn.mikulink.rabbitbot.command;


import cn.mikulink.rabbitbot.entity.CommandProperties;
import cn.mikulink.rabbitbot.entity.rabbitbotmessage.MessageInfo;
import cn.mikulink.rabbitbot.utils.StringUtil;

import java.util.ArrayList;

/**
* @author MikuLink
* @date 2020/8/31 10:50
* for the Reisen
* <p>
* 指令接口
* 当接收到 指令(properties) 执行动作
*/
public abstract class Command {

   /**
    * 构造指令名称以及别称
    * 不区分大小写
    * @return 指令名称对象
    */
   public abstract CommandProperties properties();

   /**
    * 权限验证
    *
    * @param messageInfo 消息对象
    * @return 权限验证结果，返回null表示不做校验
    */
   public MessageInfo permissionCheck(MessageInfo messageInfo) {
      return null;
   }

   /**
    * 具体业务执行入口
    * @param messageInfo 消息对象
    * @return 回复的消息内容，返回null不做处理
    */
   public abstract MessageInfo execute(MessageInfo messageInfo);

   /**
    * 从消息体中获得 用空格分割的参数
    *
    * @param msg 消息
    * @return 分割出来的参数
    */
   public ArrayList<String> getArgs(String msg) {
      String[] args = msg.trim().split(" ");
      ArrayList<String> list = new ArrayList<>();
      for (String arg : args) {
         if (StringUtil.isNotEmpty(arg)) list.add(arg);
      }
      list.remove(0);
      return list;
   }
}
