package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.ReviewLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class LeaveReviewCommand implements ActionCommand {
	private static final String COMMAND_SHOW_MOVIE = "/controller?command=show_movie&movie_id=";
	private static final String RATING = "rating";
	private static final String REVIEW = "review";
	private static final String ERROR_MESSAGE = "Problem in Leave Review Command";
	private static final String REVIEW_ADDED_STATUS = "reviewAddedStatus";
	private static final String USER_ID = "user_id";
	private static final String MOVIE_ID = "movie_id";


	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		int rating = Integer.valueOf(request.getParameter(RATING));
		String review = request.getParameter(REVIEW);
		long movieId = Long.valueOf(request.getParameter(MOVIE_ID));
		long userId = Long.valueOf(request.getParameter(USER_ID));

		ReviewLogic reviewLogic = new ReviewLogic();
		try {
			boolean result = reviewLogic.leaveReview(movieId, userId, rating, review);
			if (result) {
				request.setAttribute(REVIEW_ADDED_STATUS, true);
			}
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return COMMAND_SHOW_MOVIE + movieId;
	}
}
