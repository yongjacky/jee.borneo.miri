package com.borneo.framework.json.mo;

import java.util.ArrayList;
import java.util.List;

public class ServiceMessage extends MoliscMessage {

    private static final long serialVersionUID = 4393692069686285017L;

    private List<MoliscMessage> parameters = new ArrayList<MoliscMessage>();
    //private String resultCode;
    private String serMethod;

    public List<MoliscMessage> getParameters() {
        return parameters;
    }

    public void setParameters(List<MoliscMessage> parameters) {
        this.parameters = parameters;
    }

    public String getSerMethod() {
        return serMethod;
    }

    public void setSerMethod(String serMethod) {
        this.serMethod = serMethod;
    }

    /*
     * public String getResultCode() { return resultCode; } public void setResultCode(String resultCode) { this.resultCode = resultCode; }
     */

    /*
     * @SuppressWarnings("unchecked") public void toObject(JSONObject jsonObj){ JSONArray array = jsonObj.getJSONArray("parameters"); try{ if(array!=null || array.size()>0){ getParameters().clear(); List objList = (List) JSONArray.toCollection(array, JSONObject.class); for(int i=0;i<objList.size();i++){ JSONObject jsonobj = (JSONObject)objList.get(i); if(!jsonobj.isEmpty()){ String clazz2 = jsonobj.getString("clazz"); String className = MobileObjectConfigUtil.getFullClazzName(clazz2); MoliscMessage mMsg = (MoliscMessage)JSONObject.toBean(jsonobj, Class.forName(className)); mMsg.toObject(jsonobj); getParameters().add(mMsg); } } } }catch(Exception e){ //e.printStackTrace(); log.error(e.getMessage()); } }
     */

}
