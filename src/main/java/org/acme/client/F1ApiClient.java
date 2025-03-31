package org.acme.client;

import io.smallrye.mutiny.Uni;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/api")
@RegisterRestClient(configKey = "f1apikey")
public interface F1ApiClient {

    @GET
    @Path("/{year}/drivers")
    String getDrivers(@PathParam("year") String year);

    @GET
    @Path("/{year}/drivers")
    Uni<String> getDriversAsync(@PathParam("year") String year);

}
