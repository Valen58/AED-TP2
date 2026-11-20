error id: file://<WORKSPACE>/src/main/java/aed/tp2/DiccionarioABB.java:_empty_/NodoABB#obtenerUsuario#
file://<WORKSPACE>/src/main/java/aed/tp2/DiccionarioABB.java
empty definition using pc, found symbol in pc: _empty_/NodoABB#obtenerUsuario#
empty definition using semanticdb
empty definition using fallback
non-local guesses:

offset: 314
uri: file://<WORKSPACE>/src/main/java/aed/tp2/DiccionarioABB.java
text:
```scala
package aed.tp2;

public class DiccionarioABB {
    private NodoABB raiz;


    public DiccionarioABB<K,V>{
        this.raiz = null; // arranca vacio
    }


    public boolean pertenece(int clave){
        NodoABB actual = this.raiz;

        while (actual != null){
            int idActual = actual.obtenerUsua@@rio().obtenerId(); // la clave es el id del usuario

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

```


#### Short summary: 

empty definition using pc, found symbol in pc: _empty_/NodoABB#obtenerUsuario#