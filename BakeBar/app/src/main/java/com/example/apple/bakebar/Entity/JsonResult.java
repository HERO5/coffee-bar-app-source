package com.example.apple.bakebar.Entity;

/**
 * Created by apple on 2017/11/1.
 */

public class JsonResult<T> extends Result {
    private static final long serialVersionUID = 7880907731807860636L;

    /**
     * 数据
     */
    private T data = null;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public JsonResult() {
        super();
    }
}
