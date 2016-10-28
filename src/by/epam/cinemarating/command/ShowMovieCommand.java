package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.*;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.AccessLogic;
import by.epam.cinemarating.logic.MovieLogic;
import by.epam.cinemarating.logic.ReviewLogic;
import by.epam.cinemarating.logic.UserLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

class ShowMovieCommand implements ActionCommand {
	private static final String MOVIE_ID = "movie_id";
	private static final String MOVIE = "movie";

	private static final String PAGE_MOVIE = "path.page.movie";
	private static final String REVIEWS = "reviews";

	private static final String ERROR_MESSAGE = "Problem in Show Movie Command";
	private static final String USER = "activeUser";
	private static final String ACTIVE_USER_REVIEW = "activeUserReview";

	private static final String LAST_MOVIE_ID = "lastMovieId";
	private static final int LIFE_TIME_OF_COOKIE = 6 * 60 * 60;
	private static final String ACCESS = "access";
	private static final String USERS = "users";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {

		try {
			long movieId = Long.parseLong(request.getParameter(MOVIE_ID));
			MovieLogic movieLogic = new MovieLogic();
			Movie movie = movieLogic.findMovieById(movieId);
			ReviewLogic reviewLogic = new ReviewLogic();
			List<ReviewAndRatingInfo> reviews = new ArrayList<>();
			ReviewAndRatingInfo activeUserReview = new ReviewAndRatingInfo();
			AccessLogic accessLogic = new AccessLogic();
			User activeUser = (User) request.getSession().getAttribute(USER);
			reviewLogic.getReviewsOfMovie(movieId, activeUser, reviews, activeUserReview);
			AccessType accessType = accessLogic.findAccessType(activeUser.getUserId(), movieId);
			UserLogic userLogic = new UserLogic();
			List<UserAccess> users = userLogic.getAllUsersWithAccesses(movieId);
			request.setAttribute(MOVIE, movie);
			request.setAttribute(USERS, users);
			request.setAttribute(ACTIVE_USER_REVIEW, activeUserReview);
			request.setAttribute(REVIEWS, reviews);
			request.setAttribute(ACCESS, accessType.toString());
			//add cookie
			Cookie lastMovieId = new Cookie(LAST_MOVIE_ID, String.valueOf(movieId));
			lastMovieId.setMaxAge(LIFE_TIME_OF_COOKIE);
			response.addCookie(lastMovieId);
			//
			return ConfigurationManager.getProperty(PAGE_MOVIE);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
	}
}
