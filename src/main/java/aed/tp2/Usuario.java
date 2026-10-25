package aed.tp2;

public class Usuario {
    //Atributos del usuario
    private int id;
    private double millasDisponibles;
    private double millasCanjeadas;
    private double millasTransferidas;
    private double millasTotales;


    //private Conjunto transferencias; NO IMPLEMENTADO TODAVIA

    //Referencias a las estructuras de datos
    private NodoABB nodoEnABB;
    private NodoHeap nodoEnHeap;


    public int obtenerId(){
        return this.id;
    }

    // Métodos públicos necesarios para NodoHeap:
    public double millasDisponibles(){
        return millasDisponibles;
    }

    public NodoHeap nodoEnHeap(){
        return nodoEnHeap;
    }

    public void setNodoEnHeap(NodoHeap nuevoNodo){
        this.nodoEnHeap = nuevoNodo;
    }

    @Override
    public boolean equals(Object otroUsuario){
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
