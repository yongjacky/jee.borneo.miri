package com.borneo.framework.spring.mvc.tag;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import javax.servlet.jsp.tagext.TryCatchFinally;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.borneo.framework.common.utils.BeanUtils;

/**
 * @author peter.yuan
 */
public class SearchValueBindControllerTag extends TagSupport implements TryCatchFinally {

    public static final String MATCH_TYPE_PREFIX = "matchType_";

    public static final String DATA_TYPE_PREFIX = "dataType_";

    public static final String ALAIS_SPLIT_KEY = "_";

    public static final String DATA_TYPE_STRING = "String";

    public static final String DATA_TYPE_DATE = "Date";

    public static final String DATA_TYPE_TIMESTAMP = "Timestamp";

    public static final String DATA_TYPE_INTEGER = "Integer";

    public static final String DATA_TYPE_LONG = "Long";

    public static final String DATA_TYPE_DOUBLE = "Double";

    public static final String DATA_TYPE_FLOAT = "Float";

    public static final String DATA_TYPE_BIGDECIMAL = "Bigdecimal";

    public static final String MATCH_TYPE_LIKE = "like";

    public static final String MATCH_TYPE_EQ = "eq";

    public static final String MATCH_TYPE_LEFTLIKE = "leftlike";

    public static final String MATCH_TYPE_RIGHTLIKE = "rightlike";

    public static final String MATCH_TYPE_GT = "gt";

    public static final String MATCH_TYPE_GE = "ge";

    public static final String MATCH_TYPE_LT = "lt";

    public static final String MATCH_TYPE_LE = "le";

    private static final long serialVersionUID = 886751509392252338L;

    private String id;

    private String name;

    private String matchType;

    private String size;

    private String maxlength;

    private String alt;

    private String dataType;

    private String items;

    private String itemLabel;

    private String itemValue;

    private String optionLabels;

    private String optionValues;

