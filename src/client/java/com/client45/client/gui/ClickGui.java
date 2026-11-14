package com.client45.client.gui;

import com.client45.client.Client45Client;
import com.client45.client.module.Category;
import com.client45.client.module.Module;
import com.client45.client.setting.BooleanSetting;
import com.client45.client.setting.ModeSetting;
import com.client45.client.setting.NumberSetting;
import com.client45.client.setting.Setting;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

import java.util.ArrayList;
import java.util.List;

public class ClickGui extends Screen {
	private static final int PRIMARY = 0xFF39FF00;
	private static final int SECONDARY = 0xFF24CC00;
	private static final int BG = 0xE0080808;
	private static final int PANEL = 0xE0111111;
	private static final int TEXT = 0xFFFFFFFF;
	private static final int TEXT_SEC = 0xFF888888;
	private static final int HOVER = 0x4039FF00;

	private Category selectedCategory = Category.COMBAT;
	private Module expandedModule = null;
	private String searchQuery = "";
	private boolean searching = false;
	private int scrollOffset = 0;
	private float openProgress = 0f;
	private long openTime;

	// Panel positions
	private int panelX = 40;
	private int panelY = 40;
	private final int panelWidth = 140;
	private final int moduleWidth = 180;
	private final int categoryHeight = 22;
	private final int moduleHeight = 18;

	public ClickGui() {
		super(Text.literal("45 Client"));
		openTime = System.currentTimeMillis();
	}

	@Override
	protected void init() {
		openTime = System.currentTimeMillis();
		openProgress = 0f;
	}

