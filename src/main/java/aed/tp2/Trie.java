package aed.tp2;

public class Trie<T>{
    
    private NodoTrie<T> raiz;

    public Trie(){
        raiz = new NodoTrie<>();
    }

    // INSERTAR
    public void insertar(String palabra, T valor){
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
    
    // BUSCAR
    public T buscar(String palabra){
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

    // ELIMINAR
    public boolean eliminar(String palabra){
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

        if(eliminado && hijo.esVacio()){
            actual.setHijo(caracter, null);
        }
        return eliminado;
    }
}