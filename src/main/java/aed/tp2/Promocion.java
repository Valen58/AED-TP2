package aed.tp2;

public class Promocion {

    private double factor;
    private Double tope;
    private int millasOtorgadas;

    public Promocion(double factor){ // sin tope
        this.factor = factor;
        this.tope = null;
        this.millasOtorgadas = 0;
    }

    public Promocion(double factor, double tope){ // con tope
        this.factor = factor;
        this.tope = tope;
        this.millasOtorgadas = 0;
    }

    public double getFactor(){
        return factor;
    }

    public Double getTope(){
        return tope;
    }

    public int getMillasOtorgadas(){
        return millasOtorgadas;
    }

    public void setMillasOtorgadas(int millasOtorgadas){
        this.millasOtorgadas = millasOtorgadas;
    }

    public boolean tieneTope(){
        return tope != null;
    }

    public boolean estaActiva(){ // sin tope siempre, con tope hasta llegar a el
        return !tieneTope() || millasOtorgadas < tope;
    }

}
