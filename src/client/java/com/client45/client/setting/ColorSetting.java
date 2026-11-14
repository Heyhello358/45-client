package com.client45.client.setting;

public class ColorSetting extends Setting<Integer> {
	public ColorSetting(String name, String description, int defaultValue) {
		super(name, description, defaultValue);
	}

	public int getRed() {
		return (value >> 16) & 0xFF;
	}

	public int getGreen() {
		return (value >> 8) & 0xFF;
	}

	public int getBlue() {
		return value & 0xFF;
	}

	public int getAlpha() {
		return (value >> 24) & 0xFF;
	}

	public int withAlpha(int alpha) {
		return (value & 0x00FFFFFF) | (alpha << 24);
	}
}
