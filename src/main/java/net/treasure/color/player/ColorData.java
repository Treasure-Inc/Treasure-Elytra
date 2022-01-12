package net.treasure.color.player;

import lombok.Getter;
import lombok.Setter;
import net.treasure.color.Color;
import net.treasure.common.Patterns;
import net.treasure.core.TreasurePlugin;

import java.util.List;
import java.util.regex.Matcher;

public class ColorData {

    @Getter
    private final List<java.awt.Color> colors;

    @Getter
    private float currentIndex = -1;

    @Getter
    private final float speed;

    @Getter
    @Setter
    private boolean revertWhenDone, forward = true;

    public ColorData(Color color, float speed, boolean revertWhenDone) {
        this.colors = color.getColors();
        this.speed = speed;
        this.revertWhenDone = revertWhenDone;
    }

    public java.awt.Color next() {
        currentIndex += forward ? (speed) : (-speed);
        if (forward ? currentIndex >= colors.size() : currentIndex < 0) {
            currentIndex = revertWhenDone ? (forward ? colors.size() - 2 : 1) : 0;
            forward = revertWhenDone != forward;
        }
        return colors.get((int) (currentIndex = Math.max(0, currentIndex)));
    }

    public org.bukkit.Color nextBukkit() {
        java.awt.Color color = next();
        return org.bukkit.Color.fromRGB(color.getRed(), color.getGreen(), color.getBlue());
    }

    public static ColorData initialize(String input) {
        Matcher colorMatcher = Patterns.COLOR.matcher(input);
        String colorName = "";
        boolean revertWhenDone = false;
        float colorSpeed = 1;
        while (colorMatcher.find()) {
            String _type = colorMatcher.group("type");
            String _colorValue = colorMatcher.group("value");
            if (_type.equalsIgnoreCase("name"))
                colorName = _colorValue;
            else if (_type.equalsIgnoreCase("revertWhenDone"))
                revertWhenDone = Boolean.parseBoolean(_colorValue);
            else if (_type.equalsIgnoreCase("speed")) {
                try {
                    colorSpeed = Float.parseFloat(_colorValue);
                } catch (Exception ignored) {
                }
            }
        }
        return new ColorData(TreasurePlugin.getInstance().getColorManager().get(colorName), colorSpeed, revertWhenDone);
    }
}