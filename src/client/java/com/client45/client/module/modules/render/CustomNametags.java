package com.client45.client.module.modules.render;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.ColorSetting;

public class CustomNametags extends Module {
	public final BooleanSetting health = addSetting(new BooleanSetting("Show Health", "Show player health", true));
	public final BooleanSetting distance = addSetting(new BooleanSetting("Show Distance", "Show distance to player", true));
	public final ColorSetting color = addSetting(new ColorSetting("Color", "Nametag color", 0xFF39FF00));

	public CustomNametags() {
		super("Custom Nametags", "Enhanced nametag rendering", Category.RENDER);
	}
}
