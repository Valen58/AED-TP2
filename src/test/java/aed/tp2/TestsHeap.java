package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestsHeap {
    private Heap<Usuario> heap;

    @BeforeEach
    void setUp(){
        heap = new Heap<>(new PorMillasDisp());
    }

    @Test
    void extraerMaximo(){
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> heap.extraerMaximo()); // heap vacio
        assertEquals("El heap esta vacio", excepcion.getMessage());

        Usuario us1 = new Usuario(26, 100, 0, 0, 0, null);
        Usuario us2 = new Usuario(99, 5000, 0, 0, 0, null);
        Usuario us3 = new Usuario(7, 0, 0, 0, 0, null);

        // Un solo usuario
        Handle<Usuario> handleUs1 = heap.agregarNodo(us1);
        us1.setHandleEnHeap(handleUs1);

        Usuario maximo = heap.extraerMaximo();
        assertEquals(maximo.obtenerId(), 26);
        assertEquals(maximo.millasDisponibles(), 100.0);
        assertEquals(us1, maximo);
        assertNotNull(maximo.handleEnHeap()); // No le quita el Handle al Usuario porque el Heap no sabe que existe usuario y particular no sabe que tiene un atributo que guarda el Handle.

        handleUs1 = heap.agregarNodo(us1); // Caso general
        us1.setHandleEnHeap(handleUs1);
        Handle<Usuario> handleUs2 = heap.agregarNodo(us2);
        us2.setHandleEnHeap(handleUs2);
        Handle<Usuario> handleUs3 = heap.agregarNodo(us3);
        us3.setHandleEnHeap(handleUs3);

        maximo = heap.extraerMaximo();
        assertEquals(maximo, us2);
        assertEquals(heap.obtenerRaizHeap(), us1);
    }

    @Test
    void obtenerRaizHeap(){
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> heap.obtenerRaizHeap()); // heap vacio
        assertEquals("El heap esta vacio", excepcion.getMessage());

        Usuario us1 = new Usuario(26, 100, 0, 0, 0, null); // caso general
        Usuario us2 = new Usuario(99, 5000, 0, 0, 0, null);
        Usuario us3 = new Usuario(7, 40000, 0, 0, 0, null);

        Handle<Usuario> handleUs1 = heap.agregarNodo(us1); // De menor a mayor
        us1.setHandleEnHeap(handleUs1);
        Handle<Usuario> handleUs2 = heap.agregarNodo(us2);
        us2.setHandleEnHeap(handleUs2);
        Handle<Usuario> handleUs3 = heap.agregarNodo(us3);
        us3.setHandleEnHeap(handleUs3);

        Usuario maximoSinEditarHeap = heap.obtenerRaizHeap();
        assertEquals(maximoSinEditarHeap, us3);
    }

    @Test
    void siftUpSiftDown(){
        Usuario us1 = new Usuario(26, 10, 0, 0, 0, null);
        Usuario us2 = new Usuario(7, 20, 0, 0, 0, null);
        Usuario us3 = new Usuario(99, 30, 0, 0, 0, null);
        Usuario us4 = new Usuario(10, 40, 0, 0, 0, null);
        Usuario us5 = new Usuario(43, 50, 0, 0, 0, null);

        Handle<Usuario> handleUs1 = heap.agregarNodo(us1); // De menor a mayor
        us1.setHandleEnHeap(handleUs1);
        Handle<Usuario> handleUs2 = heap.agregarNodo(us2);
        us2.setHandleEnHeap(handleUs2);
        Handle<Usuario> handleUs3 = heap.agregarNodo(us3);
        us3.setHandleEnHeap(handleUs3);
        Handle<Usuario> handleUs4 = heap.agregarNodo(us4);
        us4.setHandleEnHeap(handleUs4);
        Handle<Usuario> handleUs5 = heap.agregarNodo(us5);
        us5.setHandleEnHeap(handleUs5);

        Usuario usMax = heap.obtenerRaizHeap();
        assertEquals(usMax.obtenerId(), us5.obtenerId());
        assertEquals(usMax.millasDisponibles(), us5.millasDisponibles());

        us1.handleEnHeap().eliminar(); // limpio el heap
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
        us4.handleEnHeap().eliminar();
        us5.handleEnHeap().eliminar();


        handleUs1 = heap.agregarNodo(us1);
        us1.setHandleEnHeap(handleUs1);
        handleUs2 = heap.agregarNodo(us2);
        us2.setHandleEnHeap(handleUs2);
        handleUs3 = heap.agregarNodo(us3);
        us3.setHandleEnHeap(handleUs3);
        handleUs4 = heap.agregarNodo(us4);
        us4.setHandleEnHeap(handleUs4);
        handleUs5 = heap.agregarNodo(us5);
        us5.setHandleEnHeap(handleUs5);

        Usuario maximo = heap.extraerMaximo();
        assertEquals(maximo.obtenerId(), 43);
        assertEquals(maximo.millasDisponibles(), 50.0);

        us1.handleEnHeap().eliminar(); // limpio el heap
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
        us4.handleEnHeap().eliminar();

        handleUs1 = heap.agregarNodo(us1); // Caso general
        us1.setHandleEnHeap(handleUs1);
        handleUs2 = heap.agregarNodo(us2);
        us2.setHandleEnHeap(handleUs2);
        handleUs3 = heap.agregarNodo(us3);
        us3.setHandleEnHeap(handleUs3);
        handleUs4 = heap.agregarNodo(us4);
        us4.setHandleEnHeap(handleUs4);
        handleUs5 = heap.agregarNodo(us5);
        us5.setHandleEnHeap(handleUs5);

        assertEquals(heap.obtenerRaizHeap(), us5);

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
        Usuario us = new Usuario(7, 20, 0, 0, 0, null);
        assertNull(us.handleEnHeap());
        Handle<Usuario> handleUs = heap.agregarNodo(us);
        us.setHandleEnHeap(handleUs);
        assertNotNull(us.handleEnHeap());
        assertEquals(us, us.handleEnHeap().valor());
    }

    @Test
    void eliminar(){
        Usuario us1 = new Usuario(99, 1230, 0, 0, 0, null);
        Usuario us2 = new Usuario(10, 4540, 0, 0, 0, null);
        Usuario us3 = new Usuario(43, 501, 0, 0, 0, null);
        Usuario us4 = new Usuario(26, 10256, 0, 0, 0, null);
        Usuario us5 = new Usuario(7, 2020, 0, 0, 0, null);

        Handle<Usuario> handleUs1 = heap.agregarNodo(us1);
        us1.setHandleEnHeap(handleUs1);
        Handle<Usuario> handleUs2 = heap.agregarNodo(us2);
        us2.setHandleEnHeap(handleUs2);
        Handle<Usuario> handleUs3 = heap.agregarNodo(us3);
        us3.setHandleEnHeap(handleUs3);
        Handle<Usuario> handleUs4 = heap.agregarNodo(us4);
        us4.setHandleEnHeap(handleUs4);
        Handle<Usuario> handleUs5 = heap.agregarNodo(us5);
        us5.setHandleEnHeap(handleUs5);

        us4.handleEnHeap().eliminar(); // la raiz
        assertEquals(heap.obtenerRaizHeap(), us2);

        us5.handleEnHeap().eliminar(); // el ultimo
        assertEquals(heap.obtenerRaizHeap(), us2); // la raiz no cambia

        us1.handleEnHeap().eliminar(); // los saco a todos
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
    }
}
