package com.dos.md.data.bean;

/**
 * Created by DOS on 026/26/3/2016.
 */
public class NetData<T> {
    public NetData(int resultCode, String resultMessage, T data) {
        this.resultCode = resultCode;
        this.data = data;
        this.resultMessage = resultMessage;
    }

    public int resultCode;
    public String resultMessage;

    public T data;

}
