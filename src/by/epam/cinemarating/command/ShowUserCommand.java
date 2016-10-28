package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Ban;
import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.logic.BanLogic;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.UserLogic;
import by.epam.cinemarating.resource.ConfigurationManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;

class ShowUserCommand implements ActionCommand {
	private static final String USER_ID = "user_id";
	private static final String USER = "user";
	private static final String ACTIVE_USER = "activeUser";

	private static final String PAGE_USER = "path.page.user";
	private static final String PAGE_PROFILE = "path.page.profile";
	private static final String ERROR_MESSAGE = "Problem in Show User Command";
	private static final String SHOW_MAIN_PAGE_COMMAND = "/controller?command=show_main_page";
	private static final String IS_BANNED = "isBanned";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long userId = Integer.valueOf(request.getParameter(USER_ID));
		User activeUser = (User) request.getSession().getAttribute(ACTIVE_USER);
		UserLogic userLogic = new UserLogic();
		BanLogic banLogic = new BanLogic();
		String page;
		try {
			User user = userLogic.findUserById(userId);
			Optional<Ban> ban = banLogic.findBanByUserId(userId);
			if (user != null) {
				request.setAttribute(USER, user);
				if (user.getUserId() == activeUser.getUserId()) {
					page = PAGE_PROFILE;
				} else {
					request.setAttribute(IS_BANNED, ban.isPresent());
					page = PAGE_USER;
				}
			} else {
				page = SHOW_MAIN_PAGE_COMMAND;
			}
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return ConfigurationManager.getProperty(page);
	}
}
