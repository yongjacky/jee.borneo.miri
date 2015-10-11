package com.borneo.framework.web.service.mo;

import java.util.Date;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

/**
 * User: seven_shi@qq.com Date: 13-8-23 Time: 上午11:12
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BorneoWSMo")
public class BorneoWSMo {
    private Integer id;
    private Date date;
    private String username;
    private Boolean status;

    public BorneoWSMo() {
    }

    public BorneoWSMo(Boolean status, Integer id, Date date, String username) {
        this.status = status;
        this.id = id;
        this.date = date;
        this.username = username;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }
}
