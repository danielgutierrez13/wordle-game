package servicio;

import constantes.ConstColor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Objects;
import org.jetbrains.annotations.NotNull;

public class Wordle {

  private static final String[] LETRAS = {
          "Q", "W", "E", "R", "T", "Y", "U", "I", "O", "P",
          "A", "S", "D", "F", "G", "H", "J", "K", "L",
          "Z", "X", "C", "V", "B", "N", "M"
  };

  private final HashMap<String, String> letrasClaveTablero = new HashMap<>();
  private final ArrayList<String> letrasListaTablero = new ArrayList<>();
  private final ArrayList<String> palabrasListaFiltro = new ArrayList<>();
  private final ArrayList<String> palabrasListaUsadas = new ArrayList<>();
  private int[][] valorestablero;

  private final Utils util = new Utils();
  private final File file = new File();

  public void jugar() {
    int opcionNumeroMenu;
    rellenarLetrasClaveLista();
    do {
      util.limpiarConsola(2);
      imprimirMenuJuego();
      opcionNumeroMenu = util.pedirOpcionNumero(1, 2, "opción de menú");
      util.limpiarConsola(2);
      if (opcionNumeroMenu == 1) {
        jugarWordle();
      }
    } while (opcionNumeroMenu != 2);
  }

  private void jugarWordle() {
    int tamanioPalabra = util.pedirOpcionNumero(1, 8, "cantidad de letras de la palabra");
    int numeroIntentos = util.pedirOpcionNumero(1, 8, "cantidad de intentos");
    palabrasListaFiltro.clear();
    valorestablero = new int[numeroIntentos][tamanioPalabra];
    file.filtrarPalabrasTamanio(palabrasListaFiltro, tamanioPalabra);
    file.escribirPalabrasFiltradas(palabrasListaFiltro);
    String aleatorio = obtenerPalabraAleatoria(tamanioPalabra);
    empezarJuegoWordle(numeroIntentos, aleatorio, tamanioPalabra);
  }

  private void imprimirMensajeContinuar(String aleatorio) {
    System.out.println("La palabra era: " + aleatorio + "\nPresione enter para continuar...");
    new java.util.Scanner(System.in).nextLine();
  }

  private void empezarJuegoWordle(int tamaniointentos, String aleatorio, int tamaniopalabra) {
    rellenarLetrasClaveDiccionario();
    boolean aux = false;
    String[] palabras = new String[tamaniointentos];
    int contador = 0;
    palabras[contador] = null;
    while (!aux && contador < tamaniointentos) {
      util.limpiarConsola(2);
      imprimirJuegoWordle(palabras, tamaniopalabra);
      System.out.print("\n");
      palabras[contador] = ingresarPalabraJuego(tamaniopalabra);
      aux = verificarTableroJuego(palabras[contador], aleatorio, contador, tamaniopalabra);
      contador++;
    }
    util.limpiarConsola(2);
    imprimirJuegoWordle(palabras, tamaniopalabra);
    imprimirMensajeContinuar(aleatorio);
  }

  private void imprimirJuegoWordle(String[] palabras, int tamaniopalabra) {
    imprimirTableroJuego(palabras, tamaniopalabra);
    System.out.print("\n");
    imprimirTecladoJuego();
  }

  private void rellenarLetrasClaveLista() {
    Collections.addAll(letrasListaTablero, LETRAS);
  }

  private void rellenarLetrasClaveDiccionario() {
    letrasListaTablero.forEach(letra -> letrasClaveTablero.put(letra, "blanco"));
  }

  private void imprimirMenuJuego() {
    System.out.println("\t\t Menu");
    System.out.println("1) Empezar Juego");
    System.out.println("2) Salir");
  }

  private void imprimirTecladoJuego() {
    int contador = 0;
    for (String letra : letrasListaTablero) {
      if (Objects.equals(letrasClaveTablero.get(letra), "verde")) {
        System.out.print(ConstColor.ANSI_GREEN_BACKGROUND + ConstColor.ANSI_BLACK + "[ " + letra + " ]" + ConstColor.ANSI_RESET);
      } else if (Objects.equals(letrasClaveTablero.get(letra), "azul")) {
        System.out.print(ConstColor.ANSI_CYAN_BACKGROUND + ConstColor.ANSI_BLACK + "[ " + letra + " ]" + ConstColor.ANSI_RESET);
      } else if (Objects.equals(letrasClaveTablero.get(letra), "amarillo")) {
        System.out.print(ConstColor.ANSI_YELLOW_BACKGROUND + ConstColor.ANSI_BLACK + "[ " + letra + " ]" + ConstColor.ANSI_RESET);
      } else {
        System.out.print(ConstColor.ANSI_WHITE_BACKGROUND + ConstColor.ANSI_BLACK + "[ " + letra + " ]" + ConstColor.ANSI_RESET);
      }
      contador++;
      if (contador % 10 == 0) {
        System.out.print("\n");
      }
      if (contador % 20 == 0) {
        System.out.print("\t");
      }
    }
    System.out.print("\n");
  }

