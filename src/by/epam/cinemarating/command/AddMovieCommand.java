package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Movie;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.MovieLogic;
import by.epam.cinemarating.resource.ConfigurationManager;
import by.epam.cinemarating.validation.AddMovieValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;


class AddMovieCommand implements ActionCommand {

	private static final String NAME = "name";
	private static final String YEAR = "year";
	private static final String COUNTRY = "country";
	private static final String DESCRIPTION = "description";
	private static final String ERROR_MESSAGE = "Can't read data";
	private static final double DEFAULT_RATING = 0.0;
	private static final String POSTER = "poster";
	private static final String MOVIE_POSTERS_LOCATION = "images/movies/";
	private static final String MOVIE_ADDED_STATUS = "movieAddedStatus";
	private static final String PAGE_ADD_MOVIE = "path.page.addMovie";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String name = request.getParameter(NAME);
		String year = request.getParameter(YEAR);
		String country = request.getParameter(COUNTRY);
		String description = request.getParameter(DESCRIPTION);
		AddMovieValidator addMovieValidator = new AddMovieValidator();
		if (addMovieValidator.validate(name, year, country, description)) {
			try {
				Part posterPart = request.getPart(POSTER);
				String fileName = Paths.get(posterPart.getSubmittedFileName()).getFileName().toString(); // MS IE fix.
				posterPart.write(request.getServletContext().getRealPath("") + MOVIE_POSTERS_LOCATION + fileName);
				Movie movie = new Movie(0, name, Integer.valueOf(year), description, country, DEFAULT_RATING, MOVIE_POSTERS_LOCATION + fileName);
				MovieLogic movieLogic = new MovieLogic();
				movieLogic.addMovie(movie);
				request.setAttribute(MOVIE_ADDED_STATUS, true);

			} catch (IOException | ServletException | LogicException e) {
				throw new CommandException(ERROR_MESSAGE, e);
			}
		}
		return ConfigurationManager.getProperty(PAGE_ADD_MOVIE);
	}
}
