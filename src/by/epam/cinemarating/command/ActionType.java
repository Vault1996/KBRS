package by.epam.cinemarating.command;

public enum ActionType {
	LOGIN(new LoginCommand()),
	LOGOUT(new LogoutCommand()),
	REGISTRATION(new RegistrationCommand()),
	CHANGE_LANGUAGE(new ChangeLanguageCommand()),
	REDIRECT(new RedirectCommand()),
	ALL_MOVIES(new AllMoviesCommand()),
	CHANGE_PAGE(new ChangePageCommand()),
	ALL_USERS(new AllUsersCommand()),
	SHOW_MOVIE(new ShowMovieCommand()),
	SHOW_USER(new ShowUserCommand()),
	EDIT_PROFILE(new EditProfileCommand()),
	LEAVE_REVIEW(new LeaveReviewCommand()),
	SHOW_MAIN_PAGE(new ShowMainPageCommand()),
	SHOW_BAN(new ShowBanCommand()),
	EDIT_REVIEW(new EditReviewCommand()),
	DELETE_REVIEW(new DeleteReviewCommand()),
	SHOW_EDIT_REVIEW(new ShowEditReviewCommand()),
	SHOW_EDIT_PROFILE(new ShowEditProfileCommand()),
	DELETE_USER(new DeleteUserCommand()),
	BAN_MESSAGE(new BanMessageCommand()),
	SHOW_BAN_MESSAGES(new ShowBanMessagesCommand()),
	DELETE_ALL_BAN_MESSAGES(new DeleteAllBanMessagesCommand()),
	ADD_MOVIE(new AddMovieCommand()),
	DELETE_MOVIE(new DeleteMovieCommand()),
	SHOW_EDIT_MOVIE(new ShowEditMovieCommand()),
	EDIT_MOVIE(new EditMovieCommand()),
	SHOW_BAN_USER(new ShowBanUserCommand()),
	BAN_USER(new BanUserCommand()),
	UNBAN_USER(new UnbanUserCommand()),
	SHOW_MY_MOVIES(new ShowMyMoviesCommand()),
	ACCESS_RIGHTS(new AccessRightsCommand()),
	CHECK_PIN(new CheckPinCommand()),
	SHOW_CHECK_PIN(new ShowCheckPinCommand());

	private ActionCommand actionCommand;

	ActionType(ActionCommand actionCommand) {
		this.actionCommand = actionCommand;
	}

	public ActionCommand getActionCommand() {
		return actionCommand;
	}
}
