package com.client45.client.module.modules.render;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.NumberSetting;

public class EntityInfo extends Module {
	public final BooleanSetting health = addSetting(new BooleanSetting("Health", "Show entity health", true));
	public final BooleanSetting distance = addSetting(new BooleanSetting("Distance", "Show distance", true));
	public final NumberSetting range = addSetting(new NumberSetting("Range", "Max range", 64, 8, 128, 1));

	public EntityInfo() {
		super("Entity Information", "Shows info about entities", Category.RENDER);
	}
}
