package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.UserLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class DeleteUserCommand implements ActionCommand {
	private static final String USER_ID = "user_id";

	private static final String DELETE_USER_STATUS = "deleteUserStatus";

	private static final String SHOW_MAIN_PAGE = "/controller?command=show_main_page";

	private static final String ERROR_MESSAGE = "Problem in Delete User Command";
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long userId = Long.valueOf(request.getParameter(USER_ID));
		UserLogic userLogic = new UserLogic();
		try {
			userLogic.deleteUser(userId);
			request.setAttribute(DELETE_USER_STATUS, true);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return SHOW_MAIN_PAGE;
	}
}
