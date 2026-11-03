package aed.tp2;

public class AirMiles implements SistemaAirMiles {
    DiccionarioABB usuarios; // El ABB de los usuarios.
    Heap maxHeap; // El heap respecto a las millas disponibles.
    Usuario elMasAcumulador; // Guardamos el usuario que mas millas totales tiene.
    Usuario elMasCanjeador; // Guardamos el usuario que mas millas totales gastó.
    private Trie<Promocion> promociones; // Guardamos las promociones con sus respectivos factores y topes

    public AirMiles() { //este seria el crearSistema() osea el constructor 
        this.usuarios = new DiccionarioABB();
        this.maxHeap = new Heap();
        this.promociones = new Trie<>();

        this.elMasAcumulador = null;
        this.elMasCanjeador = null;
    }
    
    public void registrarUsuario(int id) {
        Usuario nuevoUsuario = new Usuario(id, 0, 0, 0, 0, null, null); // Definimos un usuario nuevo.
        if (!usuarios.pertenece(id)){ // Si el usuario no existe, lo agrega en ambas estructuras.
            usuarios.setKey(id, nuevoUsuario);
            maxHeap.agregarNodo(nuevoUsuario);
            if (elMasAcumulador == null){ // En el caso de que sea el primer usuario que se ingresa, se actualiza el que mas millas tiene.
                elMasAcumulador = nuevoUsuario;
            }
        } else{ // En el caso de que el usuario ya esté registrado, lanzamos un error.
            throw new UnsupportedOperationException("El usuario que se quiere ingresar ya existe en el sistema");
        }

    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        Usuario referidor = usuarios.obtener(ref);
        Usuario nuevoUsuario = new Usuario(id, 0, 0, 0, 0, null, null);
        

        if (referidor != null){
            referidor.sumarMillas(500); //metodo dentro de Usuario
            maxHeap.actualizarHeap(referidor);

            //¿por que lo meto aca este if? porque si lo dejo afuera puede pasar que el referidor sea null (muy raro, no deberia pasar) y que me tire error de nullPointerException
            if (elMasAcumulador == null || referidor.millasDisponibles() > elMasAcumulador.millasDisponibles()){ //segun el enunciado el masAcumulador es sobre las millas disponibles y nos las totales
                elMasAcumulador = referidor;
        }
        }
        
        if (usuarios.pertenece(id)){
            throw new UnsupportedOperationException("El usuario ya existe");
        }else{
            usuarios.setKey(id, nuevoUsuario);
            maxHeap.agregarNodo(nuevoUsuario);
        }

        //casos super borde por si el referidor no existe y no hay masAcumulador ni masCanjeador para ese momento
        if(elMasAcumulador == null){
            elMasAcumulador = nuevoUsuario;
        }
        if (elMasCanjeador == null){
            elMasCanjeador = nuevoUsuario;
        }
    }

    //  <<NO ESTA TERMINADA>> al no estar implementado el trie de promos, voy a suponer los nombres de las operaciones y despues cuando este hecho lo hago bien pero asi queda la estructura por lo menos
    public void acumularMillas(int id, double distancia, String nombrePromo) {
        //double factorPromo = promociones.obtenerFactor(nombrePromo); NO ESTA IMPLEMENTADO EL TRIE 
        double factorPromo = 1.0; //valor provisorio hasta tener el trie hecho
       
        Usuario viajero = usuarios.obtener(id);
        double categoriaViajero = viajero.factorCategoria();
        double formulaMillas = distancia * categoriaViajero * factorPromo;
            
        viajero.sumarMillas(formulaMillas);
        maxHeap.actualizarHeap(viajero);

        if (elMasAcumulador == null || elMasAcumulador.millasTotales() < viajero.millasTotales()){
            elMasAcumulador = viajero;
        }
    

    }

    public void canjearMillas(int id, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public double consultarSaldo(int id) {
        Usuario usuarioID = usuarios.obtener(id); // Obtenemos el usuario respecto del ID.
        if (usuarioID != null){ // Si el usuario existe, devolvemos la cantidad de millas disponibles que tiene.
            return usuarioID.millasDisponibles();
        } else{ // En caso de que no exista, lanzamos un error.
            throw new UnsupportedOperationException("El usuario que se intentó consultar no existe.");
        }
    }
    
    public String consultarCategoria(int id) {
        Usuario usuarioID = usuarios.obtener(id); // Obtenemos el usuario respecto del ID
        if (usuarioID != null){ // Si el usuario existe, calculamos la categoría a la que pertenece.
            return usuarioID.categoria();
       } else{ // En el caso de que no exista el usuario, lanzamos un error.
           throw new UnsupportedOperationException("El usuario que se intentó consultar no existe.");
       }
    }

    public int[] rankingMillas(int n) {
        int[] res = new int[n];
        Usuario[] sacados = new Usuario[n]; //yo lo cambiaria por arrayList, pero no se si se puede usar en el tp sin que lo implementemos nosotros
        int i = 0;
        int j = 0;
        while (i < n){
            Usuario maxActual = maxHeap.extraerMaximo(); //es maxActual porque cada vez que saco el maximo se reordena el heap y vuelve a tener un maximo
            res[i] = maxActual.obtenerId(); //obtengo la id del usuario maximo del heap
            sacados[i] = maxActual;

            i++;
        } //O(Nlog(u))

        //recompongo todo el heap que le habia sacado los n elementos maximos para meter en res --- O(Nlog(u))
        while (j < n){
            maxHeap.agregarNodo(sacados[j]);
            
            j++;
        }
        
        return res; //O(Nlog(u) + O(Nlog(u)) = Nlog(u))
    }

    public void iniciarPromocion(String nombre, double factor) {
        Promocion p = new Promocion(factor);
        promociones.insertar(nombre, p);
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        Promocion p = new Promocion(factor, tope);
        promociones.insertar(nombre, p);
    }

    public void finalizarPromocion(String nombre) {
        promociones.eliminar(nombre);
    }

    public int elMasAcumulador() {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int elMasCanjeador() {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public int[][] paresTransferidoresExclusivos() {
        throw new UnsupportedOperationException("Sin implementar");
    }
}
