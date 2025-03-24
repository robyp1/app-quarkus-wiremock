package org.acme.wiremock;


import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import jakarta.inject.Inject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Singleton;


@Singleton
public class WireMockServerManager {

    private WireMockServer wireMockServer;


    private int port;

    public WireMock wireMockClient;

    Logger log = LoggerFactory.getLogger(WireMockServerManager.class);

    public WireMockServerManager() {
        // Choose a random port (WireMock defaults to 8080, but we can specify a custom port)
//        this.port = 0; // 0 tells the system to pick an available port
        this.port = 56191; // 0 tells the system to pick an available port
    }

    public void startServer() {
        WireMockConfiguration config = WireMockConfiguration.options()
                .port(port);  // We use 0 here to allow the system to choose an available port
        wireMockServer = new WireMockServer(config);

        // Start the server
        wireMockServer.start();

        // If you need the actual port that was assigned, you can retrieve it like this
        port = wireMockServer.port();

        // Optionally, set up WireMock client to configure mocks
        WireMock.configureFor("localhost", port);
        log.info("wiremock is listening on: {}:{}", "localhost",port);

        wireMockClient = new WireMock("localhost", port);

        log.info("WireMock server started on port: " + port);
    }

    public void stopServer() {
        if (wireMockServer != null && wireMockServer.isRunning()) {
            wireMockServer.stop();
            System.out.println("WireMock server stopped.");
        } else {
            System.out.println("WireMock server is not running.");
        }
    }


    public int getPort() {
        return port;
    }




//    public static void main(String[] args) {
//        WireMockServerManager serverManager = new WireMockServerManager();
//        serverManager.startServer();
//
//        // Perform your tests or interactions with the WireMock server here
//
//        // Example: stopping the server
//        serverManager.stopServer();
//    }
}

