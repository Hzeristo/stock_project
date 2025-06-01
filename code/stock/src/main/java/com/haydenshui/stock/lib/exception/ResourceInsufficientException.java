package com.haydenshui.stock.lib.exception;

import com.haydenshui.stock.constants.ErrorCode;
import com.haydenshui.stock.utils.MessageSourceUtils;

public class ResourceInsufficientException extends BusinessException {

    private String resourceType;

    private String resourceIdentifier;
    
    private String operation;

    public ResourceInsufficientException(String resourceType, String resourceIdentifier, String operation) {
        super(
            MessageSourceUtils.getMessage(
                "error.resource.not.sufficient",
                resourceType,
                resourceIdentifier,
                operation
            ),
            ErrorCode.RESOURCE_INSUFFICIENT.toString()
        );
        this.resourceType = resourceType;
        this.resourceIdentifier = resourceIdentifier;
        this.operation = operation;
    }

    public String getResourceType() {
        return resourceType;
    }

    public String getResourceIdentifier() {
        return resourceIdentifier;
    }

    public String getOperation() {
        return operation;
    }

}
