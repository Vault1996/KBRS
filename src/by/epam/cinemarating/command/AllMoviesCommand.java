package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Movie;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.MovieLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;


class AllMoviesCommand implements ActionCommand {
	private static final String MOVIES = "movies";
	private static final String SORT_BY = "sortBy";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_ALL_MOVIES = "path.page.allMovies";
	private static final String ERROR_MESSAGE = "Problem in All Movies Command";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		try {
			String sortBy = request.getParameter(SORT_BY);
			MovieLogic movieLogic = new MovieLogic();
			List<Movie> movies = movieLogic.getAllMovies(sortBy);
			request.setAttribute(MOVIES, movies);
			request.getSession().setAttribute(PAGE_NUMBER, 0);
			request.setAttribute(SORT_BY, sortBy);
			return ConfigurationManager.getProperty(PAGE_ALL_MOVIES);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE , e);
		}

	}
}
