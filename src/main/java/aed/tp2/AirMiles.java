package aed.tp2;

public class AirMiles implements SistemaAirMiles {
    DiccionarioABB usuarios; // El ABB de los usuarios.
    Heap maxHeap; // El heap respecto a las millas disponibles.
    Usuario elMasAcumulador; // Guardamos el usuario que mas millas totales tiene.
    RegistroActividad elMasCanjeador; // Guardamos el usuario que mas millas totales gastó.
    ABBPorId<RegistroActividad> actividadPorUsuario;
    Trie<Promocion> promociones; // Guardamos las promociones con sus respectivos factores y topes

    public AirMiles() { //este seria el crearSistema() osea el constructor 
        this.usuarios = new DiccionarioABB();
        this.maxHeap = new Heap();
        this.promociones = new Trie<>();
        this.actividadPorUsuario = new ABBPorId<>();

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
            nuevoUsuario.sumarMillas(500);
            maxHeap.actualizarHeap(referidor);

            //¿por que lo meto aca este if? porque si lo dejo afuera puede pasar que el referidor sea null (muy raro, no deberia pasar) y que me tire error de nullPointerException
            if (elMasAcumulador == null || referidor.millasTotales() > elMasAcumulador.millasTotales()){ //segun el enunciado el masAcumulador es sobre las millas disponibles y nos las totales
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
    }

    
    public void acumularMillas(int id, double distancia, String nombrePromo) {
        Promocion promo = promociones.buscar(nombrePromo);
        double factorPromo = 1.0;
        if (promo != null && promo.estaActiva()){ // aca chequeo si la promo no se paso del tope si es que tiene
            factorPromo = promo.getFactor();
        }
       
        Usuario viajero = usuarios.obtener(id);
        double categoriaViajero = viajero.factorCategoria();
        double formulaMillas = distancia * categoriaViajero * factorPromo;
            
        if (promo != null && promo.estaActiva()){
            int acumuladas = promo.getMillasOtorgadas();
            acumuladas += (int) formulaMillas;
            promo.setMillasOtorgadas(acumuladas);
            
        }


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
        if (elMasAcumulador == null){
            return -1;
        }
        return elMasAcumulador.obtenerId();
    }

    public int elMasCanjeador() {
        if (elMasCanjeador == null){
            return -1;
        }
        return elMasCanjeador.obtenerId();
    } 

    public int[][] paresTransferidoresExclusivos() {
        RegistroActividad[] registrosOrdenadosPorId = new RegistroActividad[actividadPorUsuario.cantidad()];
        actividadPorUsuario.guardarValoresEnOrden(registrosOrdenadosPorId); // O(U)

        int cantidadDePares = 0; // Contamos los pares para saber de que tamaño crear el array. O(U)
        for (int i = 0; i < registrosOrdenadosPorId.length; i++){
            if (esElDeIdMasChicoDeUnParExclusivo(registrosOrdenadosPorId[i])){
                cantidadDePares++;
            }
        }

        int[][] paresExclusivos = new int[cantidadDePares][2];
        int posicionDelProximoPar = 0; // Guardamos cada par en el array de salida. O(U)
        for (int i = 0; i < registrosOrdenadosPorId.length; i++){
            RegistroActividad registro = registrosOrdenadosPorId[i];
            if (esElDeIdMasChicoDeUnParExclusivo(registro)){
                paresExclusivos[posicionDelProximoPar][0] = registro.obtenerId();
                paresExclusivos[posicionDelProximoPar][1] = registro.obtenerContraparte().obtenerId();
                posicionDelProximoPar++;
            }
        }

        return paresExclusivos;
    }

    private boolean esElDeIdMasChicoDeUnParExclusivo(RegistroActividad registro){
        boolean esParExclusivo = false;
        RegistroActividad contraparte = registro.obtenerContraparte();

        if (contraparte != null && !registro.transfirioConVariosUsuarios() && !contraparte.transfirioConVariosUsuarios()){
            boolean laContraparteLoTieneComoContraparte = contraparte.obtenerContraparte() == registro;
            esParExclusivo = laContraparteLoTieneComoContraparte && registro.obtenerId() < contraparte.obtenerId();
        }

        return esParExclusivo;
    }

    // Metodos aux

    // Se llama cada vez que a un usuario le aumentan las millas totales. Como las millas totales nunca bajan, alcanza con comparar al usuario que acaba de cambiar contra el maximo actual, si lo supera pasa a ser el nuevo maximo y si no, el maximo sigue siendo el de antes. 
    // Complejidad: O(1).
    public void actualizarElMasAcumulador(Usuario usuario){
        if (usuario != null && (elMasAcumulador == null || usuario.millasTotales() > elMasAcumulador.millasTotales())){
            elMasAcumulador = usuario;
        }
    }

    // Devuelve el registro de actividad del usuario, y si todavia no tenia lo crea. 
    // Es una busqueda en el ABB "actividadPorUsuario": O(log U) en promedio y O(U) en el peor caso.
    
    private RegistroActividad registroDelUsuario(int idUsuario){
        RegistroActividad registro = actividadPorUsuario.obtener(idUsuario);
        if (registro == null){
            registro = new RegistroActividad(idUsuario);
            actividadPorUsuario.agregar(idUsuario, registro);
        }
        return registro;
    }

    // Se llama cada vez que un usuario gasta milla, suma lo gastado en su registro y, si supera al maximo actual, pasa a ser el nuevo elMasCanjeador. Como las gastadas nunca bajan, una sola comparacion alcanza. Se pide que lo gastado sea > 0 para que alguien que no gasto nada nunca quede como maximo.
    // Complejidad: O(log U) promedio y O(U) peor caso por la busqueda del registro, que entra en la complejidad de canjearMillas y transferirMillas. La comparacion en si es O(1).
    
    public void registrarGasto(Usuario usuario, double millasGastadas){
        if (usuario != null && millasGastadas > 0){
            RegistroActividad registro = registroDelUsuario(usuario.obtenerId());
            registro.sumarMillasGastadas(millasGastadas);
            if (elMasCanjeador == null || registro.obtenerMillasGastadas() > elMasCanjeador.obtenerMillasGastadas()){
                elMasCanjeador = registro;
            }
        }
    }

    // Se llama cada vez que se concreta una transferencia. Deja anotado en los dos usuarios
    // Complejidad: dos busquedas en el ABB "actividadPorUsuario" (O(log U) promedio, O(U) peor caso) y el resto O(1)
    
    public void registrarTransferenciaEntre(Usuario origen, Usuario destino){
        if (origen != null && destino != null){
            RegistroActividad registroDelOrigen = registroDelUsuario(origen.obtenerId());
            RegistroActividad registroDelDestino = registroDelUsuario(destino.obtenerId());
            registroDelOrigen.registrarTransferenciaCon(registroDelDestino);
            registroDelDestino.registrarTransferenciaCon(registroDelOrigen);
        }
    }
}