  private void imprimirTableroJuego(String @NotNull [] palabras, int tamaniopalabra) {
    for (int k = 0; k < palabras.length; k++) {
      imprimirLineaSuperior(tamaniopalabra);
      if (palabras[k] != null) {
        imprimirLineaPalabra(palabras[k], k);
      } else {
        imprimirLineaVacia(tamaniopalabra);
      }
    }
    imprimirLineaSuperior(tamaniopalabra);
  }

  private void imprimirLineaSuperior(int tamaniopalabras) {
    for (int l = 0; l < tamaniopalabras; l++) {
      System.out.print("+---");
    }
    System.out.println("+");
  }

  private void imprimirLineaPalabra(@NotNull String palabra, int indice) {
    for (int i = 0; i < palabra.length(); i++) {
      System.out.print("|");
      char letra = palabra.charAt(i);
      imprimirLetraConColor(letra, indice, i);
    }
    System.out.println("|");
  }

  private void imprimirLetraConColor(char letra, int indice, int posicion) {
    String color;
    switch (valorestablero[indice][posicion]) {
      case 1 -> color = ConstColor.ANSI_GREEN_BACKGROUND;
      case 2 -> color = ConstColor.ANSI_YELLOW_BACKGROUND;
      default -> color = ConstColor.ANSI_CYAN_BACKGROUND;
    }
    System.out.print(color + ConstColor.ANSI_BLACK + " " + Character.toUpperCase(letra) + " " + ConstColor.ANSI_RESET);
  }

  private void imprimirLineaVacia(int tamaniopalabras) {
    for (int l = 0; l < tamaniopalabras; l++) {
      System.out.print("|   ");
    }
    System.out.println("|");
  }

  private String ingresarPalabraJuego(int tamanioPalabra) {
    String palabra = util.pedirPalabra("Palabra", tamanioPalabra);
    while (!(palabrasListaFiltro.contains(palabra))) {
      System.out.println("Esta palabra no esta en la base de datos");
      palabra = util.pedirPalabra("Palabra", tamanioPalabra);
    }
    return palabra;
  }

  private String obtenerPalabraAleatoria(int tamanioPalabra) {
    String palabra;
    int tamanio = palabrasListaFiltro.size();
    int aleatorio;
    do {
      aleatorio = util.numeroAleatorio(tamanio,0);
      palabra = palabrasListaFiltro.get(aleatorio);
    } while (palabrasListaUsadas.contains(palabra) || palabra.length() != tamanioPalabra);

    palabrasListaUsadas.add(palabra);
    return palabra;
  }

  private boolean verificarTableroJuego(String palabra, String aleatorio, int indice, int tamanioPalabra) {
    int contador = 0;
    palabra = palabra.toLowerCase();
    aleatorio = aleatorio.toLowerCase();

    for (int i = 0; i < palabra.length(); i++) {
      char letraPalabra = palabra.charAt(i);
      String clave = String.valueOf(letraPalabra).toUpperCase();
      boolean coincide = false;

      for (int j = 0; j < aleatorio.length(); j++) {
        if (letraPalabra == aleatorio.charAt(j)) {
          coincide = true;
          if (i == j) {
            procesarLetraCorrecta(clave, indice, i);
            contador++;
          } else {
            procesarLetraDesplazada(clave, indice, i);
          }
          break;
        }
      }

      if (!coincide) {
        procesarLetraIncorrecta(clave, indice, i);
      }
    }
    return contador == tamanioPalabra;
  }

  private void procesarLetraCorrecta(String clave, int indice, int posicion) {
    letrasClaveTablero.put(clave, "verde");
    valorestablero[indice][posicion] = 1;
  }

  private void procesarLetraDesplazada(String clave, int indice, int posicion) {
    if (!"verde".equals(letrasClaveTablero.get(clave))) {
      valorestablero[indice][posicion] = 2;
      letrasClaveTablero.put(clave, "amarillo");
    }
  }

  private void procesarLetraIncorrecta(String clave, int indice, int posicion) {
    letrasClaveTablero.put(clave, "azul");
    valorestablero[indice][posicion] = 3;
  }
}
