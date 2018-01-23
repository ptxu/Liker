package com.liker.model;

/**
 * @ClassName: Field
 * @Description: Field
 * @author xupengtao
 * @date 2018年1月16日 下午1:50:40
 *
 */
public class Field {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 是否索引：如果索引则不存储，不索引则存储
     */
    private boolean indexed = true;

    public Field() {

    }

    public Field(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    /**
     * getter method
     * 
     * @return the fieldName
     */
    public String getFieldName() {
        return fieldName;
    }

    /**
     * setter method
     * 
     * @param fieldName
     *            the fieldName to set
     */
    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    /**
     * getter method
     * 
     * @return the fieldValue
     */
    public String getFieldValue() {
        return fieldValue;
    }

    /**
     * setter method
     * 
     * @param fieldValue
     *            the fieldValue to set
     */
    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    /**
     * getter method
     * 
     * @return the indexed
     */
    public boolean isIndexed() {
        return indexed;
    }

    /**
     * setter method
     * 
     * @param indexed
     *            the indexed to set
     */
    public void setIndexed(boolean indexed) {
        this.indexed = indexed;
    }

    /*
     * <p>Title: toString</p> <p>Description: </p>
     * 
     * @return
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "Field [fieldName=" + fieldName + ", fieldValue=" + fieldValue + ", indexed=" + indexed + "]";
    }

}
