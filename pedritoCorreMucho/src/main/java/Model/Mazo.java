package Model;

import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Collections;

public class Mazo {

    public List<Carta> mazo;

    public Mazo() {
        this.mazo = new ArrayList<Carta>();
    }

    public void addCarta(Carta carta) {
        this.mazo.add(carta);
    }

    public void addAllCartas(List<Carta> mazo) {
        this.mazo.addAll(mazo);
    }

    public void mezclar() {
        Collections.shuffle(mazo);
    }

    public void removeCarta(Carta carta) {
        mazo.remove(carta);
    }

    public void devolverCartasDescartadas(List<Carta> mazo) {
        this.mazo.addAll(mazo); // Añadir las cartas descartadas al mazo
    }

    public int size(){
        return mazo.size();
    }

    public Carta get(int index){
        if (index >= 0 && index < mazo.size()) {
            return mazo.get(index);
        } else {
            throw new IndexOutOfBoundsException("Índice fuera de rango");
        }
    }

    public List<Carta> repartir(int cantidad) {
        List<Carta> cartasRepartidas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            if (!mazo.isEmpty()) {
                cartasRepartidas.add(mazo.remove(0));
            }
        }
        return cartasRepartidas;
    }

    public List<Carta> getCartasMazo(){
        return this.mazo;
    }


    public static void descartarUnaCarta(Jugador jugador, Mazo mazo) {
        Scanner scanner = new Scanner(System.in);
        
        // Mostrar el mazo actual del jugador
        System.out.println("Mazo actual de " + jugador.getNombre() + ":");
        for (int i = 0; i < jugador.getMazo().size(); i++) {
            Carta carta = jugador.getMazo().get(i);
            System.out.println((i + 1) + ". " + carta);
        }
        
        // Solicitar al jugador que seleccione la carta a descartar
        int cartaSeleccionada;
        do {
            System.out.println("Seleccione el número de la carta que desea descartar:");
            cartaSeleccionada = scanner.nextInt();
        } while (cartaSeleccionada < 1 || cartaSeleccionada > jugador.getMazo().size());
        
        // Descartar la carta seleccionada
        Carta cartaDescartada = jugador.getMazo().get(cartaSeleccionada - 1);
        jugador.descartarCarta(cartaDescartada); // Descartar la carta del jugador
        mazo.devolverCartasDescartadas(Collections.singletonList(cartaDescartada)); // Devolver la carta al mazo
    }


}
