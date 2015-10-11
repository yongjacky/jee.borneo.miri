package com.borneo.framework.base.vo;

/**
 * @author peter.yuan
 */
public class LabelValueVO {

    public LabelValueVO(String label, Object value) {
        this.label = label;
        this.value = value;
    }

    public LabelValueVO() {

    }

    public String label;

    public Object value;

    /**
     * @return the label
     */
    public String getLabel() {
        return label;
    }

    /**
     * @param label the label to set
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * @return the value
     */
    public Object getValue() {
        return value;
    }

    /**
     * @param value the value to set
     */
    public void setValue(Object value) {
        this.value = value;
    }

}
