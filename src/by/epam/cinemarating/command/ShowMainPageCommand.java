package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Movie;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.MovieLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

class ShowMainPageCommand implements ActionCommand {
	private static final int NUMBER_OF_TOP_MOVIES = 3;
	private static final String PAGE_MAIN = "path.page.main";
	private static final String ERROR_MESSAGE = "Problem in Show Main Page Command";
	private static final String TOP_MOVIES = "topMovies";

	private static final String LAST_MOVIE_ID = "lastMovieId";
	private static final String LAST_MOVIE = "lastMovie";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		MovieLogic movieLogic = new MovieLogic();
		try {
			List<Movie> topMovies = movieLogic.findTopMovies(NUMBER_OF_TOP_MOVIES);
			request.setAttribute(TOP_MOVIES, topMovies);
			//get cookies
			Cookie[] cookies = request.getCookies();
			if (cookies != null) {
				for (Cookie cookie : cookies) {
					if (LAST_MOVIE_ID.equals(cookie.getName())) {
						long lastMovieId = Long.valueOf(cookie.getValue());
						Movie lastMovie = movieLogic.findMovieById(lastMovieId);
						request.setAttribute(LAST_MOVIE, lastMovie);
					}
				}
			}
			//
			return ConfigurationManager.getProperty(PAGE_MAIN);
		}catch(LogicException e){
			throw new CommandException(ERROR_MESSAGE, e);
		}
	}
}
