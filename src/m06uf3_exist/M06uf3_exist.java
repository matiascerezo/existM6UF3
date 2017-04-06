package m06uf3_exist;

import java.util.List;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;

public class M06uf3_exist {

    static String[] etiquetasNormales = {"COMMON", "BOTANICAL", "ZONE", "LIGHT", "PRICE", "AVAILABILITY"};
    static String[] etiquetasTraducidas = {"COMUN", "BOTANICA", "ZONA", "LUZ", "PRECIO", "DISPONIBILIDAD"};

    public static void imprimirPlantas(List<Node> ns) {
        for (Node n : ns) {
            System.out.println(n.getTextContent());
        }
    }

    private static void imprimirPlantaNode(Node planta) {
        if (planta != null) {
            NamedNodeMap attributes = planta.getAttributes();

            for (int i = 0; i < attributes.getLength(); i++) {
                System.out.println(attributes.item(i).getNodeValue());
            }

            System.out.println(planta.getTextContent());
        }
    }

    public static void main(String[] args) {

        ConfigConnexio cc = new ConfigConnexio();
        Consultes cs = new Consultes(cc.getCon());

        //Obtener todas las plantas.
        imprimirPlantas(cs.obtenirPlantes());

        //Traducir las etiquetas del inglés al castellano.
        cs.traducirNombreEtiquetas(etiquetasNormales, etiquetasTraducidas);

        //Para añadir una nueva planta al principio.
        cs.afegirPlanta("Planta4", "Botanical1", 0, "HOLA", "HOLAHOLA", "20158912");

        //Para eliminar el $ del precio.
        cs.modificarFormatoDolar();

        //Cerca planta per nom comú.
        imprimirPlantaNode(cs.cercarPerNomComu("Planta4"));

        //Afegir atribut a totes les plantes
        cs.afegirAtribut("Clase", "1");

        //Afegir un node de tipus text amb un valor per defecte a totes les plantes d'una mateixa zona.
        cs.afegirEtiqueta("prueba", "HolaHala", 0);

        //Para imprimir las plantas que estan en la zona X.
        imprimirPlantas(cs.cercarPlantesPerZona(4));

        //Para modificar el precio de una planta(Planta Bloodroot en este caso).
        cs.modificarPreuPlanta("Bloodroot", 5);

        //Para eliminar una planta con el nombre X. (Eliminar Bloodroot en este caso).
        cs.eliminarPlanta("Bloodroot");
    }
}