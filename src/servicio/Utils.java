package servicio;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import org.apache.log4j.Logger;

public class Utils {

  private static final Logger log = Logger.getLogger(Utils.class);

  public int numeroAleatorio(int max, int min) {
    if (min > max) {
      throw new IllegalArgumentException("El valor mínimo no puede ser mayor que el valor máximo.");
    }
    return ThreadLocalRandom.current().nextInt(min, max + 1);
  }

  public String pedirPalabra(String men, int tamanio) {
    Scanner lec = new Scanner(System.in);
    String palabra;
    boolean bandera;
    do {
      System.out.print(">Ingrese " + men + ": ");
      palabra = lec.nextLine().toLowerCase().trim();
      bandera = palabra.length() != tamanio || !palabra.chars().allMatch(Character::isLetter);
      if (bandera) {
        System.out.println("Caracteres o tamaño no permitido");
      }
    } while (bandera);

    return palabra;
  }

  public int pedirOpcionNumero(int li, int ls, String men) {
    Scanner lec = new Scanner(System.in);
    System.out.print(">Ingrese " + men + ": ");
    int opcionMenu = lec.nextInt();
    while (opcionMenu < li || opcionMenu > ls) {
      System.out.print(">Ingrese una " + men + " valida entre " + li + " y " + ls + ": ");
      opcionMenu = lec.nextInt();
    }
    return opcionMenu;
  }

  public void limpiarConsola(int opcion) {
    switch (opcion) {
      case 1 -> limpiarPantallaSistema();
      case 2 -> limpiarPantallaSistemaPowerShell();
      case 3 -> limpiarConsolaIDENetbeans();
      default -> System.out.println("Opción no válida");
    }
  }

  private void limpiarConsolaIDENetbeans() {
    try {
      Robot pressbot = new Robot();
      pressbot.keyPress(KeyEvent.VK_CONTROL);
      pressbot.keyPress(KeyEvent.VK_L);
      pressbot.keyRelease(KeyEvent.VK_CONTROL);
      pressbot.keyRelease(KeyEvent.VK_L);
    } catch (AWTException ex) {
      log.error("Error al limpiar la pantalla" + ex.getMessage());
    }
  }

  private void limpiarPantallaSistema() {
    for (int i = 0; i < 100; i++) {
      System.out.println();
    }
    System.out.print("\033[H\033[2J");
    System.out.flush();
  }

  private void limpiarPantallaSistemaPowerShell() {
    try {
      String sistemaOperativo = System.getProperty("os.name");
      ArrayList<String> comando = new ArrayList<>();
      if (sistemaOperativo.contains("Windows")) {
        comando.add("cmd");
        comando.add("/C");
        comando.add("cls");
      } else {
        comando.add("clear"); // UNIX => MAC, LINUX
      }
      ProcessBuilder pb = new ProcessBuilder(comando);
      Process iniciarProceso = pb.inheritIO().start();
      iniciarProceso.waitFor();
    } catch (InterruptedException ex) {
      Thread.currentThread().interrupt();
      log.error("Proceso interrumpido: " + ex.getMessage());
    } catch (Exception ex) {
      log.error("Error al limpiar la pantalla: " + ex.getMessage());
    }
  }
}
