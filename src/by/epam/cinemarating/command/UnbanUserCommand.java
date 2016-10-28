package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.logic.BanLogic;
import by.epam.cinemarating.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class UnbanUserCommand implements ActionCommand {
	private static final String USER_ID = "user_id";

	private static final String UNBAN_USER_STATUS = "unbanUserStatus";

	private static final String SHOW_USER_PAGE = "/controller?command=show_user&user_id=";

	private static final String ERROR_MESSAGE = "Problem in Unban User Command";
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long userId = Long.valueOf(request.getParameter(USER_ID));
		BanLogic banLogic = new BanLogic();
		try {
			banLogic.unbanUser(userId);
			request.setAttribute(UNBAN_USER_STATUS, true);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return SHOW_USER_PAGE + userId;
	}
}
