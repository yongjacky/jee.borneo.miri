/*
 * Powered By [rapid-framework] Web Site: http://www.rapid-framework.org.cn Google Code: http://code.google.com/p/rapid-framework/ Since 2008 - 2013
 */

package com.borneo.framework.base.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

@Entity
@Table(name = "t_role_module")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class TroleModule implements BaseEntity {

    //alias
    public static final String TABLE_ALIAS = "TroleModule";
    public static final String ALIAS_TID = "tid";
    public static final String ALIAS_TROLE = "trole";
    public static final String ALIAS_TMODULE = "tmodule";
    public static final String ALIAS_TCREATE_PERMISSION = "tcreatePermission";
    public static final String ALIAS_TREAD_PERMISSION = "treadPermission";
    public static final String ALIAS_TEDIT_PERMISSION = "teditPermission";
    public static final String ALIAS_TDELETE_PERMISSION = "tdeletePermission";
    public static final String ALIAS_HIDDEN_FLAG = "hiddenFlag";
    
    private static final long serialVersionUID = 5454155825314635342L;

    //date formats

    //可以直接使用: @Length(max=50,message="用户名长度不能大于50")显示错误消息
    //columns START
    /**
     * tid db_column: t_id
     */

    private java.lang.Long tid;
    /**
     * tcreatePermission db_column: t_create_permission
     */
    @NotNull
    private java.lang.Boolean tcreatePermission;
    /**
     * treadPermission db_column: t_read_permission
     */
    @NotNull
    private java.lang.Boolean treadPermission;
    /**
     * teditPermission db_column: t_edit_permission
     */
    @NotNull
    private java.lang.Boolean teditPermission;
    /**
     * tdeletePermission db_column: t_delete_permission
     */
    @NotNull
    private java.lang.Boolean tdeletePermission;

    //Added by Jacky Y. 13.Aug.15.1429
    //hidden_flag
    private Boolean hiddenFlag;
    
    //columns END
    private Trole trole;
    private Tmodule tmodule;

    public void setHiddenFlag(Boolean hiddenFlag) {
    	if (hiddenFlag==null) hiddenFlag = new Boolean(false);
		this.hiddenFlag = hiddenFlag;
	}

	public TroleModule() {
    }

    public TroleModule(java.lang.Long tid) {
        this.tid = tid;
    }

    //    @SequenceGenerator(name = "t_role_module_t_id_seq", sequenceName = "t_role_module_t_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "t_id", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
    public java.lang.Long getTid() {
        return this.tid;
    }

    public void setTid(java.lang.Long value) {
        this.tid = value;
    }

    @Column(name = "t_create_permission", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
    public java.lang.Boolean getTcreatePermission() {
        return this.tcreatePermission;
    }

    public void setTcreatePermission(java.lang.Boolean value) {
        this.tcreatePermission = value;
    }

    @Column(name = "t_read_permission", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
    public java.lang.Boolean getTreadPermission() {
        return this.treadPermission;
    }

    public void setTreadPermission(java.lang.Boolean value) {
        this.treadPermission = value;
    }

    @Column(name = "t_edit_permission", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
    public java.lang.Boolean getTeditPermission() {
        return this.teditPermission;
    }

    public void setTeditPermission(java.lang.Boolean value) {
        this.teditPermission = value;
    }

    @Column(name = "t_delete_permission", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
    public java.lang.Boolean getTdeletePermission() {
        return this.tdeletePermission;
    }

    public void setTdeletePermission(java.lang.Boolean value) {
        this.tdeletePermission = value;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({ @JoinColumn(name = "t_role_id") })
    public Trole getTrole() {
        return trole;
    }

    public void setTrole(Trole trole) {
        this.trole = trole;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @NotFound(action = NotFoundAction.IGNORE)
    @JoinColumns({ @JoinColumn(name = "t_module_id") })
    public Tmodule getTmodule() {
        return tmodule;
    }

    public void setTmodule(Tmodule tmodule) {
        this.tmodule = tmodule;
    }

    @Column(name = "hidden_flag", unique = false, nullable = false, insertable = true, updatable = true, length = 1)
    public Boolean getHiddenFlag() {
		return hiddenFlag;
	}
    
    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Tid", getTid()).append("TcreatePermission", getTcreatePermission()).append("TreadPermission", getTreadPermission()).append("TeditPermission", getTeditPermission()).append("TdeletePermission", getTdeletePermission()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getTid()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof TroleModule)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        TroleModule other = (TroleModule) obj;
        return new EqualsBuilder().append(getTid(), other.getTid()).isEquals();
    }
}
