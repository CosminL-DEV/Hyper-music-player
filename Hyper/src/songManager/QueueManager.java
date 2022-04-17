package songManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * ************************************
 *
 * @author Cosmin Ionut Lungu
 * @since 16-04-2022
 * @version 1.0
 *
 * ************************************
 */
public class QueueManager {

    private Cancion primeraLista;
    private Cancion ultimaLista;
    private Cancion actual;

    public void addLista(ArrayList<String> lista) {
        for (String e : lista) {
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
        }
    }

    public void setListaRandom(ArrayList<String> lista) {
        Collections.shuffle(lista);
        ultimaLista = actual;
        for (String e : lista) {
            Cancion cancion = new Cancion(ultimaLista, null, e);
            this.ultimaLista.next = cancion;
            this.ultimaLista = cancion;
        }
    }

    public void setListaNormal(ArrayList<String> lista) {
        ultimaLista = actual;
        for (String e : lista) {
            Cancion cancion = new Cancion(ultimaLista, null, e);
            this.ultimaLista.next = cancion;
            this.ultimaLista = cancion;
        }
    }

    public void addCola(String e) {
        // Metodo creado pero no implementado.
        Cancion index = this.primeraLista;
        while (index != null) {
            if (index == actual) {
                break;
            }
            index = index.next;
        }
        Cancion insertValue = new Cancion(index, index.next, e);
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
        return actual;
    }

    // Solo para pruebas, quizas sirve para añadir el preview de la cola.
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer();
        Cancion current = this.primeraLista;
        while (current != null) {
            sb.append(current.e + "->");
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
