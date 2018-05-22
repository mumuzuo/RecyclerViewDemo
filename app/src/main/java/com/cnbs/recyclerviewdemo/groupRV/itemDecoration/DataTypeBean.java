package com.cnbs.recyclerviewdemo.groupRV.itemDecoration;

import java.util.List;

/**
 * 接收数据的实体类
 *
 * @author zuo
 * @date 2018/5/22 11:40
 */
public class DataTypeBean {
    private String code;
    private String msg;
    private List<TypeBean> list;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public List<TypeBean> getList() {
        return list;
    }

    public void setList(List<TypeBean> list) {
        this.list = list;
    }
}
