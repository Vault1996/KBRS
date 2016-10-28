package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.UserLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

class AllUsersCommand implements ActionCommand {
	private static final String USERS = "users";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_ALL_USERS = "path.page.allUsers";
	private static final String ERROR_MESSAGE = "Problem in All Users Command: ";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		try {
			UserLogic userLogic = new UserLogic();
			List<User> users = userLogic.getAllUsers();
			request.setAttribute(USERS, users);
			request.getSession().setAttribute(PAGE_NUMBER, 0);
			return ConfigurationManager.getProperty(PAGE_ALL_USERS);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
	}
}
