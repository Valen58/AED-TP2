package aed.tp2;

public class DiccionarioABB {
    private NodoABB raiz;


    public DiccionarioABB(){
        this.raiz = null; // arranca vacio
    }


    public boolean pertenece(int clave){
        NodoABB actual = this.raiz;

        while (actual != null){
            int idActual = actual.obtenerUsuario().obtenerId(); // la clave es el id del usuario

            if (clave == idActual){
                return true;
            }else if (clave > idActual){
                actual = actual.obtenerDerecho();   // mayor, va a la derecha
            }else{
                actual = actual.obtenerIzquierdo(); // menor, va a la izquierda
            }
        }

        return false; // llego a null y no estaba
    }

    public Usuario obtener(int clave){
        NodoABB actual = this.raiz;

        while (actual != null){
            int idActual = actual.obtenerUsuario().obtenerId();

            if (clave == idActual){
                return actual.obtenerUsuario();
            }else if (clave > idActual){
                actual = actual.obtenerDerecho();
            }else{
                actual = actual.obtenerIzquierdo();
            }
        }
        return null; // no esta
    }


    public void setKey(int clave, Usuario valor){ // si ya existe lo reemplaza, sino lo agrega
        NodoABB actual = this.raiz;
        NodoABB padre = actual;

        if (this.raiz == null){
            this.raiz = new NodoABB(valor); // arbol vacio, queda de raiz
            return;
        }

        while (actual != null){
            padre = actual; // me guardo el ultimo nodo porque actual termina en null
            int idActual = actual.obtenerUsuario().obtenerId();

            if (clave == idActual){ // ya existia, lo reemplazo
                actual.setUsuario(valor);
                return;
            }else if(clave > idActual){
                actual = actual.obtenerDerecho();
            }else{
                actual = actual.obtenerIzquierdo();
            }
        }


        NodoABB nuevoNodo = new NodoABB(valor); // no estaba, lo cuelgo del padre

        if (clave > padre.obtenerUsuario().obtenerId()){
            padre.setDerecho(nuevoNodo);
        }else{
            padre.setIzquierdo(nuevoNodo);
        }


    }

}
