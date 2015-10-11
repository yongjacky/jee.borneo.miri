package com.borneo.framework.base.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.collections.CollectionUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.base.entity.Trole;
import com.borneo.framework.base.service.TappUserService;
import com.borneo.framework.base.service.TroleService;
import com.borneo.framework.common.utils.DateUtils;
import com.borneo.framework.common.utils.EConstant;
import com.borneo.framework.common.utils.PageConstant;
import com.borneo.framework.common.utils.PaginationSupport;
//#TOFIX_IN_TECHSTORE import com.borneo.rapidkl.service.SystemLogService;
//#TOFIX_IN_TECHSTORE import com.borneo.rapidkl.utils.ProjectConstant;

@Controller
@RequestMapping("/admin/tappUser")
@SessionAttributes({ TappUserController.MODEL_NAME })
public class TappUserController extends BaseController {

    public static final String MODEL_NAME = "tappUser";
    private static final String LIST_ACTION = "redirect:/admin/tappUser";
    protected final Logger log = LoggerFactory.getLogger(getClass());
    @Resource
    private TappUserService tappUserService;
    @Resource
    private TroleService troleService;
    
    ////#TOFIX_IN_TECHSTORE
    //@Resource
    //private SystemLogService systemLogService;

    @RequestMapping(value = "/{id}/edit", method = RequestMethod.GET)
    public String update(@PathVariable Long id, Model model) {
        TappUser tappUser = tappUserService.get(TappUser.class, id);
        List<Trole> list = troleService.getTroles();//.findAll(Trole.class);
        Map<Long, Trole> map = new HashMap<Long, Trole>(0);

        for (Trole trole : list) {
            map.put(trole.getTid(), trole);
        }
        if (CollectionUtils.isNotEmpty(tappUser.getTroles())) {
            for (Trole trole : tappUser.getTroles()) {
                map.get(trole.getTid()).setChecked(true);
            }
        }
        //model.addAttribute("userTypes", EConstant.getUserTpyes());
        model.addAttribute(tappUser);
        model.addAttribute("troles", list);
        model.addAttribute("userTypes", EConstant.getUserTpyes());
        return "/admin/tappUser/tappUser-edit";
    }

    @RequestMapping(value = "/edit", method = RequestMethod.GET)
    public String editProfile(Model model) {
        TappUser tappUser = (TappUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        model.addAttribute(tappUser);
        model.addAttribute("userTypes", EConstant.getUserTpyes());
        return "/admin/tappUser/tappUser-edit";
    }

    @RequestMapping(value = "/{id}/changePass", method = RequestMethod.GET)
    public String changePass(@PathVariable Long id, Model model) {
        TappUser tappUser = tappUserService.get(TappUser.class, id);
        model.addAttribute(tappUser);
        return "/admin/tappUser/tappUser-changepass";
    }

    @RequestMapping(value = "/savePass", method = RequestMethod.POST)
    public String savePass(TappUser tappUser, HttpServletRequest req) {
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        if (org.apache.commons.lang.StringUtils.isNotBlank(tappUser.getNewPassword())) {
            tappUser.setPassword(md5.encodePassword(tappUser.getNewPassword(), null));
        }
      //#TOFIX_IN_TECHSTORE systemLogService.saveSystemLog(ProjectConstant.ModuleName.CMCUserManagement.name(), ProjectConstant.Action.ChangePassword.name(), " Username:" + tappUser.getUsername(), ProjectConstant.Terminal.CMS.name(), ProjectConstant.Terminal.CMS.name(), getCurrentUserInfo().getUsername());
        tappUserService.merge(tappUser);
        return LIST_ACTION;
    }

    @RequestMapping(value = "/add", method = RequestMethod.GET)
    public String add(Model model) {
        TappUser tappUser = new TappUser();
        model.addAttribute("userTypes", EConstant.getUserTpyes());
        model.addAttribute("troles", troleService.getTroles());
        model.addAttribute(tappUser);
        return "/admin/tappUser/tappUser-add";
    }

    @RequestMapping(value = "/check", method = RequestMethod.GET)
    @ResponseBody
    public boolean check(@RequestParam String username, Long id) {
        return !tappUserService.checkDuplicateField(TappUser.class, TappUser.ALIAS_ID, id, TappUser.ALIAS_USERNAME, username);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(TappUser tappUser, HttpServletRequest req) throws Exception {
        if (tappUser.getId() == null) {
            tappUser.setCreatedBy(getCurrentUserInfo().getUsername());
            tappUser.setCreatedDate(DateUtils.getToday());
         //#TOFIX_IN_TECHSTORE   systemLogService.saveSystemLog(ProjectConstant.ModuleName.CMCUserManagement.name(), ProjectConstant.Action.Added.name(), " Username:" + tappUser.getUsername(), ProjectConstant.Terminal.CMS.name(), ProjectConstant.Terminal.CMS.name(), getCurrentUserInfo().getUsername());
            tappUser.setUserType( EConstant.UserType.SystemUserAcct.ordinal());
        } else {
            tappUser.setModifiedBy(getCurrentUserInfo().getUsername());
            tappUser.setModifiedDate(DateUtils.getToday());
          //#TOFIX_IN_TECHSTORE   systemLogService.saveSystemLog(ProjectConstant.ModuleName.CMCUserManagement.name(), ProjectConstant.Action.Updated.name(), " Username:" + tappUser.getUsername(), ProjectConstant.Terminal.CMS.name(), ProjectConstant.Terminal.CMS.name(), getCurrentUserInfo().getUsername());
        }
        
        List<Trole> list = troleService.getTroles();//.findAll(Trole.class);
        Map<Long, Trole> map = new HashMap<Long, Trole>(0);

        for (Trole trole : list) {
            map.put(trole.getTid(), trole);
        }
        List<Trole> set = new ArrayList<Trole>(0);
        String[] idsArr = req.getParameterValues("roles_id");
        if ((idsArr != null) && (idsArr.length > 0)) {
            for (String element : idsArr) {
                Trole trole = map.get(Long.parseLong(element));
                set.add(trole);
            }
        }
        tappUser.setTroles(set);
        Md5PasswordEncoder md5 = new Md5PasswordEncoder();
        if (org.apache.commons.lang.StringUtils.isNotBlank(tappUser.getNewPassword())) {
            tappUser.setPassword(md5.encodePassword(tappUser.getNewPassword(), null));
        }
        tappUserService.merge(tappUser);
        return LIST_ACTION;
    }

    @RequestMapping
    public String list(HttpServletRequest request) {
        int recordNum = PageConstant.MAX_RESULTS_PER_PAGE;
        int sindex = currentPageNum(request) * recordNum;
        DetachedCriteria dc = DetachedCriteria.forClass(TappUser.class);
        dc.add(Restrictions.eq(TappUser.ALIAS_USER_TYPE, EConstant.UserType.SystemUserAcct.ordinal()));
        dc.add(Restrictions.eq(TappUser.ALIAS_HIDDEN_FLAG, new Boolean(false)));//--Added by Jacky Y. 13.Aug.15.1455------
        PaginationSupport page = tappUserService.findPageByCriteria(buildTableModel(request), dc, recordNum, sindex);
        addPageToReq(request, page);
        request.setAttribute(PageConstant.PAGE_DISPLAY_NUM_KEY, recordNum);
        return "/admin/tappUser/tappUser";
    }
}
