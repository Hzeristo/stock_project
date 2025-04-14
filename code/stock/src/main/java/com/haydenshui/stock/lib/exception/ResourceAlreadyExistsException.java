package com.haydenshui.stock.lib.exception;

public class ResourceAlreadyExistsException extends RuntimeException {
    private String resourceType;
    private String resourceIdentifier;

    public ResourceAlreadyExistsException(String resourceType, String resourceIdentifier) {
        super(String.format("Resource of type '%s' with identifier '%s' already exists.", resourceType, resourceIdentifier));
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
