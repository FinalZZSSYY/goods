package com.zsy.goods.common.utils;

import com.zsy.goods.common.RRException;

import java.util.HashMap;

/**
 * @author LiangYongjie
 * @date 2019-02-24
 */
public class Result extends HashMap<String, Object> {

    private Result(int code, String message) {
        super.put("code", code);
        super.put("msg", message);
    }
    public static Result ok(String message) {
        return new Result(200, message);
    }
    public static Result ok() {
        return new Result(200, "success");
    }

    public static Result error(int code, String message) {
        return new Result(code, message);
    }

    public static Result error(String message) {
        return Result.error(500, message);
    }

    @Override
    public Result put(String key, Object value) {
        super.put(key, value);
        return this;
    }
    public static Result error(RRException e) {

        return error(e.getCode(), e.getMessage());
    }

}