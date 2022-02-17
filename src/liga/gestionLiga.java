/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package liga;

import static java.awt.font.TextAttribute.WIDTH;
import static java.awt.image.ImageObserver.WIDTH;
import javax.swing.JOptionPane;
import org.neodatis.odb.ODB;
import org.neodatis.odb.ODBFactory;
import org.neodatis.odb.Objects;
import org.neodatis.odb.core.query.IQuery;
import org.neodatis.odb.core.query.criteria.Where;
import org.neodatis.odb.impl.core.query.criteria.CriteriaQuery;


public class gestionLiga {
    
    public void insertarJugador(String nombre, String deporte, String ciudad, int edad, String pais){
        //conecto con la base de datos
        ODB odb = ODBFactory.open("Juego2.test");
        //hago la query
        IQuery query = new CriteriaQuery(paises.class, Where.equal("nombre_pais", pais));
        
        //Creo una lista de objetos pais con el resultado de la query
        Objects<paises> listadoPais = odb.getObjects(query);
        
        //instancio objetos con los que voy a trabajar posteriormente
        paises p = null;
        paises paisIdMayor = null;
        paises paisNuevo = null;
        int idPaisACrear;
        Jugadores j;
        
        //si listado tiene resultado, no está vacio, quiere decir que hay un pais que concuerde
        //con el pais introducido por parametro.
        if (listadoPais.hasNext()) {
            p = listadoPais.getFirst();
            
            j = new Jugadores(nombre, deporte, ciudad, edad, p);
            
        } else {// y si no, creo el pais que ha introducido con el id siguiente al nr mayor de ID
            //vuelvo a hacer una query para que me recoja todos los paises, sin clausula WHERE
            query = new CriteriaQuery(paises.class);
            
            //ordeno el resultado de la query inversamente
            query.orderByDesc("id");
            
            //vuelvo a introducir en el listado, el resultado de la query
            listadoPais = odb.getObjects(query);
            
            //recojo el primer resultado
            paisIdMayor = listadoPais.getFirst();
            idPaisACrear = paisIdMayor.getId() + 1;
            
            //creo el pais nuevo con el id siguiente al ID mayor encontrado en la base de datos y el nombre introducido
            paisNuevo = new paises (idPaisACrear, pais.toLowerCase());
            
            j = new Jugadores(nombre, deporte, ciudad, edad, paisNuevo);
                        
        }
        
        
        odb.store(j);
        odb.close();
        
    }
    
    public boolean bajaJugador(String nombre){
        //conecto con la base de datos
        ODB odb = ODBFactory.open("Juego2.test");
        
        //hago la query
        IQuery query = new CriteriaQuery(Jugadores.class, Where.equal("nombre", nombre));
        
        //Creo una lista de objetos Jugadores con el resultado de la query
        Objects<Jugadores> listadoJugadores = odb.getObjects(query);
        
        Jugadores j;
        boolean respuesta;
        
        if (listadoJugadores.hasNext()) {
            j = listadoJugadores.getFirst();
            odb.delete(j);
            respuesta = true;
        } else {
            respuesta = false;
            
        }
        return respuesta;
    }
    
    public void modificaCampo(String nombre, String campo){
        
    }
    
}
