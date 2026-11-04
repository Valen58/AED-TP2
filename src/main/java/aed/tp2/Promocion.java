package aed.tp2;

public class Promocion {

    private double factor;
    private Double tope;
    private int millasOtorgadas;

    // PROMO SIN TOPE
    public Promocion(double factor){
        this.factor = factor;
        this.tope = null;
        this.millasOtorgadas = 0;
    }

    // PROMO CON TOPE
    public Promocion(double factor, double tope){
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

    public boolean estaActiva(){
        return !tieneTope() || millasOtorgadas < tope;
    }

}