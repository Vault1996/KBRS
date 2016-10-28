package by.epam.cinemarating.command;

import by.epam.cinemarating.entity.User;
import by.epam.cinemarating.exception.CommandException;
import by.epam.cinemarating.logic.EditProfileLogic;
import by.epam.cinemarating.exception.LogicException;
import by.epam.cinemarating.logic.UserLogic;
import by.epam.cinemarating.resource.ConfigurationManager;
import by.epam.cinemarating.validation.EditProfileValidator;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.nio.file.Paths;


class EditProfileCommand implements ActionCommand {
	private static final String SHOW_USER_COMMAND = "/controller?command=show_user&user_id=";
	private static final String PAGE_EDIT_PROFILE = "path.page.editProfile";
	private static final String NAME = "name";
	private static final String SURNAME = "surname";
	private static final String PHOTO = "photo";
	private static final String NEW_PASSWORD = "newPassword";
	private static final String REPEAT_PASSWORD = "repeatPassword";
	private static final String ACTIVE_USER = "activeUser";
	private static final String INVALID_DATA_MESSAGE = "invalidDataMessage";
	private static final String USER_IMAGES_LOCATION = "images/user/";
	private static final String ERROR_MESSAGE = "Problem with changing info";
	private static final String USER_ID = "user_id";
	private static final String USER_EDITED = "userEdited";

	@Override
	public String execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
		long userId = Long.valueOf(request.getParameter(USER_ID));
		String name = request.getParameter(NAME);
		String surname = request.getParameter(SURNAME);
		String newPassword = request.getParameter(NEW_PASSWORD);
		String repeatPassword = request.getParameter(REPEAT_PASSWORD);

		EditProfileValidator editProfileValidator = new EditProfileValidator();

		if (editProfileValidator.validate(name, surname, newPassword, repeatPassword)) {
			try {
				Part photoPart = request.getPart(PHOTO);
				String fileName = Paths.get(photoPart.getSubmittedFileName()).getFileName().toString(); // MS IE fix.
				UserLogic userLogic = new UserLogic();
				User user = userLogic.findUserById(userId);
				User activeUser = (User) request.getSession().getAttribute(ACTIVE_USER);
				if (name != null && !name.isEmpty()) {
					user.setName(name);
					activeUser.setName(name);
				}
				if (surname != null && !surname.isEmpty()) {
					user.setSurname(surname);
					activeUser.setSurname(surname);
				}
//				if (newPassword != null && !newPassword.isEmpty()) {
//					user.setPassword(new MD5Hash().hexHash(newPassword));
//					activeUser.setPassword(new MD5Hash().hexHash(newPassword));
//				}
				if (photoPart.getSize() != 0) {
					user.setPhoto(USER_IMAGES_LOCATION + fileName);
					activeUser.setPhoto(USER_IMAGES_LOCATION + fileName);
					photoPart.write(request.getServletContext().getRealPath("") + USER_IMAGES_LOCATION + fileName);
				}
				EditProfileLogic editProfileLogic = new EditProfileLogic();
				editProfileLogic.updateProfile(user);
				request.getSession().setAttribute(ACTIVE_USER, activeUser);
				return SHOW_USER_COMMAND + userId;
			} catch (LogicException|IOException|ServletException e) {
				throw new CommandException(ERROR_MESSAGE, e);
			}
		} else {
			UserLogic userLogic = new UserLogic();
			User user;
			try {
				user = userLogic.findUserById(userId);
			} catch (LogicException e) {
				throw new CommandException(ERROR_MESSAGE, e);
			}
			request.setAttribute(USER_EDITED, user);
			request.setAttribute(INVALID_DATA_MESSAGE, true);
			return ConfigurationManager.getProperty(PAGE_EDIT_PROFILE);
		}
	}
}
