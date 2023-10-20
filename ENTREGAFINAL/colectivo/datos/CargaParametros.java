package datos;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CargaParametros {

	private static String archivoLinea;
	private static String archivoParada;
	private static String archivoTramo;

	public static void parametros() throws IOException {

		Properties prop = new Properties();
		InputStream input = new FileInputStream("config.properties");
		// load a properties file
		prop.load(input);
		// get the property value
		archivoLinea = prop.getProperty("linea");
		archivoParada = prop.getProperty("parada");
		archivoTramo = prop.getProperty("tramo");
	}

	public static String getArchivoLinea() {
		return archivoLinea;
	}

	public static String getArchivoParada() {
		return archivoParada;
	}

	public static String getArchivoTramo() {
		return archivoTramo;
	}

}
