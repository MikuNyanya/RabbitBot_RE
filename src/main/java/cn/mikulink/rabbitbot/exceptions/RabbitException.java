package cn.mikulink.rabbitbot.exceptions;

/**
 * create by MikuLink on 2020/1/9 18:32
 * for the Reisen
 * 自定义业务异常
 */
public class RabbitException extends Exception {
    public RabbitException(String msg) {
        super(msg);
    }
}
