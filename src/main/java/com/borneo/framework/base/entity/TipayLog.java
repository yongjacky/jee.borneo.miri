package com.borneo.framework.base.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

@Entity
@Table(name = "t_ipay_log")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TipayLog implements BaseEntity {

    public static final String ALIAS_ID = "id";
    public static final String ALIAS_ORDER_ID = "orderId";
    public static final String ALIAS_MERCHANT_CODE = "merchantCode";
    public static final String ALIAS_MERCHANT_KEY = "merchantKey";
    public static final String ALIAS_PAYMENT_ID = "paymentId";
    public static final String ALIAS_AMOUNT = "amount";
    public static final String ALIAS_CURRENCY = "currency";
    public static final String ALIAS_STATUS = "status";
    public static final String ALIAS_REMARKS = "remarks";
    public static final String ALIAS_IPAY_TRANS_ID = "ipayTransId";
    public static final String ALIAS_AUTH_CODE = "authCode";
    public static final String ALIAS_ERROR_DESC = "errorDesc";
    public static final String ALIAS_SIGNATURE = "signature";
    public static final String ALIAS_CREATED_DATE = "createdDate";
    public static final String ALIAS_MODIFIED_DATE = "modifiedDate";
    
    @NotNull
    private Long id;
    @NotNull
    private String orderId;
    @NotNull
    private String merchantCode;
    @NotNull
    private String merchantKey;
    private Integer paymentId;
    @NotNull
    private Double amount;
    @NotNull
    private String currency;
    private String ipayStatus;   // 1 (sucess) or 0 (fail) . Used when backend post from ipay.
    
    private String status;    // status coming from mobile end OR status update from re-query function
    private String remarks;
    private String ipayTransId;
    private String authCode;
    private String errorDesc;
    @NotNull
    private String signature;
    @NotNull
    private Date createdDate;
    private Date modifiedDate;
    
    public TipayLog() {
        
    }
    
    public TipayLog(Long id) {
        this.id = id;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "order_id", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    @Column(name = "merchant_code", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
    public String getMerchantCode() {
        return merchantCode;
    }

    public void setMerchantCode(String merchantCode) {
        this.merchantCode = merchantCode;
    }

    @Column(name = "merchant_key", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
    public String getMerchantKey() {
        return merchantKey;
    }

    public void setMerchantKey(String merchantKey) {
        this.merchantKey = merchantKey;
    }

    @Column(name = "payment_id", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Integer paymentId) {
        this.paymentId = paymentId;
    }
    
    @Column(name = "amount", unique = false, nullable = false, insertable = true, updatable = true, length = 22)
    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    @Column(name = "currency", unique = false, nullable = false, insertable = true, updatable = true, length = 5)
    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    @Column(name = "status", unique = false, nullable = true, insertable = true, updatable = true, length = 10)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "remarks", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    @Column(name = "ipay_trans_id", unique = false, nullable = true, insertable = true, updatable = true, length = 30)
    public String getIpayTransId() {
        return ipayTransId;
    }

    public void setIpayTransId(String ipayTransId) {
        this.ipayTransId = ipayTransId;
    }

    @Column(name = "auth_code", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    @Column(name = "error_desc", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getErrorDesc() {
        return errorDesc;
    }

    public void setErrorDesc(String errorDesc) {
        this.errorDesc = errorDesc;
    }

    @Column(name = "signature", unique = false, nullable = true, insertable = true, updatable = true, length = 100)
    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    @Column(name = "created_date", unique = false, nullable = false, insertable = true, updatable = true, length = 29)
    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    @Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 29)
    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    @Column(name = "ipay_status", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public String getIpayStatus() {
        return ipayStatus;
    }

    public void setIpayStatus(String ipayStatus) {
        this.ipayStatus = ipayStatus;
    }
    
   
}
