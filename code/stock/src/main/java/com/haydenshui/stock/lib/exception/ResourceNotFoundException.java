package com.haydenshui.stock.lib.exception;

public class ResourceNotFoundException extends RuntimeException {
    private String resourceType;
    private String resourceIdentifier;


    public ResourceNotFoundException(String resourceType, String resourceIdentifier) {
        super(String.format("Resource of type '%s' with identifier '%s' not found.", resourceType, resourceIdentifier));
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
