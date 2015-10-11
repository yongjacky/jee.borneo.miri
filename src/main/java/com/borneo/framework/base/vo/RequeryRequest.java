package com.borneo.framework.base.vo;

public class RequeryRequest {

    String merchantCode;
    String refNo;
    String amount;
    
    public RequeryRequest(String merchantCode, String refNo, String amount) {
        this.merchantCode = merchantCode;
        this.refNo = refNo;
        this.amount = amount;
    }

    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    public String getRefNo() {
        return refNo;
    }

    public void setRefNo(String refNo) {
        this.refNo = refNo;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "MerchantCode="+merchantCode+"&RefNo="+refNo+"&Amount="+amount;
    }
    
    
    
}
