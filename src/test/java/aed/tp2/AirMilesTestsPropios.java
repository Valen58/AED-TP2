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
    
    // TESTS HEAP
    @Test
    void extraerMaximo(){
        // Intentar sacar el maximo de un heap vacío.
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> heap.extraerMaximo());
        assertEquals("El heap esta vacio", excepcion.getMessage());

        Usuario us1 = new Usuario(26, 100, 0, 0, 0, null, null);
        Usuario us2 = new Usuario(99, 5000, 0, 0, 0, null, null);
        Usuario us3 = new Usuario(7, 0, 0, 0, 0, null, null);
        
        // Extraer el unico usuario
        heap.agregarNodo(us1);
        
        Usuario maximo = heap.extraerMaximo();
        assertEquals(maximo.obtenerId(), 26);
        assertEquals(maximo.millasDisponibles(), 100.0);
        assertEquals(us1, maximo);
        assertNull(maximo.handleEnHeap());
        
        // Caso general
        heap.agregarNodo(us1);
        heap.agregarNodo(us2);
        heap.agregarNodo(us3);
        
        maximo = heap.extraerMaximo();
        assertEquals(maximo, us2);
        assertEquals(heap.usConMasMillasDisp(), us1);
    }
    
    @Test
    void usConMasMillasDisp(){
        // Intentar consultar las millas disponibles en un heap vacío.
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> heap.usConMasMillasDisp());
        assertEquals("El heap esta vacio", excepcion.getMessage());
        
        // Caso general
        Usuario us1 = new Usuario(26, 100, 0, 0, 0, null, null);
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
        
        // Caso insertar los usuarios de menor a mayor, respecto a las millas disponibles.
        heap.agregarNodo(us1);
        heap.agregarNodo(us2);
        heap.agregarNodo(us3);
        heap.agregarNodo(us4);
        heap.agregarNodo(us5);
        
        Usuario usMax = heap.usConMasMillasDisp();
        assertEquals(usMax.obtenerId(), us5.obtenerId());
        assertEquals(usMax.millasDisponibles(), us5.millasDisponibles());

        // Limpia el heap.
        us1.handleEnHeap().eliminar();
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
        us4.handleEnHeap().eliminar();
        us5.handleEnHeap().eliminar();

        // Caso insertar los usuarios de mayor a menor, respecto de las millas disponibles.

        heap.agregarNodo(us5);
        heap.agregarNodo(us4);
        heap.agregarNodo(us3);
        heap.agregarNodo(us2);
        heap.agregarNodo(us1);
        
        Usuario maximo = heap.extraerMaximo();
        assertEquals(maximo.obtenerId(), 43);
        assertEquals(maximo.millasDisponibles(), 50.0);

        // Limpia el heap.
        us1.handleEnHeap().eliminar();
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
        us4.handleEnHeap().eliminar();

        // Caso general
        heap.agregarNodo(us3);
        heap.agregarNodo(us1);
        heap.agregarNodo(us5);
        heap.agregarNodo(us2);
        heap.agregarNodo(us4);

        assertEquals(heap.usConMasMillasDisp(), us5);

        // Caso extraer todos los usuarios
        assertEquals(heap.extraerMaximo().obtenerId(), 43);
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

        // Caso eliminar la raíz
        us4.handleEnHeap().eliminar();
        assertEquals(heap.usConMasMillasDisp(), us2);
        
        // Caso eliminar un nodo cualquiera, en particular el último
        us5.handleEnHeap().eliminar();
        assertEquals(heap.usConMasMillasDisp(), us2); // Verifico que eliminar un nodo cualquiera no altera la raíz.
        
        // Caso eliminar todos los usuarios
        us1.handleEnHeap().eliminar();
        us2.handleEnHeap().eliminar();
        us3.handleEnHeap().eliminar();
        
        // Caso eliminar cuando el heap está vacío
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> Heap.eliminar(null));
        assertEquals("No se puede eliminar un nodo vacio", excepcion.getMessage());


    }
}