package test.jersey.filters;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by kaemiin on 2016/3/11.
 */
public class HTML5CorsFilter implements javax.servlet.Filter {

    static Logger _logger = LogManager.getLogger(HTML5CorsFilter.class.getName());

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        if (_logger.isDebugEnabled()) {

            javax.servlet.http.HttpServletRequest hrequest =
                    (javax.servlet.http.HttpServletRequest) request;
            String url = hrequest.getRequestURL().toString();
            String queryString = hrequest.getQueryString();

            _logger.debug("The request URL: " + url + "?" + queryString);

            printRequestAttributes(hrequest);

            printRequestParameters(hrequest);
        }

        HttpServletResponse res = (HttpServletResponse) response;
        res.addHeader("Access-Control-Allow-Origin", "*");
        res.addHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT");
        res.addHeader("Access-Control-Allow-Headers", "Content-Type");
        chain.doFilter(request, response);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // NOOP
    }

    @Override
    public void destroy() {
        // NOOP
    }

    private void printRequestParameters(HttpServletRequest req) {

        _logger.debug("Printing All Request Parameters From HttpSerlvetRequest:");

        Enumeration<String> requestParameters = req.getParameterNames();

        String paramName = null;

        while (requestParameters.hasMoreElements()) {
            paramName = requestParameters.nextElement();
            _logger.debug("Request Paramter Name: " + paramName
                    + ", Value - " + req.getParameter(paramName));
        }
    }

    private void printRequestAttributes(HttpServletRequest req) {

        _logger.debug("Printing All Request Parameters From HttpSerlvetRequest:");

        Enumeration<String> requestAttributes = req.getAttributeNames();

        String attributeName = null;

        while (requestAttributes.hasMoreElements()) {
            attributeName = requestAttributes.nextElement();
            _logger.debug("Request Attribute Name: " + attributeName
                    + ", Value - " + (req.getAttribute(attributeName)).toString());
        }
    }
}
