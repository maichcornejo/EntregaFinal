package datos;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.junit.BeforeClass;
import org.junit.Test;

public class CargaParametrosTest {

	private static Properties prop;

	@BeforeClass
	public static void setUpBeforeClass() throws IOException {
		prop = new Properties();
		InputStream input = new FileInputStream("config.properties");
		prop.load(input);
	}

	@Test
	public void testCargarParametros() throws IOException {
		CargaParametros.parametros();
		String archivoLinea = CargaParametros.getArchivoLinea();
		String archivoParada = CargaParametros.getArchivoEstacion();
		String archivoTramo = CargaParametros.getArchivoTramo();

		assertNotNull(archivoLinea);
		assertNotNull(archivoParada);
		assertNotNull(archivoTramo);

		assertEquals(prop.getProperty("linea"), archivoLinea);
		assertEquals(prop.getProperty("parada"), archivoParada);
		assertEquals(prop.getProperty("tramo"), archivoTramo);

		// Llama al método para imprimir el contenido de los archivos
		imprimirContenidoArchivo("linea.txt");
		imprimirContenidoArchivo("parada.txt");
		imprimirContenidoArchivo("tramo.txt");
	}

	// Método para imprimir el contenido de un archivo
	private void imprimirContenidoArchivo(String archivo) throws IOException {
		InputStream input = new FileInputStream(archivo);
		byte[] buffer = new byte[1024];
		int bytesRead;
		System.out.println("Contenido de " + archivo + ":");
		System.out.println();
		while ((bytesRead = input.read(buffer)) != -1) {
			String chunk = new String(buffer, 0, bytesRead);
			System.out.print(chunk);
		}
		input.close();
	}
}
