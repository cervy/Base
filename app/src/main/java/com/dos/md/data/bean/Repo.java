package com.dos.md.data.bean;

/**
 * Created by Administrator on 026/26/3/2016.
 */

public class Repo {
    public String name; // 库的名字
    public String description; // 描述
    public String language; // 语言

    //  public String testNullField; // 试错
    public Repo(String n, String description, String language) {
        this.name = n;
        this.description = description;
        this.language = language;
    }
}