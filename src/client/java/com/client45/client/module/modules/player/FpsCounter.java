package com.client45.client.module.modules.player;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.NumberSetting;
import net.minecraft.client.gui.DrawContext;

public class FpsCounter extends Module {
	public final BooleanSetting showLabel = addSetting(new BooleanSetting("Show Label", "Show 'FPS:' prefix", true));
	public final NumberSetting x = addSetting(new NumberSetting("X", "X position", 4, 0, 2000, 1));
	public final NumberSetting y = addSetting(new NumberSetting("Y", "Y position", 4, 0, 2000, 1));
	public final NumberSetting scale = addSetting(new NumberSetting("Scale", "Text scale", 1.0, 0.5, 3.0, 0.1));

	public FpsCounter() {
		super("FPS Counter", "Displays current FPS", Category.PLAYER);
		setEnabled(true);
	}

	@Override
	public void onRenderHud(DrawContext context, float tickDelta) {
		if (mc == null) return;
		String text = showLabel.getValue() ? "FPS: " + mc.getCurrentFps() : String.valueOf(mc.getCurrentFps());
		float s = scale.getFloat();
		context.getMatrices().push();
		context.getMatrices().scale(s, s, 1);
		context.drawTextWithShadow(mc.textRenderer, text, (int)(x.getInt() / s), (int)(y.getInt() / s), 0x39FF00);
		context.getMatrices().pop();
	}
}
