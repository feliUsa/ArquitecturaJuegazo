package Model;

/*
  Clase carta con constructor, getters y setters
*/

public class Carta {
    public String nombre;
    public String tipo;

    public Carta(String tipo, String nombre) {
        this.nombre = nombre;
        this.tipo = tipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String toString() {
        return "Carta " + nombre + " de calidad "+ tipo;
    }


}
