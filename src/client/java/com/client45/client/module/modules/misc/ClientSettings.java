package com.client45.client.module.modules.misc;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.NumberSetting;

public class ClientSettings extends Module {
	public final BooleanSetting blur = addSetting(new BooleanSetting("GUI Blur", "Blur behind GUI", true));
	public final NumberSetting guiScale = addSetting(new NumberSetting("GUI Scale", "ClickGUI scale", 1.0, 0.5, 2.0, 0.1));
	public final BooleanSetting animations = addSetting(new BooleanSetting("Animations", "Enable GUI animations", true));

	public ClientSettings() {
		super("Client Settings", "General client configuration", Category.MISC);
	}
}
