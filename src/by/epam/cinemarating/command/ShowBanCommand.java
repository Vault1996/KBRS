package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ShowBanCommand implements ActionCommand {
	private static final String USER_ID = "userId";
	private static final String PAGE_BAN = "path.page.ban";
	private static final String ACTIVE_USER = "activeUser";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		request.getSession().invalidate();
		return ConfigurationManager.getProperty(PAGE_BAN);
	}
}
