package servicio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.apache.log4j.Logger;
import org.jetbrains.annotations.NotNull;

public class File {
  private static final Logger logger = Logger.getLogger(File.class);
  private final Acentos acentos = new Acentos();

  public void escribirPalabrasFiltradas(@NotNull List<String> palabrasListaFiltro) {
    String direccion = "./assets/output/listado-filtrado.txt";
    try (PrintWriter pw = new PrintWriter(Files.newBufferedWriter(Path.of(direccion)))) {
      for (String palabra : palabrasListaFiltro) {
        pw.println(palabra);
      }
    } catch (IOException e) {
      logger.error("Error leyendo el archivo: " + direccion, e);
    }
  }

  public void filtrarPalabrasTamanio(List<String> palabrasListaFiltro, int tamanio) {
    String direccion = "./assets/input/listado-general.txt";

    try (BufferedReader br = Files.newBufferedReader(Path.of(direccion))) {
      String linea;
      while ((linea = br.readLine()) != null) {
        if (linea.trim().length() == tamanio) {
          linea = acentos.eliminarAcentos(linea.toLowerCase());
          palabrasListaFiltro.add(linea);
        }
      }
    } catch (IOException e) {
      logger.error("Error leyendo el archivo: " + direccion, e);
    }
  }
}
