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
 * @author Jorge
 */
public class Consultes {

    private final XQConnection con;
    private XQExpression xqe;
    private XQPreparedExpression xqpe;

    public Consultes(XQConnection con) {
        this.con = con;
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
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void modificarPreuNode(String codigo, String precio) {
        try {
            xqe = con.createExpression();
            String xq = "update value doc('/m06uf3/plantas/plantas.xml')//PLANT[@codigo='" + codigo + "']/preu with '" + precio + "'";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void eliminarPlanta(String codigo) {

        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/m06uf3/plantas/plantas.xml')//PLANT[@codigo='" + codigo + "']";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    public void eliminarEtiqueta(String etiqueta) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/m06uf3/plantas/plantas.xml')//PLANT/" + etiqueta;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    void eliminarAtribut(String atributo) {
        try {
            xqe = con.createExpression();
            String xq = "update delete doc('/m06uf3/plantas/plantas.xml')//PLANT/@" + atributo;
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Método que traduce todas las etiquetas de las plantas.
     *
     * @param etiquetaNormal
     * @param etiquetaTraducida
     */
    void traducirNombreEtiquetas(String[] etiquetaNormal, String[] etiquetaTraducida) {
        try {
            xqe = con.createExpression();
            for (int i = 0; i < etiquetaNormal.length; i++) {
                String xq = "update rename doc('/m06uf3/plantas/plantas.xml')//PLANT/" + etiquetaNormal[i] + " as '" + etiquetaTraducida[i] + "'";
                xqe.executeCommand(xq);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que modifica el precio de cada planta, eliminando el símbolo del
     * dólar.
     */
    void modificarFormatoDolar() {
        try {
            xqe = con.createExpression();
            String xq = "for $b in doc('/m06uf3/plantas/plantas.xml')//PLANT/PRICE return update value $b with substring($b,2)";
            xqe.executeCommand(xq);
        } catch (XQException ex) {
            System.out.println(ex.getMessage());
        }
    }
    
    /**
     * Método que devuelve las plantas que esten en la zona X. (No funciona, acabar).
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
}
