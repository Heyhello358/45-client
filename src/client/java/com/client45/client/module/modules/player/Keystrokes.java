package com.client45.client.module.modules.player;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.option.KeyBinding;
import org.lwjgl.glfw.GLFW;

public class Keystrokes extends Module {
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 44, 0, 2000, 1));
	public final NumberSetting size = addSetting(new NumberSetting("Size", "Key size", 18, 12, 40, 1));

	public Keystrokes() {
		super("Keystrokes", "Shows WASD and mouse buttons", Category.PLAYER);
		setEnabled(true);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null || mc.options == null) return;
		int sx = x.getInt();
		int sy = y.getInt();
		int s = size.getInt();
		int gap = 2;

		drawKey(context, mc.options.forwardKey, "W", sx + s + gap, sy, s);
		drawKey(context, mc.options.leftKey, "A", sx, sy + s + gap, s);
		drawKey(context, mc.options.backKey, "S", sx + s + gap, sy + s + gap, s);
		drawKey(context, mc.options.rightKey, "D", sx + 2 * (s + gap), sy + s + gap, s);
		drawKey(context, mc.options.jumpKey, "SPACE", sx, sy + 2 * (s + gap), s * 3 + gap * 2, s);
		drawMouse(context, GLFW.GLFW_MOUSE_BUTTON_LEFT, "LMB", sx + 3 * (s + gap) + 4, sy, s + 4, s);
		drawMouse(context, GLFW.GLFW_MOUSE_BUTTON_RIGHT, "RMB", sx + 3 * (s + gap) + 4, sy + s + gap, s + 4, s);
	}

	private void drawKey(DrawContext context, KeyBinding key, String label, int x, int y, int w) {
		drawKey(context, key, label, x, y, w, w);
	}

	private void drawKey(DrawContext context, KeyBinding key, String label, int x, int y, int w, int h) {
		boolean pressed = key.isPressed();
		int bg = pressed ? 0x8039FF00 : 0x80080808;
		int border = 0xFF39FF00;
		context.fill(x, y, x + w, y + h, bg);
		context.drawBorder(x, y, w, h, border);
		int tw = mc.textRenderer.getWidth(label);
		context.drawTextWithShadow(mc.textRenderer, label, x + (w - tw) / 2, y + (h - 8) / 2, 0xFFFFFF);
	}

	private void drawMouse(DrawContext context, int button, String label, int x, int y, int w, int h) {
		boolean pressed = GLFW.glfwGetMouseButton(mc.getWindow().getHandle(), button) == GLFW.GLFW_PRESS;
		int bg = pressed ? 0x8039FF00 : 0x80080808;
		int border = 0xFF39FF00;
		context.fill(x, y, x + w, y + h, bg);
		context.drawBorder(x, y, w, h, border);
		int tw = mc.textRenderer.getWidth(label);
		context.drawTextWithShadow(mc.textRenderer, label, x + (w - tw) / 2, y + (h - 8) / 2, 0xFFFFFF);
	}
}
