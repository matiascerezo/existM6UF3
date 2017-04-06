package Colecciones;

/**
 *
 * @author Matias
 */
public class Test {

    public static void main(String[] args) {
        
        Consultas c = new Consultas();
        
        //Para obtener el nombre de la coleccion actual.
        c.obtenerNombreColeccionActual();
        
        //Para obtener el nombre padre de la coleccion
        c.obtenerNombreColeccionPadre();
        
        //Crear nueva coleccion
        c.crearColeccion("NuevaColeccion");
        
        //Eliminar nueva coleccion
        //c.eliminarColeccion("NuevaColeccion");
        
        //Comprobar si un recurso esta dentro de una coleccion.
        //System.out.println(c.comprobarRecursoDentroColeccion("", ""));
    }

}
