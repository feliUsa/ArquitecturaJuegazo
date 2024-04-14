package Controller;

import Model.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Controlador{

    private Scanner p = new Scanner(System.in);
    

    public Mazo crearCartas(){
        Mazo barajaCartas = new Mazo();
        barajaCartas.addAllCartas(CartasRuta.crearCartas());

        // Mostrar cartas
        /*for (Carta carta : barajaCartas.getCartasMazo()) {
            System.out.println("Tipo: " + carta.getNombre() + ", Descripción: " + carta.getTipo());
        }*/

        return barajaCartas;
    }

    public List<Jugador> crearJugadores(int numeroJugadores){
        List<Jugador> jugadores = new ArrayList<Jugador>();

        for (int i = 1; i <= numeroJugadores; i++) {
            System.out.println("Ingrese el nombre del jugador " + i + ":");
            String nombre = p.nextLine();
            Jugador jugador = new Jugador(i, nombre);
            jugador.setMazo(new Mazo()); 
            jugadores.add(jugador);
        }

        for (Jugador jugador : jugadores) {
            System.out.println(jugador);
        }
        
        return jugadores;
    }

    public void repartirCartas(List<Jugador> jugadores, Mazo barajaCartas){
        barajaCartas.mezclar();
        
        for (Jugador jugador : jugadores) {
            // Obtener una lista de cartas para el jugador desde el mazo
            List<Carta> cartasRepartidas = barajaCartas.repartir(6); // Repartir 7 cartas a cada jugador
            // Agregar las cartas al mazo del jugador
            jugador.getMazo().addAllCartas(cartasRepartidas);
        }
    }

    public void verCartas(Jugador jugador){
        for (Carta carta : jugador.getMazo().getCartasMazo()) {
            System.out.println(carta.toString());
        }
    }


    @SuppressWarnings("resource")
    public void pedritoCorreExcesivamenteMucho(){
        boolean haIniciadoJuego = false;

        System.out.println("Numero jugadores");
        int numPlayers = p.nextInt();
        p.nextLine(); 
        System.out.println("Puntaje a alcanzar");
        int puntajeMax = p.nextInt();
        p.nextLine(); 
        
        System.out.println("Barajando cartas...");
        Mazo barajaCartas = crearCartas();
        List<Jugador> jugadores = crearJugadores(numPlayers);

        System.out.println("\nRepartiendo cartas...\n\n\n");
        repartirCartas(jugadores, barajaCartas);

        // Lanzar iniciarJuego para decidir quién tiene la carta "Siga"
        Reglas.iniciarJuego(jugadores, barajaCartas);

        // Verificar quién tiene la carta "Siga"
        Jugador jugadorConSiga = null;
        for (Jugador jugador : jugadores) {
            if (Reglas.tieneCartaSiga(jugador.getMazo().getCartasMazo())) {
                jugadorConSiga = jugador;
                haIniciadoJuego = true;
                break;
            }
        }
        

        // Bucle principal del juego
        int indiceJugadorActual = jugadores.indexOf(jugadorConSiga);
        int totalJugadores = jugadores.size();

        while (true) {
            // Obtener el jugador actual
            Jugador jugadorActual = jugadores.get(indiceJugadorActual);

            // Iniciar el turno del jugador actual
            Reglas.iniciarTurno(jugadorActual, barajaCartas);

            // Mostrar el estado actual del juego y el turno del jugador
            System.out.println("Turno del jugador: " + jugadorActual.getNombre());
            System.out.println("Puntaje actual: " + Reglas.calcularPuntaje(jugadorActual.getMazo().getCartasMazo()));
            System.out.println("Cartas en mano:");
            verCartas(jugadorActual);

            // Permitir al jugador elegir una acción
            System.out.println("\n\n\nSeleccione una acción:");
            System.out.println("1. Jugar una carta");
            System.out.println("2. Ver el mazo actual");
            System.out.println("3. Pasar al siguiente jugador");

            Scanner s = new Scanner(System.in);
            int opcion = s.nextInt();

            switch (opcion) {
                case 1:
                    jugarCarta(jugadorActual);
                    Reglas.finalizarTurno(jugadorActual, barajaCartas);
                    break;
                case 2:
                    verCartas(jugadorActual);
                    break;
                case 3:
                    // Finalizar el turno del jugador actual
                    Reglas.finalizarTurno(jugadorActual, barajaCartas);
                    System.out.println("Pasando al siguiente jugador...");
                    // Mover al siguiente jugador en la lista circularmente
                    indiceJugadorActual = (indiceJugadorActual + 1) % totalJugadores;
                    break;
                default:
                    System.out.println("Opción no válida. Por favor, seleccione una acción válida.");
            }

            // Verificar si algún jugador ha alcanzado la puntuación máxima
            if (Reglas.calcularPuntaje(jugadorActual.getMazo().getCartasMazo()) >= puntajeMax) {
                System.out.println("¡" + jugadorActual.getNombre() + " ha ganado!");
                return; // Finalizar el juego
            }
        } // fin while


    } // fin metodo pedritoCorreExcesivamenteMucho

    
    public void jugarCarta(Jugador jugador){
        Scanner scanner = new Scanner(System.in);

        // Mostrar las cartas en mano del jugador
        System.out.println("Cartas en mano:");
        verCartas(jugador);
    
        // Solicitar al jugador que seleccione una carta para jugar
        System.out.println("Seleccione el número de la carta que desea jugar:");
        int numeroCarta = scanner.nextInt();
    
        // Verificar si el número de carta seleccionado es válido
        if (numeroCarta >= 1 && numeroCarta <= jugador.getMazo().size()) {
            // Obtener la carta seleccionada
            Carta cartaSeleccionada = jugador.getMazo().getCartasMazo().get(numeroCarta - 1);
    
            // Implementar la lógica para jugar la carta
            // (Por ejemplo, aplicar el efecto de la carta al juego)
    
            // Mostrar un mensaje indicando que la carta fue jugada
            System.out.println("¡Has jugado la carta " + cartaSeleccionada.getNombre() + "!");
        } else {
            // Mostrar un mensaje de error si el número de carta seleccionado no es válido
            System.out.println("Número de carta inválido. Por favor, seleccione un número válido.");
        }
    }



}