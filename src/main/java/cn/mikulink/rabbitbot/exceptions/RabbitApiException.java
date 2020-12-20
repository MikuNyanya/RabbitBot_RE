package cn.mikulink.rabbitbot.exceptions;

/**
 * create by MikuLink on 2020/12/17 18:32
 * for the Reisen
 * 自定义业务异常 api专用
 */
public class RabbitApiException extends Exception {
    public RabbitApiException(String msg) {
        super(msg);
    }
}
