package com.client45.client.module.modules.render;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.NumberSetting;

public class CustomScoreboard extends Module {
	public final BooleanSetting hide = addSetting(new BooleanSetting("Hide Scoreboard", "Hide vanilla scoreboard", false));
	public final NumberSetting scale = addSetting(new NumberSetting("Scale", "Scoreboard scale", 1.0, 0.5, 2.0, 0.1));

	public CustomScoreboard() {
		super("Custom Scoreboard", "Customize scoreboard appearance", Category.RENDER);
	}
}
