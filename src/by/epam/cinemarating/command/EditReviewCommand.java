package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.ReviewLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class EditReviewCommand implements ActionCommand {
	private static final String SHOW_MOVIE_COMMAND = "/controller?command=show_movie&movie_id=";
	private static final String ERROR_MESSAGE = "Problem with changing info";
	private static final String MOVIE_ID = "movie_id";
	private static final String RATING = "rating";
	private static final String REVIEW = "review";
	private static final String USER_ID = "user_id";
	private static final String REVIEW_EDITED_STATUS = "reviewEditedStatus";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long movieId = Long.valueOf(request.getParameter(MOVIE_ID));
		long userId = Long.valueOf(request.getParameter(USER_ID));
		int rating = Integer.valueOf(request.getParameter(RATING));
		String review = request.getParameter(REVIEW);

		try {
			ReviewLogic reviewLogic = new ReviewLogic();
			reviewLogic.editReview(movieId, userId, rating, review);
			request.setAttribute(REVIEW_EDITED_STATUS, true);
			return SHOW_MOVIE_COMMAND + movieId;
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
	}
}
