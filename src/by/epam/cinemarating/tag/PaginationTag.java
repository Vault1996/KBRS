package by.epam.cinemarating.tag;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

public class PaginationTag extends TagSupport{
	private static final String PAGINATION_START = "<div class=\"text-center\"><ul class=\"pagination\">";
	private static final String LIST_ITEM_START = "<li";
	private static final String CLASS = " class=\"active\"";
	private static final String LIST_ITEM_END = ">";
	private static final String REFERENCE = "<a href=\"/controller?command=change_page&next=";
	private static final String REFERENCE_END = "\">";
	private static final String END_OF_REFERENCE = "</a>";
	private static final String END_Of_LIST_ITEM = "</li>";
	private static final String PAGINATION_END = "</ul></div>";

	private static final String PAGE_NUMBER = "pageNumber";

	private int numberOfElements;
	private int numberOfElementsOnPage;

	public void setNumberOfElementsOnPage(int numberOfElementsOnPage) {
		this.numberOfElementsOnPage = numberOfElementsOnPage;
	}

	public void setNumberOfElements(int numberOfElements) {
		this.numberOfElements = numberOfElements;
	}

	@Override
	public int doStartTag() throws JspException {
		JspWriter out = pageContext.getOut();
		int pageNumber = Integer.parseInt(pageContext.getSession().getAttribute(PAGE_NUMBER).toString());
		try {
			out.write(PAGINATION_START);
			for(int i = 0; i <= (numberOfElements - 1) / numberOfElementsOnPage; i++) {
				out.write(LIST_ITEM_START);
				if (i == pageNumber) {
					out.write(CLASS);
				}
				out.write(LIST_ITEM_END);
				out.write(REFERENCE + i + REFERENCE_END);
				out.write(i + END_OF_REFERENCE + END_Of_LIST_ITEM);
			}
			out.write(PAGINATION_END);
		} catch (IOException e) {
			throw new JspException(e.getMessage());
		}
		return SKIP_BODY;
	}
}
