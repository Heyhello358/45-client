package com.client45.client.module.modules.player;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.ItemStack;

public class InventoryHud extends Module {
	public final BooleanSetting showHotbar = addSetting(new BooleanSetting("Show Hotbar", "Highlight selected slot", true));
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 120, 0, 2000, 1));

	public InventoryHud() {
		super("Inventory HUD", "Shows inventory overview", Category.PLAYER);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.player == null) return;
		int sx = x.getInt();
		int sy = y.getInt();
		for (int i = 0; i < 4; i++) {
			ItemStack stack = mc.player.getInventory().getArmorStack(3 - i);
			if (!stack.isEmpty()) {
				context.drawItem(stack, sx + i * 18, sy);
			}
		}
	}
}
