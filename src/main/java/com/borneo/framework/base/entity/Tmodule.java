/*
 * Powered By [rapid-framework] Web Site: http://www.rapid-framework.org.cn Google Code: http://code.google.com/p/rapid-framework/ Since 2008 - 2013
 */

package com.borneo.framework.base.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.CollectionUtils;

import com.borneo.framework.common.utils.DateUtils;

@Entity
@Table(name = "t_module")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Tmodule implements BaseEntity {

    //alias
    public static final String TABLE_ALIAS = "Tmodule";
    public static final String ALIAS_TID = "tid";
    public static final String ALIAS_TMODULE_CODE = "tmoduleCode";
    public static final String ALIAS_TMODULE_NAME = "tmoduleName";
    public static final String ALIAS_CREATED_DATE = "createdDate";
    public static final String ALIAS_MODIFIED_DATE = "modifiedDate";
    public static final String ALIAS_CREATED_BY = "createdBy";
    public static final String ALIAS_MODIFIED_BY = "modifiedBy";
    public static final String ALIAS_HIDDEN_FLAG = "hiddenFlag";
   
    //date formats
    public static final String FORMAT_CREATED_DATE = DATE_TIME_FORMAT;
    public static final String FORMAT_MODIFIED_DATE = DATE_TIME_FORMAT;
    private static final long serialVersionUID = 5454155825314635342L;

    //可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
    //columns START
    /**
     * tid db_column: t_id
     */

    private java.lang.Long tid;
    /**
     * tmoduleCode db_column: t_module_code
     */
    @NotBlank
    @Length(max = 50)
    private java.lang.String tmoduleCode;
    /**
     * tmoduleName db_column: t_module_name
     */
    @NotBlank
    @Length(max = 50)
    private java.lang.String tmoduleName;
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
    /**
     * tcreatePermission
     */
    
    //Added by Jacky Y. 13.Aug.15.1429
    //hidden_flag
    private Boolean hiddenFlag;
    
    @Transient
    private java.lang.Boolean tcreatePermission;
    /**
     * treadPermission
     */
    @Transient
    private java.lang.Boolean treadPermission;
    /**
     * teditPermission
     */
    @Transient
    private java.lang.Boolean teditPermission;
    /**
     * tdeletePermission
     */
    @Transient
    private java.lang.Boolean tdeletePermission;
    private Set troleModules = new HashSet(0);

    @Transient
    public boolean isDeleteEnable() {
        if (CollectionUtils.isEmpty(troleModules)) {
            return true;
        } else {
            return false;
        }
    }

    public void setHiddenFlag(Boolean hiddenFlag) {
    	if (hiddenFlag==null) hiddenFlag = new Boolean(false);
		this.hiddenFlag = hiddenFlag;
	}

	public Tmodule() {
    }

    public Tmodule(java.lang.Long tid) {
        this.tid = tid;
    }

    //    @SequenceGenerator(name = "t_module_t_id_seq", sequenceName = "t_module_t_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "t_id", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
    public java.lang.Long getTid() {
        return this.tid;
    }

    public void setTid(java.lang.Long value) {
        this.tid = value;
    }

    @Column(name = "t_module_code", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public java.lang.String getTmoduleCode() {
        return this.tmoduleCode;
    }

    public void setTmoduleCode(java.lang.String value) {
        this.tmoduleCode = value;
    }

    @Column(name = "t_module_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public java.lang.String getTmoduleName() {
        return this.tmoduleName;
    }

    public void setTmoduleName(java.lang.String value) {
        this.tmoduleName = value;
    }

    @OneToMany(cascade = { CascadeType.MERGE }, fetch = FetchType.LAZY, mappedBy = "tmodule")
    public Set<TroleModule> getTroleModules() {
        return troleModules;
    }

    public void setTroleModules(Set<TroleModule> troleModule) {
        this.troleModules = troleModule;
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

    //----Added by Jacky Y. 13.Aug.15.1431-----
    @Column(name = "hidden_flag", unique = false, nullable = true, insertable = true, updatable = true, length = 1)
    public Boolean getHiddenFlag() {
		return hiddenFlag;
	}
    //-----------------------------------------

    @Transient
    public java.lang.Boolean getTcreatePermission() {
        return tcreatePermission;
    }

    @Transient
    public void setTcreatePermission(java.lang.Boolean tcreatePermission) {
        this.tcreatePermission = tcreatePermission;
    }

    @Transient
    public java.lang.Boolean getTreadPermission() {
        return treadPermission;
    }

    @Transient
    public void setTreadPermission(java.lang.Boolean treadPermission) {
        this.treadPermission = treadPermission;
    }

    @Transient
    public java.lang.Boolean getTeditPermission() {
        return teditPermission;
    }

    @Transient
    public void setTeditPermission(java.lang.Boolean teditPermission) {
        this.teditPermission = teditPermission;
    }

    @Transient
    public java.lang.Boolean getTdeletePermission() {
        return tdeletePermission;
    }

    @Transient
    public void setTdeletePermission(java.lang.Boolean tdeletePermission) {
        this.tdeletePermission = tdeletePermission;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Tid", getTid()).append("TmoduleCode", getTmoduleCode()).append("TmoduleName", getTmoduleName()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getTid()).toHashCode();
    }

    //    public boolean canDelete() {
    //        if (userRoles != null && !userRoleCrossOwners.isEmpty()) {
    //            return false;
    //        }
    //        return true;
    //    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Tmodule)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Tmodule other = (Tmodule) obj;
        return new EqualsBuilder().append(getTid(), other.getTid()).isEquals();
    }
}
