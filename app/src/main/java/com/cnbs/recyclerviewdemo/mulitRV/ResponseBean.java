package com.cnbs.recyclerviewdemo.mulitRV;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class ResponseBean {

    private int code;
    private String msg;
    private List<MulitBean> list;

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

    public List<MulitBean> getList() {
        return list;
    }

    public void setList(List<MulitBean> list) {
        this.list = list;
    }

}
