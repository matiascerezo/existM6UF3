/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Colecciones;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Service;
import org.xmldb.api.base.XMLDBException;
import org.xmldb.api.modules.BinaryResource;
import org.xmldb.api.modules.CollectionManagementService;
import org.xmldb.api.modules.XMLResource;

/**
 *
 * @author Matias
 */
public class Consultas {

    Collection coleccion;
    CollectionManagementService cms;
    ConfigConnexio cc = new ConfigConnexio();
    Service[] servicios;
    XMLResource xmlresource;
    BinaryResource binaryresource;

    public Consultas() {
        this.coleccion = cc.conectar();
        establecerCollectionManagementService();
    }

    /**
     * Para establecer a CollectionManagementService el servicio
     * correspondiente.
     */
    public void establecerCollectionManagementService() {
        try {
            servicios = coleccion.getServices();
            for (Service servicio : servicios) {
                if (servicio.getName().equals("CollectionManagementService")) {
                    cms = (CollectionManagementService) servicio;
                }
            }
        } catch (XMLDBException e) {
            System.out.println(e);
        }
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
     * @return
     */
    public String[] obtenerListaNombreColeccionHijas() {
        try {
            return coleccion.listChildCollections();
        } catch (Exception e) {
            System.out.println(e);
        }
        return null;
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
     * @throws org.xmldb.api.base.XMLDBException
     */
    public void comprobarRecursoDentroColeccion(String nombreColeccion, String nombreRecurso) throws XMLDBException {
        coleccion = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db/" + nombreColeccion, "admin", "123456");
        if (coleccion.getResource(nombreRecurso).getId() != null) {
            System.out.println("Existe! Recurso --> " + coleccion.getResource(nombreRecurso).getId());
        } else {
            System.out.println("No existe.");
        }
    }

    //RECURSOS
    /**
     * Método que añade un recurso XML a la BD siempre y cuando exista.
     *
     * @param nombreRecurso
     */
    public void afegirRecursXML(String nombreRecurso) {
        try {
            xmlresource = (XMLResource) coleccion.createResource(nombreRecurso, XMLResource.RESOURCE_TYPE);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document doc = db.parse(new File(nombreRecurso));
            xmlresource.setContentAsDOM(doc);
            coleccion.storeResource(xmlresource);
            System.out.println("Añadido el recurso: " + nombreRecurso);
        } catch (XMLDBException | ParserConfigurationException | SAXException | IOException e) {
            System.out.println(e);
        }
    }

    /**
     * Método que elimina un recurso XML siempre y cuando exista.
     *
     * @param nombreRecurso
     */
    public void eliminarRecursXML(String nombreRecurso) {
        try {
            xmlresource = (XMLResource) coleccion.getResource(nombreRecurso);
            coleccion.removeResource(xmlresource);
            System.out.println("Eliminado!");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que obtiene el recurso XML con el nombre pasado por parametro.
     *
     * @param nombreRecurso
     */
    public void obtenerRecursoXML(String nombreRecurso) {
        try {
            xmlresource = (XMLResource) coleccion.getResource(nombreRecurso);
            System.out.println(xmlresource.getContent());
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que añade un recurso binario.
     *
     * @param nombreRecursoBinario
     */
    public void afegirRecursBinari(String nombreRecursoBinario) {
        try {
            binaryresource = (BinaryResource) coleccion.createResource(nombreRecursoBinario, BinaryResource.RESOURCE_TYPE);
            binaryresource.setContent(new File(nombreRecursoBinario));
            coleccion.storeResource(binaryresource);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    /**
     * Método que obtiene un recurso binario.
     *
     * @param nombreRecursoBinario
     */
    public void obtenirRecursBinari(String nombreRecursoBinario) {
        try {
            binaryresource = (BinaryResource) coleccion.getResource(nombreRecursoBinario);
            byte[] arrayContenido = (byte[]) binaryresource.getContent();
            Path path = Paths.get(nombreRecursoBinario);
            Files.write(path, arrayContenido);
            coleccion.storeResource(binaryresource);
        } catch (XMLDBException | IOException e) {
            System.out.println(e);
        }
    }
}