package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestsRegistroActividad {
    private RegistroActividad reg1;
    private RegistroActividad reg2;
    private RegistroActividad reg3;

    @BeforeEach
    void setUp(){
        reg1 = new RegistroActividad(1);
        reg2 = new RegistroActividad(2);
        reg3 = new RegistroActividad(3);
    }

    @Test
    void registroNuevo(){
        assertEquals(1, reg1.obtenerId());
        assertEquals(0.0, reg1.obtenerMillasGastadas());
        assertNull(reg1.obtenerContraparte());
        assertFalse(reg1.transfirioConVariosUsuarios());
    }

    @Test
    void sumarMillasGastadas(){
        reg1.sumarMillasGastadas(100); // caso general
        reg1.sumarMillasGastadas(50.5);
        assertEquals(150.5, reg1.obtenerMillasGastadas());

        reg1.sumarMillasGastadas(0); // cero y negativas no suman
        reg1.sumarMillasGastadas(-40);
        assertEquals(150.5, reg1.obtenerMillasGastadas());
    }

    @Test
    void primeraContraparte(){
        assertNull(reg1.obtenerContraparte()); // sin transferencias no tiene

        reg1.registrarTransferenciaCon(reg2); // la primera define la contraparte
        assertEquals(reg2, reg1.obtenerContraparte());
        assertFalse(reg1.transfirioConVariosUsuarios());

        reg1.registrarTransferenciaCon(reg2); // repetir con el mismo no rompe la exclusividad
        reg1.registrarTransferenciaCon(reg2);
        assertEquals(reg2, reg1.obtenerContraparte());
        assertFalse(reg1.transfirioConVariosUsuarios());
    }

    @Test
    void segundaContraparteRompeLaExclusividad(){
        reg1.registrarTransferenciaCon(reg2);
        reg1.registrarTransferenciaCon(reg3);
        assertTrue(reg1.transfirioConVariosUsuarios());

        assertEquals(reg2, reg1.obtenerContraparte()); // la primera se mantiene

        reg1.registrarTransferenciaCon(reg2); // volver con la primera no devuelve la exclusividad
        assertTrue(reg1.transfirioConVariosUsuarios());
    }

    @Test
    void parSimetricoYRomperUnLado(){
        reg1.registrarTransferenciaCon(reg2);
        reg2.registrarTransferenciaCon(reg1);
        assertEquals(reg2, reg1.obtenerContraparte());
        assertEquals(reg1, reg2.obtenerContraparte());

        reg1.registrarTransferenciaCon(reg3); // reg1 deja de ser exclusivo pero reg2 sigue con reg1
        assertTrue(reg1.transfirioConVariosUsuarios());
        assertFalse(reg2.transfirioConVariosUsuarios());
        assertEquals(reg1, reg2.obtenerContraparte());
    }

    @Test
    void registrarTransferenciaNoModificaAlOtroRegistro(){
        reg1.registrarTransferenciaCon(reg2);

        assertNull(reg2.obtenerContraparte()); // reg2 no sabe nada hasta que lo registren de su lado
        assertFalse(reg2.transfirioConVariosUsuarios());
        assertEquals(0.0, reg2.obtenerMillasGastadas());
    }
}
