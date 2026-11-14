package com.client45.client.config;

import com.client45.Client45;
import com.client45.client.Client45Client;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.NumberSetting;
import com.client45.client.setting.ModeSetting;
import com.client45.client.setting.Setting;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;

public class ConfigManager {
	private final Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private final Path configDir;
	private final File configFile;

	public ConfigManager() {
		configDir = net.fabricmc.loader.api.FabricLoader.getInstance().getConfigDir().resolve("45client");
		configFile = configDir.resolve("config.json").toFile();
		try {
			Files.createDirectories(configDir);
		} catch (Exception e) {
			Client45.LOGGER.error("Failed to create config directory", e);
		}
	}

	public void save() {
		try {
			JsonObject root = new JsonObject();
			JsonObject modules = new JsonObject();
			for (Module module : Client45Client.INSTANCE.moduleManager.getModules()) {
				JsonObject modObj = new JsonObject();
				modObj.addProperty("enabled", module.isEnabled());
				modObj.addProperty("keybind", module.getKeybind());
				JsonObject settings = new JsonObject();
				for (Setting<?> setting : module.getSettings()) {
					if (setting instanceof BooleanSetting bs) {
						settings.addProperty(setting.getName(), bs.getValue());
					} else if (setting instanceof NumberSetting ns) {
						settings.addProperty(setting.getName(), ns.getValue());
					} else if (setting instanceof ModeSetting ms) {
						settings.addProperty(setting.getName(), ms.getValue());
					}
				}
				modObj.add("settings", settings);
				modules.add(module.getName(), modObj);
			}
			root.add("modules", modules);
			try (FileWriter writer = new FileWriter(configFile)) {
				gson.toJson(root, writer);
			}
			Client45.LOGGER.info("Config saved.");
		} catch (Exception e) {
			Client45.LOGGER.error("Failed to save config", e);
		}
	}

	public void load() {
		if (!configFile.exists()) {
			Client45.LOGGER.info("No config found, using defaults.");
			return;
		}
		try (FileReader reader = new FileReader(configFile)) {
			JsonObject root = JsonParser.parseReader(reader).getAsJsonObject();
			if (!root.has("modules")) return;
			JsonObject modules = root.getAsJsonObject("modules");
			for (Module module : Client45Client.INSTANCE.moduleManager.getModules()) {
				if (!modules.has(module.getName())) continue;
				JsonObject modObj = modules.getAsJsonObject(module.getName());
				if (modObj.has("enabled")) {
					module.setEnabled(modObj.get("enabled").getAsBoolean());
				}
				if (modObj.has("keybind")) {
					module.setKeybind(modObj.get("keybind").getAsInt());
				}
				if (modObj.has("settings")) {
					JsonObject settings = modObj.getAsJsonObject("settings");
					for (Setting<?> setting : module.getSettings()) {
						if (!settings.has(setting.getName())) continue;
						if (setting instanceof BooleanSetting bs) {
							bs.setValue(settings.get(setting.getName()).getAsBoolean());
						} else if (setting instanceof NumberSetting ns) {
							ns.setValue(settings.get(setting.getName()).getAsDouble());
						} else if (setting instanceof ModeSetting ms) {
							ms.setValue(settings.get(setting.getName()).getAsString());
						}
					}
				}
			}
			Client45.LOGGER.info("Config loaded.");
		} catch (Exception e) {
			Client45.LOGGER.error("Failed to load config", e);
		}
	}
}
