package com.client45.client.module.modules.combat;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.ColorSetting;
import net.minecraft.client.gui.DrawContext;

public class HitIndicator extends Module {
	public final BooleanSetting show = addSetting(new BooleanSetting("Show Indicator", "Show hit feedback", true));
	public final ColorSetting color = addSetting(new ColorSetting("Color", "Indicator color", 0xFF39FF00));

	private long lastHitTime = 0;

	public HitIndicator() {
		super("Hit Indicator", "Visual feedback on hits", Category.COMBAT);
	}

	public void onHit() {
		lastHitTime = System.currentTimeMillis();
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (!show.getValue() || mc == null) return;
		long elapsed = System.currentTimeMillis() - lastHitTime;
		if (elapsed > 300) return;
		float alpha = 1.0f - (elapsed / 300.0f);
		int a = (int)(alpha * 180);
		int c = (color.getValue() & 0x00FFFFFF) | (a << 24);
		int cx = mc.getWindow().getScaledWidth() / 2;
		int cy = mc.getWindow().getScaledHeight() / 2;
		context.fill(cx - 20, cy - 20, cx + 20, cy + 20, c);
	}
}
