package org.orinn.tuchetao;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.json.JSONComponentSerializer;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();
    private static final Pattern HEX_PATTERN = Pattern.compile("&#([A-Fa-f0-9]{6})");

    public static Component TranslateColorCodes(String message) {
        // Replace hex color codes with legacy color codes
        Matcher matcher = HEX_PATTERN.matcher(message);
        while (matcher.find()) {
            String hexColor = matcher.group(1);
            String legacyColor = "&x&" + hexColor.charAt(0) + "&" + hexColor.charAt(1) + "&" + hexColor.charAt(2) + "&" + hexColor.charAt(3) + "&" + hexColor.charAt(4) + "&" + hexColor.charAt(5);
            message = message.replace("&#" + hexColor, legacyColor);
        }

        // Deserialize the message with legacy color codes
        return SERIALIZER.deserialize(message);
    }
}
