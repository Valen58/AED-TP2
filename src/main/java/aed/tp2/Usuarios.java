package aed.tp2;

public class Usuarios {
    //Atributos del usuario
    private int id;
    private double millasDisponibles;
    private double millasCanjeadas;
    private double millasTransferidas;
    private double millasTotales;


    //private Conjunto transferencias; NO IMPLEMENTADO TODAVIA

    //Referencias a las estructuras de datos
    private NodoABB nodoEnABB;
    //private NodoHeap nodoEnHeap; NO IMPLEMENTADO TODAVIA


    public int obtenerId(){
        return this.id;
    }

}
