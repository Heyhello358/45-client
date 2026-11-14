package com.client45.client.module.modules.render;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.effect.StatusEffectInstance;

public class PotionHud extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 260, 0, 2000, 1));

	public PotionHud() {
		super("Potion HUD", "Displays active potion effects", Category.RENDER);
		setEnabled(true);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.player == null) return;
		int sy = y.getInt();
		int sx = x.getInt();
		int i = 0;
		for (StatusEffectInstance effect : mc.player.getStatusEffects()) {
			String name = effect.getEffectType().value().getName().getString();
			int duration = effect.getDuration() / 20;
			String text = name + " " + (duration / 60) + ":" + String.format("%02d", duration % 60);
			context.drawTextWithShadow(mc.textRenderer, text, sx, sy + i * 12, 0x39FF00);
			i++;
		}
	}
}
