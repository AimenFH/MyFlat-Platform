package fhcampus.myflat.configurations;

import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple CORS (Cross-Origin Resource Sharing) filter that allows for customizable CORS configurations.
 * This filter intercepts all incoming HTTP requests and adds CORS headers to the response, allowing for
 * cross-origin requests from the specified origins.
 */
@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter {

    /**
     * Processes each HTTP request to include CORS headers in the response.
     * Allows for methods such as POST, GET, PUT, OPTIONS, DELETE and sets the max age for the CORS preflight request.
     * Also allows all headers.
     *
     * @param req The incoming servlet request.
     * @param res The outgoing servlet response.
     * @param chain The filter chain to pass the request and response to the next entity in the chain.
     * @throws IOException If an input or output exception occurs.
     * @throws ServletException If the request could not be handled.
     */
    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse response = (HttpServletResponse) res;
        HttpServletRequest request = (HttpServletRequest) req;
        Map<String, String> map = new HashMap<>();
        String originHeader = request.getHeader("origin");
        response.setHeader("Access-Control-Allow-Origin", originHeader);
        response.setHeader("Access-Control-Allow-Methods", "POST, GET, PUT, OPTIONS, DELETE");
        response.setHeader("Access-Control-Max-Age", "3600");
        response.setHeader("Access-Control-Allow-Headers", "*");
        if ("OPTIONS".equalsIgnoreCase(request.getMethod())) {
            response.setStatus(HttpServletResponse.SC_OK);
        } else {
            chain.doFilter(req, res);
        }
    }

    /**
     * Initializes the filter configuration. This method is called by the web container to indicate to a filter
     * that it is being placed into service. This implementation is empty.
     *
     * @param filterConfig The filter configuration object used by a servlet container to pass information to a filter during initialization.
     */
    @Override
    public void init(FilterConfig filterConfig) {
    }

    /**
     * Called by the web container to indicate to a filter that it is being taken out of service.
     * This implementation is empty.
     */
    @Override
    public void destroy() {
    }

}