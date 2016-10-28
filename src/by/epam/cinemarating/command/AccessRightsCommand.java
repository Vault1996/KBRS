package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.AccessLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AccessRightsCommand implements ActionCommand {
	private static final String RW = "rw";
	private static final String MOVIE_ID = "movie_id";
	private static final String COMMAND_SHOW_MOVIE = "/controller?command=show_movie&movie_id=";
	private static final String USER = "activeUser";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String[] values = request.getParameterValues(RW);
		long movieId = Long.parseLong(request.getParameter(MOVIE_ID));
		AccessLogic accessLogic = new AccessLogic();
		User activeUser = (User) request.getSession().getAttribute(USER);
		try {
			accessLogic.deleteAccesses(movieId, activeUser.getUserId());
			for (String value : values) {
				long userId = Long.valueOf(value);
				accessLogic.updateAccess(userId, movieId);
			}
		} catch (LogicException e) {
			e.printStackTrace();
		}
		return COMMAND_SHOW_MOVIE + movieId;
	}
}
