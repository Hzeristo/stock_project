package com.haydenshui.stock.lib.exception;

import com.haydenshui.stock.constants.ErrorCode;
import com.haydenshui.stock.utils.MessageSourceUtils;

public class ResourceNotFoundException extends BusinessException {

    private String resourceType;
    
    private String resourceIdentifier;

    public ResourceNotFoundException(String resourceType, String resourceIdentifier) {
        super(
            MessageSourceUtils.getMessage(
                "error.resource.not.found",
                resourceType,
                resourceIdentifier
            ),
            ErrorCode.RESOURCE_NOT_FOUND.toString()
        );
        this.resourceType = resourceType;
        this.resourceIdentifier = resourceIdentifier;
    }

    public ResourceNotFoundException(String resourceType, String resourceIdentifier, Object payload) {
        super(
            MessageSourceUtils.getMessage(
                "error.resource.not.found",
                resourceType,
                resourceIdentifier
            ),
            ErrorCode.RESOURCE_NOT_FOUND.toString(),
            payload
        );
        this.resourceType = resourceType;
        this.resourceIdentifier = resourceIdentifier;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceIdentifier() {
        return resourceIdentifier;
    }

}
