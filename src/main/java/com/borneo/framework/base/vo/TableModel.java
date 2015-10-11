/**
 *
 */
package com.borneo.framework.base.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * @author peter.yuan
 */
public class TableModel {

    public static final String PROP_SORTFIELD = "sortField";

    public static final String PROP_SORTTYPE = "sortType";

    private String sortField;

    private String sortType;

    private Map<String, String> searchKeyMap = new HashMap<String, String>();

    private Map<String, String> matchTypeMap = new HashMap<String, String>();

    private Map<String, String> dataTypeMap = new HashMap<String, String>();

    /**
     * @return the sortField
     */
    public String getSortField() {
        return sortField;
    }

    /**
     * @param sortField the sortField to set
     */
    public void setSortField(String sortField) {
        this.sortField = sortField;
    }

    /**
     * @return the sortType
     */
    public String getSortType() {
        return sortType;
    }

    /**
     * @param sortType the sortType to set
     */
    public void setSortType(String sortType) {
        this.sortType = sortType;
    }

    public boolean isSortActive() {
        if (org.apache.commons.lang.StringUtils.isNotBlank(sortField) && !"default".equals(sortType)) {
            return true;
        }
        return false;
    }

    /**
     * @return the searchKeyMap
     */
    public Map<String, String> getSearchKeyMap() {
        return searchKeyMap;
    }

    /**
     * @param searchKeyMap the searchKeyMap to set
     */
    public void setSearchKeyMap(Map<String, String> searchKeyMap) {
        this.searchKeyMap = searchKeyMap;
    }

    /**
     * @return the matchTypeMap
     */
    public Map<String, String> getMatchTypeMap() {
        return matchTypeMap;
    }

    /**
     * @param matchTypeMap the matchTypeMap to set
     */
    public void setMatchTypeMap(Map<String, String> matchTypeMap) {
        this.matchTypeMap = matchTypeMap;
    }

    /**
     * @return the dataTypeMap
     */
    public Map<String, String> getDataTypeMap() {
        return dataTypeMap;
    }

    /**
     * @param dataTypeMap the dataTypeMap to set
     */
    public void setDataTypeMap(Map<String, String> dataTypeMap) {
        this.dataTypeMap = dataTypeMap;
    }

}
