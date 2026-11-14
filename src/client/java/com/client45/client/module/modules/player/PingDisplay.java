package com.client45.client.module.modules.player;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.network.PlayerListEntry;

public class PingDisplay extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 100, 0, 2000, 1));

	public PingDisplay() {
		super("Ping Display", "Shows current latency", Category.PLAYER);
		setEnabled(true);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.player == null || mc.getNetworkHandler() == null) return;
		PlayerListEntry entry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getUuid());
		int ping = entry != null ? entry.getLatency() : 0;
		String text = "Ping: " + ping + "ms";
		context.drawTextWithShadow(mc.textRenderer, text, x.getInt(), y.getInt(), 0x39FF00);
	}
}
