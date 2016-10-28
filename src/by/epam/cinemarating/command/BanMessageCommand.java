package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Ban;
import by.epam.cinemarating.entity.BanMessage;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.function.TimeConverter;
import by.epam.cinemarating.logic.BanLogic;
import by.epam.cinemarating.exception.LogicException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Optional;

class BanMessageCommand implements ActionCommand {
	private static final String BAN_ID = "banId";
	private static final String BAN_MESSAGE = "banMessage";
	private static final String BAN_MESSAGE_ADDED_STATUS = "banMessageAddedStatus";
	private static final String BAN_MESSAGE_EXISTS_STATUS = "banMessageExistsStatus";
	private static final String SHOW_BAN_COMMAND = "/controller?command=show_ban";
	private static final String ERROR_MESSAGE = "Problem in Ban Message Command";
	private static final String BAN = "ban";
	private static final String TIME_LEFT = "timeLeft";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long banId = Long.valueOf(request.getParameter(BAN_ID));
		String banMessage = request.getParameter(BAN_MESSAGE);
		BanLogic banLogic = new BanLogic();
		try {
			boolean isAdded = banLogic.leaveMessage(banId, banMessage);
			Optional<Ban> banMessageOptional = banLogic.findBanByBanId(banId);
			Ban ban = banMessageOptional.orElse(new Ban());
			BanMessage existingBanMessage = banLogic.findBanMessageById(ban.getBanId());
			request.setAttribute(BAN, ban);
			request.setAttribute(TIME_LEFT, TimeConverter.findDifference(ban.getTill(),
					new Timestamp(System.currentTimeMillis())));
			request.setAttribute(BAN_MESSAGE, existingBanMessage);
			if (isAdded) {
				request.setAttribute(BAN_MESSAGE_ADDED_STATUS, true);
			} else {
				request.setAttribute(BAN_MESSAGE_EXISTS_STATUS, true);
			}
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
		return SHOW_BAN_COMMAND;
	}
}
