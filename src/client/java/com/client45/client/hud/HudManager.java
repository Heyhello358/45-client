package com.client45.client.hud;

import com.client45.client.Client45Client;
import com.client45.client.module.Module;
import net.minecraft.client.gui.DrawContext;

public class HudManager {
	public void render(DrawContext context, float tickDelta) {
		if (Client45Client.INSTANCE == null || Client45Client.INSTANCE.moduleManager == null) return;
		for (Module module : Client45Client.INSTANCE.moduleManager.getModules()) {
			if (module.isEnabled()) {
				module.onRenderHud(context, tickDelta);
			}
		}
	}
}
