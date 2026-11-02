package aed.tp2;

public class NodoABB {
    private Usuario usuario;
    private NodoABB izq;
    private NodoABB der;


    //Constructor
    public NodoABB(Usuario usuario){
        this.usuario = usuario;
        this.der = null;
        this.izq = null;
    }

    //Getters y Setters
    public Usuario obtenerUsuario(){
        return usuario;
    }

    public void setUsuario(Usuario usuario){
        this.usuario = usuario;
    }

    public NodoABB obtenerIzquierdo(){
        return izq;
    }

    public void setIzquierdo(NodoABB nodo){
        this.izq = nodo;
    }

    public NodoABB obtenerDerecho(){
        return der;
    }

    public void setDerecho(NodoABB nodo){
        this.der = nodo;
    }


}
