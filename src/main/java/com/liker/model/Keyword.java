package com.liker.model;

/**
 * @ClassName: Keyword
 * @Description: 检索关键字
 * @author xupengtao
 * @date 2018年1月16日 下午1:50:40
 *
 */
public class Keyword {

    /**
     * 字段名
     */
    private String fieldName;

    /**
     * 字段值
     */
    private String fieldValue;

    /**
     * 是否分词
     */
    private boolean tokenized = true;

    public Keyword() {

    }

    public Keyword(String fieldName, String fieldValue) {
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
     * @return the tokenized
     */
    public boolean isTokenized() {
        return tokenized;
    }

    /**
     * setter method
     * 
     * @param tokenized
     *            the tokenized to set
     */
    public void setTokenized(boolean tokenized) {
        this.tokenized = tokenized;
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
        return "Field [fieldName=" + fieldName + ", fieldValue=" + fieldValue + ", tokenized=" + tokenized + "]";
    }

}
