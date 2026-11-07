package aed.tp2;

public class RegistroActividad {
    private int idUsuario;
    private double millasGastadas; // canjeadas + transferidas enviadas
    private RegistroActividad primeraContraparte; // primer usuario con el que transfirio (enviando o recibiendo), null si todavia no transfirio
    private boolean transfirioConVariosUsuarios; // true apenas opero con dos usuarios distintos, nunca vuelve a false

    public RegistroActividad(int idUsuario){
        this.idUsuario = idUsuario;
        this.millasGastadas = 0;
        this.primeraContraparte = null;
        this.transfirioConVariosUsuarios = false;
    }

    public int obtenerId(){
        return idUsuario;
    }

    public double obtenerMillasGastadas(){
        return millasGastadas;
    }

    // Suma millas gastadas (por un canje o por una transferencia enviada), O(1)
    public void sumarMillasGastadas(double millas){
        if (millas > 0){
            this.millasGastadas += millas;
        }
    }

    public RegistroActividad obtenerContraparte(){
        return primeraContraparte;
    }

    public boolean transfirioConVariosUsuarios(){
        return transfirioConVariosUsuarios;
    }

    public void registrarTransferenciaCon(RegistroActividad otroRegistro){
        if (this.primeraContraparte == null){
            this.primeraContraparte = otroRegistro;
        } else if (this.primeraContraparte != otroRegistro){
            this.transfirioConVariosUsuarios = true;
        }
    }
}
