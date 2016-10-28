package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Movie;
import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.MovieLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

class ShowMyMoviesCommand implements ActionCommand {
	private static final String ACTIVE_USER = "activeUser";
	private static final String MOVIES = "movies";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_MY_MOVIES = "path.page.myMovies";
	private static final String ERROR_MESSAGE = "Problem in All Movies Command";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		try {
			MovieLogic movieLogic = new MovieLogic();
			User activeUser = (User) request.getSession().getAttribute(ACTIVE_USER);
			List<Movie> movies = movieLogic.getMyMovies(activeUser.getUserId());
			request.setAttribute(MOVIES, movies);
			request.getSession().setAttribute(PAGE_NUMBER, 0);
			return ConfigurationManager.getProperty(PAGE_MY_MOVIES);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE , e);
		}
	}
}