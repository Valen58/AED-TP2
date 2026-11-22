package aed.tp2;
import java.util.ArrayList;
import java.util.Comparator;

public class Heap<T> {
    private ArrayList<NodoHeap> heap;
    private Comparator<T> comparador;

    public Heap(Comparator<T> comparador){
        this.heap = new ArrayList<NodoHeap>();
        this.comparador = comparador; // Asumiendo que nunca me pasan un comparador nulo porque entonces dentro del MaxHeap no podría comparar nada.
    }

    private class NodoHeap implements Handle<T>{ // el handle guarda la posicion en el heap
        private T valor;
        private int posicion;

        public NodoHeap (T val, int pos){
            valor = val;
            posicion = pos;
        }

        public T valor(){
            return valor;
        }

        /*
            En el caso de eliminar un nodo que no estaba en el heap (o que ya fue eliminado) podría tirar error.
            como este método lo estamos implementando unicamente porque está en la interfaz, es decir que no
            eliminamos nunca un handle, entonces este error no podría suceder.
        */
        public void eliminar(){
            Heap.this.eliminar(this); // Heap.this hace referencia al heap del nodo, con "this" solo estaría haciendo referencia al nodo.
        }

        public void modificar(T valorActualizado){
            this.valor = valorActualizado;
            Heap.this.actualizarHeap(this);
        }

        public boolean equals(Handle<T> handle){
            boolean res = false;
            if(handle != null){
                res = this.valor.equals(handle.valor());
            }
            return res;
        }

        public int posicion(){
            return posicion;
        }

        private void setPosicion(int i){ // solo lo tiene que llamar el Heap
            this.posicion = i;
        }
    }


    /*
        Método público que a partir de un valor, lo agrega en el heap. Además devuelve el Handle<T>
        que hace referencia al nuevo nodo.
    */
    public Handle<T> agregarNodo(T nuevoValor){
        NodoHeap nodoNuevoValor = new NodoHeap(nuevoValor, heap.size());
        heap.add(nodoNuevoValor); // lo agrego al final
        siftUp(heap.size() - 1); // lo subo hasta su lugar
        return nodoNuevoValor;
    }


    /*
        Método público que retira la raíz del Heap, en el caso de que tenga.
    */
    public T extraerMaximo(){
        if (heap.size() > 0){
            NodoHeap max = heap.get(0);
            NodoHeap ultimo = heap.get(heap.size() - 1);
            heap.set(0, ultimo); // el ultimo pasa a ser la raiz
            ultimo.setPosicion(0);
            heap.remove(heap.size() - 1);
            if (heap.size() > 0){
                siftDown(0); // lo bajo hasta su lugar
            }
            T maximo = max.valor();
            max.setPosicion(-1);
            return maximo;
        } else{
            throw new UnsupportedOperationException("El heap esta vacio");
        }
    }

    /*
        Método público que consulta la raíz del Heap, en caso de tenerla.
    */
    public T obtenerRaizHeap(){
        if (heap.size() > 0){
            return heap.get(0).valor(); // la raiz es el maximo
        } else {
            throw new UnsupportedOperationException("El heap esta vacio");
        }
    }

    // La cantidad de nodos que hay en el MaxHeap.
    public int cantidad(){
        return heap.size();

    }

    // Se invoca luego de modificar el valor de un nodo, para mantener los invariantes.
    public void actualizarHeap(Handle<T> valorHandle){
        if (valorHandle != null){
            NodoHeap nodoDelHeap = (NodoHeap) valorHandle;
            int posNodoDelHeap = nodoDelHeap.posicion();
            if (posNodoDelHeap > 0 && comparador.compare(nodoDelHeap.valor(), heap.get(indicePadre(posNodoDelHeap)).valor()) > 0){ // si supera al padre sube
                siftUp(posNodoDelHeap);
            } else { // sino baja
                siftDown(posNodoDelHeap);
            }
        }
    }

    /*
        Método privado que realiza el intercambio de nodos, para mantener los invariantes.
    */
    private void swap(int j, int k){
        NodoHeap nodoJ = heap.get(j);
        NodoHeap nodoK = heap.get(k);
        heap.set(j, nodoK);
        heap.set(k, nodoJ);
        nodoJ.setPosicion(k); // hay que actualizar la posicion de los dos handles
        nodoK.setPosicion(j);
    }

    private void siftUp(int i){ // "Sube" el nodo mientras no sea la raíz y que sea mayor al padre.
        int k = i;
        while (k > 0 && comparador.compare(heap.get(k).valor(), heap.get(indicePadre(k)).valor()) > 0){
            int p = indicePadre(k);
            swap(k, p);
            k = p;
        }
    }

    private void siftDown(int i){ // "Baja" el nodo mientras no sea una hoja y sea menor a su hijo mayor.
        int k = i;
        int m = indiceMayorDeLosHijos(k);
        while (m != -1 && comparador.compare(heap.get(k).valor(), heap.get(m).valor()) < 0){
            swap(k, m);
            k = m;
            m = indiceMayorDeLosHijos(k);
        }
    }

    /*
        Método público que elimina un nodo existente, al hacerlo le define una posición invalida.
        Este método no se utiliza en el sistema general, pero al ser parte de la interfaz de Handle, lo definimos.
    */
    public void eliminar(Handle<T> nodoAEliminar){
        NodoHeap nodoDelHeap = (NodoHeap) nodoAEliminar;
        int posicion = nodoDelHeap.posicion();
        int ultimaPos = heap.size() - 1;
        if (posicion != -1){
            if (posicion != ultimaPos){
                NodoHeap ultimoNodo = heap.get(ultimaPos);
                heap.set(posicion, ultimoNodo); // el ultimo ocupa su lugar
                ultimoNodo.setPosicion(posicion);
            }
            heap.remove(ultimaPos);
            nodoDelHeap.setPosicion(-1); // ya no tiene una posicion valida
            if (posicion < heap.size()){ // si no era el ultimo, lo reacomodo
                siftUp(posicion);
                siftDown(posicion);
            }
        } else {
            throw new UnsupportedOperationException("El handle ya fue eliminado");
        }
    }

    private int indicePadre(int i){
        return (i - 1) / 2;
    }

    private int indiceHijoDer(int i){
        return 2 * i + 2;
    }

    private int indiceHijoIzq(int i){
        return 2 * i + 1;
    }

    boolean hayHijoDer(int i){
        return indiceHijoDer(i) < heap.size();
    }

    boolean hayHijoIzq(int i){
        return indiceHijoIzq(i) < heap.size();
    }

    int indiceMayorDeLosHijos(int i){ // -1 si no tiene hijos
        if (hayHijoDer(i) && hayHijoIzq(i)){
            if (comparador.compare(heap.get(indiceHijoIzq(i)).valor(), heap.get(indiceHijoDer(i)).valor()) >= 0){
                return indiceHijoIzq(i);
            } else{
                return indiceHijoDer(i);
            }
        } else if (hayHijoDer(i)){
            return indiceHijoDer(i);
        } else if (hayHijoIzq(i)){
            return indiceHijoIzq(i);
        } else{
            return -1;
        }
    }
}
