package datos;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import net.datastructures.TreeMap;
import modelo.Parada;
import modelo.Linea;
import modelo.Tramo;

public class CargaDatos {

    public static TreeMap<Integer, Parada> cargarParadas(String fileName) {
    	
        TreeMap<Integer, Parada> paradas = new TreeMap<Integer, Parada>();
        try {
            Scanner read = new Scanner(new File(fileName));
            read.useDelimiter("\\s*;\\s*");
            String direccion;
            int id;
            while (read.hasNext()) {
                id = Integer.parseInt(read.next());	
                direccion = read.next();
                paradas.put(id, new Parada(id, direccion));
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se pudo encontrar el archivo: " + fileName);
        } catch (Exception e) {
            System.err.println("Ocurrio un error al cargar las paradas desde el archivo: " + fileName);
            e.printStackTrace();
        }

        return paradas;
    }

    public static TreeMap<String, Linea> cargarLineas(String fileName, TreeMap<Integer, Parada> paradas) {
        TreeMap<String, Linea> lineas = new TreeMap<String, Linea>();

        try (Scanner read = new Scanner(new File(fileName))) {
            while (read.hasNextLine()) {
                String lineaInfo = read.nextLine();
                String[] parts = lineaInfo.split(";");
                if (parts.length >= 3) {
                    String nombre = parts[0].trim();
                    String sentido = parts[1].trim(); 
                    String[] paradasIdsStr = parts[2].split(",");
                    Integer[] paradasIds = new Integer[paradasIdsStr.length];
                    for (int i = 0; i < paradasIdsStr.length; i++) {
                        paradasIds[i] = Integer.parseInt(paradasIdsStr[i].trim());
                    }
                    Linea linea = new Linea(nombre);
                    for (Integer paradaId : paradasIds) {
                        Parada parada = paradas.get(paradaId);
                        if (parada != null) {
                            if ("I".equals(sentido)) {
                                linea.agregarParadasIda(parada);
                                parada.agregarLinea(linea); 
                            } else if ("R".equals(sentido)) {
                                linea.agregarParadasRegreso(parada);
                                parada.agregarLinea(linea);
                            }
                        }
                    }
                    lineas.put(nombre, linea);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("No se pudo encontrar el archivo: " + fileName);
        } catch (Exception e) {
            System.err.println("Ocurrió un error al cargar los tramos desde el archivo: " + fileName);
            e.printStackTrace();
        }

        return lineas;
    }


    public static List<Tramo> cargarTramos(String fileName, TreeMap<Integer, Parada> paradas) {
        List<Tramo> tramos = new ArrayList<Tramo>();

        try {
            Scanner read = new Scanner(new File(fileName));
            read.useDelimiter("\\s*;\\s*");
            Parada p1, p2;
            int tiempo, tipo;
            while (read.hasNext()) {
                p1 = paradas.get(Integer.parseInt(read.next()));
                p2 = paradas.get(Integer.parseInt(read.next()));
                tiempo = read.nextInt();
                tipo = read.nextInt();
                tramos.add(new Tramo(p1, p2, tiempo, tipo));
            }
            read.close();
        } catch (FileNotFoundException e) {
            System.err.println("No se pudo encontrar el archivo: " + fileName);
        } catch (Exception e) {
            System.err.println("Ocurrio un error al cargar los tramos desde el archivo: " + fileName);
            e.printStackTrace();
        }

        return tramos;
    }
}
