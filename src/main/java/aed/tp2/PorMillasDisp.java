package aed.tp2;
import java.util.Comparator;

public class PorMillasDisp implements Comparator<Usuario>{
    @Override 
    public int compare(Usuario us1, Usuario us2){
        return Double.compare(us1.millasDisponibles(), us2.millasDisponibles());
    }
}
