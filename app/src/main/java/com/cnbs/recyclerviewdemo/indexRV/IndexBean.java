package com.cnbs.recyclerviewdemo.indexRV;

import java.util.List;

/**
 * Created by Administrator on 2017/8/23.
 */

public class IndexBean {


    /**
     * list : [{"code":"8002","inputcode":"C","name":"产科门诊"},{"code":"2008","inputcode":"E","name":"耳鼻喉科"},{"code":"6014","inputcode":"E","name":"儿科"},{"code":"8001","inputcode":"F","name":"妇科门诊"},{"code":"2046","inputcode":"F","name":"腹透中心"},{"code":"6013","inputcode":"G","name":"骨科"},{"code":"2023","inputcode":"G","name":"感染门诊"},{"code":"6015","inputcode":"H","name":"呼吸内科"},{"code":"2053","inputcode":"K","name":"康复科门诊"},{"code":"3011","inputcode":"K","name":"口腔科"},{"code":"6010","inputcode":"M","name":"泌尿外科"},{"code":"2019","inputcode":"N","name":"内分泌科"},{"code":"9001","inputcode":"N","name":"男科"},{"code":"3013","inputcode":"P","name":"皮肤科"},{"code":"6016","inputcode":"R","name":"乳腺疾病门诊"},{"code":"2002","inputcode":"S","name":"肾病内科"},{"code":"6009","inputcode":"S","name":"神经内科"},{"code":"2031","inputcode":"S","name":"神经外科"},{"code":"3000","inputcode":"S","name":"碎石门诊"},{"code":"3012","inputcode":"S","name":"烧伤整形科"},{"code":"2015","inputcode":"T","name":"透析中心"},{"code":"6012","inputcode":"X","name":"胸腹微创外科"},{"code":"6008","inputcode":"X","name":"消化内科"},{"code":"2029","inputcode":"X","name":"新生儿"},{"code":"6011","inputcode":"X","name":"心血管内科"},{"code":"6002","inputcode":"Y","name":"预防保健科"},{"code":"2009","inputcode":"Y","name":"眼科"},{"code":"2025","inputcode":"Z","name":"肿瘤专科"},{"code":"6007","inputcode":"Z","name":"中医科门诊"}]
     * code : 0
     * msg : 当日挂号列表
     */

    private String code;
    private String msg;
    /**
     * code : 8002
     * inputcode : C
     * name : 产科门诊
     */

    private List<ListBean> list;

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

    public List<ListBean> getList() {
        return list;
    }

    public void setList(List<ListBean> list) {
        this.list = list;
    }

    public static class ListBean {
        private String code;
        private String inputcode;
        private String name;

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }

        public String getInputcode() {
            return inputcode;
        }

        public void setInputcode(String inputcode) {
            this.inputcode = inputcode;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
