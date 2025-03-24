package org.acme;

import com.github.tomakehurst.wiremock.client.WireMock;
import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.common.http.TestHTTPEndpoint;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.*;
import io.smallrye.context.api.*;
import jakarta.inject.Inject;

import org.acme.models.*;
import org.acme.rest.AppResource;
import org.acme.wiremock.*;
import org.eclipse.microprofile.context.*;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.github.tomakehurst.wiremock.client.WireMock.containing;
import static com.github.tomakehurst.wiremock.client.WireMock.matchingJsonPath;
import static com.github.tomakehurst.wiremock.client.WireMock.okJson;
import static com.github.tomakehurst.wiremock.client.WireMock.verify;
import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.CoreMatchers.is;

@QuarkusTest
@TestHTTPEndpoint(AppResource.class)
@QuarkusTestResource(value = WiremockTestResourceConfigurableLifecycleManager.class, restrictToAnnotatedClass = false, parallel = true)
class AppResourceTest {


    @WireMockClient
    WireMock wireMockClient;

    Logger logger = LoggerFactory.getLogger(AppResourceTest.class);

    @Test
    void getDrivers() {
        assert  wireMockClient != null;
        logger.info(wireMockClient.toString());

        wireMockClient.register(WireMock.get("/api/2010/drivers").willReturn(
                WireMock.aResponse().withStatus(200).withBody(jsonResponse)));

        final Response response = given()
                .when().get("/drivers")
                .then()
                .statusCode(200)
                .assertThat()
                .statusCode(SC_OK)
                .contentType(ContentType.JSON)
                .extract().as(Response.class);

        //FIXME : uncomment  this, and magically verify works! InnerThreadLocal not work, why? Different Threadpool, CompletableFutures?
        //WireMock.configureFor(wireMockClient);

        verify(1,
                WireMock.getRequestedFor(WireMock.urlPathEqualTo("/api/2010/drivers")));
        logger.info("resp:{}", response );
    }

    String jsonResponse = """
            {
            "api": "https://f1api.dev",
            "url": "https://f1api.dev/api/2010/drivers",
            "limit": 30,
            "offset": 0,
            "total": 27,
            "season": "2010",
            "championshipId": "f1_2010",
            "drivers": [
            {
            "driverId": "vettel",
            "name": "Sebastian",
            "surname": "Vettel",
            "nationality": "Germany",
            "birthday": "03/07/1987",
            "number": 5,
            "shortName": "VET",
            "url": "https://en.wikipedia.org/wiki/Sebastian_Vettel",
            "teamId": "red_bull"
            }]}
            """;
}