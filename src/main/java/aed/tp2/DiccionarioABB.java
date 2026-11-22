package aed.tp2;

public class DiccionarioABB<K extends Comparable<K> ,V> { //java no sabe como comparar dos claves de tipo K generico, hay que exigir que la clave K implemente comparable
    private NodoABB<K, V> raiz;
    private int cantidadDeNodos; //abbporId


    public DiccionarioABB(){
        this.raiz = null; // arranca vacio
        this.cantidadDeNodos = 0; //arranca con el arbol vacio
    }


    private class NodoABB<K, V> implements Handle<V> { //al ser de tipo generico, no sirve el metodo obtenerId() ya que la clave podria ser de cualquier tipo, en un diccionario generico se guarda directamente la clave y valor
        private K clave;
        private V valor;
        private NodoABB<K, V> izq;
        private NodoABB<K, V> der;


        public NodoABB(K clave, V valor){ //nada debe ir con usuario para que sea generico, todo va con K (en nuestro sistema siempre sera int por la id) y V que seria el usuario en nuestro caso
            this.clave = clave;
            this.valor = valor;
            this.der = null;
            this.izq = null;
        }
        
        //metodos del handle para no romper el encapsulamiento
        @Override 
        public V valor(){
            return this.valor;
        }

        @Override 
        public void modificar(V nuevoValor){
            this.valor = nuevoValor;
        }

        @Override
        public void eliminar(){
            //realmente no usamos eliminar, se podria sacar este metodo
        }

        @Override 
        public boolean equals(Handle<V> h){
            if (h == null){
                return false;
            }
            return this.valor.equals(h.valor());
        }



        public NodoABB<K, V> obtenerIzquierdo(){
            return izq;
        }

        public void setIzquierdo(NodoABB<K, V> nodo){
            this.izq = nodo;
        }

        public NodoABB<K, V> obtenerDerecho(){
            return der;
        }

        public void setDerecho(NodoABB<K, V> nodo){
            this.der = nodo;
        }
    }
    
    public int cantidad(){ //O(1) solo devuelve la cantidad de nodos previamente calculada
        return this.cantidadDeNodos;
    }

    public boolean pertenece(K clave){ //el tipo de dato que entra de la clave tiene que ser generico, no un int
        NodoABB<K, V> actual = this.raiz;

        while (actual != null){
            int comparacion = clave.compareTo(actual.clave); //al ser tipos de dato genericos comparables ahora hay que usar el compareTo

            if (comparacion == 0){
                return true;
            }else if (comparacion > 0){
                actual = actual.obtenerDerecho();   // mayor, va a la derecha
            }else{
                actual = actual.obtenerIzquierdo(); // menor, va a la izquierda
            }
        }

        return false; // llego a null y no estaba
    }

    public V obtener(K clave){ //el dato que sale es el valor generico y el que entra es la clave de tipo generico
        NodoABB<K,V> actual = this.raiz;

        while (actual != null){
            int comparacion = clave.compareTo(actual.clave);         

            if (comparacion == 0){
                return actual.valor;
            }else if (comparacion > 0){
                actual = actual.obtenerDerecho();
            }else{
                actual = actual.obtenerIzquierdo();
            }
        }
        return null; // no esta
    }

    //es igual a agregar del abbporid, si existe modifica sino crea
    public Handle<V> setKey(K clave, V valor){ //los tipos de dato de entrada deben ser generico, no pueden ser usuario o int    
        if (this.raiz == null){
            NodoABB<K, V> nuevo = new NodoABB<>(clave, valor);
            this.raiz = nuevo; // arbol vacio, queda de raiz
            this.cantidadDeNodos++;
            return nuevo;
        }

        NodoABB<K, V> actual = this.raiz;
        NodoABB<K, V> padre = actual;

        while (actual != null){
            padre = actual; // me guardo el ultimo nodo porque actual termina en null
            int comparacion = clave.compareTo(actual.clave);

            if (comparacion == 0){ // ya existia, lo reemplazo
                actual.valor = valor;
                return actual;
            }else if(comparacion > 0){
                actual = actual.obtenerDerecho();
            }else{
                actual = actual.obtenerIzquierdo();
            }
        }

        // no estaba, lo cuelgo del padre
        NodoABB<K, V> nuevoNodo = new NodoABB<>(clave, valor); 

        if (clave.compareTo(padre.clave) > 0){
            padre.setDerecho(nuevoNodo);
        }else{
            padre.setIzquierdo(nuevoNodo);
        }
        this.cantidadDeNodos++;
        return nuevoNodo;
    }

    public void guardarValoresEnOrden(V[] arregloDestino){ // ordenados por id, O(U)
        guardarSubarbolEnOrden(raiz, arregloDestino, 0);
    }

    private int guardarSubarbolEnOrden(NodoABB<K, V> nodoActual, V[] arregloDestino, int posicionInicial){ // devuelve la proxima posicion libre
        int siguientePosicionLibre = posicionInicial;
        if (nodoActual != null){
            siguientePosicionLibre = guardarSubarbolEnOrden(nodoActual.obtenerIzquierdo(), arregloDestino, siguientePosicionLibre);
            arregloDestino[siguientePosicionLibre] = nodoActual.valor;
            siguientePosicionLibre++;
            siguientePosicionLibre = guardarSubarbolEnOrden(nodoActual.obtenerDerecho(), arregloDestino, siguientePosicionLibre);
        }
        return siguientePosicionLibre;
    }

}
