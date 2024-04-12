import Model.Carta;
import Model.CartasRuta;
import Model.Mazo;

public class HolaMundo {



    public static void main(String[] args) {

        Mazo mazo = new Mazo();

        mazo.addAllCartas(CartasRuta.crearCartas());

        for (Carta carta : mazo.getCartasMazo()) {
            System.out.println("Tipo: " + carta.getNombre() + ", Descripción: " + carta.getTipo());
        }

    }

}
