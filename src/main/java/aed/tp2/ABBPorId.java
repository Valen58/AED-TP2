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

    // Cantidad de ids guardados, O(1) porque es un atributo
    public int cantidad(){
        return cantidadDeNodos;
    }

    // Devuelve el valor asociado al id, o null si el id no esta
    public V obtener(int id){
        Nodo nodoActual = raiz;
        while (nodoActual != null){
            if (id == nodoActual.id){
                return nodoActual.valor;
            } else if (id > nodoActual.id){
                nodoActual = nodoActual.hijoDerecho; // los ids mas grandes estan a la derecha
            } else {
                nodoActual = nodoActual.hijoIzquierdo; // los ids mas chicos estan a la izquierda
            }
        }
        return null;
    }

    // Agrega el id con su valor, si el id ya estaba, reemplaza el valor
    public void agregar(int id, V valor){
        if (raiz == null){
            raiz = new Nodo(id, valor);
            cantidadDeNodos++;
            return;
        }

        Nodo nodoActual = raiz;
        Nodo nodoPadre = raiz;
        while (nodoActual != null){
            nodoPadre = nodoActual; // guardamos el ultimo nodo visitado porque nodoActual termina en null
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

    public void guardarValoresEnOrden(V[] arregloDestino){
        guardarSubarbolEnOrden(raiz, arregloDestino, 0);
    }

    private int guardarSubarbolEnOrden(Nodo nodoActual, V[] arregloDestino, int posicionInicial){
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
