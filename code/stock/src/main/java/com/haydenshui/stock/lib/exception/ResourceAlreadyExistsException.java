package com.haydenshui.stock.lib.exception;

import com.haydenshui.stock.constants.ErrorCode;
import com.haydenshui.stock.utils.MessageSourceUtils;

public class ResourceAlreadyExistsException extends BusinessException {

    private String resourceType;
    
    private String resourceIdentifier;

    public ResourceAlreadyExistsException(String resourceType, String resourceIdentifier) {
        super(
            MessageSourceUtils.getMessage(
                "error.resource.already.exists",
                resourceType,
                resourceIdentifier
            ),
            ErrorCode.RESOURCE_ALREADY_EXISTS.toString()
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
