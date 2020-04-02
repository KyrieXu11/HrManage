package com.kyriexu.exception;

/**
 * @author KyrieXu
 * @since 2020/3/29 16:52
 **/
public class BaseException extends RuntimeException {
    private ResultSatus resultSatus;

    public ResultSatus getResultSatus() {
        return resultSatus;
    }

    public void setResultSatus(ResultSatus resultSatus) {
        this.resultSatus = resultSatus;
    }

    public BaseException(ResultSatus resultSatus) {
        this.resultSatus = resultSatus;
    }
}
