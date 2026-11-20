package aed.tp2;

public class ABBPorId<V> {

    private class Nodo {
        int id;
        V valor;
        Nodo hijoIzquierdo;
        Nodo hijoDerecho;

        Nodo(int id, V valor){
            this.id = id;
            this.valor = valor;
            this.hijoIzquierdo = null;
            this.hijoDerecho = null;
        }
    }

    private Nodo raiz;
    private int cantidadDeNodos;

    public ABBPorId(){
        this.raiz = null;
        this.cantidadDeNodos = 0;
    }

    public int cantidad(){ // O(1)
        return cantidadDeNodos;
    }

    public V obtener(int id){ // null si no esta
        Nodo nodoActual = raiz;
        while (nodoActual != null){
            if (id == nodoActual.id){
                return nodoActual.valor;
            } else if (id > nodoActual.id){
                nodoActual = nodoActual.hijoDerecho; // ids mas grandes a la derecha
            } else {
                nodoActual = nodoActual.hijoIzquierdo; // ids mas chicos a la izquierda
            }
        }
        return null;
    }

    public void agregar(int id, V valor){ // si el id ya estaba reemplaza el valor
        if (raiz == null){
            raiz = new Nodo(id, valor);
            cantidadDeNodos++;
            return;
        }

        Nodo nodoActual = raiz;
        Nodo nodoPadre = raiz;
        while (nodoActual != null){
            nodoPadre = nodoActual; // me guardo el ultimo nodo porque nodoActual termina en null
            if (id == nodoActual.id){
                nodoActual.valor = valor;
                return;
            } else if (id > nodoActual.id){
                nodoActual = nodoActual.hijoDerecho;
            } else {
                nodoActual = nodoActual.hijoIzquierdo;
            }
        }

        Nodo nodoNuevo = new Nodo(id, valor);
        if (id > nodoPadre.id){
            nodoPadre.hijoDerecho = nodoNuevo;
        } else {
            nodoPadre.hijoIzquierdo = nodoNuevo;
        }
        cantidadDeNodos++;
    }

    public void guardarValoresEnOrden(V[] arregloDestino){ // ordenados por id, O(U)
        guardarSubarbolEnOrden(raiz, arregloDestino, 0);
    }

    private int guardarSubarbolEnOrden(Nodo nodoActual, V[] arregloDestino, int posicionInicial){ // devuelve la proxima posicion libre
        int siguientePosicionLibre = posicionInicial;
        if (nodoActual != null){
            siguientePosicionLibre = guardarSubarbolEnOrden(nodoActual.hijoIzquierdo, arregloDestino, siguientePosicionLibre);
            arregloDestino[siguientePosicionLibre] = nodoActual.valor;
            siguientePosicionLibre++;
            siguientePosicionLibre = guardarSubarbolEnOrden(nodoActual.hijoDerecho, arregloDestino, siguientePosicionLibre);
        }
        return siguientePosicionLibre;
    }
}
