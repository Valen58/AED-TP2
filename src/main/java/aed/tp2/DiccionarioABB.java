package aed.tp2;

public class DiccionarioABB {
    private NodoABB raiz;
    

    public DiccionarioABB(){
        this.raiz = null; // Crea el diccionario vacio
    }


    public boolean pertenece(int clave){
        NodoABB actual = this.raiz;

        while (actual != null){
            int idActual = actual.obtenerUsuario().obtenerId(); //accede al usuario del nodo y entra a su id osea la clave del diccionario
            
            //Todo este if es como si fuera un compareTo pero me da fiaca implementarlo ademas son tipos primitivos los que comparo asi que no hace falta por ahora :)
            if (clave == idActual){
                return true;
            }else if (clave > idActual){ 
                actual = actual.obtenerDerecho();   //en el ABB si el valor que buscamos es MAYOR al padre se mueve a la derecha
            }else{                      
                actual = actual.obtenerIzquierdo(); //en el ABB si el valor que buscamos es MENOR al padre se mueve a la izquierda
            }
        }

        return false; //si llego al final (actual = null) y no encontro la clave significa que la clave no esta en el diccionario
    }



    public void setKey(int clave, Usuarios valor){ //Como el de TADS, si no existe la crea, si no, la modifica
        NodoABB actual = this.raiz;
        NodoABB padre = actual;
        
        if (this.raiz == null){
            this.raiz = new NodoABB(valor); //si el arbol esta vacio, la raiz sera la nueva clave del diccionario. Y directamente salimos del metodo porque no hay nada mas por hacer
            return;
        }
        
        while (actual != null){
            padre = actual; //Esto es porque actual al final del ciclo seguro termina en null (en el peor caso) y pierdo su puntero entonces guardo el ultimo nodo antes de que se anule actual en otra variable
            int idActual = actual.obtenerUsuario().obtenerId();

            if (clave == idActual){ //Si ya existe la clave entonces solo la modifico no busco nada mas
                actual.setUsuario(valor);
                return;
            }else if(clave > idActual){
                actual = actual.obtenerDerecho();
            }else{
                actual = actual.obtenerIzquierdo();
            }
        }


        //en el caso de que actual sea null es porque la clave no esta en el diccionario y hay que engancharle a padre el nuevo nodo con la clave y el valor osea el objeto Usuarios entero
        NodoABB nuevoNodo = new NodoABB(valor);

        if (clave > padre.obtenerUsuario().obtenerId()){ 
            padre.setDerecho(nuevoNodo);
        }else{
            padre.setIzquierdo(nuevoNodo);
        }
        

    }

}
