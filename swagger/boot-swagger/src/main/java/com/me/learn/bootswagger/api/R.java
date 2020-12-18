/*
 * Copyright 2020 tu.cn All right reserved. This software is the
 * confidential and proprietary information of tu.cn ("Confidential
 * Information"). You shall not disclose such Confidential Information and shall
 * use it only in accordance with the terms of the license agreement you entered
 * into with Tu.cn
 */
package com.me.learn.bootswagger.api;

/**
 * @author Administrator
 * @date 2020/12/9 23:37
 * Project Name: boot-swagger
 */
public class R<T> {

    private int code;
    private String msg;
    private T data;

    public R() {
    }

    public R(Builder<T> builder) {
        this.code=builder.code;
        this.msg=builder.msg;
        this.data=builder.data;
    }

    public static class Builder<T>{
        private int code;
        private String msg;
        private T data;

        public Builder() {
        }
        public R buildCustomize(int code,String msg){
            this.code=code;
            this.msg=msg;
            return new R(this);
        }
        public R buildCustomize(String msg){
            this.code=-1;
            this.msg=msg;
            return new R(this);
        }
        public R buildOk(){
            this.code=200;
            this.msg="success";
            return new R(this);
        }
        public R buildFail(){
            this.code=-1;
            this.msg="failed";
            return new R(this);
        }

        public Builder setData(T data){
            this.data=data;
            return this;
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "code=" + code +
                    ", msg='" + msg + '\'' +
                    ", data=" + data +
                    '}';
        }
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public T getData() {
        return data;
    }

    @Override
    public String toString() {
        return "R{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
