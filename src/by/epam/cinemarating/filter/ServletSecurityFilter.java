package by.epam.cinemarating.filter;
import by.epam.cinemarating.entity.Role;
import by.epam.cinemarating.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
@WebFilter(dispatcherTypes = {
		DispatcherType.REQUEST,
		DispatcherType.FORWARD},
		urlPatterns = { "/controller" },
		servletNames = { "Controller" })
public class ServletSecurityFilter implements Filter {
	private static final String ACTIVE_USER = "activeUser";
	public void init(FilterConfig fConfig) throws ServletException {
	}
	public void destroy() {
	}
	public void doFilter(ServletRequest request, ServletResponse response,
						 FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpSession session = req.getSession();
		User user = (User) session.getAttribute(ACTIVE_USER);
		if (user == null) {
			user = new User();
		}
		if (user.getRole() == null) {
			user.setRole(Role.GUEST);
		}
		session.setAttribute(ACTIVE_USER, user);
		chain.doFilter(request, response);
	}
}
