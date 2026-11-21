package aed.tp2;

public class Trie<T>{

    private NodoTrie<T> raiz;

    public Trie(){
        raiz = new NodoTrie<>();
    }

    private static class NodoTrie<T>{ //le pongo static solo para decirle a java "cada nodo es independiente y no tiene que guardar un puntero oculto al trie que lo creo" es para evitar que tire una warning de the type T is hiding the type T

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


    public void insertar(String palabra, T valor){ // si ya estaba reemplaza el valor
        NodoTrie<T> actual = raiz;
        for (int i=0; i < palabra.length(); i++){
            char caracter = palabra.charAt(i);
            NodoTrie<T> hijo = actual.getHijo(caracter);
            if(hijo == null){
                hijo = new NodoTrie<>();
                actual.setHijo(caracter, hijo);
            }
            actual = hijo;
        }
        actual.setValor(valor);
    }

    public T buscar(String palabra){ // null si no esta
        NodoTrie<T> actual = raiz;
        for(int i = 0; i < palabra.length(); i++){
            char caracter = palabra.charAt(i);
            actual = actual.getHijo(caracter);

            if(actual == null){
                return null;
            }
        }
        return actual.getValor();
    }

    public boolean eliminar(String palabra){ // false si no estaba
        return eliminar(raiz, palabra, 0);
    }
    private boolean eliminar(NodoTrie<T> actual, String palabra, int indice){
        if (indice == palabra.length()){
            if (actual.getValor() == null){
                return false;
            }
            actual.setValor(null);
            return true;
        }
        char caracter = palabra.charAt(indice);
        NodoTrie<T> hijo = actual.getHijo(caracter);

        if(hijo == null){
            return false;
        }

        boolean eliminado = eliminar(hijo, palabra, indice+1);

        if(eliminado && hijo.esVacio()){ // si quedo vacio lo saco del arbol
            actual.setHijo(caracter, null);
        }
        return eliminado;
    }
}
