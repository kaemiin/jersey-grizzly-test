package test.jersey.service;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.Cookie;
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
@Path("/draft")
@Produces(MediaType.APPLICATION_JSON)
public class DraftService {

    static Logger _logger = LogManager.getLogger(DraftService.class.getName());

    @Context
    private transient HttpServletRequest servletRequest;

    @Context
    private transient HttpServletResponse servletResponse;

    @Context
    private transient UriInfo info;

    private boolean IS_SUPPORT_COOKIE = false;

    private final String MOBILE_APP_TOKEN_NAME = "SESSIONID";

    @GET
    @Path("/{DraftID}/joiners")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getJoinersFormDraft(@PathParam("DraftID") String strDraftID) {

        _logger.info("The draft id:" + strDraftID);

        String result = "{\"id\":\"1234\"}";

        return Response.ok(result).build();
    }

    private String getTokenOldFashionedWay() throws Exception {

        String token = null;

        try {

            if (IS_SUPPORT_COOKIE) {

                Cookie[] cookies = servletRequest.getCookies();

                if (cookies != null) {
                    for (int i = 0; i < cookies.length; i++) {

                        if (cookies[i].getName().equalsIgnoreCase(MOBILE_APP_TOKEN_NAME)) {
                            token = cookies[i].getValue();
                            break;
                        }
                    }
                }
            }
            else {
                token = servletRequest.getParameter(MOBILE_APP_TOKEN_NAME);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            _logger.error(e);
        }

        if (token == null || token.isEmpty()) {
            String err = "Invalid SessionID";
            _logger.debug(err);
            throw new Exception(err);
        }
        return token;
    }

    @POST
    @Path("/{DraftID}/send/oldfashionedway")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendFromDraftOldFashionedWay(@PathParam("DraftID") String strDraftID,
                                  @FormParam("Topic") String strTopic,
                                  @FormParam("Message") String strMessage,
                                  @FormParam("IsUseCookie") String strIsUseCookie) {

        try {

            if (strIsUseCookie != null && strIsUseCookie.equalsIgnoreCase("true")) {

                IS_SUPPORT_COOKIE = true;
            }

            String token = getTokenOldFashionedWay();

            _logger.info("The token:" + token);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String result = "{\"result\":\"This is old fashioned way.\"}";

        return Response.ok(result).build();
    }

    private String getTokenNewWay() throws Exception {

        String token = null;

        try {

            if (IS_SUPPORT_COOKIE) {

                System.out.println(servletRequest);

                Cookie[] cookies = servletRequest.getCookies();

                if (cookies != null) {

                    for (int i = 0; i < cookies.length; i++) {

                        if (cookies[i].getName().equalsIgnoreCase(MOBILE_APP_TOKEN_NAME)) {
                            token = cookies[i].getValue();
                            break;
                        }
                    }
                }
            }
            else {

//                String queryStr = info.getRequestUri().getQuery();

                String queryStr = servletRequest.getQueryString();

                System.out.println(servletRequest);

                if (queryStr != null) {

                  String[] params = queryStr.split("&");

                  for (String param: params) {

                     if (param.contains(MOBILE_APP_TOKEN_NAME)) {

                         token = param.split("=")[1];

                         break;
                     }
                  }
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
            _logger.error(e);
        }

        if (token == null || token.isEmpty()) {
            String err = "Invalid SessionID";
            _logger.debug(err);
            throw new Exception(err);
        }
        return token;
    }

    @POST
    @Path("/{DraftID}/send/new")
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    @Produces(MediaType.APPLICATION_JSON)
    public Response sendFromDraftNewWay(@PathParam("DraftID") String strDraftID,
                                        @FormParam("Topic") String strTopic,
                                        @FormParam("Message") String strMessage,
                                        @FormParam("IsUseCookie") String strIsUseCookie) {

        try {

            if (strIsUseCookie != null && strIsUseCookie.equalsIgnoreCase("true")) {

                IS_SUPPORT_COOKIE = true;
            }

            String token = getTokenNewWay();

            _logger.info("The token:" + token);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        String result = "{\"result\":\"This is new way.\"}";

        return Response.ok(result).build();
    }
}
