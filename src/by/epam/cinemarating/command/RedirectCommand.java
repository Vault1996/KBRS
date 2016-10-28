package by.epam.cinemarating.command;

import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class RedirectCommand implements ActionCommand {
	private static final String NEXT_PAGE = "next";
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		String nextPage = request.getParameter(NEXT_PAGE);
		return ConfigurationManager.getProperty(nextPage);
	}
}