package m06uf3_exist;

import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class M06uf3_exist {

    static String[] etiquetasNormales = {"COMMON","BOTANICAL", "ZONE", "LIGHT", "PRICE", "AVAILABILITY"};
    static String[] etiquetasTraducidas = {"PLANTA", "BOTANICA", "ZONA", "LUZ", "PRECIO", "DISPONIBILIDAD"};

    public static void imprimirPlantas(List<Node> ns) {
        for (Node n : ns) {
            System.out.println(n.getTextContent());
        }
    }

    private static void imprimirPlantaNode(Node libro) {
        if (libro != null) {
            NamedNodeMap attributes = libro.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.item(i).getNodeValue());
            }

            System.out.println(libro.getTextContent());
        }
    }

    public static void main(String[] args) {

        ConfigConnexio cc = new ConfigConnexio();
        Consultes cs = new Consultes(cc.getCon());
        

        //Llibre
//        String codigo = "16-205";
//        String categoria = "BBDD";
//        String fecha_pub = "2017-03-19";
//        String titulo = "BBDD XML con eXist";
//        String ventas = "7";
//
//        String codigo2 = "16-041";
//        String precio = "50€";
//        String etiqueta = "preu";
//        String atributo = "disponible";
//        String valor = "S";
//        String valor1 = "0€";

        //cs.afegirPlanta(codigo, categoria, fecha_pub, titulo, ventas);
//        cs.afegirAtribut(atributo, valor);
//
//        cs.afegirEtiqueta(etiqueta, valor1);
//        cs.modificarPreuNode(codigo2, precio);
        
        //LLISTAR Plantas
        //imprimirPlantas(cs.obtenirPlantes());

        //CERCAR PER TITOL
//        imprimirPlantaNode(cs.cercarPerNomComu("instant html"));
//
//        cs.eliminarEtiqueta(etiqueta);
//        cs.eliminarAtribut(atributo);
//        cs.eliminarPlanta(codigo);
        
        
        
        //Obtener todas las plantas.
        //imprimirLibros(cs.obtenirPlantes());
        
        //Traducir las etiquetas del ingles al castellano.
        //cs.traducirNombreEtiquetas(etiquetasNormales, etiquetasTraducidas);
        
        //Para añadir una nueva planta al principio.
        //cs.afegirPlanta("Planta4", "Botanical1", 0, "HOLA","HOLAHOLA", "20158912");
        
        //Para modificar el $ del precio.
        //cs.modificarFormatoDolar();
        
        //Cerca planta per nom comú.
        //imprimirPlantaNode(cs.cercarPerNomComu("Planta4"));
                
        //Afegir atribut a totes les plantes
        //cs.afegirAtribut("Clase", "1");
        
        //Afegir un node de tipus text amb un valor per defecte a totes les plantes d'una mateixa
        //zona.
        cs.afegirEtiqueta("prueba", "HolaHala", 0);
        
    }
}
