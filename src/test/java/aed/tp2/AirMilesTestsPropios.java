package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class AirMilesTestsPropios {
    private Heap heap;

    @BeforeEach
    void setUp(){
        heap = new Heap();
    }

    @Test
    void extraerMaximo(){
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> heap.extraerMaximo()); // heap vacio
        assertEquals("El heap esta vacio", excepcion.getMessage());

        Usuario us1 = new Usuario(26, 100, 0, 0, 0, null, null);
        Usuario us2 = new Usuario(99, 5000, 0, 0, 0, null, null);
        Usuario us3 = new Usuario(7, 0, 0, 0, 0, null, null);

        heap.agregarNodo(us1); // un solo usuario

        Usuario maximo = heap.extraerMaximo();
        assertEquals(maximo.obtenerId(), 26);
        assertEquals(maximo.millasDisponibles(), 100.0);
        assertEquals(us1, maximo);
        assertNull(maximo.handleEnHeap());

        heap.agregarNodo(us1); // caso general
        heap.agregarNodo(us2);
        heap.agregarNodo(us3);

        maximo = heap.extraerMaximo();
        assertEquals(maximo, us2);
        assertEquals(heap.usConMasMillasDisp(), us1);
    }

    @Test
    void usConMasMillasDisp(){
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> heap.usConMasMillasDisp()); // heap vacio
        assertEquals("El heap esta vacio", excepcion.getMessage());

        Usuario us1 = new Usuario(26, 100, 0, 0, 0, null, null); // caso general
        Usuario us2 = new Usuario(99, 5000, 0, 0, 0, null, null);
        Usuario us3 = new Usuario(7, 40000, 0, 0, 0, null, null);

        heap.agregarNodo(us1);
        heap.agregarNodo(us2);
        heap.agregarNodo(us3);

        Usuario maximoSinEditarHeap = heap.usConMasMillasDisp();
        assertEquals(maximoSinEditarHeap, us3);
    }

    @Test
    void siftUpSiftDown(){
        Usuario us1 = new Usuario(26, 10, 0, 0, 0, null, null);
        Usuario us2 = new Usuario(7, 20, 0, 0, 0, null, null);
        Usuario us3 = new Usuario(99, 30, 0, 0, 0, null, null);
        Usuario us4 = new Usuario(10, 40, 0, 0, 0, null, null);
        Usuario us5 = new Usuario(43, 50, 0, 0, 0, null, null);

        heap.agregarNodo(us1); // de menor a mayor
        heap.agregarNodo(us2);
        heap.agregarNodo(us3);
        heap.agregarNodo(us4);
        heap.agregarNodo(us5);

        Usuario usMax = heap.usConMasMillasDisp();
        assertEquals(usMax.obtenerId(), us5.obtenerId());
        assertEquals(usMax.millasDisponibles(), us5.millasDisponibles());

        us1.handleEnHeap().eliminar(); // limpio el heap
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
        us4.handleEnHeap().eliminar();
        us5.handleEnHeap().eliminar();


        heap.agregarNodo(us5); // de mayor a menor
        heap.agregarNodo(us4);
        heap.agregarNodo(us3);
        heap.agregarNodo(us2);
        heap.agregarNodo(us1);

        Usuario maximo = heap.extraerMaximo();
        assertEquals(maximo.obtenerId(), 43);
        assertEquals(maximo.millasDisponibles(), 50.0);

        us1.handleEnHeap().eliminar(); // limpio el heap
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
        us4.handleEnHeap().eliminar();

        heap.agregarNodo(us3); // caso general
        heap.agregarNodo(us1);
        heap.agregarNodo(us5);
        heap.agregarNodo(us2);
        heap.agregarNodo(us4);

        assertEquals(heap.usConMasMillasDisp(), us5);

        assertEquals(heap.extraerMaximo().obtenerId(), 43); // los saco a todos
        assertEquals(heap.extraerMaximo().obtenerId(), 10);
        assertEquals(heap.extraerMaximo().obtenerId(), 99);
        assertEquals(heap.extraerMaximo().obtenerId(), 7);
        assertEquals(heap.extraerMaximo().obtenerId(), 26);
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> heap.extraerMaximo());
        assertEquals("El heap esta vacio", excepcion.getMessage());
    }

    @Test
    void actualizacionHeap(){
        Usuario us = new Usuario(7, 20, 0, 0, 0, null, null);
        assertNull(us.handleEnHeap());
        heap.agregarNodo(us);
        assertNotNull(us.handleEnHeap());
        assertEquals(us, us.handleEnHeap().valor());
    }

    @Test
    void eliminar(){
        Usuario us1 = new Usuario(99, 1230, 0, 0, 0, null, null);
        Usuario us2 = new Usuario(10, 4540, 0, 0, 0, null, null);
        Usuario us3 = new Usuario(43, 501, 0, 0, 0, null, null);
        Usuario us4 = new Usuario(26, 10256, 0, 0, 0, null, null);
        Usuario us5 = new Usuario(7, 2020, 0, 0, 0, null, null);

        heap.agregarNodo(us1);
        heap.agregarNodo(us2);
        heap.agregarNodo(us3);
        heap.agregarNodo(us4);
        heap.agregarNodo(us5);

        us4.handleEnHeap().eliminar(); // la raiz
        assertEquals(heap.usConMasMillasDisp(), us2);

        us5.handleEnHeap().eliminar(); // el ultimo
        assertEquals(heap.usConMasMillasDisp(), us2); // la raiz no cambia

        us1.handleEnHeap().eliminar(); // los saco a todos
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
    }
}
