package com.client45.client.module.modules.world;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class WeatherInfo extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 312, 0, 2000, 1));

	public WeatherInfo() {
		super("Weather Information", "Shows current weather", Category.WORLD);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.world == null) return;
		String weather = "Clear";
		if (mc.world.isRaining()) weather = mc.world.isThundering() ? "Thunder" : "Rain";
		context.drawTextWithShadow(mc.textRenderer, "Weather: " + weather, x.getInt(), y.getInt(), 0x39FF00);
	}
}
