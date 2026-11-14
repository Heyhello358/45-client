package com.client45;

import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Client45 implements ModInitializer {
	public static final String MOD_ID = "client45";
	public static final String NAME = "45 Client";
	public static final String VERSION = "1.0.0";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("{} v{} initialized!", NAME, VERSION);
	}
}
