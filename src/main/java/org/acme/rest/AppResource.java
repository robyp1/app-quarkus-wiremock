package org.acme.rest;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import org.acme.client.F1ApiClient;
import org.acme.models.Response;
import org.eclipse.microprofile.rest.client.inject.RestClient;

import com.fasterxml.jackson.core.*;
import com.fasterxml.jackson.databind.*;

import java.time.Duration;
import java.time.temporal.TemporalAmount;

@Path("/v1")
public class AppResource {


    @Inject
    @RestClient
    F1ApiClient f1ApiClient;

    @GET
    @Path("/drivers")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDrivers() throws JsonProcessingException {
        String drivers = f1ApiClient.getDrivers("2010");
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(drivers, Response.class);
    }

    @GET
    @Path("/drivers/async")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDriversAsync() throws JsonProcessingException {
        String response = f1ApiClient.getDriversAsync("2010").await()
                .atMost(Duration.ofSeconds(1000));
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(response, Response.class);
    }


}
