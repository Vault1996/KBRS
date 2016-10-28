package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.BanMessageInfo;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.logic.BanLogic;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

class ShowBanMessagesCommand implements ActionCommand {
	private static final String ERROR_MESSAGE = "Problem in Show Ban Message Command";
	private static final String BAN_MESSAGES = "banMessages";
	private static final String PAGE_NUMBER = "pageNumber";
	private static final String PAGE_BAN_MESSAGES = "path.page.banMessages";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		BanLogic banLogic = new BanLogic();
		try {
			List<BanMessageInfo> banMessageInfoList = banLogic.findAllBanMessages();
			request.setAttribute(BAN_MESSAGES, banMessageInfoList);
			request.getSession().setAttribute(PAGE_NUMBER, 0);
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return ConfigurationManager.getProperty(PAGE_BAN_MESSAGES);
	}
}
