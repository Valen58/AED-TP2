error id: file://<WORKSPACE>/src/main/java/aed/tp2/DiccionarioABB.java:local6
file://<WORKSPACE>/src/main/java/aed/tp2/DiccionarioABB.java
empty definition using pc, found symbol in pc: 
found definition using semanticdb; symbol local6
empty definition using fallback
non-local guesses:

offset: 2133
uri: file://<WORKSPACE>/src/main/java/aed/tp2/DiccionarioABB.java
text:
```scala
package aed.tp2;

public class DiccionarioABB<K,V> {
    private NodoABB<V> raiz;


    public DiccionarioABB(){
        this.raiz = null; // arranca vacio
    }


    public boolean pertenece(int clave){
        NodoABB<V> actual = this.raiz;

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
        NodoABB<V> actual = this.raiz;

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
        NodoABB<V> actual = this.raiz;
        NodoABB<V> padre = actual;

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

        if (clave@@ > padre.obtenerUsuario().obtenerId()){
            padre.setDerecho(nuevoNodo);
        }else{
            padre.setIzquierdo(nuevoNodo);
        }


    }

}

```


#### Short summary: 

empty definition using pc, found symbol in pc: 