package com.borneo.framework.base.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.FileAttachDAO;

@Service(FileAttachService.BEAN_NAME)
public class FileAttachServiceImpl extends BaseServiceImpl implements FileAttachService {

    @Resource
    private FileAttachDAO fileAttachDAO;

}
