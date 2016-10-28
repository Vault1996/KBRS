package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionCommand {
	/**
	 * Method that validates data from request, do specific checkUser and return page URI of the page to proceed.
	 * @param request an {@link HttpServletRequest} object that contains the request the client has made of the servlet
	 * @param response an {@link HttpServletResponse} object that contains the response the servlet sends to the client
	 * @return page URI as {@link String} of next page
	 * @throws CommandException
	 */
	String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
