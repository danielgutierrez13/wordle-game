package servicio;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class Acentos {

  private static final Map<Character, Character> ACENTO_MAP;

  static {
    ACENTO_MAP = new HashMap<>();
    ACENTO_MAP.put('á', 'a');
    ACENTO_MAP.put('é', 'e');
    ACENTO_MAP.put('í', 'i');
    ACENTO_MAP.put('ó', 'o');
    ACENTO_MAP.put('ú', 'u');
    ACENTO_MAP.put('ñ', 'n');
    ACENTO_MAP.put('Á', 'A');
    ACENTO_MAP.put('É', 'E');
    ACENTO_MAP.put('Í', 'I');
    ACENTO_MAP.put('Ó', 'O');
    ACENTO_MAP.put('Ú', 'U');
    ACENTO_MAP.put('Ñ', 'N');
  }

  public String eliminarAcentos(@NotNull String evaluar) {
    StringBuilder palabra = new StringBuilder(evaluar.length());

    for (int i = 0; i < evaluar.length(); i++) {
      char c = evaluar.charAt(i);
      palabra.append(ACENTO_MAP.getOrDefault(c, c));
    }

    return palabra.toString();
  }
}
