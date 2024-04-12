package Model;

import java.util.List;
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


}
