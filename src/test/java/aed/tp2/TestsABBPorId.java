package aed.tp2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class TestsABBPorId {
    private ABBPorId<Usuario> abb;

    @BeforeEach
    public void setUp(){
        abb = new ABBPorId<>();
    }

    @Test
    public void testDiccionarioVacio(){
        assertEquals(0, abb.cantidad());
        assertNull(abb.obtener(10));
    }

    @Test
    public void testInsertarRaizYObtener(){
        Usuario usuario = new Usuario(15, 0.0, 0.0, 0.0, 0.0, null, null);
        abb.agregar(15, usuario);

        assertEquals(1, abb.cantidad());
        assertEquals(usuario, abb.obtener(15));
        assertNull(abb.obtener(10));
    }

    @Test
    public void testActualizarValor(){
        Usuario usuario = new Usuario(20, 100.0, 100.0, 0.0, 0.0, null, null);
        Usuario usuarioModificado = new Usuario(20, 500.0, 500.0, 0.0, 0.0, null, null);
        abb.agregar(20, usuario);
        assertEquals(100.0, abb.obtener(20).millasDisponibles());

        abb.agregar(20, usuarioModificado); // misma clave, pisa el valor y no crea otro nodo
        assertEquals(1, abb.cantidad());
        assertEquals(500.0, abb.obtener(20).millasDisponibles());
    }

    @Test
    public void testSoloDerecha(){
        int[] ids = {10, 20, 30, 40, 50}; // todos por la rama derecha
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            abb.agregar(id, new Usuario(id, 0.0, 0.0, 0.0, 0.0, null, null));
        }

        assertEquals(5, abb.cantidad());
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            assertNotNull(abb.obtener(id));
            assertEquals(id, abb.obtener(id).obtenerId());
        }

        assertNull(abb.obtener(5));
        assertNull(abb.obtener(25));
        assertNull(abb.obtener(60));
    }

    @Test
    public void testSoloIzquierda(){
        int[] ids = {50, 40, 30, 20, 10}; // todos por la rama izquierda
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            abb.agregar(id, new Usuario(id, 0.0, 0.0, 0.0, 0.0, null, null));
        }

        assertEquals(5, abb.cantidad());
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            assertNotNull(abb.obtener(id));
            assertEquals(id, abb.obtener(id).obtenerId());
        }
    }

    @Test
    public void testBuscarPorBordes(){
        int[] ids = {50, 20, 70, 10, 30, 60, 80};
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            abb.agregar(id, new Usuario(id, 0.0, 0.0, 0.0, 0.0, null, null));
        }
        for (int i = 0; i < ids.length; i++){ // chequeo que se inserto todo
            int id = ids[i];
            assertNotNull(abb.obtener(id));
        }
        assertNull(abb.obtener(5)); // de aca para abajo no existen
        assertNull(abb.obtener(25));
        assertNull(abb.obtener(55));
        assertNull(abb.obtener(90));
        assertEquals(7, abb.cantidad()); // buscar no modifica nada
    }

    @Test
    public void testModificacionesEnHojasYRamas(){
        abb.agregar(50, new Usuario(50, 10.0, 10.0, 0.0, 0.0, null, null));
        abb.agregar(20, new Usuario(20, 20.0, 20.0, 0.0, 0.0, null, null));
        abb.agregar(80, new Usuario(80, 30.0, 30.0, 0.0, 0.0, null, null));

        abb.agregar(20, new Usuario(20, 999.0, 999.0, 0.0, 0.0, null, null)); // modifico una hoja
        assertEquals(999.0, abb.obtener(20).millasDisponibles());

        assertEquals(10.0, abb.obtener(50).millasDisponibles()); // los otros no se rompieron
        assertEquals(30.0, abb.obtener(80).millasDisponibles());
        assertEquals(3, abb.cantidad());
    }

    @Test
    public void testRecorridoEnOrden(){
        int[] ids = {50, 20, 70, 10, 30, 60, 80}; // desordenados
        int[] esperado = {10, 20, 30, 50, 60, 70, 80}; // tienen que salir ordenados por id
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            abb.agregar(id, new Usuario(id, 0.0, 0.0, 0.0, 0.0, null, null));
        }

        Usuario[] ordenados = new Usuario[abb.cantidad()];
        abb.guardarValoresEnOrden(ordenados);
        for (int i = 0; i < esperado.length; i++){
            assertEquals(esperado[i], ordenados[i].obtenerId());
        }
    }

    @Test
    public void testRecorridoEnOrdenConIdsNegativosYCero(){
        int[] ids = {5, -10, 0, -5};
        int[] esperado = {-10, -5, 0, 5};
        for (int i = 0; i < ids.length; i++){
            int id = ids[i];
            abb.agregar(id, new Usuario(id, 0.0, 0.0, 0.0, 0.0, null, null));
        }

        Usuario[] ordenados = new Usuario[abb.cantidad()];
        abb.guardarValoresEnOrden(ordenados);
        for (int i = 0; i < esperado.length; i++){
            assertEquals(esperado[i], ordenados[i].obtenerId());
        }
    }

    @Test
    public void testRecorridoConArregloMasGrandeQueLaCantidad(){
        abb.agregar(2, new Usuario(2, 0.0, 0.0, 0.0, 0.0, null, null));
        abb.agregar(1, new Usuario(1, 0.0, 0.0, 0.0, 0.0, null, null));

        Usuario[] ordenados = new Usuario[4]; // sobra lugar, lo que sobra no se toca
        abb.guardarValoresEnOrden(ordenados);
        assertEquals(1, ordenados[0].obtenerId());
        assertEquals(2, ordenados[1].obtenerId());
        assertNull(ordenados[2]);
        assertNull(ordenados[3]);
    }

    @Test
    public void testGuardaOtrosTiposDeValores(){
        ABBPorId<String> abbDeStrings = new ABBPorId<>(); // sirve para cualquier tipo, no solo Usuario
        abbDeStrings.agregar(2, "dos");
        abbDeStrings.agregar(1, "uno");

        assertEquals("uno", abbDeStrings.obtener(1));
        assertEquals("dos", abbDeStrings.obtener(2));
        assertNull(abbDeStrings.obtener(3));
    }
}
