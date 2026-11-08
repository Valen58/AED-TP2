package aed.tp2;

public class RegistroActividad {
    private int idUsuario;
    private double millasGastadas; // canjeadas y transferidas enviadas
    private RegistroActividad primeraContraparte; // con quien transfirio primero, null si todavia no
    private boolean transfirioConVariosUsuarios; // una vez en true no vuelve a false

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

    public void sumarMillasGastadas(double millas){ // por un canje o una transferencia enviada
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

    public void registrarTransferenciaCon(RegistroActividad otroRegistro){ // cuenta enviando o recibiendo
        if (this.primeraContraparte == null){
            this.primeraContraparte = otroRegistro;
        } else if (this.primeraContraparte != otroRegistro){ // aparecio otro distinto
            this.transfirioConVariosUsuarios = true;
        }
    }
}
