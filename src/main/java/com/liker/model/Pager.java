package com.liker.model;

/**
 * @ClassName: Pager
 * @Description: Pager
 * @author xupengtao
 * @date 2018年1月16日 下午4:37:33
 *
 */
public class Pager {

    /**
     * 页码
     */
    private int currentPage = 1;

    /**
     * 每页的结果数
     */
    private int pageSize = 1000;

    /**
     * getter method
     * 
     * @return the currentPage
     */
    public int getCurrentPage() {
        return currentPage;
    }

    /**
     * setter method
     * 
     * @param currentPage
     *            the currentPage to set
     */
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    /**
     * getter method
     * 
     * @return the pageSize
     */
    public int getPageSize() {
        return pageSize;
    }

    /**
     * setter method
     * 
     * @param pageSize
     *            the pageSize to set
     */
    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

}
