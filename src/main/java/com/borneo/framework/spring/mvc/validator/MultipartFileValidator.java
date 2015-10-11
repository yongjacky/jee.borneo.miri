/**
 *
 */
package com.borneo.framework.spring.mvc.validator;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.ArrayUtils;
import org.springframework.util.Assert;
import org.springframework.web.multipart.MultipartFile;

import com.borneo.framework.common.exception.FileOutOfMaxLengthException;

/**
 * @author peter.yuan
 */
public class MultipartFileValidator {

    private static final long MAX_SIZE = 1024 * 1024;

    private long maxSize = MAX_SIZE;

    private String[] allowedContentTypes;

    public MultipartFileValidator(String[] allowedContentTypes) {
        this.allowedContentTypes = allowedContentTypes;
    }

    @PostConstruct
    public void afterPropertiesSet() {
        Assert.notEmpty(allowedContentTypes, "The content types allowed to be uploaded must contain one at least!");
    }

    public void validate(MultipartFile file) throws FileOutOfMaxLengthException {
        Assert.notNull(file, "The multipart file is null!");
        if (file.getSize() > maxSize) {
            throw new FileOutOfMaxLengthException("The file uploaded is out of max file size : " + maxSize);
        }
        if (!ArrayUtils.contains(allowedContentTypes, file.getContentType())) {
        }
    }

    public void setMaxSize(long maxSize) {
        this.maxSize = maxSize;
    }

    public void setAllowedContentTypes(String[] allowedContentTypes) {
        this.allowedContentTypes = allowedContentTypes;
    }

}
