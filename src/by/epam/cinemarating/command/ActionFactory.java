package by.epam.cinemarating.command;

import by.epam.cinemarating.exception.UnsupportedCommandException;

public class ActionFactory {
	private static final String ERROR_MESSAGE = "Illegal argument: ";

	/**
	 * Defines the command by the input {@link String}
	 * @param command input {@link String} to define command
	 * @return {@link ActionCommand}
	 * @throws UnsupportedCommandException Throws when the is no such command
	 */
	public static ActionCommand defineCommand(String command) throws UnsupportedCommandException {
		command = command.toUpperCase();
		try {
			return ActionType.valueOf(command).getActionCommand();
		} catch (IllegalArgumentException e) {
			throw new UnsupportedCommandException(ERROR_MESSAGE + command, e);
		}
	}
}
