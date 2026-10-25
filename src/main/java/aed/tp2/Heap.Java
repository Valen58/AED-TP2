import java.util.ArrayList;

public class Heap{
    private ArrayList<NodoHeap> heap;

    // Constructor del Heap
    public Heap(){
        heap = new ArrayList<NodoHeap>();
    }

    // Operación que me permite agregar usuarios al Heap.
    /* 
        Guardo al usuario en un nodo, lo agrego al final del heap, setteo el 
        nodo en el usuario y después lo acomodo en el Heap a donde corresponda.
    */
    public void agregarNodo(Usuario us){
        NodoHeap nuevoUs = new NodoHeap(us, heap.size(), this);
        heap.add(nuevoUs);
        us.setNodoEnHeap(nuevoUs);
        siftUp(heap.size() - 1);
    }

    // Operación que me permite sacar la raíz del Heap.
    /*
        Si el Heap tiene nodos, saco el primero y el último lo defino como raíz (1° pos. del Heap).
        Después le setteo su nueva posición y lo elimino de su posición anterior. Si me quedan
        nodos, lo acomodo donde vaya. Por último elimino la referencia al Heap del usuario que saqué.
        Si no tenía elementos, no podía sacar nada.
    */
    public Usuario extraerMaximo(){
        if (heap.size() > 0){
            NodoHeap max = heap.get(0);
            NodoHeap ultimo = heap.get(heap.size() - 1);
            heap.set(0, ultimo);
            ultimo.setPosicion(0);
            heap.remove(heap.size() - 1);
            if (heap.size() > 0){
                siftDown(0);
            }
            Usuario maximo = max.valor();
            maximo.setNodoEnHeap(null);
            return maximo;
        } else{
            throw new UnsupportedOperationException("El heap esta vacio");
        }
    }
    
    // Operación que me permite saber quien tiene mas millas disponibles.
    /*
        Si tengo algún nodo, devuelvo el usuario que guarda. Sino, no puedo ver ningún usuario.
    */
    public Usuario usConMasMillasDisp(){
        if (heap.size() > 0){
            return heap.get(0).valor();
        } else {
            throw new UnsupportedOperationException("El heap esta vacio");
        }
    }
    
    // Operación que me permite actualizar la posición cuando se agrega un nuevo usuario.
    /* 
        Obtengo el nodo del usuario ingresado y si existe, pido su posición en el Heap y si sus millas
        disponibles son mayores a las del padre entonces los acomodo, sino lo acomodo con sus hijos.
        Acá si me pasan el nodo padre no pasa nada por como estan implementadas las operaciones.
        Antes de llamar a este método, las millas disponibles ya deben estar cambiadas.
    */
    public void actualizarHeap(Usuario us){
        NodoHeap nodoUs = us.nodoEnHeap(); // getter en usuario para obtener el nodo del Heap del usuario. (el atributo nodoEnHeap)
        if (nodoUs != null){
            int posNodoUs = nodoUs.posicion();
            if (posNodoUs > 0 && us.millasDisponibles() > heap.get(indicePadre(posNodoUs)).valor().millasDisponibles()){ // getter en usuario para obtener las millasDisponibles.
                siftUp(posNodoUs);
            } else {
                siftDown(posNodoUs);
            }
        }
    }

    // Operación que intercambia la posición entre nodos.
    /*
        Obtengo los nodos cuyas posiciones me pasan por parámetro y las invierto en el Heap. Después,
        cambio su posición en el atributo de cada nodo. Es importante cambiarlo en el Heap y en el
        usuario porque sino el usuario va a apuntar a una posición incorrecta dentro del ArrayList.
    */
    private void swap(int j, int k){
        NodoHeap nodoJ = heap.get(j);
        NodoHeap nodoK = heap.get(k);
        heap.set(j, nodoK);
        heap.set(k, nodoJ);
        nodoJ.setPosicion(k);
        nodoK.setPosicion(j);
    }

    // Operación que intercambia posiciones hacia arriba para ordenar el Heap.
    /*
        Copio el valor del parámetro, para no editarlo, y mientras el usuario en el nodo actual tenga mas millas que su padre los intercambio
        de lugar. Para no caer en un loop infinito, actualizo la posición del nodo actual con la de su padre. Este ciclo continua hasta que
        las millas de algún padre sean mayores a las del actual o que no tenga mas padre, es decir lleguemos a la raíz.
            Aclaración: Aunque por definición en un Heap los hijos son menores o iguales que sus padres,
            para no hacer operaciones de mas acá uso estricto.
    */
    private void siftUp(int i){
        int k = i;
        while (k > 0 && heap.get(k).valor().millasDisponibles() > heap.get(indicePadre(k)).valor().millasDisponibles()){
            int p = indicePadre(k);
            swap(k, p);
            k = p;
        }
    }

