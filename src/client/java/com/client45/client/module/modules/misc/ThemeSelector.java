package com.client45.client.module.modules.misc;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.ModeSetting;

public class ThemeSelector extends Module {
	public final ModeSetting theme = addSetting(new ModeSetting("Theme", "Client theme", "Neon Green", "Neon Green", "Classic", "Purple", "Blue"));

	public ThemeSelector() {
		super("Theme Selector", "Change client color theme", Category.MISC);
	}
}
