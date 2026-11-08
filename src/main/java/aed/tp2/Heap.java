package aed.tp2;
import java.util.ArrayList;

public class Heap {
    private ArrayList<NodoHeap> heap;

    public Heap(){
        heap = new ArrayList<NodoHeap>();
    }

    private class NodoHeap implements Handle<Usuario>{ // el handle guarda la posicion en el heap
        private Usuario usuario;
        private int posicion;

        public NodoHeap (Usuario us, int pos){
            usuario = us;
            posicion = pos;
        }

        public Usuario valor(){
            return usuario;
        }

        public void eliminar(){
            Heap.this.eliminar(this);
        }

        public void modificar(Usuario us){
            this.usuario = us;
            Heap.this.actualizarHeap(us);
        }

        public boolean equals(Handle<Usuario> handleUs){
            boolean res = false;
            if(handleUs != null){
                res = this.usuario.equals(handleUs.valor());
            }
            return res;
        }

        public int cantidad(){ // cuantos usuarios hay en el heap
            return heap.size();
        }

        public int posicion(){
            return posicion;
        }

        private void setPosicion(int i){ // solo lo tiene que llamar el Heap
            this.posicion = i;
        }
    }

    public void agregarNodo(Usuario us){
        NodoHeap nuevoUs = new NodoHeap(us, heap.size());
        heap.add(nuevoUs); // lo agrego al final
        us.setHandleEnHeap(nuevoUs); // el usuario se guarda su handle
        siftUp(heap.size() - 1); // lo subo hasta su lugar
    }

    public Usuario extraerMaximo(){
        if (heap.size() > 0){
            NodoHeap max = heap.get(0);
            NodoHeap ultimo = heap.get(heap.size() - 1);
            heap.set(0, ultimo); // el ultimo pasa a ser la raiz
            ultimo.setPosicion(0);
            heap.remove(heap.size() - 1);
            if (heap.size() > 0){
                siftDown(0); // lo bajo hasta su lugar
            }
            Usuario maximo = max.valor();
            maximo.setHandleEnHeap(null); // ya no esta en el heap
            return maximo;
        } else{ // heap vacio
            throw new UnsupportedOperationException("El heap esta vacio");
        }
    }

    public Usuario usConMasMillasDisp(){
        if (heap.size() > 0){
            return heap.get(0).valor(); // la raiz es el maximo
        } else {
            throw new UnsupportedOperationException("El heap esta vacio");
        }
    }

    public int cantidad(){ // cuantos usuarios hay
        return heap.size();
    }

    public void actualizarHeap(Usuario us){ // se llama despues de cambiar las millas
        NodoHeap nodoUs = (NodoHeap) us.handleEnHeap(); // null si no esta en el heap
        if (nodoUs != null){
            int posNodoUs = nodoUs.posicion();
            if (posNodoUs > 0 && us.millasDisponibles() > heap.get(indicePadre(posNodoUs)).valor().millasDisponibles()){ // si supera al padre sube
                siftUp(posNodoUs);
            } else { // sino baja
                siftDown(posNodoUs);
            }
        }
    }

    private void swap(int j, int k){
        NodoHeap nodoJ = heap.get(j);
        NodoHeap nodoK = heap.get(k);
        heap.set(j, nodoK);
        heap.set(k, nodoJ);
        nodoJ.setPosicion(k); // hay que actualizar la posicion de los dos handles
        nodoK.setPosicion(j);
    }

    private void siftUp(int i){ // sube mientras sea mayor que el padre
        int k = i;
        while (k > 0 && heap.get(k).valor().millasDisponibles() > heap.get(indicePadre(k)).valor().millasDisponibles()){
            int p = indicePadre(k);
            swap(k, p);
            k = p;
        }
    }

    private void siftDown(int i){ // baja mientras sea menor que su mayor hijo
        int k = i;
        int m = indiceMayorDeLosHijos(k);
        while (m != -1 && heap.get(k).valor().millasDisponibles() < heap.get(m).valor().millasDisponibles()){
            swap(k, m);
            k = m;
            m = indiceMayorDeLosHijos(k);
        }
    }

    public void eliminar(NodoHeap nodoAEliminar){ // saca un nodo cualquiera
        int posicion = nodoAEliminar.posicion();
        int ultimaPos = heap.size() - 1;
        if (posicion != ultimaPos){
            NodoHeap ultimoNodo = heap.get(ultimaPos);
            heap.set(posicion, ultimoNodo); // el ultimo ocupa su lugar
            ultimoNodo.setPosicion(posicion);
        }
        heap.remove(ultimaPos);
        nodoAEliminar.setPosicion(-1); // ya no tiene una posicion valida
        nodoAEliminar.valor().setHandleEnHeap(null);
        if (posicion < heap.size()){ // si no era el ultimo, lo reacomodo
            siftUp(posicion);
            siftDown(posicion);
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
            if (heap.get(indiceHijoIzq(i)).valor().millasDisponibles() >= heap.get(indiceHijoDer(i)).valor().millasDisponibles()){
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
