package org.orinn.tuchetao;

import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {

    private static final LegacyComponentSerializer SERIALIZER = LegacyComponentSerializer.legacyAmpersand();

    public static String TranslateColorCodes(String message) {
        // Chuyển đổi mã màu & thông thường trước
        message = SERIALIZER.deserialize(message).toString();

        // Sau đó, chuyển đổi mã màu hex
        Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher matcher = pattern.matcher(message);
        while (matcher.find()) {
            String color = message.substring(matcher.start(), matcher.end());
            message = message.replace(color, TextColor.fromHexString(color.replace("&", "#")) + "");
        }
        return message;
    }

}