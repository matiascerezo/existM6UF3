/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package m06uf3_exist;

import java.util.ArrayList;
import java.util.List;
import javax.xml.xquery.XQConnection;
import javax.xml.xquery.XQException;
import javax.xml.xquery.XQExpression;
import javax.xml.xquery.XQPreparedExpression;
import javax.xml.xquery.XQResultSequence;
import org.w3c.dom.Node;

/**
 *
 * @author Matias
 */
public class Consultes {

    private final XQConnection con;
    private XQExpression xqe;
    private XQPreparedExpression xqpe;

    public Consultes(XQConnection con) {
        this.con = con;
    }

    /**
     * Método que traduce todas las etiquetas de las plantas.
     *
     * @param etiquetaNormal
     * @param etiquetaTraducida
     */
    public void traducirNombreEtiquetas(String[] etiquetaNormal, String[] etiquetaTraducida) {
        try {
            xqe = con.createExpression();
            for (int i = 0; i < etiquetaNormal.length; i++) {
                String xq = "update rename doc('/m06uf3/plantas/plantas.xml')//PLANT/" + etiquetaNormal[i] + " as '" + etiquetaTraducida[i] + "'";
                xqe.executeCommand(xq);
                System.out.println("Etiquetas traducidas!");
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que modifica el precio de cada planta, eliminando el símbolo del
     * dólar.
     */
    public void modificarFormatoDolar() {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/m06uf3/plantas/plantas.xml')//PLANT/PRICE return update value $b with substring($b,2)";
            xqe.executeCommand(xq);
            System.out.println("Modificado!");
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que devuelve una lista con todas las plantas.
     *
     * @return
     */
    public List<Node> obtenirPlantes() {
        List<Node> plantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/m06uf3/plantas/plantas.xml')//PLANT return $b/COMMON";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantas;
    }

    /**
     * Método al que se le pasa por parametro un nombre de una planta y devuelve
     * el nodo con su correspondientes atributos.
     *
     * @param nom
     * @return
     */
    public Node cercarPerNomComu(String nom) {
        Node planta = null;
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/m06uf3/plantas/plantas.xml')"
                    + "//PLANT where every $a in $b/COMMON satisfies ($a = '" + nom + "') return $b";
            XQResultSequence rs = xqe.executeQuery(xq);
            rs.next();
            planta = rs.getItem().getNode();
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return planta;
    }

    /**
     * Método al que se le pasan por parametro los valores de la planta y la
     * añade al principio del todo.
     *
     * @param common
     * @param botanical
     * @param zone
     * @param light
     * @param price
     * @param availability
     */
    public void afegirPlanta(String common, String botanical, int zone, String light, String price, String availability) {
        try {
            xqe = con.createExpression();
            String xq = "update insert "
                    + "    <PLANT>"
                    + "        <COMMON>" + common + "</COMMON>"
                    + "        <BOTANICAL>" + botanical + "</BOTANICAL>"
                    + "        <ZONE>" + zone + "</ZONE>"
                    + "        <LIGHT>" + light + "</LIGHT>"
                    + "        <PRICE>" + price + "</PRICE>"
                    + "         <AVAILABILITY>" + availability + "</AVAILABILITY>"
                    + "    </PLANT>"
                    + "preceding doc('/m06uf3/plantas/plantas.xml')//PLANT[1]";
            xqe.executeCommand(xq);
            System.out.println("Añadida planta: " + common);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que añade un atributo a todas las plantas. El nombre y el valor
     * del atributo a añadir se le pasa por parametro.
     *
     * @param atributo
     * @param valor
     */
    public void afegirAtribut(String atributo, String valor) {
        try {
            xqe = con.createExpression();
            String xq = "update insert attribute " + atributo + " {'" + valor + "'} into doc('/m06uf3/plantas/plantas.xml')//PLANT";
            xqe.executeCommand(xq);
            System.out.println("Añadido el atributo " + atributo + " con valor: " + valor);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que añade la etiqueta con el nombre que se pasa por parametro con
     * el valor que se para por parametro teniendo en cuenta que solo se
     * añadirán esas etiquetas a las plantas con zona X (Se pasa por parametro).
     *
     * @param etiqueta
     * @param valor
     * @param zona
     */
    public void afegirEtiqueta(String etiqueta, String valor, int zona) {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/m06uf3/plantas/plantas.xml')//PLANT where every $a in $b/ZONE satisfies($a='" + zona + "') return update insert <" + etiqueta.toUpperCase() + "> {'" + valor + "'} </" + etiqueta.toUpperCase() + "> into $b";
            xqe.executeCommand(xq);
            System.out.println("Añadida la etiqueta: " + etiqueta + " con valor " + valor + " a las plantas de la zona: " + zona);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que obtiene las plantas con un rango de precio.
     *
     * @param minimo
     * @param maximo
     * @return
     */
    public List<Node> obtenirPerRangPreu(double minimo, double maximo) {
        List<Node> plantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc ('/plantas/plantes.xml')//PLANT where every $a in $b/PRICE satisfies($a >= '" + minimo + "' and $a <= '" + maximo + "') return $b";

            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                plantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return plantas;
    }

    /**
     * Método que devuelve las plantas que esten en la zona X.
     *
     * @param zona
     * @return
     */
    public List<Node> cercarPlantesPerZona(int zona) {
        List<Node> listaPlantas = new ArrayList<>();
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/m06uf3/plantas/plantas.xml')//PLANT where every $a in $b/ZONE satisfies($a='" + zona + "') return $b";
            XQResultSequence rs = xqe.executeQuery(xq);
            while (rs.next()) {
                listaPlantas.add(rs.getItem().getNode());
            }
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
        return listaPlantas;
    }

    /**
     * Método que modifica el precio de la planta. Se cambia el precio de la planta
     * con el nombre que el usuario introduce.
     * @param nombre
     * @param precio 
     */
    public void modificarPreuPlanta(String nombre, double precio) {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/m06uf3/plantas/plantas.xml')//PLANT where every $a in $b/COMMON satisfies($a = '" + nombre + "') return update value $b/PRICE with " + precio;
            xqe.executeCommand(xq);
            System.out.println("Modificada la planta " + nombre + "\nPrecio actual: " + precio);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método para eliminar una planta por su nombre.
     *
     * @param nombre
     */
    public void eliminarPlanta(String nombre) {

        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/m06uf3/plantas/plantas.xml')//PLANT where every $a in $b/COMMON satisfies($a ='" + nombre + "') return update delete $b";
            xqe.executeCommand(xq);
            System.out.println("Eliminada la planta: " + nombre + ".");
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
