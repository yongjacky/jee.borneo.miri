package com.borneo.framework.json.mo;

import java.io.Serializable;

public abstract class MoliscMessage implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5086986087741155478L;
    private String clazz;

    public String getClazz() {
        if (clazz == null) {
            return this.getClass().getSimpleName();
        } else {
            return clazz;
        }
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    /*
     * public JSONObject wrapObject(){ JSONObject jsonobj = JSONObject.fromObject(this); return jsonobj; } public abstract void toObject(JSONObject jsonObj);
     */
}
