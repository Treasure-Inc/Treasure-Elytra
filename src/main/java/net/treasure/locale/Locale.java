package net.treasure.locale;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Locale {
    ENGLISH("en"),
    TURKISH("tr");
    final String key;
}