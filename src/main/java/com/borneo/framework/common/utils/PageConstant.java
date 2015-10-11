package com.borneo.framework.common.utils;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.i18n.SessionLocaleResolver;

/**
 * @author peter.yuan
 */
public class PageConstant {

    public static final String PAGE_LIST_OBJECT_NAME = "page_list_object";

    public static final String PAGE_LIST_TOTAL_ROWS_NAME = "page_list_total_rows";

    public static final String PAGE_TABLE_ID_NAME = "listTable";

    public static final String PAGE_TABLE_TOTAL_ROWS_NAME = "listTable_totalRows";

    public static final String PAGE_TABLE_VIEW_NAME = "freemarker";

    //public static final String PAGE_TABLE_IMAGE_PATH = "/extremeDemo/improve/table/*.gif";

    public static String PAGINATION_OBJECT = "PaginationObject";

    //set the same name with PageQueryForm
    public static final String PAGE_QUERY_FORM_NAME = "PageQueryForm";

    public static final String PAGE_FORM_EXPORT_FILE_TYPE_NAME = "listTable_ev";

    public static final String PAGE_FORM_CURRENT_PAGE_NAME = "listTable_p";

    public static final String PAGE_FORM_SEARCH_KEY_NAME = "searchKey";

    public static final String PAGE_FORM_SEARCH_KEY_TYPE = "searchType";

    //sort field
    public static final String PAGE_FORM_SORT_FIELD_NAME = "sortField";

    public static final String PAGE_FORM_SORT_TYPE_NAME = "sortType";

    public static int MAX_RESULTS_PER_PAGE = 200;

    public static final String PAGE_DISPLAY_NUM_KEY = "recordNum";

    public static final String SEARCH_KEY_1 = "searchKey1";

    public static final String SEARCH_KEY_2 = "searchKey2";

    public static final String SEARCH_KEY_3 = "searchKey3";

    public static final String SEARCH_KEY_4 = "searchKey4";

    public static final String SEARCH_KEY_5 = "searchKey5";

    public static final String SEARCH_KEY_6 = "searchKey6";

    public static final String SEARCH_KEY_7 = "searchKey7";

    public static final String SEARCH_KEY_8 = "searchKey8";

    public static final String SEARCH_KEY_9 = "searchKey9";

    public static final String SEARCH_KEY_10 = "searchKey10";

    public static final String SEARCH_KEY_11 = "searchKey11";

    public static final String SEARCH_KEY_12 = "searchKey12";

    public static final String SEARCH_KEY_13 = "searchKey13";

    public static final String SEARCH_KEY_14 = "searchKey14";

    /**
     * return current locale,if session locale is empty,get it from request
     * @param request
     * @return
     */
    public static String getLocale(HttpServletRequest request) {
        //        String localeCode = request.getLocale().toString();
        String localeCode = "en_US";
        Object locale = request.getSession().getAttribute(SessionLocaleResolver.LOCALE_SESSION_ATTRIBUTE_NAME);//just for spring mvc
        if (locale != null) {
            localeCode = ((Locale) locale).toString();
        }
        return localeCode;
    }

}
