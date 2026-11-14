package com.client45.client.module.modules.render;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.ModeSetting;

public class CustomParticles extends Module {
	public final BooleanSetting enabled = addSetting(new BooleanSetting("Custom Particles", "Enable custom particles", false));
	public final ModeSetting style = addSetting(new ModeSetting("Style", "Particle style", "Default", "Default", "Minimal", "Neon"));

	public CustomParticles() {
		super("Custom Particles", "Customize particle effects", Category.RENDER);
	}
}
