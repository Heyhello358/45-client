package com.client45.client.module.modules.combat;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.ColorSetting;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class Crosshair extends Module {
	public final BooleanSetting custom = addSetting(new BooleanSetting("Custom Crosshair", "Use custom crosshair", true));
	public final ColorSetting color = addSetting(new ColorSetting("Color", "Crosshair color", 0xFF39FF00));
	public final NumberSetting size = addSetting(new NumberSetting("Size", "Crosshair size", 4, 1, 20, 1));
	public final NumberSetting gap = addSetting(new NumberSetting("Gap", "Center gap", 2, 0, 10, 1));

	public Crosshair() {
		super("Crosshair", "Custom crosshair customization", Category.COMBAT);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (!custom.getValue() || mc == null) return;
		int cx = mc.getWindow().getScaledWidth() / 2;
		int cy = mc.getWindow().getScaledHeight() / 2;
		int s = size.getInt();
		int g = gap.getInt();
		int c = color.getValue() | 0xFF000000;
		// Horizontal
		context.fill(cx - s - g, cy - 1, cx - g, cy + 1, c);
		context.fill(cx + g, cy - 1, cx + s + g, cy + 1, c);
		// Vertical
		context.fill(cx - 1, cy - s - g, cx + 1, cy - g, c);
		context.fill(cx - 1, cy + g, cx + 1, cy + s + g, c);
	}
}
