package com.klav.service.ext;

public class NotificationContext<I> {

    private String to;

    private I boundedContext;

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public I getBoundedContext() {
        return boundedContext;
    }

    public void setBoundedContext(I boundedContext) {
        this.boundedContext = boundedContext;
    }
}
