package com.client45.client.module.modules.player;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class CpsCounter extends Module {
	public final BooleanSetting left = addSetting(new BooleanSetting("Left Click", "Track left CPS", true));
	public final BooleanSetting right = addSetting(new BooleanSetting("Right Click", "Track right CPS", true));
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 28, 0, 2000, 1));

	private final List<Long> leftClicks = new ArrayList<>();
	private final List<Long> rightClicks = new ArrayList<>();
	private boolean wasLeftPressed;
	private boolean wasRightPressed;

	public CpsCounter() {
		super("CPS Counter", "Displays clicks per second", Category.PLAYER);
		setEnabled(true);
	}

	@Override
	public void onTick() {
		if (mc == null || mc.getWindow() == null) return;
		long handle = mc.getWindow().getHandle();
		boolean leftPressed = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_LEFT) == GLFW.GLFW_PRESS;
		boolean rightPressed = GLFW.glfwGetMouseButton(handle, GLFW.GLFW_MOUSE_BUTTON_RIGHT) == GLFW.GLFW_PRESS;

		long now = System.currentTimeMillis();
		if (leftPressed && !wasLeftPressed) leftClicks.add(now);
		if (rightPressed && !wasRightPressed) rightClicks.add(now);
		wasLeftPressed = leftPressed;
		wasRightPressed = rightPressed;

		leftClicks.removeIf(t -> now - t > 1000);
		rightClicks.removeIf(t -> now - t > 1000);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null) return;
		StringBuilder sb = new StringBuilder();
		if (left.getValue()) sb.append("L: ").append(leftClicks.size());
		if (left.getValue() && right.getValue()) sb.append("  ");
		if (right.getValue()) sb.append("R: ").append(rightClicks.size());
		context.drawTextWithShadow(mc.textRenderer, sb.toString(), x.getInt(), y.getInt(), 0x39FF00);
	}
}
