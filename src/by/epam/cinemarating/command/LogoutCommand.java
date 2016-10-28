package by.epam.cinemarating.command;

import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class LogoutCommand implements ActionCommand {
	private static final String PAGE_INDEX = "path.page.index";
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) {
		request.getSession().invalidate();
		return ConfigurationManager.getProperty(PAGE_INDEX);
	}
}