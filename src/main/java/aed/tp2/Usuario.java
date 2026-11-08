package aed.tp2;

public class Usuario {
    private int id;
    private double millasDisponibles;
    private double millasTotales;
    private double millasCanjeadas;
    private double millasTransferidas;
    private NodoABB nodoEnABB;
    private Handle<Usuario> handleEnHeap; // lo usa el heap


    public Usuario(int id, double millasD, double millasT, double millasC, double millasTrans, NodoABB nodoABB, Handle<Usuario> hEnHeap){
        this.id = id;
        this.millasDisponibles = millasD;
        this.millasTotales = millasT;
        this.millasCanjeadas = millasC;
        this.millasTransferidas = millasTrans;
        this.nodoEnABB = nodoABB;
        this.handleEnHeap = hEnHeap;
    }

    public double factorCategoria(){ // segun las millas totales
        double factor;
        if (millasTotales >= 50000 && millasTotales < 1000000){
            factor = 2.0;
        } else if (millasTotales >= 10000 && millasTotales < 50000){
            factor = 1.5;
        } else if (millasTotales >= 0 && millasTotales < 10000){
            factor = 1.0;
        } else {
            factor = 0;
        }
        return factor;
    }

    public String categoria(){ // segun las millas totales
        String categoriaCalculada;
        if (millasTotales >= 50000 && millasTotales < 1000000){
            categoriaCalculada = "Oro";
        } else if (millasTotales >= 10000 && millasTotales < 50000){
            categoriaCalculada = "Plata";
        } else if (millasTotales >= 0 && millasTotales < 10000){
            categoriaCalculada = "Bronce";
        } else {
            categoriaCalculada = "Platino";
        }
        return categoriaCalculada;
    }

        public void sumarMillas(double millas){
        if (millas > 0){
            this.millasDisponibles += millas;
            this.millasTotales += millas;
        }
    }

    public void restarMillas(double millas){ // solo las disponibles, las totales no
        if (millas > 0){
            this.millasDisponibles -= millas;
        }
    }

    public void recibirMillas(double millas){ // solo las disponibles, las totales no
        if (millas > 0){
            this.millasDisponibles += millas;
        }
    }

    public int obtenerId(){
        return this.id;
    }

    public double millasTotales(){
        return millasTotales;
    }

    public double millasDisponibles(){ // el heap ordena por esto
        return millasDisponibles;
    }

    public Handle<Usuario> handleEnHeap(){
        return handleEnHeap;
    }

    public void setHandleEnHeap(Handle<Usuario> nuevoNodo){
        this.handleEnHeap = nuevoNodo;
    }

    @Override
    public boolean equals(Object otroUsuario){ // dos usuarios son iguales si tienen el mismo id
        boolean iguales = false;
        if (otroUsuario != null){
            boolean mismaClase = this.getClass() == otroUsuario.getClass();
            if (mismaClase){
                Usuario otroUsuarioCasteado = (Usuario) otroUsuario;
                iguales = this.obtenerId() == otroUsuarioCasteado.obtenerId();
            }
        }
        return iguales;
    }
}
