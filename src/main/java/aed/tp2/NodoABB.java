package aed.tp2;


interface NodoABB<T>{
    public Usuario obtenerUsuario(); // devuelve el valor del elemento

    public void setUsuario(Usuario usuario); // lo saca de la coleccion

    public NodoABB<T> obtenerIzquierdo(); // lo modifica y reacomoda si hace falta

    public void setIzquierdo(NodoABB<T> nodo); // si los dos representan el mismo valor

    public NodoABB<T> obtenerDerecho();

    public void setDerecho(NodoABB<T> nodo);

}

//ESTE ARCHIVO NO VA MAS
