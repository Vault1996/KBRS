package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.UserLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ShowEditProfileCommand implements ActionCommand {
	private static final String PAGE_EDIT_REVIEW = "path.page.editProfile";
	private static final String USER_ID = "user_id";
	private static final String USER_EDITED = "userEdited";
	private static final String ERROR_MESSAGE = "Problem in Show Edit Profile Command";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long userId = Long.valueOf(request.getParameter(USER_ID));
		UserLogic userLogic = new UserLogic();
		try {
			User user = userLogic.findUserById(userId);
			request.setAttribute(USER_EDITED, user);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return ConfigurationManager.getProperty(PAGE_EDIT_REVIEW);
	}
}
