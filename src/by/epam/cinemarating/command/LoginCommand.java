package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.Ban;
import by.epam.cinemarating.entity.BanMessage;
import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.function.TimeConverter;
import by.epam.cinemarating.logic.BanLogic;
import by.epam.cinemarating.logic.LoginLogic;
import by.epam.cinemarating.resource.ConfigurationManager;
import by.epam.cinemarating.validation.AuthenticationValidator;
import by.epam.cinemarating.validation.CaptchaValidator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.Timestamp;
import java.util.Optional;

class LoginCommand implements ActionCommand {
	private static final String LOGIN = "login";
	private static final String PASSWORD = "password";
	private static final String ACTIVE_USER = "activeUser";
	private static final String BAN = "ban";

	private static final String PAGE_LOGIN = "path.page.login";
	private static final String ERROR_LOGIN_VALIDATION = "errorLoginValidation";
	private static final String SHOW_MAIN_PAGE_COMMAND = "/controller?command=show_main_page";
	private static final String ERROR_MESSAGE = "Problem in Login Command";
	private static final String SHOW_BAN_COMMAND = "/controller?command=show_ban";
	private static final String TIME_LEFT = "timeLeft";
	private static final String ERROR_LOGIN_PASSWORD = "errorLoginPassword";
	private static final String BAN_MESSAGE = "banMessage";
	private static final String PIN = "pin";
	private static final String TOKEN = "token";
	private static final String CAPTCHA_RESPONSE = "g-recaptcha-response";
	private static final String CAPTCHA_CHALLENGE = "g-recaptcha-challenge";



	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		String login = request.getParameter(LOGIN);
		String password = request.getParameter(PASSWORD);
		String pin = request.getParameter(PIN);

		String remoteAddr = request.getRemoteAddr();
		String uresponse = request.getParameter(CAPTCHA_RESPONSE);

		CaptchaValidator captchaValidator = new CaptchaValidator();

		AuthenticationValidator validator = new AuthenticationValidator();
		boolean flag1 = validator.validate(login, password);
		boolean flag2 = captchaValidator.validate(remoteAddr, uresponse);

		if (!flag1 || !flag2) {
			request.setAttribute(ERROR_LOGIN_VALIDATION, true);
			request.setAttribute(LOGIN, login);
			//request.setAttribute(PASSWORD, password);
			return ConfigurationManager.getProperty(PAGE_LOGIN);
		}
		LoginLogic loginLogic = new LoginLogic();
		try {
			User user = new User();
			StringBuilder token = new StringBuilder();
			boolean flag = loginLogic.checkUser(login, password, pin, user, token);
			if (flag) {
				BanLogic banLogic = new BanLogic();
				Optional<Ban> banOptional = banLogic.findBanByUserId(user.getUserId());
				if (banOptional.isPresent()) {
					Ban ban = banOptional.get();
					BanMessage banMessage = banLogic.findBanMessageById(ban.getBanId());
					request.setAttribute(BAN, ban);
					request.setAttribute(TIME_LEFT, TimeConverter.findDifference(ban.getTill(),
							new Timestamp(System.currentTimeMillis())));
					request.setAttribute(BAN_MESSAGE, banMessage);
					return SHOW_BAN_COMMAND;
				} else {
					request.getSession().setAttribute(ACTIVE_USER, user);
					request.getSession().setAttribute(TOKEN, token.toString());
					return SHOW_MAIN_PAGE_COMMAND;
				}
			} else {
				request.setAttribute(LOGIN, login);
				//request.setAttribute(PASSWORD, password);
				request.setAttribute(ERROR_LOGIN_PASSWORD, true);
				return ConfigurationManager.getProperty(PAGE_LOGIN);
			}
		} catch (LogicException e) {
			throw new CommandException(ERROR_MESSAGE, e);
		}
	}
}
