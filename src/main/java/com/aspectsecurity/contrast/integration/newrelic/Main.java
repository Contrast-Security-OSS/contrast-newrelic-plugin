package com.aspectsecurity.contrast.integration.newrelic;

import com.newrelic.metrics.publish.Runner;
import com.newrelic.metrics.publish.configuration.ConfigurationException;

import java.io.File;

public class Main {
    public static void main(String[] args) {
        Runner runner = new Runner();
        runner.add(new ContrastAgentFactory());

        try {
            //Never returns
            runner.setupAndRun();
        } catch (ConfigurationException e) {
            e.printStackTrace();
            System.err.println("Error configuring");
            System.exit(-1);
        }
    }
}
