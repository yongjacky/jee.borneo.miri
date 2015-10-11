package com.borneo.framework.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.TmoduleDAO;
import com.borneo.framework.base.entity.Tmodule;
import com.borneo.framework.common.exception.CustomGenericException;
import com.borneo.framework.common.utils.EConstant;

@Service(TmoduleService.BEAN_NAME)
public class TmoduleServiceImpl extends BaseServiceImpl implements TmoduleService {

    @Resource
    private TmoduleDAO tmoduleDAO;

    public List<Tmodule> getTmodules(){
    	List<Tmodule> list = new ArrayList<Tmodule>();
    	
    	try{
    		list = this.tmoduleDAO.getTmodules();
    	}catch (CustomGenericException cge) {
            throw cge;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new CustomGenericException(EConstant.CustomErrorCode.UnknowError.toString(), getMessage("error.is.unknown", ex.getMessage()));
        }
    	return list;
    }
}