	@Override
	public void render(DrawContext context, int mouseX, int mouseY, float delta) {
		// Animation
		long elapsed = System.currentTimeMillis() - openTime;
		openProgress = Math.min(1f, elapsed / 200f);
		float anim = easeOutCubic(openProgress);

		// Dark overlay
		context.fill(0, 0, width, height, (int)(0xA0 * anim) << 24);

		int offsetY = (int)((1 - anim) * -30);

		// Categories panel
		drawRoundedRect(context, panelX, panelY + offsetY, panelWidth, 30 + Category.values().length * categoryHeight + 10, 6, PANEL);
		// Header
		context.drawTextWithShadow(textRenderer, "45 CLIENT", panelX + 12, panelY + 10 + offsetY, PRIMARY);
		context.fill(panelX + 8, panelY + 26 + offsetY, panelX + panelWidth - 8, panelY + 27 + offsetY, PRIMARY);

		int catY = panelY + 34 + offsetY;
		for (Category cat : Category.values()) {
			boolean selected = cat == selectedCategory;
			boolean hovered = isHovered(mouseX, mouseY, panelX, catY, panelWidth, categoryHeight);
			if (selected || hovered) {
				context.fill(panelX + 4, catY, panelX + panelWidth - 4, catY + categoryHeight, HOVER);
			}
			if (selected) {
				context.fill(panelX + 4, catY, panelX + 6, catY + categoryHeight, PRIMARY);
			}
			context.drawTextWithShadow(textRenderer, cat.getName(), panelX + 14, catY + 6, selected ? PRIMARY : TEXT);
			catY += categoryHeight;
		}

		// Modules panel
		int modPanelX = panelX + panelWidth + 12;
		List<Module> modules = getFilteredModules();
		int modPanelHeight = 40 + Math.min(modules.size(), 14) * moduleHeight + 10;
		drawRoundedRect(context, modPanelX, panelY + offsetY, moduleWidth, modPanelHeight, 6, PANEL);

		// Search / header
		context.drawTextWithShadow(textRenderer, selectedCategory.getName(), modPanelX + 10, panelY + 10 + offsetY, PRIMARY);
		String searchHint = searching ? searchQuery + "_" : (searchQuery.isEmpty() ? "Search..." : searchQuery);
		context.drawTextWithShadow(textRenderer, searchHint, modPanelX + 10, panelY + 24 + offsetY, TEXT_SEC);

		int modY = panelY + 42 + offsetY - scrollOffset;
		for (Module module : modules) {
			if (modY + moduleHeight < panelY + 40 + offsetY || modY > panelY + offsetY + modPanelHeight - 10) {
				modY += moduleHeight;
				continue;
			}
			boolean hovered = isHovered(mouseX, mouseY, modPanelX + 4, modY, moduleWidth - 8, moduleHeight);
			if (hovered) {
				context.fill(modPanelX + 4, modY, modPanelX + moduleWidth - 4, modY + moduleHeight, HOVER);
			}
			int nameColor = module.isEnabled() ? PRIMARY : TEXT;
			context.drawTextWithShadow(textRenderer, module.getName(), modPanelX + 12, modY + 5, nameColor);
			// Toggle indicator
			int indX = modPanelX + moduleWidth - 20;
			context.fill(indX, modY + 5, indX + 10, modY + 13, module.isEnabled() ? PRIMARY : 0xFF333333);
			modY += moduleHeight;
		}

		// Expanded settings panel
		if (expandedModule != null) {
			int setX = modPanelX + moduleWidth + 12;
			List<Setting<?>> settings = expandedModule.getSettings();
			int setH = 40 + (settings.size() + 1) * 20;
			drawRoundedRect(context, setX, panelY + offsetY, 160, setH, 6, PANEL);
			context.drawTextWithShadow(textRenderer, expandedModule.getName(), setX + 10, panelY + 10 + offsetY, PRIMARY);
			context.drawTextWithShadow(textRenderer, expandedModule.getDescription(), setX + 10, panelY + 22 + offsetY, TEXT_SEC);

			int setY = panelY + 42 + offsetY;
			// Enabled toggle
			boolean enHov = isHovered(mouseX, mouseY, setX + 8, setY, 144, 16);
			if (enHov) context.fill(setX + 8, setY, setX + 152, setY + 16, HOVER);
			context.drawTextWithShadow(textRenderer, "Enabled", setX + 12, setY + 4, TEXT);
			context.fill(setX + 130, setY + 4, setX + 144, setY + 12, expandedModule.isEnabled() ? PRIMARY : 0xFF333333);
			setY += 20;

			for (Setting<?> setting : settings) {
				boolean shov = isHovered(mouseX, mouseY, setX + 8, setY, 144, 16);
				if (shov) context.fill(setX + 8, setY, setX + 152, setY + 16, HOVER);
				context.drawTextWithShadow(textRenderer, setting.getName(), setX + 12, setY + 4, TEXT);
				String val = getSettingDisplay(setting);
				int vw = textRenderer.getWidth(val);
				context.drawTextWithShadow(textRenderer, val, setX + 148 - vw, setY + 4, PRIMARY);
				setY += 20;
			}
		}

		// Footer branding
		context.drawTextWithShadow(textRenderer, "45 Client v1.0.0", width - 90, height - 14, TEXT_SEC);

		super.render(context, mouseX, mouseY, delta);
	}

	private String getSettingDisplay(Setting<?> setting) {
		if (setting instanceof BooleanSetting bs) return bs.getValue() ? "ON" : "OFF";
		if (setting instanceof NumberSetting ns) return String.format("%.1f", ns.getValue());
		if (setting instanceof ModeSetting ms) return ms.getValue();
		return "";
	}

	private List<Module> getFilteredModules() {
		List<Module> list = Client45Client.INSTANCE.moduleManager.getModulesByCategory(selectedCategory);
		if (searchQuery.isEmpty()) return list;
		List<Module> filtered = new ArrayList<>();
		String q = searchQuery.toLowerCase();
		for (Module m : Client45Client.INSTANCE.moduleManager.getModules()) {
			if (m.getName().toLowerCase().contains(q) || m.getDescription().toLowerCase().contains(q)) {
				filtered.add(m);
			}
		}
		return filtered;
	}

