package com.liker.model;

/**
 * @ClassName: IndexTask
 * @Description: IndexTask
 * @author xupengtao
 * @date 2018年1月18日 下午5:20:04
 *
 */
public class IndexTask {

    private TaskType type;

    private Document doc;

    private Field targetField;

    /**
     * getter method
     * 
     * @return the type
     */
    public TaskType getType() {
        return type;
    }

    /**
     * setter method
     * 
     * @param type
     *            the type to set
     */
    public void setType(TaskType type) {
        this.type = type;
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

    /**
     * getter method
     * 
     * @return the targetFields
     */
    public Field getTargetField() {
        return targetField;
    }

    /**
     * setter method
     * 
     * @param targetFields
     *            the targetFields to set
     */
    public void setTargetField(Field targetField) {
        this.targetField = targetField;
    }

}
