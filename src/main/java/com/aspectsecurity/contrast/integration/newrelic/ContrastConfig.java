package com.aspectsecurity.contrast.integration.newrelic;

public class ContrastConfig {
    private String appName;
    private String username;
    private String serviceKey;
    private String apiKey;
    private String hostname;

    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public void setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
    }

    public String getApiKey() {
        return apiKey;
    }

    public void setApiKey(String apiKey) {
        this.apiKey = apiKey;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
}
