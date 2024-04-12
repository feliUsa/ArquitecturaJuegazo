package Model;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*En esta clase se crean las cartas*/

public class CartasRuta {

    public static List<Carta> crearCartas(){

        /*
         * Distancia
         * 10 cartas 25 km
         * 10 cartas 50 km
         * 10 cartas 75 km
         * 12 cartas 100 km
         * 4 cartas 200 km
         *
         * Problema
         * 3 cartas Falta de combustible
         * 3 cartas Neumatico pinchado
         * 3 cartas Accidente
         * 4 cartas Limite Velocidad
         * 5 cartas Stop
         *
         * Solucion
         * 6 cartas combustible
         * 6 cartas Neumatico extra
         * 6 cartas taller
         * 6 cartas Fin limite velocidad
         * 14 cartas Siga
         *
         * Seguridad
         * 1 carta de cada tipo
         * Prioridad de paso
         * Combustible extra
         * As del volante
         * A prueba de pinchaduras
         */

        // c --> objeto de tipo List<Carta>
        List<Carta> c = new ArrayList<Carta>();

        // Cartas distancia

        for(int i = 0; i < 10; i++){
            c.add(new Carta("Distancia", "25 km"));
            c.add(new Carta("Distancia", "50 km"));
            c.add(new Carta("Distancia", "75 km"));
        }

        for(int i = 0; i < 12; i++) {
            c.add(new Carta("Distancia", "100 km"));
        }

        for(int i = 0; i < 4; i++) {
            c.add(new Carta("Distancia", "200 km"));
        }

        // Cartas problema

        for(int i = 0; i < 3; i++) {
            c.add(new Carta("Problema", "Falta de combustible"));
            c.add(new Carta("Problema", "Neumatico pinchado"));
            c.add(new Carta("Problema", "Accidente"));
        }

        for(int i = 0; i < 4; i++) {
            c.add(new Carta("Problema", "Limite de velocidad 50 km"));
        }

        for(int i = 0; i < 5; i++) {
            c.add(new Carta("Problema", "Stop"));
        }

        // Cartas Solucion

        for(int i = 0; i < 6; i++) {
            c.add(new Carta("Solucion", "Combustible"));
            c.add(new Carta("Solucion", "Neumatico extra"));
            c.add(new Carta("Solucion", "Taller"));
            c.add(new Carta("Solucion", "Fin limite de velocidad 50 km"));
        }

        for(int i = 0; i < 14; i++) {
            c.add(new Carta("Solucion", "Siga"));
        }

        // Cartas Seguridad

        c.add(new Carta("Seguridad", "Prioridad de paso"));
        c.add(new Carta("Seguridad", "Combustible extra"));
        c.add(new Carta("Seguridad", "As de volante"));
        c.add(new Carta("Seguridad", "A prueba de pinchaduras"));

        return c;
    }

}