	@Override
	public boolean mouseClicked(double mouseX, double mouseY, int button) {
		int offsetY = 0;

		// Categories
		int catY = panelY + 34;
		for (Category cat : Category.values()) {
			if (isHovered(mouseX, mouseY, panelX, catY, panelWidth, categoryHeight)) {
				selectedCategory = cat;
				expandedModule = null;
				scrollOffset = 0;
				return true;
			}
			catY += categoryHeight;
		}

		// Modules
		int modPanelX = panelX + panelWidth + 12;
		List<Module> modules = getFilteredModules();
		int modY = panelY + 42 - scrollOffset;
		for (Module module : modules) {
			if (isHovered(mouseX, mouseY, modPanelX + 4, modY, moduleWidth - 8, moduleHeight)) {
				if (button == 0) {
					module.toggle();
					if (Client45Client.INSTANCE.configManager != null) {
						Client45Client.INSTANCE.configManager.save();
					}
				} else if (button == 1) {
					expandedModule = expandedModule == module ? null : module;
				}
				return true;
			}
			modY += moduleHeight;
		}

		// Settings
		if (expandedModule != null) {
			int setX = modPanelX + moduleWidth + 12;
			int setY = panelY + 42;
			if (isHovered(mouseX, mouseY, setX + 8, setY, 144, 16)) {
				expandedModule.toggle();
				return true;
			}
			setY += 20;
			for (Setting<?> setting : expandedModule.getSettings()) {
				if (isHovered(mouseX, mouseY, setX + 8, setY, 144, 16)) {
					if (setting instanceof BooleanSetting bs) {
						bs.toggle();
					} else if (setting instanceof ModeSetting ms) {
						ms.cycle();
					} else if (setting instanceof NumberSetting ns) {
						double next = ns.getValue() + ns.getIncrement();
						if (next > ns.getMax()) next = ns.getMin();
						ns.setValue(next);
					}
					if (Client45Client.INSTANCE.configManager != null) {
						Client45Client.INSTANCE.configManager.save();
					}
					return true;
				}
				setY += 20;
			}
		}
		return super.mouseClicked(mouseX, mouseY, button);
	}

	@Override
	public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
		if (keyCode == GLFW.GLFW_KEY_ESCAPE) {
			close();
			return true;
		}
		if (keyCode == GLFW.GLFW_KEY_BACKSPACE && searching) {
			if (!searchQuery.isEmpty()) {
				searchQuery = searchQuery.substring(0, searchQuery.length() - 1);
			}
			return true;
		}
		if (keyCode == GLFW.GLFW_KEY_ENTER) {
			searching = !searching;
			return true;
		}
		return super.keyPressed(keyCode, scanCode, modifiers);
	}

	@Override
	public boolean charTyped(char chr, int modifiers) {
		if (searching || searchQuery.length() > 0) {
			searching = true;
			if (Character.isLetterOrDigit(chr) || chr == ' ') {
				searchQuery += chr;
				return true;
			}
		}
		return super.charTyped(chr, modifiers);
	}

	@Override
	public boolean mouseScrolled(double mouseX, double mouseY, double horizontalAmount, double verticalAmount) {
		scrollOffset -= (int)(verticalAmount * 12);
		if (scrollOffset < 0) scrollOffset = 0;
		return true;
	}

	@Override
	public void close() {
		if (Client45Client.INSTANCE != null && Client45Client.INSTANCE.configManager != null) {
			Client45Client.INSTANCE.configManager.save();
		}
		super.close();
	}

	@Override
	public boolean shouldPause() {
		return false;
	}

	private boolean isHovered(double mx, double my, int x, int y, int w, int h) {
		return mx >= x && mx <= x + w && my >= y && my <= y + h;
	}

	private void drawRoundedRect(DrawContext context, int x, int y, int w, int h, int r, int color) {
		context.fill(x + r, y, x + w - r, y + h, color);
		context.fill(x, y + r, x + w, y + h - r, color);
		context.fill(x, y, x + r, y + r, color);
		context.fill(x + w - r, y, x + w, y + r, color);
		context.fill(x, y + h - r, x + r, y + h, color);
		context.fill(x + w - r, y + h - r, x + w, y + h, color);
	}

	private float easeOutCubic(float t) {
		return 1 - (float)Math.pow(1 - t, 3);
	}
}
