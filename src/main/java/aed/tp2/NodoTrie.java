package aed.tp2;

public class NodoTrie<T>{

    private NodoTrie<T>[] hijos;
    private T valor;
    private int cantidadHijos;

    @SuppressWarnings("unchecked")
    public NodoTrie(){
        hijos = (NodoTrie<T>[]) new NodoTrie[256];
        valor = null;
        cantidadHijos = 0;
    }

    public NodoTrie<T> getHijo(char caracter){
        return hijos[caracter];
    }

    public void setHijo(char caracter, NodoTrie<T> hijo){
        if(hijos[caracter] == null && hijo != null){
            cantidadHijos++;
        }
        if(hijos[caracter] != null && hijo == null){
            cantidadHijos--;
        }
        
        hijos[caracter] = hijo;
    }

    public T getValor(){
        return valor;
    }

    public void setValor(T valor){
        this.valor = valor;
    }

    public boolean tieneHijos(){
        return cantidadHijos > 0;
    }

    public boolean esVacio(){
        return valor == null && !tieneHijos();
    }

}