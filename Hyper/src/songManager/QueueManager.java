package songManager;

import java.util.ArrayList;
import java.util.Collections;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 24-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class QueueManager {

    private Cancion primeraLista;
    private Cancion ultimaLista;
    private Cancion actual;

    public void addLista(ArrayList<String> lista) {
        lista.forEach(e -> {
            if (ultimaLista == null) {
                Cancion cancion = new Cancion(null, null, e);
                this.primeraLista = cancion;
                this.actual = cancion;
                this.ultimaLista = cancion;
            } else {
                Cancion cancion = new Cancion(ultimaLista, null, e);
                this.ultimaLista.next = cancion;
                this.ultimaLista = cancion;
            }
        });
    }

    public void setListaRandom(ArrayList<String> lista) {
        Collections.shuffle(lista);
        ultimaLista = actual;
        lista.stream().map(e -> new Cancion(ultimaLista, null, e)).map(cancion -> {
            this.ultimaLista.next = cancion;
            return cancion;
        }).forEachOrdered(cancion -> {
            this.ultimaLista = cancion;
        });
    }

    public void setListaNormal(ArrayList<String> lista) {
        ultimaLista = actual;
        lista.stream().map(e -> new Cancion(ultimaLista, null, e)).map(cancion -> {
            this.ultimaLista.next = cancion;
            return cancion;
        }).forEachOrdered(cancion -> {
            this.ultimaLista = cancion;
        });
    }

    public void addCola(String e) {
        Cancion index = this.primeraLista;
        while (index != null) {
            if (index == actual) {
                break;
            }
            index = index.next;
        }
        Cancion insertValue = new Cancion(index, index.next, e);
        if (index.next == null) {
            index.next = ultimaLista;
        }
        index.next.previous = insertValue;
        index.next = insertValue;
    }

    public void nextCancion() {
        actual = actual.next;
    }

    public void anteriorCancion() {
        actual = actual.previous;
    }

    public Cancion getActual() {
        if (actual == null) {
            return new Cancion(null, null, "null");
        }
        return actual;
    }

    public void reiniciarActual() {
        this.actual = this.primeraLista;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Cancion current = this.primeraLista;
        boolean llego = false;
        while (current != null) {
            if (llego) {
                sb.append(current.e).append(",");
            }
            if (current == actual) {
                llego = true;
            }
            current = current.next;
        }
        return sb.toString();
    }

    public class Cancion {

        private Cancion previous;
        private Cancion next;
        private String e;

        public Cancion(Cancion previous, Cancion next, String e) {
            this.previous = previous;
            this.next = next;
            this.e = e;
        }

        public String getId() {
            return e;
        }
    }
}
