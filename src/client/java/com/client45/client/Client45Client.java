package com.client45.client;

import com.client45.Client45;
import com.client45.client.gui.ClickGui;
import com.client45.client.module.ModuleManager;
import com.client45.client.config.ConfigManager;
import com.client45.client.hud.HudManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Client45Client implements ClientModInitializer {
	public static Client45Client INSTANCE;
	public static MinecraftClient mc;

	public ModuleManager moduleManager;
	public ConfigManager configManager;
	public HudManager hudManager;
	public ClickGui clickGui;

	private KeyBinding openGuiKey;

	@Override
	public void onInitializeClient() {
		INSTANCE = this;
		mc = MinecraftClient.getInstance();

		Client45.LOGGER.info("Initializing 45 Client client-side...");

		moduleManager = new ModuleManager();
		configManager = new ConfigManager();
		hudManager = new HudManager();
		clickGui = new ClickGui();

		// Register Right Shift to open GUI
		openGuiKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
				"key.client45.opengui",
				InputUtil.Type.KEYSYM,
				GLFW.GLFW_KEY_RIGHT_SHIFT,
				"category.client45"
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			if (openGuiKey.wasPressed()) {
				if (client.currentScreen == null) {
					client.setScreen(clickGui);
				} else if (client.currentScreen instanceof ClickGui) {
					client.setScreen(null);
				}
			}
			moduleManager.onTick();
		});

		HudRenderCallback.EVENT.register((drawContext, tickCounter) -> {
			if (mc != null && mc.player != null && !mc.options.hudHidden) {
				hudManager.render(drawContext, tickCounter.getTickDelta(false));
			}
		});

		// Load config after everything is set up
		configManager.load();

		Client45.LOGGER.info("45 Client ready! Press Right Shift to open the Click GUI.");
	}
}
