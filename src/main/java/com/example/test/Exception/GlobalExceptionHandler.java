package com.example.test.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler(AccessDeniedException.class)
    public void handleAccessDeniedException(AccessDeniedException ex) {
        throw ex; // Allow Spring Security AccessDeniedHandler to handle authorization redirects
    }

    @ExceptionHandler(Exception.class)
    public ModelAndView handleException(Exception ex) {
        logger.error("An unexpected error occurred: ", ex);
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("error", "An unexpected error occurred. Please contact system support or try again later.");
        return modelAndView;
    }
}
