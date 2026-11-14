package com.client45.client.module.modules.movement;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class MovementHud extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 140, 0, 2000, 1));

	public MovementHud() {
		super("Movement HUD", "Shows speed and movement info", Category.MOVEMENT);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.player == null) return;
		double speed = Math.sqrt(mc.player.getVelocity().x * mc.player.getVelocity().x + mc.player.getVelocity().z * mc.player.getVelocity().z) * 20;
		String text = String.format("Speed: %.2f b/s", speed);
		context.drawTextWithShadow(mc.textRenderer, text, x.getInt(), y.getInt(), 0x39FF00);
	}
}
