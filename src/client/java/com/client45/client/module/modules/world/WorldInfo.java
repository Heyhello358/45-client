package com.client45.client.module.modules.world;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class WorldInfo extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 324, 0, 2000, 1));

	public WorldInfo() {
		super("World Information", "Shows world seed and dimension", Category.WORLD);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.world == null) return;
		String dim = mc.world.getRegistryKey().getValue().getPath();
		context.drawTextWithShadow(mc.textRenderer, "Dimension: " + dim, x.getInt(), y.getInt(), 0x39FF00);
	}
}
