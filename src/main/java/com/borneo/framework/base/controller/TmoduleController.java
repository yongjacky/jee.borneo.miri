package com.borneo.framework.base.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.borneo.framework.base.entity.Tmodule;
import com.borneo.framework.base.service.SpringUtil;
import com.borneo.framework.base.service.TmoduleService;
import com.borneo.framework.common.utils.Constant;
import com.borneo.framework.common.utils.DateUtils;
import com.borneo.framework.common.utils.PageConstant;
import com.borneo.framework.common.utils.PaginationSupport;

@Controller
@RequestMapping("/admin/tmodule")
@SessionAttributes({ TmoduleController.MODEL_NAME })
public class TmoduleController extends BaseController {

    public static final String MODEL_NAME = "tmodule";
    private static final String LIST_ACTION = "redirect:/admin/tmodule";
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private TmoduleService tmoduleService;

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        Tmodule tmodule = tmoduleService.get(Tmodule.class, id);
        model.addAttribute(tmodule);
        return "/admin/tmodule/tmodule-edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable Long id, Model model, HttpServletRequest request) {
        Tmodule tmodule = tmoduleService.get(Tmodule.class, id);
        if (tmodule.isDeleteEnable()) {
            tmoduleService.delete(tmodule);
        } else {
            model.addAttribute(Constant.ERROR_MESSAGE_NAME, SpringUtil.getMessage("other.data.is.based.on.this"));
        }
        return LIST_ACTION;
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public String view(@PathVariable Long id, Model model) {
        Tmodule tmodule = tmoduleService.get(Tmodule.class, id);
        model.addAttribute(tmodule);
        return "/admin/tmodule/tmodule-view";
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Tmodule());
        return "/admin/tmodule/tmodule-add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Tmodule tmodule) throws Exception {
        if (tmodule.getTid() == null) {
            tmodule.setCreatedBy(getCurrentUserInfo().getUsername());
            tmodule.setCreatedDate(DateUtils.getToday());
        } else {
            tmodule.setModifiedBy(getCurrentUserInfo().getUsername());
            tmodule.setModifiedDate(DateUtils.getToday());
        }
        tmoduleService.merge(tmodule);
        return LIST_ACTION;
    }

    @RequestMapping
    public String list(HttpServletRequest request) {
        int recordNum = PageConstant.MAX_RESULTS_PER_PAGE;
        int sindex = currentPageNum(request) * recordNum;
        DetachedCriteria dc = DetachedCriteria.forClass(Tmodule.class);
        dc.add(Restrictions.eq(Tmodule.ALIAS_HIDDEN_FLAG, new Boolean(false)));//Added by Jacky Y. 13.Aug.15.1434
        PaginationSupport page = tmoduleService.findPageByCriteria(buildTableModel(request), dc, recordNum, sindex);
        addPageToReq(request, page);
        request.setAttribute(PageConstant.PAGE_DISPLAY_NUM_KEY, recordNum);
        String errorMessage = request.getParameter("errorMessage");
        if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage)) {
            request.setAttribute(Constant.ERROR_MESSAGE_NAME, errorMessage);
        }
        return "/admin/tmodule/tmodule";
    }
}
