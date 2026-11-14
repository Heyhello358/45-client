package com.client45.client.module.modules.render;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class ArmorHud extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 180, 0, 2000, 1));

	public ArmorHud() {
		super("Armor HUD", "Displays armor durability", Category.RENDER);
		setEnabled(true);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.player == null) return;
		int sx = x.getInt();
		int sy = y.getInt();
		for (int i = 0; i < 4; i++) {
			ItemStack stack = mc.player.getInventory().getArmorStack(3 - i);
			if (!stack.isEmpty()) {
				context.drawItem(stack, sx, sy + i * 18);
				if (stack.isDamageable()) {
					int max = stack.getMaxDamage();
					int dmg = max - stack.getDamage();
					String dur = dmg + "/" + max;
					context.drawTextWithShadow(mc.textRenderer, dur, sx + 18, sy + i * 18 + 4, 0x39FF00);
				}
			}
		}
	}
}
