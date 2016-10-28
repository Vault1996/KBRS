package by.epam.cinemarating.servlet;

import by.epam.cinemarating.command.*;
import by.epam.cinemarating.database.ConnectionPool;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.UnsupportedCommandException;
import by.epam.cinemarating.memento.Caretaker;
import by.epam.cinemarating.memento.MementoRequestAttributes;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/controller")
@MultipartConfig
public class Controller extends HttpServlet {
	private static final String COMMAND = "command";
	private static final String MEMENTO = "memento";
	private static final String EXCEPTION = "exception";

	private static final Logger LOGGER = LogManager.getLogger(Controller.class);

	public Controller() {
		super();
	}
	@Override
	public void init() throws ServletException {
		super.init();
		LOGGER.info("Initializing servlet.");
	}
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		processRequest(request, response);
	}
	private void processRequest(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		try {
			String command = request.getParameter(COMMAND);
			ActionCommand actionCommand = ActionFactory.defineCommand(command);
			String page = actionCommand.execute(request, response);

			//***Saving changes after every command***
			MementoRequestAttributes memento = (MementoRequestAttributes) request.getSession().getAttribute(MEMENTO);
			if (memento == null) {
				memento = new MementoRequestAttributes();
			}
			Caretaker caretaker = new Caretaker(memento);
			if (ActionType.valueOf(command.toUpperCase()) != ActionType.CHANGE_LANGUAGE
					&& ActionType.valueOf(command.toUpperCase()) != ActionType.REDIRECT ) {
				caretaker.extractToMemento(request);
				request.getSession().setAttribute(MEMENTO, memento);
			} else {
				caretaker.fillRequest(request);
			}
			//***
			request.getRequestDispatcher(page).forward(request, response);
		} catch (UnsupportedCommandException | CommandException e) {
			LOGGER.error(e);
			request.getSession().setAttribute(EXCEPTION, ExceptionUtils.getStackTrace(e));
			response.sendError(500, e.getMessage());
		}
	}

	@Override
	public void destroy() {
		super.destroy();
		LOGGER.info("Servlet destroyed.");
		ConnectionPool.getInstance().closePool();
	}
}
