package Model;

import java.util.ArrayList;
import java.util.List;

public class Jugador {

    public int id;
    public String nombre;
    public List<Carta> mazo;
    public int cantidadJugadores;


    public Jugador(int id, String nombre) {
        this.id = id;
        mazo = new ArrayList<Carta>();
    }


    public int getId() {
        return id;
    }


    public void setId(int id) {
        this.id = id;
    }


    public String getNombre() {
        return nombre;
    }


    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    // Ver mazo jugador
    public List<Carta> getMazo() {
        return mazo;
    }

    public void setMazo(List<Carta> mazo) {
        this.mazo = mazo;
        this.mazo.addAll(mazo);
    }

    public void deleteCardPlayer(Carta carta) {
        this.mazo.remove(carta);
    }


    public int getCantidadJugadores() {
        return cantidadJugadores;
    }


    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    public String toString() {
        return "Jugador " + nombre + " identificado con el ID: " + id + "\n Este es tu mazo " + mazo + "\n\n Hay "+ cantidadJugadores + " en la partida";
    }

}
