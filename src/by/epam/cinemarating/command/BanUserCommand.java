package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Ban;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.function.DateConverter;
import by.epam.cinemarating.logic.BanLogic;
import by.epam.cinemarating.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;

class BanUserCommand implements ActionCommand {
	private static final String SHOW_USER_COMMAND = "/controller?command=show_user&user_id=";
	private static final String USER_ID = "user_id";
	private static final String TILL = "till";
	private static final String REASON = "reason";
	private static final String ERROR_MESSAGE = "Problem in Ban User Command";
	private static final String USER_BANNED_STATUS = "userBannedStatus";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long userId = Long.valueOf(request.getParameter(USER_ID));
		Timestamp till = DateConverter.stringToTimestamp(request.getParameter(TILL));
		String reason = request.getParameter(REASON);
		BanLogic banLogic = new BanLogic();
		Ban ban = new Ban(0, userId, till, reason);
		try {
			banLogic.banUser(ban);
			request.setAttribute(USER_BANNED_STATUS, true);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}

		return SHOW_USER_COMMAND + userId;
	}
}
