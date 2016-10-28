package by.epam.cinemarating.tag;

import by.epam.cinemarating.entity.Role;
import by.epam.cinemarating.entity.User;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

public class NotGuestTag extends TagSupport {
	private static final String ACTIVE_USER = "activeUser";

	@Override
	public int doStartTag() throws JspException {
		User user = (User) pageContext.getSession().getAttribute(ACTIVE_USER);
		if (user != null && user.getRole() != null && user.getRole() != Role.GUEST) {
			return EVAL_BODY_INCLUDE;
		} else {
			return SKIP_BODY;
		}
	}

	@Override
	public int doAfterBody() throws JspTagException {
		return SKIP_BODY;
	}

	@Override
	public int doEndTag() throws JspException {
		return EVAL_PAGE;
	}
}