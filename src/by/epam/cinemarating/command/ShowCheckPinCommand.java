package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ShowCheckPinCommand implements ActionCommand {
	private static final String FROM = "from";
	private static final String NEXT_PAGE = "nextPage";
	private static final String PAGE_CHECK_PIN = "path.page.checkPin";

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
		request.setAttribute(FROM, from);
		request.setAttribute(NEXT_PAGE, nextPage);
		return ConfigurationManager.getProperty(PAGE_CHECK_PIN);
	}
}
