package com.aspectsecurity.contrast.integration.newrelic;

import com.contrastsecurity.sdk.App;
import com.contrastsecurity.sdk.Apps;
import com.contrastsecurity.sdk.ContrastConnector;
import com.contrastsecurity.sdk.exception.InitializationException;
import com.newrelic.metrics.publish.Agent;

import java.util.logging.Logger;

public class ContrastAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(ContrastAgent.class.getName());

    public static final String AGENT_CONFIG_FILE = "com.aspectsecurity.contrast.integration.newrelic.contrast.json";

    private static final String CONTRAST = "Contrast";

    private ContrastConfig contrastConfig;

    private ContrastConnector contrast;

    public ContrastAgent(ContrastConfig contrastConfig) {
        // Initialize the New Relic Agent API
        super("com.aspectsecurity.contrast.integration.newrelic", "1.0.0");

        this.contrastConfig = contrastConfig;

        // Initialize the Contrast Client API
        try {
            contrast = new ContrastConnector(
                    this.contrastConfig.getUsername(),
                    this.contrastConfig.getServiceKey(),
                    this.contrastConfig.getApiKey(),
                    this.contrastConfig.getHostname()
            );
        } catch (InitializationException e) {
            LOG.severe("Unable to initialize the Contrast SDK");
            System.exit(1);
        }
    }

    @Override
    public void pollCycle() {
        Apps apps = contrast.getAllAppData();
        reportMetric(CONTRAST, "value", apps.getApps().size());

        for (App app : apps.getApps())
            if (app.getName().equals(contrastConfig.getAppName())) {
                // Total Traces
                reportMetric(getComponentLabel("Traces"), "traces", app.getNumTraces());

                // Libraries
                reportMetric(getComponentLabel("Libraries/Stale"), "libraries", app.getAppStats().getAppLibraries().getStale());
                reportMetric(getComponentLabel("Libraries/Total"), "libraries", app.getAppStats().getAppLibraries().getTotal());
                reportMetric(getComponentLabel("Libraries/Unknown"), "libraries", app.getAppStats().getAppLibraries().getUnknown());

                // Coverage
                reportMetric(getComponentLabel("Coverage/Total"), "methods", app.getAppStats().getMethodsTotal());
                reportMetric(getComponentLabel("Coverage/Seen"), "methods", app.getAppStats().getMethodsSeen());

                // Vulnerabilities (By Severity)
                reportMetric(getComponentLabel("Vulnerabilities/Severity/Critical"), "vulnerabilities", app.getAppStats().getAppVulns().getCriticals());
                reportMetric(getComponentLabel("Vulnerabilities/Severity/High"), "vulnerabilities", app.getAppStats().getAppVulns().getHighs());
                reportMetric(getComponentLabel("Vulnerabilities/Severity/Medium"), "vulnerabilities", app.getAppStats().getAppVulns().getMediums());
                reportMetric(getComponentLabel("Vulnerabilities/Severity/Low"), "vulnerabilities", app.getAppStats().getAppVulns().getLows());
                reportMetric(getComponentLabel("Vulnerabilities/Severity/Note"), "vulnerabilities", app.getAppStats().getAppVulns().getNotes());

                // Vulnerabilities (By Type)
                for (String key : app.getUniqueTraceTypes().keySet()) {
                    reportMetric(getComponentLabel("Vulnerabilities/Category/" + key), "vulnerabilities", app.getUniqueTraceTypes().get(key).size());
                }
            }
    }

    @Override
    public String getComponentHumanLabel() {
        return contrastConfig.getAppName();
    }

    private String getComponentLabel(String metric) {
        return CONTRAST + "/" + metric;
    }
}
