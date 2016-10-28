package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.memento.Caretaker;
import by.epam.cinemarating.memento.MementoRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

class ChangePageCommand implements ActionCommand {
	private static final String NEXT = "next";
	private static final String CURRENT_PAGE = "page";
	private static final String MEMENTO = "memento";
	private static final String PAGE_NUMBER = "pageNumber";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String nextPage = request.getParameter(NEXT);

		MementoRequestAttributes memento = (MementoRequestAttributes) request.getSession().getAttribute(MEMENTO);
		Caretaker caretaker = new Caretaker(memento);
		caretaker.fillRequest(request);

		request.getSession().setAttribute(PAGE_NUMBER, nextPage);

		return request.getSession().getAttribute(CURRENT_PAGE).toString();
	}
}
