package com.borneo.framework.base.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
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
import com.borneo.framework.base.entity.Trole;
import com.borneo.framework.base.entity.TroleModule;
import com.borneo.framework.base.service.SpringUtil;
import com.borneo.framework.base.service.TmoduleService;
import com.borneo.framework.base.service.TroleModuleService;
import com.borneo.framework.base.service.TroleService;
import com.borneo.framework.common.utils.Constant;
import com.borneo.framework.common.utils.DateUtils;
import com.borneo.framework.common.utils.PageConstant;
import com.borneo.framework.common.utils.PaginationSupport;

@Controller
@RequestMapping("/admin/trole")
@SessionAttributes({ TroleController.MODEL_NAME })
public class TroleController extends BaseController {

    public static final String MODEL_NAME = "trole";
    private static final String LIST_ACTION = "redirect:/admin/trole";
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private TroleService troleService;
    @Resource
    private TmoduleService tmoduleService;
    @Resource
    private TroleModuleService troleModuleService;

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        Trole trole = troleService.get(Trole.class, id);
        model.addAttribute(trole);
        List<Tmodule> list = tmoduleService.findAll(Tmodule.class);
        List<TroleModule> lisTroleModules = troleModuleService.getTroleModules(trole.getTid());

        Map<Long, Tmodule> map = new HashMap<Long, Tmodule>(0);
        for (Tmodule tmodule : list) {
            map.put(tmodule.getTid(), tmodule);
        }
        for (TroleModule troleModule : lisTroleModules) {
            Tmodule tmodule = map.get(troleModule.getTmodule().getTid());
            tmodule.setTcreatePermission(troleModule.getTcreatePermission());
            tmodule.setTreadPermission(troleModule.getTreadPermission());
            tmodule.setTeditPermission(troleModule.getTeditPermission());
            tmodule.setTdeletePermission(troleModule.getTdeletePermission());
        }
        model.addAttribute("tmoduleList", list);
        return "/admin/trole/trole-edit";
    }

    @RequestMapping(value = "/{id}/delete", method = RequestMethod.GET)
    public String delete(@PathVariable Long id, Model model) {
        Trole trole = troleService.get(Trole.class, id);
        if (trole.isDeleteEnable()) {
            troleService.delete(trole);
        } else {
            model.addAttribute(Constant.ERROR_MESSAGE_NAME, SpringUtil.getMessage("other.data.is.based.on.this"));
        }
        return LIST_ACTION;
    }

    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        model.addAttribute(new Trole());
        List<Tmodule> list = tmoduleService.findAll(Tmodule.class);
        model.addAttribute("tmoduleList", list);
        return "/admin/trole/trole-add";
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(Trole trole, HttpServletRequest req) throws Exception {
    	//-------Modiied by Jacky Y. 13.Aug.15.1451-----------
        //List<Tmodule> listTmodules = tmoduleService.findAll(Tmodule.class);
    	List<Tmodule> listTmodules = tmoduleService.getTmodules();
    	//----------------------------------------------------
        Map<Long, TroleModule> mapTroleModules = new HashMap<Long, TroleModule>(0);
        if (trole.getTid() != null) {
            List<TroleModule> list = troleModuleService.getTroleModules(trole.getTid());
            if (CollectionUtils.isNotEmpty(list)) {
                for (TroleModule troleModule : list) {
                    mapTroleModules.put(troleModule.getTmodule().getTid(), troleModule);
                }
            }
        }
        Set<TroleModule> listToSaveAndUpdate = new HashSet<TroleModule>();
        for (Tmodule tmodule : listTmodules) {
            String createString = req.getParameter("create_" + tmodule.getTid());
            String readString = req.getParameter("read_" + tmodule.getTid());
            String editString = req.getParameter("edit_" + tmodule.getTid());
            String deleteString = req.getParameter("delete_" + tmodule.getTid());

            Boolean create = false;
            Boolean read = false;
            Boolean edit = false;
            Boolean delete = false;

            Boolean flag = false;
            if (org.apache.commons.lang.StringUtils.isNotBlank(createString)) {
                create = true;
                flag = true;
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(readString)) {
                read = true;
                flag = true;
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(editString)) {
                edit = true;
                flag = true;
            }
            if (org.apache.commons.lang.StringUtils.isNotBlank(deleteString)) {
                delete = true;
                flag = true;
            }
            if (flag) {
                TroleModule troleModule = null;
                if (mapTroleModules != null) {
                    troleModule = mapTroleModules.get(tmodule.getTid());
                }
                if (troleModule != null) {
                    listToSaveAndUpdate.add(troleModule);
                } else {
                    troleModule = new TroleModule();
                    troleModule.setTmodule(tmodule);
                    listToSaveAndUpdate.add(troleModule);
                }
                troleModule.setTcreatePermission(create);
                troleModule.setTreadPermission(read);
                troleModule.setTeditPermission(edit);
                troleModule.setTdeletePermission(delete);
            }
        }
        if (trole.getTid() == null) {
            trole.setCreatedBy(getCurrentUserInfo().getUsername());
            trole.setCreatedDate(DateUtils.getToday());
        } else {
            trole.setModifiedBy(getCurrentUserInfo().getUsername());
            trole.setModifiedDate(DateUtils.getToday());
        }
        if (!listToSaveAndUpdate.isEmpty()) {
            troleService.merge(trole);
            for (TroleModule troleModule : listToSaveAndUpdate) {
                troleModule.setTrole(trole);
            }
            troleService.saveOrUpdateAll(listToSaveAndUpdate);
        }
        trole.setTroleModules(listToSaveAndUpdate);
        troleService.merge(trole);
        return LIST_ACTION;
    }

    @RequestMapping
    public String list(HttpServletRequest request) {
        int recordNum = PageConstant.MAX_RESULTS_PER_PAGE;
        int sindex = currentPageNum(request) * recordNum;
        DetachedCriteria dc = DetachedCriteria.forClass(Trole.class);
        dc.add(Restrictions.eq(Trole.ALIAS_HIDDEN_FLAG, new Boolean(false)));//Added by Jacky Y. 13.Aug.15.1439
        PaginationSupport page = troleService.findPageByCriteria(buildTableModel(request), dc, recordNum, sindex);
        addPageToReq(request, page);
        request.setAttribute(PageConstant.PAGE_DISPLAY_NUM_KEY, recordNum);
        String errorMessage = request.getParameter("errorMessage");
        if (org.apache.commons.lang.StringUtils.isNotBlank(errorMessage)) {
            request.setAttribute(Constant.ERROR_MESSAGE_NAME, errorMessage);
        }
        return "/admin/trole/trole";
    }
}
