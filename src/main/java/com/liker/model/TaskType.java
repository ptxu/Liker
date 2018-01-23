package com.liker.model;

/**
 * @ClassName: TaskType
 * @Description: TaskType
 * @author xupengtao
 * @date 2018年1月18日 下午5:40:01
 *
 */
public enum TaskType {
    Add, Update, Delete;

    public static TaskType findByValue(int value) {
        switch (value) {
            case 1:
                return Add;
            case 0:
                return Update;
            case -1:
                return Delete;
            default:
                return null;
        }
    }
}
