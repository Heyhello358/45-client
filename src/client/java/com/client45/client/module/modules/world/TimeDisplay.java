package com.client45.client.module.modules.world;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class TimeDisplay extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 300, 0, 2000, 1));

	public TimeDisplay() {
		super("Time Display", "Shows in-game time", Category.WORLD);
		setEnabled(true);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.world == null) return;
		long time = mc.world.getTimeOfDay() % 24000;
		int hours = (int) ((time / 1000 + 6) % 24);
		int minutes = (int) ((time % 1000) * 60 / 1000);
		String text = String.format("Time: %02d:%02d", hours, minutes);
		context.drawTextWithShadow(mc.textRenderer, text, x.getInt(), y.getInt(), 0x39FF00);
	}
}
