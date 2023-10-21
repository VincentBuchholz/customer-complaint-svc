package dk.vv.customer.complaint.svc.api;


import dk.vv.customer.complaint.svc.Configuration;
import dk.vv.customer.complaint.svc.pojos.ResponseMessage;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;

import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.camel.ProducerTemplate;



@Produces(MediaType.APPLICATION_JSON)
@ApplicationScoped
@Path("/api")
@Consumes(MediaType.APPLICATION_JSON)
public class ComplaintApi {


    private final Configuration configuration;
    private final ProducerTemplate producerTemplate;

    @Inject
    public ComplaintApi(Configuration configuration, ProducerTemplate producerTemplate) {
        this.configuration = configuration;
        this.producerTemplate = producerTemplate;
    }

    @POST
    @Path("/complaint")
    public Response createComplaint(String content){

        producerTemplate.sendBody(configuration.routes().initRoute().in(),content);


        return Response.ok(
                        new ResponseMessage() {{
                            setMsg("Upload initiated. See logs for more info..");
                        }})
                .build();
    }
}
