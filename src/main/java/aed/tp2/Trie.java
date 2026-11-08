package aed.tp2;

public class Trie<T>{

    private NodoTrie<T> raiz;

    public Trie(){
        raiz = new NodoTrie<>();
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
