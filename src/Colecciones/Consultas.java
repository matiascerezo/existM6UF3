/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Colecciones;

import java.util.Arrays;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.CollectionManagementService;

/**
 *
 * @author Matias
 */
public class Consultas {

    Collection coleccion;
    CollectionManagementService cms;
    ConfigConnexio cc = new ConfigConnexio();

    public Consultas() {
        this.coleccion = cc.conectar();
    }

    /**
     * Para obtener el nombre de la conexion actual.
     */
    public void obtenerNombreColeccionActual() {
        try {
            System.out.println("Nombre colección actual: " + coleccion.getName());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Obtener el nombre de la coleccion padre.
     */
    public void obtenerNombreColeccionPadre() {
        try {
            System.out.println("Nombre colección padre: " + coleccion.getParentCollection());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Para obtener la lista de nombres de las colecciones hijas.
     *
     */
    public void obtenerListaNombreColeccionHijas() {
        try {
            System.out.println("Lista nombre colección hijas: " + Arrays.toString(coleccion.listChildCollections()));
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Para crear una nueva colección con el nombre pasado por parametro.
     *
     * @param nombreColeccion
     */
    public void crearColeccion(String nombreColeccion) {
        try {
            cms.createCollection(nombreColeccion);
            System.out.println("Colección creada!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que elimina la colección con el nombre pasado por parametro.
     *
     * @param nombreColeccion
     */
    public void eliminarColeccion(String nombreColeccion) {
        try {
            cms.removeCollection(nombreColeccion);
            System.out.println("Colección " + nombreColeccion + " eliminada.");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Comprueba si un recurso se encuentra dentro de una coleccion con el
     * nombre X o no.
     *
     * @param nombreColeccion
     * @param nombreRecurso
     * @return
     */
    public boolean comprobarRecursoDentroColeccion(String nombreColeccion, String nombreRecurso) {
        return true;
    }
}
