package com.cnbs.recyclerviewdemo.groupRV.adapter;

import com.chad.library.adapter.base.entity.SectionEntity;
import com.cnbs.recyclerviewdemo.groupRV.vlayout.DataBean;

/**
 * @author zuo
 * @date 2018/5/22 19:10
 */
public class TypeBean extends SectionEntity {
    public TypeBean(boolean isHeader, String header) {
        super(isHeader, header);
    }

    public TypeBean(DataBean bean) {
        super(bean);
    }
}
