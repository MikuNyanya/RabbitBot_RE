package cn.mikulink.entity;

import lombok.Getter;
import lombok.Setter;

/**
 * @author MikuLink
 * @date 2020/07/25 06:33
 * for the Reisen
 * <p>
 * 简易返回信息
 */
@Getter
@Setter
public class ReString {
    //指示是否成功
    private boolean success;
    //返回信息
    private String message;
    //返回数据
    private Object data;

    public ReString() {
    }

    public ReString(boolean success) {
        this.success = success;
    }

    public ReString(boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public ReString(boolean success, String message, Object data) {
        this.success = success;
        this.message = message;
        this.data = data;
    }
}
