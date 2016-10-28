package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.MovieLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class DeleteMovieCommand implements ActionCommand {
	private static final String SHOW_MAIN_PAGE_COMMAND = "/controller?command=show_main_page";
	private static final String MOVIE_ID = "movie_id";
	private static final String ERROR_MESSAGE = "Problem in Delete Movie Command";
	private static final String MOVIE_DELETED_STATUS = "movieDeletedStatus";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long movieId = Long.valueOf(request.getParameter(MOVIE_ID));
		MovieLogic movieLogic = new MovieLogic();
		try {
			movieLogic.deleteMovie(movieId);
			request.setAttribute(MOVIE_DELETED_STATUS, true);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return SHOW_MAIN_PAGE_COMMAND;
	}
}
