package com.aspectsecurity.contrast.integration.newrelic;

import com.contrastsecurity.rest.*;
import com.contrastsecurity.sdk.App;
import com.contrastsecurity.sdk.Apps;
import com.contrastsecurity.sdk.ContrastConnector;
import com.contrastsecurity.sdk.exception.InitializationException;
import com.newrelic.metrics.publish.Agent;

import java.util.List;
import java.util.logging.Logger;

public class ContrastAgent extends Agent {
    private static final Logger LOG = Logger.getLogger(ContrastAgent.class.getName());

    public static final String AGENT_CONFIG_FILE = "com.aspectsecurity.contrast.integration.newrelic.contrast.json";

    private static final String CONTRAST = "Contrast";

    private ContrastConfig contrastConfig;

    private ContrastConnection contrast;

    public ContrastAgent(ContrastConfig contrastConfig) {
        // Initialize the New Relic Agent API
        super("com.aspectsecurity.contrast.integration.newrelic", "1.0.0");

        this.contrastConfig = contrastConfig;

        // Initialize the Contrast Client API
        try {
            contrast = new ContrastConnection(
                    this.contrastConfig.getUsername(),
                    this.contrastConfig.getServiceKey(),
                    this.contrastConfig.getApiKey(),
                    this.contrastConfig.getHostname()
            );
        } catch (Exception e) {
            LOG.severe("Unable to initialize the Contrast SDK");
            System.exit(1);
        }
    }

    @Override
    public void pollCycle() {
/*
        List<Application> apps = contrast.getApplications();

        if (apps == null) {
            LOG.severe("No applications found for account. Please check your configuration.");
            System.exit(0);
        }

        reportMetric(CONTRAST, "value", apps.getApps().size());

        for (Application application : apps)
            if (application.getName().equals(contrastConfig.getAppName())) {
                try {
                    List<Trace> traces = contrast.getTraces(application.getId());
                    List<Library> libraries = contrast.getLibraries(application.getId());
                    Coverage coverage = contrast.getCoverage(application.getId());

                    // Total Traces
                    reportMetric(getComponentLabel("Traces"), "traces", traces.size());

                    // Libraries
                    int stale, unknown, total;
                    for (Library lib : libraries) {
                        lib.
                    }

                    reportMetric(getComponentLabel("Libraries/Stale"), "libraries", );
                    reportMetric(getComponentLabel("Libraries/Total"), "libraries", application.getAppStats().getAppLibraries().getTotal());
                    reportMetric(getComponentLabel("Libraries/Unknown"), "libraries", application.getAppStats().getAppLibraries().getUnknown());

                    // Coverage
                    reportMetric(getComponentLabel("Coverage/Total"), "methods", application.getAppStats().getMethodsTotal());
                    reportMetric(getComponentLabel("Coverage/Seen"), "methods", application.getAppStats().getMethodsSeen());

                    // Vulnerabilities (By Severity)
                    reportMetric(getComponentLabel("Vulnerabilities/Severity/Critical"), "vulnerabilities", application.getAppStats().getAppVulns().getCriticals());
                    reportMetric(getComponentLabel("Vulnerabilities/Severity/High"), "vulnerabilities", application.getAppStats().getAppVulns().getHighs());
                    reportMetric(getComponentLabel("Vulnerabilities/Severity/Medium"), "vulnerabilities", application.getAppStats().getAppVulns().getMediums());
                    reportMetric(getComponentLabel("Vulnerabilities/Severity/Low"), "vulnerabilities", application.getAppStats().getAppVulns().getLows());
                    reportMetric(getComponentLabel("Vulnerabilities/Severity/Note"), "vulnerabilities", application.getAppStats().getAppVulns().getNotes());

                    // Vulnerabilities (By Type)
                    for (String key : application.getUniqueTraceTypes().keySet()) {
                        reportMetric(getComponentLabel("Vulnerabilities/Category/" + key), "vulnerabilities", application.getUniqueTraceTypes().get(key).size());
                    }
                } catch (Exception e) {

                }
            }
*/
    }

    @Override
    public String getComponentHumanLabel() {
        return contrastConfig.getAppName();
    }

    private String getComponentLabel(String metric) {
        return CONTRAST + "/" + metric;
    }
}
