package br.com.starter.infrastructure.services.utils;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class StringSanitizer {
    public static String sanitizeString(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        // Converter para minúsculas
        String normalized = input.toLowerCase();

        // Remover acentos
        normalized = Normalizer.normalize(normalized, Normalizer.Form.NFD);
        Pattern pattern = Pattern.compile("\\p{M}");
        normalized = pattern.matcher(normalized).replaceAll("");

        // Remover espaços
        normalized = normalized.replaceAll("\\s+", "");

        return normalized;
    }
}
