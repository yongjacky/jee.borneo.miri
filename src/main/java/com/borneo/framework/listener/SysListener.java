package com.borneo.framework.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpServlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.borneo.framework.common.utils.Constant;

/**
 * @author peter.yuan
 */
public class SysListener extends HttpServlet implements ServletContextListener {

    /**
     *
     */
    private static final long serialVersionUID = 1034314109251279542L;

    private final Logger log = LoggerFactory.getLogger(SysListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        log.info("ContextInitialized START...");
        System.setProperty("java.awt.headless", "true");
        System.setProperty("jmagick.systemclassloader", "no");

        String rootpath = sce.getServletContext().getRealPath("/");

        if (rootpath != null) {
            rootpath = rootpath.replaceAll("\\\\", "/");
        } else {
            rootpath = "/";
        }
        if (!rootpath.endsWith("/")) {
            rootpath = rootpath + "/";
        }
        Constant.SYSTEM_ROOT_PATH = rootpath;
        Constant.CONTENT_PATH = sce.getServletContext().getContextPath();
        log.info("ContextInitialized END...");

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        //
    }

}
