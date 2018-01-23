package com.liker.model;

import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: Document
 * @Description: Document
 * @author xupengtao
 * @date 2018年1月16日 下午1:54:06
 *
 */
public class Document {

    /**
     * 字段
     */
    private List<Field> fields;

    /**
     * getter method
     * 
     * @return the fields
     */
    public List<Field> getFields() {
        return fields;
    }

    /**
     * setter method
     * 
     * @param fields
     *            the fields to set
     */
    public void setFields(List<Field> fields) {
        this.fields = fields;
    }

    public void addField(Field field) {
        if (fields != null) {
            fields.add(field);
        }
        else {
            fields = new ArrayList<>();
            fields.add(field);
        }
    }
}
