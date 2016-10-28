package by.epam.cinemarating.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@WebFilter(urlPatterns = { "/jsp/*" },
		initParams = { @WebInitParam(name = "indexPath", value = "/index.jsp") })
public class PageRedirectSecurityFilter implements Filter {
	private static final String INDEX_PATH = "indexPath";
	private String indexPath;
	public void init(FilterConfig fConfig) throws ServletException {
		indexPath = fConfig.getInitParameter(INDEX_PATH);
	}
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
// redirecting to the next page
		httpResponse.sendRedirect(httpRequest.getContextPath() + indexPath);
		chain.doFilter(request, response);
	}
	public void destroy() {
	}
}
