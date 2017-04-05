package Colecciones;

import java.util.logging.Level;
import java.util.logging.Logger;
import m06uf3_exist.Put;
import org.xmldb.api.DatabaseManager;
import org.xmldb.api.base.Collection;
import org.xmldb.api.base.Database;
import org.xmldb.api.base.XMLDBException;

/**
 *
 * @author Matias
 */
public class ConfigConnexio {

    private Database db;
    private Collection coleccion;

    public ConfigConnexio() {
        conectar();
    }

    public Collection conectar() {
        try {
            db = (Database) Class.forName("org.exist.xmldb.DatabaseImpl").newInstance();
            DatabaseManager.registerDatabase(db);
            coleccion = DatabaseManager.getCollection("xmldb:exist://localhost:8080/exist/xmlrpc/db", "admin", "123456");
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | XMLDBException ex) {
            Logger.getLogger(Put.class.getName()).log(Level.SEVERE, null, ex);
        }
        return coleccion;
    }
}
