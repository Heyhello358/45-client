package com.client45.client.module;

import com.client45.client.module.modules.combat.*;
import com.client45.client.module.modules.movement.*;
import com.client45.client.module.modules.render.*;
import com.client45.client.module.modules.player.*;
import com.client45.client.module.modules.world.*;
import com.client45.client.module.modules.misc.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ModuleManager {
	private final List<Module> modules = new ArrayList<>();

	public ModuleManager() {
		// Combat
		add(new Crosshair());
		add(new HitIndicator());
		add(new AttackStats());

		// Movement
		add(new Sprint());
		add(new MovementHud());
		add(new DirectionIndicator());

		// Render
		add(new CustomNametags());
		add(new CustomScoreboard());
		add(new ArmorHud());
		add(new PotionHud());
		add(new EntityInfo());
		add(new CustomParticles());

		// Player
		add(new InventoryHud());
		add(new Coordinates());
		add(new CpsCounter());
		add(new Keystrokes());
		add(new FpsCounter());
		add(new PingDisplay());

		// World
		add(new TimeDisplay());
		add(new WeatherInfo());
		add(new WorldInfo());

		// Misc
		add(new ClientSettings());
		add(new Notifications());
		add(new ConfigManagerModule());
		add(new ThemeSelector());
	}

	private void add(Module module) {
		modules.add(module);
	}

	public List<Module> getModules() {
		return modules;
	}

	public List<Module> getModulesByCategory(Category category) {
		return modules.stream()
				.filter(m -> m.getCategory() == category)
				.collect(Collectors.toList());
	}

	public Module getModuleByName(String name) {
		return modules.stream()
				.filter(m -> m.getName().equalsIgnoreCase(name))
				.findFirst()
				.orElse(null);
	}

	public <T extends Module> T getModule(Class<T> clazz) {
		return modules.stream()
				.filter(clazz::isInstance)
				.map(clazz::cast)
				.findFirst()
				.orElse(null);
	}

	public void onTick() {
		for (Module module : modules) {
			if (module.isEnabled()) {
				module.onTick();
			}
		}
	}
}
