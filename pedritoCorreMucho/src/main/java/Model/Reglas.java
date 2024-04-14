package Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Reglas {

    public static int calcularPuntaje(List<Carta> cartasJugadas) {
        int puntaje = 0;
        for (Carta carta : cartasJugadas) {
            String[] partes = carta.getTipo().split(" ");
            if (partes.length > 1) {
                puntaje += Integer.parseInt(partes[1]);
            }
        }
        return puntaje;
    }
    
    // si tienes las cuatro cartas de seguridad te suman 800km
    public static int recogeSeguridad(List<Carta> cartasJugadas, int distanciaTotal) {
        int contadorSeguridad = 0;
        // Contar cuántas cartas de seguridad tiene el jugador en cartasJugadas
        for (Carta carta : cartasJugadas) {
            if (carta.getTipo().equals("Seguridad")) {
                contadorSeguridad++;
            }
        }
        // Si el jugador tiene las cuatro cartas de seguridad, sumar 800 a la distanciaTotal
        if (contadorSeguridad == 4) {
            distanciaTotal += 800;
        }
        return distanciaTotal;
    }

    // Metodos adicional para iniciar Juego
    public static boolean tieneCartaSiga(List<Carta> cartasJugador) {
        for (Carta carta : cartasJugador) {
            if (carta.getTipo().equals("Solucion") && carta.getNombre().equals("Siga")) {
                return true;
            }
        }
        return false;
    }



    public static void iniciarJuego(List<Jugador> jugadores, Mazo mazo) {
        List<Jugador> jugadoresConSiga = new ArrayList<>();

        // Verificar qué jugadores tienen la carta "Siga"
        for (Jugador jugador : jugadores) {
            if (tieneCartaSiga(jugador.getMazo().getCartasMazo())) {
                jugadoresConSiga.add(jugador);
            }
        }

        // Si solo un jugador tiene la carta "Siga", ese jugador inicia el juego
        if (jugadoresConSiga.size() == 1) {
            Jugador jugadorQueInicia = jugadoresConSiga.get(0);
            System.out.println(jugadorQueInicia.getNombre() + " tiene la carta 'Siga'. ¡El juego puede comenzar!");
            iniciarTurno(jugadorQueInicia, mazo);
        }
        // Si hay más de un jugador con la carta "Siga", seleccionar uno aleatoriamente para iniciar el juego
        else if (jugadoresConSiga.size() > 1) {
            Collections.shuffle(jugadoresConSiga); // Mezclar la lista para seleccionar aleatoriamente
            Jugador jugadorQueInicia = jugadoresConSiga.get(0); // Seleccionar el primer jugador de la lista
            System.out.println(jugadorQueInicia.getNombre() + " tiene la carta 'Siga' y comenzará el juego.");
            iniciarTurno(jugadorQueInicia, mazo);
        }
        // Si ningún jugador tiene la carta "Siga", continuar con el siguiente jugador en la lista
        else {
            for (Jugador jugador : jugadores) {
                iniciarTurno(jugador, mazo); // Iniciar turno del jugador
                // Verificar si el jugador tiene una carta "Siga" en su mazo
                boolean tieneSiga = tieneCartaSiga(jugador.getMazo().getCartasMazo());
                // Si el jugador no tiene una carta "Siga", descartar una carta y finalizar su turno
                if (!tieneSiga) {
                    Mazo.descartarUnaCarta(jugador, mazo); // Descartar una carta del jugador
                    finalizarTurno(jugador, mazo);
                }
            }
        }
    }

    // Siempre al iniciar turno debe de coger una carta
    public static void iniciarTurno(Jugador jugador, Mazo mazo) {
        // Verificar si el jugador tiene menos de 7 cartas en su mano
        if (jugador.getMazo().size() < 7) {
            // Repartir cartas adicionales al jugador desde el mazo
            int cartasFaltantes = 7 - jugador.getMazo().size();
            List<Carta> nuevasCartas = mazo.repartir(cartasFaltantes);
            
            // Añadir las nuevas cartas al mazo del jugador
            for (Carta carta : nuevasCartas) {
                jugador.getMazo().addCarta(carta);
                System.out.println("\n\n\n¡" + jugador.getNombre() + ", se te añadió la carta '" + carta.getNombre() + "'!");
            }
        }
    }
    
    
    // Siempre al finalizar turno debe de tener 6 cartas
    public static void finalizarTurno(Jugador jugador, Mazo mazo) {
        // Verificar si el jugador tiene más de 6 cartas en su mano
        if (jugador.getMazo().size() > 6) {
            System.out.println("El jugador " + jugador.getNombre() + " tiene más de 6 cartas en su mano. Debe descartar una carta.");
            Mazo.descartarUnaCarta(jugador, mazo);
        }
    }
    


    // Siempre despues de colocar una carta solucion se debe colocar un siga
    // A menos de que tenga un prioridad de paso
    public static boolean validarColocarSolucion(List<Carta> cartasJugadas) {
        // Verificar si la lista de cartas jugadas no está vacía
        if (!cartasJugadas.isEmpty()) {
            // Obtener la última carta jugada por el jugador
            Carta ultimaCarta = cartasJugadas.get(cartasJugadas.size() - 1);

            // Verificar si la última carta jugada fue una carta de solución
            if (ultimaCarta.getTipo().equals("Solucion")) {
                // Verificar si el jugador ha jugado la carta "Prioridad de paso"
                boolean tienePrioridadDePaso = false;
                for (Carta carta : cartasJugadas) {
                    if (carta.getTipo().equals("Seguridad") && carta.getTipo().equals("Prioridad de paso")) {
                        tienePrioridadDePaso = true;
                        break;
                    }
                }

                // Si tiene "Prioridad de paso", puede jugar cualquier tipo de carta sin necesidad de colocar "Siga"
                if (tienePrioridadDePaso) {
                    return true;
                }

                // Si no tiene "Prioridad de paso", debe colocar "Siga" después de una carta de solución
                return false;
            }
        }

        return true; // La regla se cumple si no hay cartas jugadas o ninguna es de solución
    }


    /*
    Cuando jugador1 le aplique un problema a jugador2
    jugador2 debe colocar la solucion correspondiente
    al problema*

    falta de combustible -> combustible
    neumatico pinchado -> neumatico extra
    accidente -> taller
    velocidad maxima 50 -> fin velocidad maxima
    stop -> siga
     */

    public static boolean validarFaltaCombustible(List<Carta> cartasJugadas) {
        // Verificar si la lista de cartas jugadas no está vacía
        if (!cartasJugadas.isEmpty()) {
            // Verificar si el jugador ha jugado la carta "Combustible extra"
            boolean tieneCombustibleExtra = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Seguridad") && carta.getTipo().equals("Combustible extra")) {
                    tieneCombustibleExtra = true;
                    break;
                }
            }

            // Si el jugador tiene la carta "Combustible extra", no puede recibir "Neumático pinchado"
            if (tieneCombustibleExtra) {
                return false;
            }

            // Si el jugador no tiene la carta "Combustible extra" pero ya ha recibido "Falta de combustible",
            // solo puede jugar la carta "Combustible" para resolver el problema
            boolean tieneFaltaCombustible = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Problema") && carta.getTipo().equals("Falta de combustible")) {
                    tieneFaltaCombustible = true;
                    break;
                }
            }
            if (tieneFaltaCombustible) {
                // El jugador solo puede jugar "Combustible" para resolver el problema
                for (Carta carta : cartasJugadas) {
                    if (carta.getTipo().equals("Solucion") && carta.getTipo().equals("Combustible")) {
                        return true;
                    }
                }
                return false; // El jugador no tiene "Combustible", no puede recibir "Neumático pinchado"
            }
        }

        return true; // El jugador no tiene la carta "Combustible extra" ni ha recibido "Falta de combustible"
    }


    public static boolean validarNeumaticoPinchado(List<Carta> cartasJugadas) {
        // Verificar si la lista de cartas jugadas no está vacía
        if (!cartasJugadas.isEmpty()) {
            // Verificar si el jugador ha jugado la carta "A prueba de pinchaduras"
            boolean tienePruebaPinchaduras = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Seguridad") && carta.getTipo().equals("A prueba de pinchaduras")) {
                    tienePruebaPinchaduras = true;
                    break;
                }
            }

            // Si el jugador tiene la carta "A prueba de pinchaduras", no puede recibir "Neumático pinchado"
            if (tienePruebaPinchaduras) {
                return false;
            }

            // Si el jugador no tiene la carta "A prueba de pinchaduras" pero ya ha recibido "Neumático pinchado",
            // solo puede jugar la carta "Neumático extra" para resolver el problema
            boolean tieneNeumaticoPinchado = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Problema") && carta.getTipo().equals("Neumatico pinchado")) {
                    tieneNeumaticoPinchado = true;
                    break;
                }
            }
            if (tieneNeumaticoPinchado) {
                // El jugador solo puede jugar "Neumático extra" para resolver el problema
                for (Carta carta : cartasJugadas) {
                    if (carta.getTipo().equals("Solucion") && carta.getTipo().equals("Neumatico extra")) {
                        return true;
                    }
                }
                return false; // El jugador no tiene "Neumático extra", no puede recibir "Neumático pinchado"
            }
        }

        return true; // El jugador no tiene la carta "A prueba de pinchaduras" ni ha recibido "Neumático pinchado"
    }


    public static boolean validarAccidente(List<Carta> cartasJugadas) {
        // Verificar si la lista de cartas jugadas no está vacía
        if (!cartasJugadas.isEmpty()) {
            // Verificar si el jugador ha jugado la carta "As del volante"
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Seguridad") && carta.getTipo().equals("As del volante")) {
                    return false; // El jugador ha jugado "As del volante", no puede recibir "Accidente"
                }
            }

            // Si el jugador no ha jugado "As del volante", verificar si ha recibido "Accidente"
            boolean haRecibidoAccidente = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Problema") && carta.getTipo().equals("Accidente")) {
                    haRecibidoAccidente = true;
                    break;
                }
            }

            // Si ha recibido "Accidente", entonces solo puede jugar "Taller"
            if (haRecibidoAccidente) {
                for (Carta carta : cartasJugadas) {
                    if (carta.getTipo().equals("Solucion") && carta.getTipo().equals("Taller")) {
                        return true; // El jugador tiene una carta "Taller", puede jugar
                    }
                }
                return false; // El jugador ha recibido "Accidente" pero no tiene "Taller", no puede jugar
            }
        }

        return true; // El jugador puede recibir "Accidente" si no ha jugado "As del volante" o no ha recibido "Accidente" antes
    }


    public static boolean validarVelMaxima(List<Carta> cartasJugadas) {
        // Verificar si la lista de cartas jugadas no está vacía
        if (!cartasJugadas.isEmpty()) {
            // Verificar si el jugador ha jugado la carta "Prioridad de paso"
            boolean tienePrioridadDePaso = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Seguridad") && carta.getTipo().equals("Prioridad de paso")) {
                    tienePrioridadDePaso = true;
                    break;
                }
            }
    
            // Si el jugador ha jugado "Prioridad de paso", no puede recibir "Inicio de límite de velocidad a 50"
            if (tienePrioridadDePaso) {
                return true;
            } else {
                // Si el jugador no ha jugado "Prioridad de paso", verificar si ha recibido "Inicio de límite de velocidad a 50"
                boolean haRecibidoInicioVelMaxima50 = false;
                for (Carta carta : cartasJugadas) {
                    if (carta.getTipo().equals("Problema") && carta.getTipo().equals("Limite de velocidad 50 km")) {
                        haRecibidoInicioVelMaxima50 = true;
                        break;
                    }
                }
    
                // Si ha recibido "Inicio de límite de velocidad a 50", entonces solo puede jugar "Fin de límite de velocidad a 50", "Distancia de 25 km" o "Distancia de 50 km"
                if (haRecibidoInicioVelMaxima50) {
                    for (Carta carta : cartasJugadas) {
                        if ((carta.getTipo().equals("Solucion") && carta.getTipo().equals("Fin limite de velocidad 50 km")) ||
                                (carta.getTipo().equals("Distancia") && (carta.getTipo().equals("25 km") || carta.getTipo().equals("50 km")))) {
                            return true; // El jugador tiene una carta válida para jugar después de "Inicio de límite de velocidad a 50"
                        }
                    }
                    return false; // El jugador ha recibido "Inicio de límite de velocidad a 50" pero no tiene una carta válida para jugar
                } else {
                    // Si el jugador no ha recibido "Inicio de límite de velocidad a 50", verificar si ha jugado "Fin de límite de velocidad a 50"
                    boolean haJugadoFinVelMaxima50 = false;
                    for (Carta carta : cartasJugadas) {
                        if (carta.getTipo().equals("Solucion") && carta.getTipo().equals("Fin limite de velocidad 50 km")) {
                            haJugadoFinVelMaxima50 = true;
                            break;
                        }
                    }
                    
                    // Si ha jugado "Fin de límite de velocidad a 50", no hay restricciones
                    if (haJugadoFinVelMaxima50) {
                        return true;
                    } else {
                        // Si no ha jugado "Fin de límite de velocidad a 50", solo puede jugar cartas de "Distancia de 25 km" o "Distancia de 50 km"
                        for (Carta carta : cartasJugadas) {
                            if (carta.getTipo().equals("Distancia") && (carta.getTipo().equals("25 km") || carta.getTipo().equals("50 km"))) {
                                return true; // El jugador puede jugar cartas de distancia válidas
                            }
                        }
                        return false; // El jugador no ha jugado "Fin de límite de velocidad a 50", y no tiene una carta válida de distancia para jugar
                    }
                }
            }
        }
    
        // Si la lista de cartas jugadas está vacía, no hay restricciones
        return true;
    }


    public static boolean validarStop(List<Carta> cartasJugadas) {
        // Verificar si la lista de cartas jugadas no está vacía
        if (!cartasJugadas.isEmpty()) {
            // Verificar si el jugador ha jugado la carta "Prioridad de paso"
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Seguridad") && carta.getTipo().equals("Prioridad de paso")) {
                    return false; // El jugador ha jugado "Prioridad de paso", no puede recibir "Stop"
                }
            }

            // Si el jugador no ha jugado "Prioridad de paso", verificar si ha recibido "Stop"
            boolean haRecibidoStop = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Problema") && carta.getTipo().equals("Stop")) {
                    haRecibidoStop = true;
                    break;
                }
            }

            // Si ha recibido "Stop", entonces solo puede jugar "Siga"
            if (haRecibidoStop) {
                for (Carta carta : cartasJugadas) {
                    if (carta.getTipo().equals("Solucion") && carta.getTipo().equals("Siga")) {
                        return true; // El jugador tiene la carta "Siga" como solución
                    }
                }
                return false; // El jugador ha recibido "Stop" pero no tiene la carta "Siga"
            }
        }

        return true; // El jugador puede recibir "Stop" si no ha jugado "Prioridad de paso" o no ha recibido "Stop" antes
    }



}
