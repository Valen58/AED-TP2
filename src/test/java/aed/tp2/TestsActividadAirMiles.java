package aed.tp2;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import static org.junit.jupiter.api.Assertions.*;

public class TestsActividadAirMiles {
    private AirMiles am;

    @BeforeEach
    void setUp() {
        am = new AirMiles();
        for (int id = 1; id <= 5; id++) { // cinco usuarios con 1000 millas cada uno
            am.registrarUsuario(id);
            am.acumularMillas(id, 1000, "NoPromo");
        }
    }

    @Test
    void paresEnAmbosSentidos() {
        am.transferirMillas(1, 2, 100);
        am.transferirMillas(2, 1, 50);

        int[][] exclusivos = am.paresTransferidoresExclusivos(); // sigue siendo un solo par
        assertEquals(1, exclusivos.length);
        assertTrue((exclusivos[0][0] == 1 && exclusivos[0][1] == 2) || (exclusivos[0][0] == 2 && exclusivos[0][1] == 1));
    }

    @Test
    void recibirTambienRompeLaExclusividad() {
        am.transferirMillas(1, 2, 100);
        assertEquals(1, am.paresTransferidoresExclusivos().length);

        am.transferirMillas(3, 2, 100); // 2 tuvo contacto con 1 y con 3, aunque a 3 nunca le envio nada
        assertEquals(0, am.paresTransferidoresExclusivos().length);
    }

    @Test
    void cadenaDeTransferencias() {
        am.transferirMillas(1, 2, 10);
        am.transferirMillas(2, 3, 10);
        am.transferirMillas(3, 4, 10);

        assertEquals(0, am.paresTransferidoresExclusivos().length);
    }

    @Test
    void trianguloDeTransferencias() {
        am.transferirMillas(1, 2, 10);
        am.transferirMillas(2, 3, 10);
        am.transferirMillas(3, 1, 10);

        assertEquals(0, am.paresTransferidoresExclusivos().length);
    }

    @Test
    void romperUnParNoAfectaALosDemas() {
        am.transferirMillas(1, 2, 10);
        am.transferirMillas(3, 4, 10);
        assertEquals(2, am.paresTransferidoresExclusivos().length);

        am.transferirMillas(1, 5, 10); // se rompe el par (1,2) pero el (3,4) sigue
        int[][] exclusivos = am.paresTransferidoresExclusivos();
        assertEquals(1, exclusivos.length);
        assertTrue((exclusivos[0][0] == 3 && exclusivos[0][1] == 4) || (exclusivos[0][0] == 4 && exclusivos[0][1] == 3));
    }

    @Test
    void paresConIdsRegistradosDesordenados() {
        AirMiles otro = new AirMiles();
        int[] ids = {50, 10, 90, 30, 70};
        for (int i = 0; i < ids.length; i++) {
            otro.registrarUsuario(ids[i]);
            otro.acumularMillas(ids[i], 1000, "NoPromo");
        }
        otro.transferirMillas(90, 10, 10);
        otro.transferirMillas(70, 30, 10);

        int[][] pares = otro.paresTransferidoresExclusivos();
        assertEquals(2, pares.length);
        boolean par1090 = false, par3070 = false;
        for (int[] par : pares) {
            int a = Math.min(par[0], par[1]);
            int b = Math.max(par[0], par[1]);
            if (a == 10 && b == 90) par1090 = true;
            if (a == 30 && b == 70) par3070 = true;
        }
        assertTrue(par1090 && par3070);
    }

    @Test
    void transferenciasRechazadasNoCreanPares() {
        Exception excepcion = assertThrows(UnsupportedOperationException.class, () -> am.transferirMillas(1, 2, 5000)); // saldo insuficiente
        assertEquals("El usuario origen no tiene millas disponibles suficientes para transferir.", excepcion.getMessage());

        excepcion = assertThrows(UnsupportedOperationException.class, () -> am.transferirMillas(1, 1, 10)); // a uno mismo
        assertEquals("El usuario origen y el usuario destino de la transferencia son el mismo.", excepcion.getMessage());

        excepcion = assertThrows(UnsupportedOperationException.class, () -> am.transferirMillas(1, 99, 10)); // destino inexistente
        assertEquals("El usuario origen o el usuario destino de la transferencia no existe.", excepcion.getMessage());

        assertEquals(0, am.paresTransferidoresExclusivos().length); // no se creo ningun par
        assertEquals(-1, am.elMasCanjeador()); // nadie gasto
        assertEquals(1000.0, am.consultarSaldo(1)); // y el saldo no cambio
    }

    @Test
    void elMasCanjeadorSinGastos() {
        assertEquals(-1, am.elMasCanjeador());
    }

    @Test
    void elMasCanjeadorSumaCanjesYTransferencias() {
        am.canjearMillas(2, 80);
        assertEquals(2, am.elMasCanjeador());

        am.transferirMillas(1, 3, 100); // 1 gasta 100, mas que los 80 de 2
        assertEquals(1, am.elMasCanjeador());

        am.canjearMillas(2, 30); // 2 llega a 110
        assertEquals(2, am.elMasCanjeador());
    }

    @Test
    void elMasCanjeadorNoCuentaLoRecibido() {
        am.transferirMillas(1, 2, 100);

        assertEquals(1, am.elMasCanjeador()); // gasto 1, aunque 2 tenga mas millas disponibles
        assertEquals(1100.0, am.consultarSaldo(2));
    }

    @Test
    void elMasAcumuladorNoCuentaTransferenciasRecibidas() {
        am.acumularMillas(5, 500, "NoPromo"); // 5 acumula 500 mas que el resto
        assertEquals(5, am.elMasAcumulador());

        am.transferirMillas(1, 2, 1000); // 2 tiene mas disponibles pero sus totales no cambian
        assertEquals(2000.0, am.consultarSaldo(2));
        assertEquals(5, am.elMasAcumulador());
    }

    @Test
    void canjearNoBajaElMasAcumulador() {
        am.acumularMillas(3, 1000, "NoPromo");
        assertEquals(3, am.elMasAcumulador());

        am.canjearMillas(3, 2000);
        assertEquals(0.0, am.consultarSaldo(3));
        assertEquals(3, am.elMasAcumulador());
        assertEquals("Bronce", am.consultarCategoria(3));
    }
}
