package aed.tp2;

interface Handle<T>{
    public T valor(); // devuelve el valor del elemento

    public void eliminar(); // lo saca de la coleccion

    public void modificar(T v); // lo modifica y reacomoda si hace falta

    public boolean equals(Handle<T> h); // si los dos representan el mismo valor



}
