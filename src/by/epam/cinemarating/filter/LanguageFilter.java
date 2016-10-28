package by.epam.cinemarating.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST,
		DispatcherType.FORWARD},
		urlPatterns = { "/*" },
		initParams = {
				@WebInitParam(name = "language", value = "en", description = "Language Param")
		})
public class LanguageFilter implements Filter {
	private static final String LANGUAGE = "language";
	private String language;
	public void init(FilterConfig fConfig) throws ServletException {
		language = fConfig.getInitParameter(LANGUAGE);
	}
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
// setting language to default language from init param if it is not exists
		if (httpRequest.getSession().getAttribute(LANGUAGE) == null) {
			httpRequest.getSession().setAttribute(LANGUAGE, language);
		}
		chain.doFilter(request, response);
	}
	public void destroy() {
		language = null;
	}
}