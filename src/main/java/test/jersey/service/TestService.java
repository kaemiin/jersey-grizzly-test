package test.jersey.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 * Created by kaemiin on 2016/3/11.
 */
@Produces(MediaType.APPLICATION_JSON)
public class TestService {

    static Logger _logger = LogManager.getLogger(TestService.class.getName());

    @Context
    private transient HttpServletRequest servletRequest;

    @Context
    private transient HttpServletResponse servletResponse;

    @Context
    private transient UriInfo info;

    protected String getToken() throws Exception {

        //TODO
        return null;
    }

    @POST
    @Path("/{DraftID}/send")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendFromDraft(@PathParam("DraftID") String strDraftID,
            @FormParam("Topic") String strTopic,
            @FormParam("Message") String strMessage) {

        //TODO
        try {
            String token = getToken();
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String result = "This is a test.";

        return Response.ok(result).build();
    }
}
