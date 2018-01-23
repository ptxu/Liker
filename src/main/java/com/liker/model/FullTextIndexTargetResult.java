package com.liker.model;

/**
 * @ClassName: FullTextIndexTargetResult
 * @Description: 全文索引命中结果
 * @author xupengtao
 * @date 2018年1月16日 下午4:42:32
 *
 */
public class FullTextIndexTargetResult {

    /**
     * 分数
     */
    private float score;

    /**
     * 文档
     */
    private Document doc;

    /**
     * getter method
     * 
     * @return the score
     */
    public float getScore() {
        return score;
    }

    /**
     * setter method
     * 
     * @param score
     *            the score to set
     */
    public void setScore(float score) {
        this.score = score;
    }

    /**
     * getter method
     * 
     * @return the doc
     */
    public Document getDoc() {
        return doc;
    }

    /**
     * setter method
     * 
     * @param doc
     *            the doc to set
     */
    public void setDoc(Document doc) {
        this.doc = doc;
    }

}
