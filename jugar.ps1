# Compila los archivos Java
javac -encoding UTF-8 -cp ".;lib\*" -d bin src\aplicacion\LaunchApplication.java src\constantes\ConstColor.java src\servicio\Acentos.java src\servicio\File.java src\servicio\Utils.java src\servicio\Wordle.java

# Verifica si la compilación fue exitosa antes de intentar ejecutar
if ($?) {
    # Ejecuta la aplicación
    java -cp ".;lib\*;bin" aplicacion.LaunchApplication
} else {
    Write-Host "La compilación falló. No se puede ejecutar la aplicación."
}
