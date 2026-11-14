package com.client45.client.module.modules.movement;

import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;

public class Sprint extends Module {
	public final BooleanSetting keepSprint = addSetting(new BooleanSetting("Keep Sprint", "Maintain sprint while attacking", true));

	public Sprint() {
		super("Sprint", "Automatically sprints when moving forward", Category.MOVEMENT);
	}

	@Override
	public void onTick() {
		if (mc == null || mc.player == null) return;
		if (mc.player.forwardSpeed > 0 && !mc.player.isSneaking() && !mc.player.isUsingItem()) {
			mc.player.setSprinting(true);
		}
	}
}
