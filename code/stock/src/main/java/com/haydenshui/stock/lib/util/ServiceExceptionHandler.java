package com.haydenshui.stock.lib.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;

public class ServiceExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(ServiceExceptionHandler.class);

    /**
     * Handle data integrity violations.
     * This method could be used to log and return an appropriate error result
     * when a DataIntegrityViolationException occurs.
     * 
     * @param e The exception
     * @param context Additional context to log
     * @return ApiResult Failure response
     */
    public static ApiResult<Object> handleDataIntegrityViolation(DataIntegrityViolationException e, String context) {
        // Log the error with context information
        logger.warn("Data integrity violation occurred: {}", context, e);
        return ApiResult.failure("Data integrity violation occurred", null);
    }

    /**
     * Handle generic service layer exceptions.
     * 
     * @param e The exception
     * @param context Additional context to log
     * @return ApiResult Failure response
     */
    public static ApiResult<Object> handleGenericException(Exception e, String context) {
        // Log the error with context information
        logger.error("Internal error occurred during {}: {}", context, e.getMessage(), e);
        return ApiResult.error("Internal server error", null);
    }

    /**
     * Handle specific cases where validation fails or invalid data is found.
     * 
     * @param errorMessage Custom error message
     * @param context Additional context to log
     * @return ApiResult Failure response
     */
    public static ApiResult<Object> handleValidationError(String errorMessage, String context) {
        // Log the validation error
        logger.warn("Validation error occurred during {}: {}", context, errorMessage);
        return ApiResult.failure(errorMessage, null);
    }
}
