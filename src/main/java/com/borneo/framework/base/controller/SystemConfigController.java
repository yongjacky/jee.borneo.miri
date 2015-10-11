package com.borneo.framework.base.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.borneo.framework.base.entity.SystemConfig;
import com.borneo.framework.base.service.SystemConfigService;
import com.borneo.framework.common.utils.DateUtils;
import com.borneo.framework.common.utils.PageConstant;
import com.borneo.framework.common.utils.PaginationSupport;

@Controller
@RequestMapping("/admin/systemConfig")
@SessionAttributes({ SystemConfigController.MODEL_NAME })
public class SystemConfigController extends BaseController {

    public static final String MODEL_NAME = "systemConfig";
    private static final String LIST_ACTION = "redirect:/admin/systemConfig";
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private SystemConfigService systemConfigService;

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String update(Model model) {
        SystemConfig systemConfig;
        List<SystemConfig> systemConfigs = systemConfigService.findAll(SystemConfig.class);
        if (systemConfigs.isEmpty()) {
            systemConfig = new SystemConfig();
        } else {
            systemConfig = systemConfigs.get(0);
        }
        model.addAttribute(systemConfig);
        return "/admin/systemConfig/systemConfig-edit";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(SystemConfig systemConfig) throws Exception {
        if (systemConfig.getId() == null) {
            systemConfig.setCreatedBy(getCurrentUserInfo().getUsername());
            systemConfig.setCreatedDate(DateUtils.getToday());
        } else {
            systemConfig.setModifiedBy(getCurrentUserInfo().getUsername());
            systemConfig.setModifiedDate(DateUtils.getToday());
        }
        systemConfigService.merge(systemConfig);
        return LIST_ACTION;
    }

    @RequestMapping
    public String list(HttpServletRequest request) {
        int recordNum = PageConstant.MAX_RESULTS_PER_PAGE;
        int sindex = currentPageNum(request) * recordNum;
        DetachedCriteria dc = DetachedCriteria.forClass(SystemConfig.class);
        PaginationSupport page = systemConfigService.findPageByCriteria(buildTableModel(request), dc, recordNum, sindex);
        addPageToReq(request, page);
        request.setAttribute(PageConstant.PAGE_DISPLAY_NUM_KEY, recordNum);
        return "/admin/systemConfig/systemConfig";
    }
}