    private String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    /**
     * @return the id
     */
    @Override
    public String getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    @Override
    public void setId(String id) {
        this.id = id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the value of the '<code>size</code>' attribute. May be a runtime expression.
     */
    public void setSize(String size) {
        this.size = size;
    }

    /**
     * Get the value of the '<code>size</code>' attribute.
     */
    protected String getSize() {
        return this.size;
    }

    /**
     * Set the value of the '<code>maxlength</code>' attribute. May be a runtime expression.
     */
    public void setMaxlength(String maxlength) {
        this.maxlength = maxlength;
    }

    /**
     * Get the value of the '<code>maxlength</code>' attribute.
     */
    protected String getMaxlength() {
        return this.maxlength;
    }

    /**
     * Set the value of the '<code>alt</code>' attribute. May be a runtime expression.
     */
    public void setAlt(String alt) {
        this.alt = alt;
    }

    /**
     * Get the value of the '<code>alt</code>' attribute.
     */
    protected String getAlt() {
        return this.alt;
    }

    /**
     * @return the matchType
     */
    public String getMatchType() {
        return matchType;
    }

    /**
     * @param matchType the matchType to set
     */
    public void setMatchType(String matchType) {
        this.matchType = matchType;
    }

    /**
     * @return the dataType
     */
    public String getDataType() {
        return dataType;
    }

    /**
     * @param dataType the dataType to set
     */
    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    /**
     * @return the items
     */
    public String getItems() {
        return items;
    }

    /**
     * @param items the items to set
     */
    public void setItems(String items) {
        this.items = items;
    }

    /**
     * @return the itemLabel
     */
    public String getItemLabel() {
        return itemLabel;
    }

    /**
     * @param itemLabel the itemLabel to set
     */
    public void setItemLabel(String itemLabel) {
        this.itemLabel = itemLabel;
    }

    /**
     * @return the itemValue
     */
    public String getItemValue() {
        return itemValue;
    }

    /**
     * @param itemValue the itemValue to set
     */
    public void setItemValue(String itemValue) {
        this.itemValue = itemValue;
    }

    /**
     * @return the optionLabels
     */
    public String getOptionLabels() {
        return optionLabels;
    }

    /**
     * @param optionLabels the optionLabels to set
     */
    public void setOptionLabels(String optionLabels) {
        this.optionLabels = optionLabels;
    }

    /**
     * @return the optionValues
     */
    public String getOptionValues() {
        return optionValues;
    }

    /**
     * @param optionValues the optionValues to set
     */
    public void setOptionValues(String optionValues) {
        this.optionValues = optionValues;
    }

    /**
     * Logger available to subclasses
     */
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public final int doStartTag() throws JspException {
        return EVAL_BODY_INCLUDE;
    }

    @Override
    public int doEndTag() throws JspException {
        if (StringUtils.isBlank(matchType)) {
            matchType = MATCH_TYPE_LIKE;
        }
        if (StringUtils.isNotBlank(alias)) {
            name = name + SearchValueBindControllerTag.ALAIS_SPLIT_KEY + alias;
        }
        String value = pageContext.getRequest().getParameter(name);
        if (value == null) {
            value = pageContext.findAttribute(name) == null ? null : (String) pageContext.findAttribute(name);
        }

        StringBuffer sb = new StringBuffer();
        if (StringUtils.isNotBlank(items)) {
            sb.append("<select ");
            if (StringUtils.isNotBlank(id)) {
                sb.append(" id=\"").append(id).append("\"");
            }
            if (StringUtils.isNotBlank(name)) {
                sb.append(" name=\"").append(name).append("\"");
            }
            sb.append(">");
            if (StringUtils.isNotBlank(optionLabels)) {
                String[] optionLabel = optionLabels.split(",");
                String[] optionValue = optionValues.split(",");
                for (int i = 0; i < optionLabel.length; i++) {
                    sb.append("<option value=\"").append(optionValue[i]).append("\"");
                    if (StringUtils.isNotBlank(value) && value.equalsIgnoreCase(optionValue[i])) {
                        sb.append(" selected=\"selected\"");
                    }
                    sb.append(">");
                    sb.append(optionLabel[i]);
                    sb.append("</option>");
                }
            }
            Object objectConllection = pageContext.findAttribute(items);
            if (objectConllection instanceof Collection) {
                Collection collection = (Collection) objectConllection;
                Iterator iterator = collection.iterator();
                while (iterator.hasNext()) {
                    Object object = iterator.next();
                    Object thisLabel = null;
                    Object thisValue = null;
                    try {
                        thisLabel = BeanUtils.getPropertyValue(object, itemLabel);
                        thisValue = BeanUtils.getPropertyValue(object, itemValue);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if ((thisLabel != null) && (thisValue != null)) {
                        sb.append("<option value=\"").append(thisValue).append("\"");
                        if (StringUtils.isNotBlank(value) && value.equalsIgnoreCase(thisValue.toString())) {
                            sb.append(" selected=\"selected\"");
                        }
                        sb.append(">");
                        sb.append(thisLabel.toString());
                        sb.append("</option>");
                    }

                }
            }

            sb.append("</select>");
        } else {
            sb.append("<input type=\"text\"");
            if (StringUtils.isNotBlank(id)) {
                sb.append(" id=\"").append(id).append("\"");
            }
            if (StringUtils.isNotBlank(name)) {
                sb.append(" name=\"").append(name).append("\"");
            }

            if (StringUtils.isNotBlank(value)) {
                sb.append(" value=\"").append(value).append("\"");
            }
            if (StringUtils.isNotBlank(dataType)) {
                if (DATA_TYPE_TIMESTAMP.equalsIgnoreCase(dataType) || DATA_TYPE_DATE.equalsIgnoreCase(dataType)) {
                    sb.append(" class=\"Wdate\" onclick=\"WdatePicker()\"");
                } else if (!DATA_TYPE_STRING.equalsIgnoreCase(dataType)) {
                    sb.append(" class=\"number\"");
                }
            } else {
                dataType = DATA_TYPE_STRING;
            }
            sb.append("/>");
        }
        sb.append("<input type=\"hidden\" name=\"").append(MATCH_TYPE_PREFIX).append(name).append("\" value=\"").append(matchType).append("\"/>");
        if (StringUtils.isNotBlank(dataType)) {
            sb.append("<input type=\"hidden\" name=\"").append(DATA_TYPE_PREFIX).append(name).append("\" value=\"").append(dataType).append("\"/>");
        }
        JspWriter out = pageContext.getOut();
        try {
            out.print(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return EVAL_PAGE;
    }

    @Override
    public void doCatch(Throwable throwable) throws Throwable {
        throw throwable;
    }

    @Override
    public void doFinally() {
        //
    }

}
