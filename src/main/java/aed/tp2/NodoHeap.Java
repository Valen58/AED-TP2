public class NodoHeap implements Handle<Usuario>{
    private Usuario usuario;
    private int posicion;
    private Heap heap;

    // Constructor de cada nodo para el Heap.
    public NodoHeap (Usuario us, int pos, Heap h){ 
        usuario = us;
        posicion = pos;
        heap = h;
    }

    // Getter para obtener el usuario.
    public Usuario valor(){
        return usuario;
    }

    public void eliminar(){
        heap.eliminar(this);
    }

    public void modificar(Usuario us){
        this.usuario = us;
        heap.actualizarHeap(us);
    }

    public boolean equals(Handle<Usuario> handleUs){
        boolean res = false;
        if(handleUs != null){
            res = this.usuario.equals(handleUs.valor());
        }
        return res;
    }

    // Getter para obtener la posición.
    public int posicion(){
        return posicion;
    }

    // Setter para la posición.
    /*
        Este método debe ser llamado unicamente por la clase Heap, no es valido
        llamarlo desde otro lado porque sino rompe el encapsulamiento.
            Una solución para hacer esto es definir todo el nodoHeap en Heap y que
        este método sea privado en dicho archivo.
    */
    public void setPosicion(int i){
        this.posicion = i;
    }
}