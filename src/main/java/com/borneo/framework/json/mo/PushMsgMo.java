package com.borneo.framework.json.mo;

public class PushMsgMo extends MoliscMessage {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5796539807642807636L;
    //private String clazz;
    private String appId;
    private String platform;
    private String msg;

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
