package by.epam.cinemarating.tag;

import by.epam.cinemarating.entity.Role;
import by.epam.cinemarating.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class RoleLoginTag extends TagSupport{
	private static final String SEPARATOR = " : ";

	private User user;

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public int doStartTag() throws JspException {
		try {
			if (user != null && user.getRole() != Role.GUEST) {
				pageContext.getOut().write(user.getRole() + SEPARATOR + user.getLogin());
			} else {
				pageContext.getOut().write(Role.GUEST.toString());
			}
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
