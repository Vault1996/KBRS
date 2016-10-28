package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.ReviewLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class DeleteReviewCommand implements ActionCommand {
	private static final String MOVIE_ID = "movie_id";
	private static final String USER_ID = "user_id";

	private static final String DELETE_REVIEW_STATUS = "deleteReviewStatus";

	private static final String SHOW_MOVIE_PAGE = "/controller?command=show_movie&movie_id=";

	private static final String ERROR_MESSAGE = "Problem in Delete Review Command";
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long movieId = Long.valueOf(request.getParameter(MOVIE_ID));
		long userId = Long.valueOf(request.getParameter(USER_ID));
		ReviewLogic reviewLogic = new ReviewLogic();
		try {
			reviewLogic.deleteReview(movieId, userId);
			request.setAttribute(DELETE_REVIEW_STATUS, true);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return SHOW_MOVIE_PAGE + movieId;
	}
}
