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
    }
        // TEST TRIE
    @Test
    void insertarYBuscarPromo(){
        Trie<Double> trie = new Trie<>();

        // inserto promociones
        trie.insertar("bariloche", 150.0);
        trie.insertar("brasil", 200.0);
        trie.insertar("caribe20", 200.01);

        // verifico q cada promo tenga su valor correspondiente
        assertEquals(150.0, trie.buscar("bariloche"));
        assertEquals(200.0, trie.buscar("brasil"));
        assertEquals(200.01, trie.buscar("caribe20"));

        // busco una promo q no existe
        assertNull(trie.buscar("MDQ"));
    }

    @Test
    void insertoConPrefijo(){
        Trie<Double> trie = new Trie<>();

        trie.insertar("mar", 2000.0);
        trie.insertar("marDeAjo", 100.1);
        trie.insertar("marDeLasPampas", 20.3);

        // Verifico q existen
        assertEquals(2000.0, trie.buscar("mar"));
        assertEquals(100.1, trie.buscar("marDeAjo"));
        assertEquals(20.3, trie.buscar("marDeLasPampas"));

        // verifico q una promo no existe
        assertNull(trie.buscar("marRojo"));
    }

    @Test
    void elimina(){
        Trie<Double> trie = new Trie<>();

        // inserto promociones
        trie.insertar("bariloche", 150.0);
        trie.insertar("brasil", 200.0);
        trie.insertar("caribe20", 200.01);

        // eliminar promo existente
        assertTrue(trie.eliminar("bariloche"));
        assertNull(trie.buscar("bariloche"));

        // verificar que las otras sigan existiendo
        assertEquals(200.0, trie.buscar("brasil"));
        assertEquals(200.01, trie.buscar("caribe20"));

        // eliminar una promo inexistente
        assertFalse(trie.eliminar("Europa"));

        // eliminar dos veces la misma promo
        assertTrue(trie.eliminar("brasil"));
        assertFalse(trie.eliminar("brasil"));
    }

    @Test
    void eliminaConPrefijo(){
        Trie<Double> trie = new Trie<>();

        trie.insertar("mar", 2000.0);
        trie.insertar("marDeAjo", 100.1);
        trie.insertar("marDeLasPampas", 20.3);

        // Elimino prefijo y verifico que las otras siguen estando
        assertTrue(trie.eliminar("mar"));
        assertNull(trie.buscar("mar"));
        assertEquals(100.1, trie.buscar("marDeAjo"));
        assertEquals(20.3, trie.buscar("marDeLasPampas"));

        // Elimino la palabra mas larga y chequeo q la otra siga estando
        assertTrue(trie.eliminar("marDeLasPampas"));
        assertNull(trie.buscar("marDeLasPampas"));
        assertEquals(100.1, trie.buscar("marDeAjo"));
    }

    @Test 
    void eliminoTodo(){
        Trie<Double> trie = new Trie<>();

        trie.insertar("mar", 2000.0);
        trie.insertar("marDeAjo", 100.1);
        trie.insertar("marDeLasPampas", 20.3);

        // elimino todo
        assertTrue(trie.eliminar("mar"));
        assertTrue(trie.eliminar("marDeAjo"));
        assertTrue(trie.eliminar("marDeLasPampas"));
        assertNull(trie.buscar("mar"));
        assertNull(trie.buscar("marDeAjo"));
        assertNull(trie.buscar("marDeLasPampas"));

        // verifico que no puedo volver a eliminar la misma
        assertFalse(trie.eliminar("mar"));
    }

    @Test
    void actualizarValor(){
        Trie<Double> trie = new Trie<>();

        trie.insertar("mar", 2000.0);

        // inserto la misma promo con otro valor y verifico q haya cambiado
        trie.insertar("mar", 200.0);
        assertEquals(200.0, trie.buscar("mar"));
    }

    @Test
    void reinsertarPromoEliminada(){
        Trie<Double> trie = new Trie<>();

        trie.insertar("mar", 2000.0);

        // elimino promo y vuelvo a insertarla

        assertTrue(trie.eliminar("mar"));
        assertNull(trie.buscar("mar"));
        trie.insertar("mar", 200.0);
        assertEquals(200.0, trie.buscar("mar"));
    }
}
