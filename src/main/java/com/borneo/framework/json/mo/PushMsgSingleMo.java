package com.borneo.framework.json.mo;

public class PushMsgSingleMo extends MoliscMessage {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5291165813570489488L;
    private String appId;
    private String platform;
    private String msg;
    private String receiverUid;
    private String lockey;

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

    public String getReceiverUid() {
        return receiverUid;
    }

    public void setReceiverUid(String receiverUid) {
        this.receiverUid = receiverUid;
    }

    public String getLockey() {
        return lockey;
    }

    public void setLockey(String lockey) {
        this.lockey = lockey;
    }

}
