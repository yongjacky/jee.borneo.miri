/**
 *
 */
package com.borneo.framework.common.utils;

import org.springframework.security.core.context.SecurityContextHolder;

import com.borneo.framework.base.entity.TappUser;
import com.borneo.framework.base.service.TappUserService;

/**
 * @author peter.yuan
 */
public class UserInfoUtil {

    public static TappUserService tappUserService;

    /**
     * get current user via spring security framework
     * @return
     */
    public static TappUser getCurrentUser() {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            return null;
        }
        if (SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof TappUser) {
            TappUser userInfo = (TappUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            userInfo = tappUserService.loadUserByUsername(userInfo.getUsername());
            return userInfo;
        }
        return null;
    }
}
