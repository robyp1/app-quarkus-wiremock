package org.acme.client;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.acme.models.Response;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient(configKey = "f1apikey")
public interface F1ApiClient {

    @GET
    @Path("/{year}/drivers")
    String getDrivers(@PathParam("year") String year);

}
