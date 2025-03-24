package org.acme.wiremock;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.common.QuarkusTestResourceLifecycleManager;

import java.util.HashMap;
import java.util.Map;

public class WiremockTestResource implements QuarkusTestResourceLifecycleManager {

    WireMockServerManager wireMockServerManager;

    WireMock wireMockClient;

    @Override
    public Map<String, String> start() {
        wireMockServerManager = new WireMockServerManager();
        wireMockServerManager.startServer();
        wireMockClient = wireMockServerManager.wireMockClient;
        return new HashMap<>();
    }

    @Override
    public void stop() {
        wireMockServerManager.stopServer();
    }

    @Override
    public void init(Map<String, String> initArgs) {
        QuarkusTestResourceLifecycleManager.super.init(initArgs);
    }

    @Override
    public void inject(TestInjector testInjector) {
        testInjector.injectIntoFields(wireMockClient, new TestInjector.AnnotatedAndMatchesType(WireMockClient.class, WireMock.class));
    }



}
