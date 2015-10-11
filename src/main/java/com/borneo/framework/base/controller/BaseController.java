package com.borneo.framework.base.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;

import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.base.service.BaseService;
import com.borneo.framework.base.vo.TableModel;
import com.borneo.framework.common.utils.PageConstant;
import com.borneo.framework.common.utils.PaginationSupport;
import com.borneo.framework.common.utils.StringUtils;
import com.borneo.framework.common.utils.UploadFileUtil;
import com.borneo.framework.common.utils.UserInfoUtil;
import com.borneo.framework.spring.mvc.tag.SearchValueBindControllerTag;

/**
 * @author peter.yuan
 */
public class BaseController {

    protected static final String REDIRECT_INDEX = "redirect:/index";
    protected static final String ERROR_MESSAGE = "errorMessage";
    protected static final String MESSAGE = "message";
    @Resource
    protected BaseService baseService;

    protected int currentPageNum(HttpServletRequest request) {
        int pagenum = Integer.valueOf(StringUtils.getParameter(request, PageConstant.PAGE_FORM_CURRENT_PAGE_NAME, "0"));
        return pagenum == 0 ? 0 : pagenum - 1;
    }

    /**
     * check param pageNo is big than database pagination
     * @param request
     * @param total
     * @param recordNum
     * @return
     */
    protected boolean checkPageNo(HttpServletRequest request, int total, int recordNum) {
        String paramPageNo = request.getParameter(PageConstant.PAGE_FORM_CURRENT_PAGE_NAME);
        if (org.apache.commons.lang.StringUtils.isNotBlank(paramPageNo)) {
            float pageNo = Float.parseFloat(paramPageNo);
            float pageTotalNo = (float) total / (float) recordNum;
            if (((pageTotalNo <= 1) && (pageNo > 1)) || ((pageTotalNo > 1) && (pageNo > (pageTotalNo + 1)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * eg: total 5 records, every page display 2 records. when delete the third record, need navigation to page2
     * @param request
     * @return
     */
    protected String guessCorrectPageUrl(HttpServletRequest request) {
        String queryString = request.getQueryString();
        Integer paramPageNo = Integer.valueOf(request.getParameter(PageConstant.PAGE_FORM_CURRENT_PAGE_NAME));
        String newUrl = org.apache.commons.lang.StringUtils.replace(queryString, PageConstant.PAGE_FORM_CURRENT_PAGE_NAME + "=" + paramPageNo, PageConstant.PAGE_FORM_CURRENT_PAGE_NAME + "=" + (paramPageNo - 1));
        return request.getRequestURI().replace(request.getContextPath(), "") + "?" + newUrl;
    }

    protected void addPageToReq(HttpServletRequest request, PaginationSupport page) {
        request.setAttribute(PageConstant.PAGINATION_OBJECT, page);
        request.setAttribute(PageConstant.PAGE_LIST_OBJECT_NAME, page.getItems());
        request.setAttribute(PageConstant.PAGE_TABLE_TOTAL_ROWS_NAME, page.getTotalCount());
    }

    protected TableModel buildTableModel(HttpServletRequest request) {
        String sortField = StringUtils.getParameter(request, PageConstant.PAGE_FORM_SORT_FIELD_NAME, "");
        String sortType = StringUtils.getParameter(request, PageConstant.PAGE_FORM_SORT_TYPE_NAME, "");
        TableModel tableModel = new TableModel();
        tableModel.setSortField(sortField);
        tableModel.setSortType(sortType);

        Map<String, String> matchTypeMap = new HashMap<String, String>();
        Map<String, String> searchKeyMap = new HashMap<String, String>();
        Map<String, String> dataTypeMap = new HashMap<String, String>();

        Map keyMap = request.getParameterMap();

        Iterator iter = keyMap.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry entry = (Map.Entry) iter.next();
            Object key = entry.getKey();
            String val = request.getParameter(key.toString());
            String matchType = request.getParameter(SearchValueBindControllerTag.MATCH_TYPE_PREFIX + key);
            String dataType = request.getParameter(SearchValueBindControllerTag.DATA_TYPE_PREFIX + key);
            if (org.apache.commons.lang.StringUtils.isNotBlank(matchType) && org.apache.commons.lang.StringUtils.isNotBlank(val) && !key.toString().contains(SearchValueBindControllerTag.MATCH_TYPE_PREFIX) && !key.toString().contains(SearchValueBindControllerTag.DATA_TYPE_PREFIX)) {
                searchKeyMap.put(key.toString(), val);
                matchTypeMap.put(key.toString(), matchType);
                if (org.apache.commons.lang.StringUtils.isNotBlank(dataType)) {
                    dataTypeMap.put(key.toString(), dataType);
                }
            }
        }
        tableModel.setMatchTypeMap(matchTypeMap);
        tableModel.setSearchKeyMap(searchKeyMap);
        tableModel.setDataTypeMap(dataTypeMap);
        request.setAttribute(PageConstant.PAGE_FORM_SORT_FIELD_NAME, sortField);//need store in jsp page
        request.setAttribute(PageConstant.PAGE_FORM_SORT_TYPE_NAME, sortType);
        return tableModel;
    }

    public String getMessage(String key, Object... params) {
        return baseService.getMessage(key, params);
    }

    public String getMessage(String key, Object[] params, Locale locale) {
        return baseService.getMessage(key, params, locale);
    }

    public TappUser getCurrentUserInfo() {
        return UserInfoUtil.getCurrentUser();
    }

    public ResponseEntity<byte[]> viewImage(File file, HttpServletResponse response) throws IOException, Exception {

        FileInputStream is = new FileInputStream(file);

        byte[] fileByte = IOUtils.toByteArray(is);

        String mediaType = UploadFileUtil.getFileExtension(file.getAbsolutePath());
        mediaType = mediaType.toLowerCase();
        HttpHeaders headers = new HttpHeaders();
        if (mediaType.equalsIgnoreCase(".jpg") || mediaType.equalsIgnoreCase(".jpeg")) {
            headers.setContentType(MediaType.IMAGE_JPEG);
        } else if (mediaType.equalsIgnoreCase(".png")) {
            headers.setContentType(MediaType.IMAGE_PNG);
        } else if (mediaType.equalsIgnoreCase(".gif")) {
            headers.setContentType(MediaType.IMAGE_GIF);
        }

        headers.setContentLength(fileByte.length);

        return new ResponseEntity<byte[]>(fileByte, headers, HttpStatus.OK);

    }
}
