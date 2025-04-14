package com.haydenshui.stock.lib.exception;

public class ResourceInsufficientException extends RuntimeException {
    private String resourceType;
    private String resourceIdentifier;
    private String operation;

    public ResourceInsufficientException(String resourceType, String resourceIdentifier, String operation) {
        super(String.format("Resource of type '%s' with identifier '%s' is insufficient for operation '%s'.", 
            resourceType, resourceIdentifier, operation));
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
