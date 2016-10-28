package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.LoginLogic;
import by.epam.cinemarating.logic.TokenLogic;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class CheckPinCommand implements ActionCommand {
	private static final String NEXT_PAGE = "nextPage";
	private static final String PIN = "pin";
	private static final String FROM = "from";
	private static final String ACTIVE_USER = "activeUser";
	private static final String TOKEN = "token";

	/**
	 * Method that validates data from request, do specific checkUser and return page URI of the page to proceed.
	 *
	 * @param request  an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 * @return page URI as {@link String} of next page
	 * @throws CommandException
	 */
	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String from = request.getParameter(FROM);
		String nextPage = request.getParameter(NEXT_PAGE);
		String pin = request.getParameter(PIN);
		User activeUser = (User) request.getSession().getAttribute(ACTIVE_USER);
		String token = request.getSession().getAttribute(TOKEN).toString();
		LoginLogic loginLogic = new LoginLogic();
		TokenLogic tokenLogic = new TokenLogic();
		try{
			boolean flag1 = loginLogic.checkUserByPin(activeUser.getUserId(), pin);
			boolean flag2 = tokenLogic.findToken(activeUser.getUserId(), token);
			if (flag1 & flag2) {
				return nextPage;
			} else {
				return from;
			}
		} catch (LogicException e) {
			throw new CommandException("HELLO", e);
		}
	}
}
