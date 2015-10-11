/*
 * Powered By [rapid-framework] Web Site: http://www.rapid-framework.org.cn Google Code: http://code.google.com/p/rapid-framework/ Since 2008 - 2013
 */

package com.borneo.framework.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;

import com.borneo.framework.common.utils.DateUtils;

@Entity
@Table(name = "system_config")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class SystemConfig implements BaseEntity {

    //alias
    public static final String TABLE_ALIAS = "SystemConfig";
    public static final String ALIAS_CONVERSIONRATE = "conversionRate";
    public static final String ALIAS_ID = "id";
    public static final String ALIAS_CREATED_DATE = "createdDate";
    public static final String ALIAS_MODIFIED_DATE = "modifiedDate";
    public static final String ALIAS_CREATED_BY = "createdBy";
    public static final String ALIAS_MODIFIED_BY = "modifiedBy";
    //date formats
    public static final String FORMAT_CREATED_DATE = DATE_FORMAT;
    public static final String FORMAT_MODIFIED_DATE = DATE_FORMAT;
    private static final long serialVersionUID = 5454155825314635342L;

    //可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
    //columns START

    private java.lang.Long id;
    /**
     * createdDate db_column: created_date
     */

    private java.util.Date createdDate;
    /**
     * modifiedDate db_column: modified_date
     */

    private java.util.Date modifiedDate;
    /**
     * createdBy db_column: created_by
     */
    @Length(max = 50)
    private java.lang.String createdBy;
    /**
     * modifiedBy db_column: modified_by
     */
    @Length(max = 50)
    private java.lang.String modifiedBy;

    private Double conversionRate;

    public SystemConfig() {
    }

    public SystemConfig(java.lang.Long id) {
        this.id = id;
    }

    //    @SequenceGenerator(name = "system_config_id_seq", sequenceName = "system_config_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long value) {
        this.id = value;
    }


    @Transient
    public boolean isDeleteEnable() {
        return false;
    }

    @Transient
    public String getCreatedDateString() {
        return DateUtils.dateToStr(getCreatedDate(), FORMAT_CREATED_DATE);
    }

    public void setCreatedDateString(String value) {
        setCreatedDate(DateUtils.string2Date(value, FORMAT_CREATED_DATE));
    }

    @Column(name = "created_date", unique = false, nullable = true, insertable = true, updatable = true, length = 29)
    public java.util.Date getCreatedDate() {
        return this.createdDate;
    }

    public void setCreatedDate(java.util.Date value) {
        this.createdDate = value;
    }

    @Transient
    public String getModifiedDateString() {
        return DateUtils.dateToStr(getModifiedDate(), FORMAT_MODIFIED_DATE);
    }

    public void setModifiedDateString(String value) {
        setModifiedDate(DateUtils.string2Date(value, FORMAT_MODIFIED_DATE));
    }

    @Column(name = "modified_date", unique = false, nullable = true, insertable = true, updatable = true, length = 29)
    public java.util.Date getModifiedDate() {
        return this.modifiedDate;
    }

    public void setModifiedDate(java.util.Date value) {
        this.modifiedDate = value;
    }

    @Column(name = "created_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public java.lang.String getCreatedBy() {
        return this.createdBy;
    }

    public void setCreatedBy(java.lang.String value) {
        this.createdBy = value;
    }

    @Column(name = "modified_by", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public java.lang.String getModifiedBy() {
        return this.modifiedBy;
    }

    public void setModifiedBy(java.lang.String value) {
        this.modifiedBy = value;
    }

    
    @Column(name = "conversion_rate", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public Double getConversionRate() {
        return conversionRate;
    }

    public void setConversionRate(Double conversionRate) {
        this.conversionRate = conversionRate;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof SystemConfig)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        SystemConfig other = (SystemConfig) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }
}
