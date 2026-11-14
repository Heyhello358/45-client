package com.client45.client.module.modules.combat;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class AttackStats extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 164, 0, 2000, 1));

	private int hits;
	private int crits;
	private long sessionStart = System.currentTimeMillis();

	public AttackStats() {
		super("Attack Statistics", "Tracks attack stats", Category.COMBAT);
	}

	public void registerHit(boolean crit) {
		hits++;
		if (crit) crits++;
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null) return;
		String text = String.format("Hits: %d | Crits: %d", hits, crits);
		context.drawTextWithShadow(mc.textRenderer, text, x.getInt(), y.getInt(), 0x39FF00);
	}
}
