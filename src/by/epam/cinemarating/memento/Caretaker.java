package by.epam.cinemarating.memento;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

public class Caretaker {
	private MementoRequestAttributes memento;

	public Caretaker(MementoRequestAttributes memento) {
		this.memento = memento;
	}

	/**
	 * Sets the data from request to memento
	 * @param request HttpServletRequest object to retrieve data
	 */
	public void extractToMemento(HttpServletRequest request) {
		Map<String, Object> attributes = new HashMap<>();
		Enumeration<String> attrs = request.getAttributeNames();
		while (attrs.hasMoreElements()) {
			String attr = attrs.nextElement();
			Object value = request.getAttribute(attr);
			attributes.put(attr, value);
		}
		memento.setAttributes(attributes);
	}

	/**
	 * Fills the request from memento
	 * @param request HttpServletRequest object to set data
	 */
	public void fillRequest(HttpServletRequest request) {
		for (Map.Entry<String, Object> entry : memento.getAttributes().entrySet()) {
			request.setAttribute(entry.getKey(), entry.getValue());
		}
	}
}
