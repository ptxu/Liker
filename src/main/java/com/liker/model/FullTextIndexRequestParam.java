package com.liker.model;

import java.util.Arrays;

/**
 * @ClassName: FullTextIndexRequestParam
 * @Description: FullTextIndexRequestParam
 * @author xupengtao
 * @date 2018年1月16日 下午4:32:42
 *
 */
public class FullTextIndexRequestParam extends Pager {

    /**
     * 关键字
     */
    private Keyword[] keywords;

    /**
     * 需要返回的字段
     */
    private String[] responseFieldNames;

    /**
     * getter method
     * 
     * @return the keywords
     */
    public Keyword[] getKeywords() {
        return keywords;
    }

    /**
     * setter method
     * 
     * @param keywords
     *            the keywords to set
     */
    public void setKeywords(Keyword[] keywords) {
        this.keywords = keywords;
    }

    /**
     * getter method
     * 
     * @return the responseFieldNames
     */
    public String[] getResponseFieldNames() {
        return responseFieldNames;
    }

    /**
     * setter method
     * 
     * @param responseFieldNames
     *            the responseFieldNames to set
     */
    public void setResponseFieldNames(String[] responseFieldNames) {
        this.responseFieldNames = responseFieldNames;
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
        return "FullTextIndexRequestParam [keywords=" + Arrays.toString(keywords) + ", responseFieldNames=" + Arrays.toString(responseFieldNames) + ", getCurrentPage()=" + getCurrentPage()
                + ", getPageSize()=" + getPageSize() + "]";
    }

}
