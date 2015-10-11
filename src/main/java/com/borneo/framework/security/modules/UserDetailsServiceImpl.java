package com.borneo.framework.security.modules;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Resource;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.borneo.framework.base.dao.TappUserDAO;
import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.base.entity.Trole;
import com.borneo.framework.base.entity.TroleModule;
import com.borneo.framework.common.utils.EConstant;
//#TOFIX_IN_TECHSTORE//import com.borneo.rapidkl.service.SystemLogService;
//#TOFIX_IN_TECHSTORE//import com.borneo.rapidkl.utils.ProjectConstant;

/**
 * @author peter.yuan
 */
@Transactional(readOnly = false)
@Service("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    @Resource
    private TappUserDAO tappUserDAO;

  //#TOFIX_IN_TECHSTORE
    //@Resource
    //private SystemLogService systemLogService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        TappUser user = tappUserDAO.loadMemberByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("the user '" + username + "' dont't exist");
        }

        Set<GrantedAuthority> grantedAuths = obtainGrantedAuthorities(user);
        user.setAuthorities(grantedAuths);
        if (user.getStatus()) {
            user.setCredentialsNonExpired(true);
            user.setAccountNonExpired(true);
            user.setAccountNonLocked(true);
            user.setEnabled(true);
          //#TOFIX_IN_TECHSTORE// systemLogService.saveSystemLog(ProjectConstant.ModuleName.Login.name(), ProjectConstant.Status.Successful.name(), " Username:" + username, ProjectConstant.Terminal.CMS.name(), ProjectConstant.Terminal.CMS.name(), username);
        }
        return user;
    }

    private Set<GrantedAuthority> obtainGrantedAuthorities(TappUser user) {
        Set<GrantedAuthority> authSet = new HashSet<GrantedAuthority>();
        for (Trole role : user.getTroles()) {
            authSet.add(new GrantedAuthorityImpl("ROLE_" + role.getTroleCode().toUpperCase()));
            for (TroleModule tm : role.getTroleModules()) {
                String p = "ROLE_" + tm.getTmodule().getTmoduleCode().toUpperCase() + "_";
                if (tm.getTcreatePermission()) {
                    authSet.add(new GrantedAuthorityImpl(p + EConstant.AuthSuffix.CREATE));
                }
                if (tm.getTreadPermission()) {
                    authSet.add(new GrantedAuthorityImpl(p + EConstant.AuthSuffix.READ));
                }
                if (tm.getTeditPermission()) {
                    authSet.add(new GrantedAuthorityImpl(p + EConstant.AuthSuffix.EDIT));
                }
                if (tm.getTdeletePermission()) {
                    authSet.add(new GrantedAuthorityImpl(p + EConstant.AuthSuffix.DELETE));
                }
            }
        }
        return authSet;
    }
}
