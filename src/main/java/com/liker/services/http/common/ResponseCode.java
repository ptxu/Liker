package com.liker.services.http.common;

import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName: ResponseCode
 * @Description: 返回状态码
 * @author xupengtao
 * @date 2018年1月12日 下午2:18:13
 *
 */
public enum ResponseCode {

    /**
     * 未知错误
     */
    UnknowErr("unknown error", 500),

    /**
     * 成功
     */
    Success("success", 200);

    /**
     * 枚举名
     */
    private String desc;
    /**
     * 枚举索引
     */
    private int value;

    ResponseCode(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    /**
     * enum value map
     */
    private static Map<String, ResponseCode> VALUE_MAP;

    static {
        VALUE_MAP = new HashMap<String, ResponseCode>();
        for (ResponseCode item : values()) {
            VALUE_MAP.put(item.name(), item);
        }

    }

    /**
     * @Title: getTypeByValue
     * @Description: 根据枚举索引获取枚举
     * @param value
     *            枚举索引
     * @return CameraType
     */
    public static ResponseCode getTypeByValue(int value) {
        for (ResponseCode c : ResponseCode.values()) {
            if (c.getValue() == value) {
                return c;
            }
        }
        return UnknowErr;
    }

    /**
     * @Title: getTypeByName
     * @Description: 根据名字获取枚举
     * @param name
     *            name
     * @return CameraType
     */
    public static ResponseCode getTypeByName(String name) {
        ResponseCode result = UnknowErr;
        if (VALUE_MAP.containsKey(name)) {
            result = VALUE_MAP.get(name);
        }

        return result;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}