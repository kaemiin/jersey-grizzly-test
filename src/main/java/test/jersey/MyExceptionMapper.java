package test.jersey;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

/**
 * Created by kaemiin on 2016/3/11.
 */
@Provider
public class MyExceptionMapper implements ExceptionMapper<WebApplicationException> {

    static Logger _logger = LogManager.getLogger(MyExceptionMapper.class.getName());

    @Override
    public Response toResponse(WebApplicationException e) {

        return Response.serverError().entity(e.getMessage()).build();
    }
}
