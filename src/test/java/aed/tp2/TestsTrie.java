package aed.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class TestsTrie {
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

