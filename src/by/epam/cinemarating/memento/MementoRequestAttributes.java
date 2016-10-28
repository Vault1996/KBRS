package by.epam.cinemarating.memento;

import java.util.HashMap;
import java.util.Map;

public class MementoRequestAttributes {
	private Map<String, Object> attributes = new HashMap<>();

	Map<String, Object> getAttributes() {
		return attributes;
	}

	void setAttributes(Map<String, Object> attributes) {
		this.attributes = attributes;
	}
}
