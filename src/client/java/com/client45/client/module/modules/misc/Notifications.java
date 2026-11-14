package com.client45.client.module.modules.misc;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;

public class Notifications extends Module {
	public final BooleanSetting moduleToggle = addSetting(new BooleanSetting("Module Toggle", "Notify on module toggle", true));
	public final BooleanSetting lowHealth = addSetting(new BooleanSetting("Low Health", "Notify on low health", true));

	public Notifications() {
		super("Notifications", "In-game notification system", Category.MISC);
		setEnabled(true);
	}
}
