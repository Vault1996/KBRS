package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Movie;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.MovieLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ShowEditMovieCommand implements ActionCommand {
	private static final String PAGE_EDIT_MOVIE = "path.page.editMovie";
	private static final String MOVIE_ID = "movie_id";
	private static final String ERROR_MESSAGE = "Problem in Show Edit Movie Command";
	private static final String MOVIE_EDITED = "movieEdited";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long movieId = Long.valueOf(request.getParameter(MOVIE_ID));
		MovieLogic movieLogic = new MovieLogic();
		try{
			Movie movie = movieLogic.findMovieById(movieId);
			request.setAttribute(MOVIE_EDITED, movie);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return ConfigurationManager.getProperty(PAGE_EDIT_MOVIE);
	}
}
