package com.borneo.framework.base.service;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.borneo.framework.base.dao.TroleDAO;
import com.borneo.framework.base.entity.Trole;
import com.borneo.framework.common.exception.CustomGenericException;
import com.borneo.framework.common.utils.EConstant;

@Service(TroleService.BEAN_NAME)
public class TroleServiceImpl extends BaseServiceImpl implements TroleService {

    @Resource
    private TroleDAO troleDAO;

    @Override
    public Trole getRoleByCode(String code) {
        return troleDAO.getRoleByCode(code);
    }
    
    public List<Trole> getTroles(){
    	List<Trole> list = new ArrayList<Trole>();
    	
    	try{
    		list = troleDAO.getTroles();
    	}catch (CustomGenericException cge) {
            throw cge;
        }catch (Exception ex) {
            ex.printStackTrace();
            throw new CustomGenericException(EConstant.CustomErrorCode.UnknowError.toString(), getMessage("error.is.unknown", ex.getMessage()));
        }
    	return list;
    }
}
