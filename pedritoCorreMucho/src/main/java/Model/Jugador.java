package Model;

public class Jugador {

    public int id;
    public String nombre;
    public Mazo mazo;
    public int cantidadJugadores;


    public Jugador(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
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
    public Mazo getMazo() {
        return mazo;
    }

    public void setMazo(Mazo mazo) {
        this.mazo = mazo;
    }

    public void descartarCarta(Carta carta) {
        this.mazo.removeCarta(carta);
    }

    public int getCantidadJugadores() {
        return cantidadJugadores;
    }


    public void setCantidadJugadores(int cantidadJugadores) {
        this.cantidadJugadores = cantidadJugadores;
    }

    public String toString() {
        return "Jugador " + nombre + " identificado con el ID: " + id + "\n Este es tu mazo " + mazo.getCartasMazo() + "\n\n Hay "+ cantidadJugadores + " jugadores en la partida";
    }

}
