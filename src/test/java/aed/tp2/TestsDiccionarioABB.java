package aed.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestsDiccionarioABB {
    private DiccionarioABB abb;

    @BeforeEach
    public void setUp(){
        abb = new DiccionarioABB();
    }

    @Test
    public void testDiccionarioVacio(){
        assertFalse(abb.pertenece(10));
        assertNull(abb.obtener(10));
    }

    @Test
    public void testInsertarRaizYObtener(){
        Usuario usuario = new Usuario(15, 0.0, 0.0, 0.0, 0.0, null, null);
        abb.setKey(15, usuario);

        assertTrue(abb.pertenece(15));
        assertEquals(usuario, abb.obtener(15));
        assertFalse(abb.pertenece(10));
        assertNull(abb.obtener(10));
    }

    @Test
    public void testActualizarValor(){
        Usuario usuario = new Usuario(20, 100.0, 100.0, 0.0, 0.0, null, null);
        Usuario usuarioModificado = new Usuario(20, 500.0, 500.0, 0.0, 0.0, null, null);
        abb.setKey(20, usuario);
        assertEquals(100.0, abb.obtener(20).millasDisponibles());

        abb.setKey(20, usuarioModificado); // misma clave, tiene que pisar el valor
        assertTrue(abb.pertenece(20));
        assertEquals(500.0, abb.obtener(20).millasDisponibles());

    }

    @Test
    public void testSoloDerecha(){
        int[] ids = {10, 20, 30, 40, 50}; // todos por la rama derecha
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            abb.setKey(id, new Usuario(id, 0.0, 0.0, 0.0, 0.0, null, null));
        }

        for (int i = 0; i < ids.length; i++){ // veo que esten todos
            int id = ids[i];
            assertTrue(abb.pertenece(id));
            assertNotNull(abb.obtener(id));
            assertEquals(id, abb.obtener(id).obtenerId());
        }

        assertFalse(abb.pertenece(5));
        assertFalse(abb.pertenece(25));
        assertFalse(abb.pertenece(60));

    }

    @Test
    public void testBuscarPorBordes(){
        int[] ids = {50, 20, 70, 10, 30, 60, 80};
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            abb.setKey(id, new Usuario(id, 0.0, 0.0, 0.0, 0.0, null, null));
        }
        for (int i = 0; i < ids.length; i++){ // chequeo que se inserto todo
            int id = ids[i];
            assertTrue(abb.pertenece(id));
            assertNotNull(abb.obtener(id));
        }
        assertFalse(abb.pertenece(5)); // de aca para abajo no existen
        assertFalse(abb.pertenece(25));
        assertFalse(abb.pertenece(55));
        assertFalse(abb.pertenece(90));
    }

    @Test public void testModificacionesEnHojasYRamas(){
        abb.setKey(50, new Usuario(50, 10.0, 10.0, 0.0, 0.0, null, null));
        abb.setKey(20, new Usuario(20, 20.0, 20.0, 0.0, 0.0, null, null));
        abb.setKey(80, new Usuario(80, 30.0, 30.0, 0.0, 0.0, null, null));

        Usuario nuevoHijoIzquierdo = new Usuario(20, 999.0, 999.0, 0.0, 0.0, null, null); // modifico una hoja
        abb.setKey(20, nuevoHijoIzquierdo);

        assertEquals(999.0, abb.obtener(20).millasDisponibles());

        assertEquals(10.0, abb.obtener(50).millasDisponibles()); // los otros no se rompieron
        assertEquals(30.0, abb.obtener(80).millasDisponibles());

    }
}
