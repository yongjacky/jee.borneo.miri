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
import org.hibernate.validator.constraints.NotBlank;

import com.borneo.framework.common.utils.DateUtils;

@Entity
@Table(name = "file_attach")
@Cache(usage = CacheConcurrencyStrategy.READ_WRITE)
public class FileAttach implements BaseEntity {

    //alias
    public static final String TABLE_ALIAS = "FileAttach";
    public static final String ALIAS_ID = "id";
    public static final String ALIAS_FILE_NAME = "fileName";
    public static final String ALIAS_CONTENT_TYPE = "contentType";
    public static final String ALIAS_PATH = "path";
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
    /**
     * id db_column: id
     */

    private java.lang.Long id;
    /**
     * fileName db_column: file_name
     */
    @NotBlank
    @Length(max = 255)
    private java.lang.String fileName;
    /**
     * contentType db_column: content_type
     */
    @NotBlank
    @Length(max = 100)
    private java.lang.String contentType;
    /**
     * path db_column: path
     */
    @NotBlank
    @Length(max = 500)
    private java.lang.String path;
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

    //columns END

    public FileAttach() {
    }

    public FileAttach(java.lang.Long id) {
        this.id = id;
    }

    //    @SequenceGenerator(name = "file_attach_id_seq", sequenceName = "file_attach_id_seq", initialValue = 1, allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", unique = true, nullable = false, insertable = true, updatable = true, length = 19)
    public java.lang.Long getId() {
        return this.id;
    }

    public void setId(java.lang.Long value) {
        this.id = value;
    }

    @Column(name = "file_name", unique = false, nullable = false, insertable = true, updatable = true, length = 255)
    public java.lang.String getFileName() {
        return this.fileName;
    }

    public void setFileName(java.lang.String value) {
        this.fileName = value;
    }

    @Column(name = "content_type", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
    public java.lang.String getContentType() {
        return this.contentType;
    }

    public void setContentType(java.lang.String value) {
        this.contentType = value;
    }

    @Column(name = "path", unique = false, nullable = false, insertable = true, updatable = true, length = 500)
    public java.lang.String getPath() {
        return this.path;
    }

    public void setPath(java.lang.String value) {
        this.path = value;
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

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE).append("Id", getId()).append("FileName", getFileName()).append("ContentType", getContentType()).append("Path", getPath()).toString();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(getId()).toHashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof FileAttach)) {
            return false;
        }
        if (this == obj) {
            return true;
        }
        FileAttach other = (FileAttach) obj;
        return new EqualsBuilder().append(getId(), other.getId()).isEquals();
    }
}
