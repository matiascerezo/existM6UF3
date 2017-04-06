package Colecciones;

import org.xmldb.api.base.XMLDBException;

/**
 *
 * @author Matias
 */
public class Test {

    public static void main(String[] args) throws XMLDBException {

        Consultas c = new Consultas();
        
        //Para obtener el nombre de la coleccion actual.
        c.obtenerNombreColeccionActual();

        //Para obtener el nombre padre de la coleccion.
        c.obtenerNombreColeccionPadre();

        //Listar coleccion de hijos.
        for (String hijo : c.obtenerListaNombreColeccionHijas()) {
            System.out.println("Hijo: " + hijo);
        }

        //Crear nueva coleccion.
        c.crearColeccion("NuevaColeccion");

        //Eliminar nueva coleccion.
        c.eliminarColeccion("NuevaColeccion");

        //Comprobar si un recurso esta dentro de una coleccion.
        c.comprobarRecursoDentroColeccion("m06uf3/plantas", "plantas.xml");
         
        
        //Añadir un recurso.
        c.afegirRecursXML("prueba.xml");
        
        //Eliminar un recurso.
        c.eliminarRecursXML("prueba.xml");
        
        //Obtener recurso XML
        c.obtenerRecursoXML("prueba.xml");
        
        //Añadir recurso binario
        c.afegirRecursBinari("monkey.jpg"); //Una foto de un monito mu grasioso
        
        //Obtener recurso binario
        c.obtenirRecursBinari("monkey.jpg");
    }
}
