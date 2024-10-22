package aplicacion;

import org.apache.log4j.PropertyConfigurator;
import servicio.Wordle;

/**
 * <b>Class</b>: LaunchApplication <br/>
 * @author 2024 Daniel Gutierrez Villegas <br/>
 * <u>Developed by</u>: Daniel Gutierrez Villegas <br/>
 * <u>Changes:</u><br/>
 * <ul>
 *   <li>
 *     Jun 25, 2024 Creación de Clase.
 *   </li>
 * </ul>
 */
public class LaunchApplication {

  public static void main(String[] args) {
    PropertyConfigurator.configure("log4j.properties");
    Wordle ap = new Wordle();
    ap.jugar();
  }
}
