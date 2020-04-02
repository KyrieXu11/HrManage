package com.kyriexu.exception;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:54
 **/
public enum ResultSatus {
    REDIS_CONNECTION_EXCEPTION(400,"redis连接失败"),
    MQCLIENT_EXCEPTION(400,"MQ客户端异常"),
    REDIS_INSERT_EXCEPTION(400,"redis插入异常"),
    MESSAGE_SEND_EXCEPTION(400,"消息队列发送异常"),
    RECONSUME_EXCEPTION(400,"重复消费"),
    JSON_PROCESS_EXCEPTION(400,"JSON转化异常"),
    MAIL_MESSAGE_EXCEPTION(400,"邮件发送异常");

    private int code;

    private String msg;

    ResultSatus() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    ResultSatus(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
