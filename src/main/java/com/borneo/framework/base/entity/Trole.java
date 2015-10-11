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
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.util.CollectionUtils;

import com.borneo.framework.common.utils.DateUtils;

@Entity
@Table(name = "t_role")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class Trole implements BaseEntity {

    //alias
    public static final String TABLE_ALIAS = "Trole";
    public static final String ALIAS_TID = "tid";
    public static final String ALIAS_TROLE_NAME = "troleName";
    public static final String ALIAS_TROLE_CODE = "troleCode";
    public static final String ALIAS_TROLE_TYPE = "troleType";
    public static final String ALIAS_CREATED_DATE = "createdDate";
    public static final String ALIAS_MODIFIED_DATE = "modifiedDate";
    public static final String ALIAS_CREATED_BY = "createdBy";
    public static final String ALIAS_MODIFIED_BY = "modifiedBy";
    public static final String ALIAS_HIDDEN_FLAG = "hiddenFlag";
    //date formats
    public static final String FORMAT_CREATED_DATE = DATE_FORMAT;
    public static final String FORMAT_MODIFIED_DATE = DATE_FORMAT;
    private static final long serialVersionUID = 5454155825314635342L;

    //可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
    //columns START
    /**
     * tid db_column: t_id
     */

    private java.lang.Long tid;
    /**
     * troleName db_column: t_role_name
     */
    @NotBlank
    @Length(max = 50)
    private java.lang.String troleName;
    /**
     * troleCode db_column: t_role_code
     */
    @NotBlank
    @Length(max = 50)
    private java.lang.String troleCode;
    /**
     * troleType db_column: t_role_type
     */
    @NotBlank
    @Length(max = 50)
    private java.lang.String troleType;
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
    
    //Added by Jacky Y. 13.Aug.15.1429
    //hidden_flag
    private Boolean hiddenFlag;
    
    @Transient
    private Boolean checked;
    private Set tappUsers = new HashSet(0);
    private Set troleModules = new HashSet(0);

    public void setHiddenFlag(Boolean hiddenFlag) {
    	if (hiddenFlag==null) hiddenFlag=new Boolean(false);
		this.hiddenFlag = hiddenFlag;
	}

	public Trole() {
    }

    public Trole(java.lang.Long tid) {
        this.tid = tid;
    }

    //    @SequenceGenerator(name = "t_role_t_id_seq", sequenceName = "t_role_t_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "t_id", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
    public java.lang.Long getTid() {
        return this.tid;
    }

    public void setTid(java.lang.Long value) {
        this.tid = value;
    }

    @Column(name = "t_role_name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public java.lang.String getTroleName() {
        return this.troleName;
    }

    public void setTroleName(java.lang.String value) {
        this.troleName = value;
    }

    @Column(name = "t_role_code", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public java.lang.String getTroleCode() {
        return this.troleCode;
    }

    public void setTroleCode(java.lang.String value) {
        this.troleCode = value;
    }

    @Column(name = "t_role_type", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public java.lang.String getTroleType() {
        return this.troleType;
    }

    public void setTroleType(java.lang.String value) {
        this.troleType = value;
    }

    //----Added by Jacky Y. 13.Aug.15.1431-----
    @Column(name = "hidden_flag", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
    public Boolean getHiddenFlag() {
		return hiddenFlag;
	}
    //-----------------------------------------
    
    @NotFound(action = NotFoundAction.IGNORE)
    @ManyToMany(cascade = { CascadeType.REFRESH }, mappedBy = "troles", fetch = FetchType.LAZY)
    public Set<TappUser> getTappUsers() {
        return tappUsers;
    }

    public void setTappUsers(Set<TappUser> tappUsers) {
        this.tappUsers = tappUsers;
    }

    @NotFound(action = NotFoundAction.IGNORE)
    @OneToMany(mappedBy = "trole", fetch = FetchType.LAZY)
    @Cascade({ org.hibernate.annotations.CascadeType.SAVE_UPDATE, org.hibernate.annotations.CascadeType.DELETE_ORPHAN })
    public Set<TroleModule> getTroleModules() {
        return troleModules;
    }

    public void setTroleModules(Set<TroleModule> troleModule) {
        this.troleModules = troleModule;
    }

    @Transient
    public boolean isDeleteEnable() {
        if (CollectionUtils.isEmpty(troleModules) && CollectionUtils.isEmpty(tappUsers)) {
            return true;
        } else {
            return false;
        }
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

    @Transient
    public Boolean getChecked() {
        return checked;
    }

    @Transient
    public void setChecked(Boolean checked) {
        this.checked = checked;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Tid", getTid()).append("TroleName", getTroleName()).append("TroleCode", getTroleCode()).append("TroleType", getTroleType()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getTid()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Trole)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        Trole other = (Trole) obj;
        return new EqualsBuilder().append(getTid(), other.getTid()).isEquals();
    }
}
