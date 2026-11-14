package com.client45.client.setting;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting<String> {
	private final List<String> modes;

	public ModeSetting(String name, String description, String defaultValue, String... modes) {
		super(name, description, defaultValue);
		this.modes = Arrays.asList(modes);
	}

	public List<String> getModes() {
		return modes;
	}

	public void cycle() {
		int index = modes.indexOf(value);
		index = (index + 1) % modes.size();
		setValue(modes.get(index));
	}
}
