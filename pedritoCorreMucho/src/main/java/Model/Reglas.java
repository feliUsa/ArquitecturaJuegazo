package Model;

import java.util.List;

public class Reglas {

    public static int calcularPuntaje(List<Carta> cartasJugadas) {
        int puntaje = 0;
        for (Carta carta : cartasJugadas) {
            if (carta.getTipo().equals("Distancia")) {
                puntaje += Integer.parseInt(carta.getTipo().split(" ")[0]);
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



    // Siempre iniciar juego con siga
    public static void iniciarJuego(List<Jugador> jugadores, Mazo mazo) {
        for (Jugador jugador : jugadores) {
            iniciarTurno(jugador, mazo); // Iniciar turno del jugador

            // Verificar si el jugador tiene una carta "Siga"
            boolean tieneSiga = false;
            for (Carta carta : jugador.getMazo()) {
                if (carta.getTipo().equals("Siga")) {
                    tieneSiga = true;
                    break;
                }
            }

            // Si el jugador no tiene una carta "Siga", finalizar su turno para que tenga 6 cartas en su mano
            if (!tieneSiga) {
                finalizarTurno(jugador, mazo);
            }
        }
    }

    // Siempre al iniciar turno debe de coger una carta
    public static void iniciarTurno(Jugador jugador, Mazo mazo) {
        // Obtener la lista de cartas en la mano del jugador
        List<Carta> manoJugador = jugador.getMazo();

        // Verificar si el jugador tiene menos de 7 cartas en su mano
        int cartasFaltantes = 7 - manoJugador.size();
        if (cartasFaltantes > 0) {
            // Repartir una carta adicional al jugador desde el mazo
            List<Carta> nuevasCartas = mazo.repartir(cartasFaltantes);
            jugador.setMazo(nuevasCartas);
        }
    }

    // Siempre al finalizar turno debe de tener 6 cartas
    public static void finalizarTurno(Jugador jugador, Mazo mazo) {
        // Obtener la lista de cartas en la mano del jugador
        List<Carta> manoJugador = jugador.getMazo();

        // Verificar si el jugador tiene más de 6 cartas en su mano
        int cartasSobrantes = manoJugador.size() - 6;
        if (cartasSobrantes > 0) {
            // Descartar cartas adicionales del jugador
            List<Carta> cartasDescartadas = manoJugador.subList(6, manoJugador.size());
            jugador.setMazo(manoJugador.subList(0, 6)); // Mantener solo las primeras 6 cartas en la mano
            // Las cartas descartadas vuelven al mazo
            mazo.addAllCartas(manoJugador);
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
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Seguridad") && carta.getTipo().equals("Prioridad de paso")) {
                    return false; // El jugador ha jugado "Prioridad de paso", no puede recibir "Inicio de límite de velocidad a 50"
                }
            }

            // Si el jugador no ha jugado "Prioridad de paso", verificar si ha recibido "Inicio de límite de velocidad a 50"
            boolean haRecibidoInicioVelMaxima50 = false;
            for (Carta carta : cartasJugadas) {
                if (carta.getTipo().equals("Problema") && carta.getTipo().equals("Inicio de limite de velocidad a 50")) {
                    haRecibidoInicioVelMaxima50 = true;
                    break;
                }
            }

            // Si ha recibido "Inicio de límite de velocidad a 50", entonces solo puede jugar "Fin de límite de velocidad a 50", "Distancia de 25 km" o "Distancia de 50 km"
            if (haRecibidoInicioVelMaxima50) {
                for (Carta carta : cartasJugadas) {
                    if ((carta.getTipo().equals("Solucion") && carta.getTipo().equals("Fin de limite de velocidad a 50")) ||
                            (carta.getTipo().equals("Distancia") && (carta.getTipo().equals("25 km") || carta.getTipo().equals("50 km")))) {
                        return true; // El jugador tiene una carta válida para jugar después de "Inicio de límite de velocidad a 50"
                    }
                }
                return false; // El jugador ha recibido "Inicio de límite de velocidad a 50" pero no tiene una carta válida para jugar
            }
        }

        return true; // El jugador puede recibir "Inicio de límite de velocidad a 50" si no ha jugado "Prioridad de paso" o no ha recibido "Inicio de límite de velocidad a 50" antes
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