    // Operación que intercambia posiciones hacia abajo para ordenar el Heap.
    /*
        Copio el valor del parámetro, para no editarlo, y calculo el indice del mayor hijo. Luego, mientras el mayor hizo sea
        distinto de -1 (todos los indices son mayores o iguales a cero porque estamos en un ArrayList entonces -1 es un valor
        de control) y el usuario actual tenga menos millas que el mayor de sus hijos, entonces los intercambia. Por último,
        actualiza la posición del usuario actual con la de su mayor hijo y el se vuelve a calcular el nuevo hijo mayor.
        Hasta que las millas del actual sean mayores a las de sus hijos o que no tengamos mas hijos, el indice sea igual a -1
        y que estemos en una hoja.
            Aclaración: Aunque por definición en un Heap los hijos son menores o iguales que sus padres,
            para no hacer operaciones de mas acá uso estricto.
    */
    private void siftDown(int i){
        int k = i;
        int m = indiceMayorDeLosHijos(k);
        while (m != -1 && heap.get(k).valor().millasDisponibles() < heap.get(m).valor().millasDisponibles()){
            swap(k, m);
            k = m;
            m = indiceMayorDeLosHijos(k);
        }
    }

    // Operación para eliminar un nodo cualquiera.
    /*
        Definimos la posición del nodo a eliminar y buscamos el último nodo del Heap. Luego si el nodo a eliminar no es el último,
        colocamos en su posición al último nodo y actualizamos también su atributo posición. En cualquier caso, eliminamos la última
        posición, esto es válido en el caso de que el nodo a eliminar sea o no el último, definimos que el atributo posición del
        nodo a eliminar no apunte a una posición valida del Heap y le quitamos el Handle al usuario. Por el último, en el caso de que
        la posición del nodo a eliminar sea menor a la longitud del Heap (esto significa que el nodo a eliminar no sea el último) lo acomodamos hacia arriba o hacia abajo.
    */
    public void eliminar(NodoHeap nodoAEliminar){
        int posicion = nodoAEliminar.posicion();
        int ultimaPos = heap.size() - 1;
        if (posicion != ultimaPos){
            NodoHeap ultimoNodo = heap.get(ultimaPos);
            heap.set(posicion, ultimoNodo);
            ultimoNodo.setPosicion(posicion);
        }
        heap.remove(ultimaPos);
        nodoAEliminar.setPosicion(-1);
        nodoAEliminar.valor().setNodoEnHeap(null);
        if (posicion < heap.size()){
            siftUp(posicion);
            siftDown(posicion);
        }
    }

    // Calcula el indice del padre del nodo actual.
    private int indicePadre(int i){ 
        return (i - 1) / 2;
    }
    
    // Calcula el indice del hijo derecho del nodo actual.
    private int indiceHijoDer(int i){
        return 2 * i + 2;
    }
    
    // Calcula el indice del hijo izquierdo del nodo actual.
    private int indiceHijoIzq(int i){
        return 2 * i + 1;
    }
    
    // Verifica si hay un hijo derecho.
    boolean hayHijoDer(int i){
        return indiceHijoDer(i) < heap.size();
    }
    
    // Verifica si hay un hijo izquierdo.
    boolean hayHijoIzq(int i){
        return indiceHijoIzq(i) < heap.size();
    }
    
    // Devuelve el indice del hijo con mayor cantidad de millas.
    /*
        En el caso de que no tengamos hijos devolvemos el valor -1,
        como ningún nodo va a estar en la posición -1, porque los
        ArrayList solo tienen posiciones positivas, entonces es un
        valor de control válido.
    */
    int indiceMayorDeLosHijos(int i){
        if (hayHijoDer(i) && hayHijoIzq(i)){
            if (heap.get(indiceHijoIzq(i)).valor().millasDisponibles() >= heap.get(indiceHijoDer(i)).valor().millasDisponibles()){ // getter en usuario para obtener las millasDisponibles.
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