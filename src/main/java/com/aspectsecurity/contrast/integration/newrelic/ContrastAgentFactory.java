package com.aspectsecurity.contrast.integration.newrelic;

import com.newrelic.metrics.publish.Agent;
import com.newrelic.metrics.publish.AgentFactory;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

import java.util.Map;

public class ContrastAgentFactory extends AgentFactory {
    public ContrastAgentFactory() {
        super(ContrastAgent.AGENT_CONFIG_FILE);
    }

    @Override
    public Agent createConfiguredAgent(Map<String, Object> stringObjectMap) throws ConfigurationException {
        ContrastConfig config = new ContrastConfig();
        config.setAppName(stringObjectMap.get("appname").toString());
        config.setApiKey(stringObjectMap.get("apikey").toString());
        config.setServiceKey(stringObjectMap.get("servicekey").toString());
        config.setHostname(stringObjectMap.get("hostname").toString());
        config.setUsername(stringObjectMap.get("username").toString());
        return new ContrastAgent(config);
    }
}
