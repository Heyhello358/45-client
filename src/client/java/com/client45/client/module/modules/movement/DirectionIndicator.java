package com.client45.client.module.modules.movement;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class DirectionIndicator extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 152, 0, 2000, 1));

	public DirectionIndicator() {
		super("Direction Indicator", "Shows facing direction", Category.MOVEMENT);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.player == null) return;
		float yaw = mc.player.getYaw();
		String dir = getDirection(yaw);
		context.drawTextWithShadow(mc.textRenderer, "Facing: " + dir + " (" + String.format("%.0f", yaw) + ")", x.getInt(), y.getInt(), 0x39FF00);
	}

	private String getDirection(float yaw) {
		yaw = yaw % 360;
		if (yaw < 0) yaw += 360;
		if (yaw >= 337.5 || yaw < 22.5) return "S";
		if (yaw < 67.5) return "SW";
		if (yaw < 112.5) return "W";
		if (yaw < 157.5) return "NW";
		if (yaw < 202.5) return "N";
		if (yaw < 247.5) return "NE";
		if (yaw < 292.5) return "E";
		return "SE";
	}
}
