package ru.nuzmsh.web.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

/**
 * Example filter that unconditionally sets the character encoding to be used
 * in parsing the incoming request to a value specified by the
 * <strong>encoding</string> filter initialization parameter in the web app
 * deployment descriptor (</code>/WEB-INF/web.xml</code>).  This filter could
 * easily be extended to be more intelligent about what character encoding to
 * set, based on characteristics of the incoming request (such as the values
 * of the <code>Accept-Language</code> and <code>User-Agent</code> headers,
 * or a value stashed in the current user's session).
 *
 * @author Craig McClanahan
 * @version $Revision: 1.1 $ $Date: 2001/07/24 00:26:55 $
 */

public class SetCharacterEncodingFilter implements Filter {

    // ----------------------------------------------------- Instance Variables


    /**
     * The default character encoding to set for requests that pass through
     * this filter.
     */
    protected String encoding = null;


    /**
     * The filter configuration object we are associated with.  If this value
     * is null, this filter instance is not currently configured.
     */
    protected FilterConfig filterConfig = null;

    // --------------------------------------------------------- Public Methods


    /**
     * Take this filter out of service.
     */
    public void destroy() {

        this.encoding = null;
        this.filterConfig = null;

    }


    /**
     * Select and set (if specified) the character encoding to be used to
     * interpret request parameters for this request.
     *
     * @param request  The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain    The filter chain we are processing
     * @throws java.io.IOException            if an input/output error occurs
     * @throws javax.servlet.ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
                         FilterChain chain)
            throws IOException, ServletException {
        // Select and set (if needed) the character encoding to be used
        String encoding = selectEncoding(request);
        if (encoding != null)
            request.setCharacterEncoding(encoding);
        // Pass control on to the next filter
        chain.doFilter(request, response);

    }


    /**
     * Place this filter into service.
     *
     * @param filterConfig The filter configuration object
     */
    public void init(FilterConfig filterConfig) throws ServletException {

        this.filterConfig = filterConfig;
        this.encoding = filterConfig.getInitParameter("encoding");

    }

    // ------------------------------------------------------ Protected Methods


    /**
     * Select an appropriate character encoding to be used, based on the
     * characteristics of the current request and/or filter initialization
     * parameters.  If no character encoding should be set, return
     * <code>null</code>.
     * <p/>
     * The default implementation unconditionally returns the value configured
     * by the <strong>encoding</strong> initialization parameter for this
     * filter.
     *
     * @param request The servlet request we are processing
     */
    protected String selectEncoding(ServletRequest request) {

        return (this.encoding);

    }


}

