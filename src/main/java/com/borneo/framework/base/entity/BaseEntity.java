package com.borneo.framework.base.entity;

import java.io.Serializable;

/**
 * @author badqiu
 */
public interface BaseEntity extends Serializable {

    String DATE_FORMAT = "dd-MM-yyyy";
    String TIME_FORMAT = "HH:mm:ss";
    String SHORT_TIME_AMPM_FORMAT = "h:mm a";
    String SHORT_TIME_FORMAT = "HH:mm";
    String DATE_TIME_FORMAT = "dd-MM-yyyy HH:mm:ss";
    String TIMESTAMP_FORMAT = "dd-MM-yyyy HH:mm:ss.S";

}
