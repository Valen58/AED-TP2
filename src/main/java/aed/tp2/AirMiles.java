package aed.tp2;

public class AirMiles implements SistemaAirMiles {
    DiccionarioABB usuarios; // busca usuarios por id
    Heap maxHeap; // ordena por millas disponibles
    Usuario elMasAcumulador; // el de mas millas totales
    RegistroActividad elMasCanjeador; // el que mas millas gasto
    ABBPorId<RegistroActividad> actividadPorUsuario; // gastos y transferencias por id
    Trie<Promocion> promociones; // promos por nombre

    public AirMiles() { // crearSistema
        this.usuarios = new DiccionarioABB();
        this.maxHeap = new Heap();
        this.promociones = new Trie<>();
        this.actividadPorUsuario = new ABBPorId<>();

        this.elMasAcumulador = null;
        this.elMasCanjeador = null;
    }

    public void registrarUsuario(int id) {
        Usuario nuevoUsuario = new Usuario(id, 0, 0, 0, 0, null, null);
        if (!usuarios.pertenece(id)){
            usuarios.setKey(id, nuevoUsuario);
            maxHeap.agregarNodo(nuevoUsuario);
            if (elMasAcumulador == null){ // si es el primero queda como mas acumulador
                elMasAcumulador = nuevoUsuario;
            }
        } else{ // id repetido
            throw new UnsupportedOperationException("El usuario que se quiere ingresar ya existe en el sistema");
        }

    }

    public void registrarUsuarioConReferidor(int id, int ref) {
        Usuario referidor = usuarios.obtener(ref);
        Usuario nuevoUsuario = new Usuario(id, 0, 0, 0, 0, null, null);


        if (referidor != null){
            referidor.sumarMillas(500); // bono para el referidor
            nuevoUsuario.sumarMillas(500); // y para el referido
            maxHeap.actualizarHeap(referidor);

            if (elMasAcumulador == null || referidor.millasTotales() > elMasAcumulador.millasTotales()){ // puede pasar a ser el mas acumulador
                elMasAcumulador = referidor;
        }
        }

        if (usuarios.pertenece(id)){ // id repetido
            throw new UnsupportedOperationException("El usuario ya existe");
        }else{
            usuarios.setKey(id, nuevoUsuario);
            maxHeap.agregarNodo(nuevoUsuario);
        }

        if(elMasAcumulador == null){ // si no habia nadie queda el nuevo
            elMasAcumulador = nuevoUsuario;
        }
    }


    public void acumularMillas(int id, double distancia, String nombrePromo) {
        Promocion promo = promociones.buscar(nombrePromo);
        double factorPromo = 1.0;
        if (promo != null && promo.estaActiva()){ // si no existe o llego al tope, factor 1
            factorPromo = promo.getFactor();
        }

        Usuario viajero = usuarios.obtener(id);
        double categoriaViajero = viajero.factorCategoria();
        double formulaMillas = distancia * categoriaViajero * factorPromo; // categoria de antes del vuelo

        if (promo != null && promo.estaActiva()){ // suma lo otorgado por la promo
            int acumuladas = promo.getMillasOtorgadas();
            acumuladas += (int) formulaMillas;
            promo.setMillasOtorgadas(acumuladas);

        }


        viajero.sumarMillas(formulaMillas);
        maxHeap.actualizarHeap(viajero); // reacomodo en el heap

        if (elMasAcumulador == null || elMasAcumulador.millasTotales() < viajero.millasTotales()){ // puede ser el nuevo mas acumulador
            elMasAcumulador = viajero;
        }


    }

    public void canjearMillas(int id, double cantidad) {
        Usuario usuarioID = usuarios.obtener(id); // busco por id
        if (usuarioID == null){ // no existe
            throw new UnsupportedOperationException("El usuario que se intentó canjear no existe.");
        }
        if (cantidad > usuarioID.millasDisponibles()){ // no le alcanzan las millas
            throw new UnsupportedOperationException("El usuario no tiene millas disponibles suficientes para canjear.");
        }

        usuarioID.restarMillas(cantidad); // las totales no cambian
        maxHeap.actualizarHeap(usuarioID); // bajo su saldo, lo reacomodo
        registrarGasto(usuarioID, cantidad); // cuenta para el mas canjeador
    }

    public void transferirMillas(int origen, int destino, double cantidad) {
        Usuario usuarioOrigen = usuarios.obtener(origen); // busco a los dos por id
        Usuario usuarioDestino = usuarios.obtener(destino);
        if (usuarioOrigen == null || usuarioDestino == null){ // alguno no existe
            throw new UnsupportedOperationException("El usuario origen o el usuario destino de la transferencia no existe.");
        }
        if (origen == destino){ // no se puede a uno mismo
            throw new UnsupportedOperationException("El usuario origen y el usuario destino de la transferencia son el mismo.");
        }
        if (cantidad > usuarioOrigen.millasDisponibles()){ // al origen no le alcanzan las millas
            throw new UnsupportedOperationException("El usuario origen no tiene millas disponibles suficientes para transferir.");
        }

        usuarioOrigen.restarMillas(cantidad); // las totales no cambian
        usuarioDestino.recibirMillas(cantidad); // tampoco cambia su categoria
        maxHeap.actualizarHeap(usuarioOrigen); // cambio el saldo de los dos, los reacomodo
        maxHeap.actualizarHeap(usuarioDestino);
        registrarGasto(usuarioOrigen, cantidad); // el gasto es del origen
        registrarTransferenciaEntre(usuarioOrigen, usuarioDestino); // para los pares exclusivos
    }

    public double consultarSaldo(int id) {
        Usuario usuarioID = usuarios.obtener(id); // busco por id
        if (usuarioID != null){
            return usuarioID.millasDisponibles();
        } else{ // no existe
            throw new UnsupportedOperationException("El usuario que se intentó consultar no existe.");
        }
    }

    public String consultarCategoria(int id) {
        Usuario usuarioID = usuarios.obtener(id); // busco por id
        if (usuarioID != null){
            return usuarioID.categoria();
       } else{ // no existe
           throw new UnsupportedOperationException("El usuario que se intentó consultar no existe.");
       }
    }

    public int[] rankingMillas(int n) {
        int cantidad = Math.min(n, maxHeap.cantidad()); // no puedo devolver mas de los que hay
        int[] res = new int[cantidad];
        Usuario[] sacados = new Usuario[cantidad]; // los guardo para devolverlos al heap
        int i = 0;
        int j = 0;
        while (i < cantidad){
            Usuario maxActual = maxHeap.extraerMaximo(); // saco el que tiene mas millas
            res[i] = maxActual.obtenerId();
            sacados[i] = maxActual;

            i++;
        } // O(N log U)

        while (j < cantidad){ // los vuelvo a meter, tambien O(N log U)
            maxHeap.agregarNodo(sacados[j]);

            j++;
        }

        return res;
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
        if (elMasAcumulador == null){ // todavia no hay usuarios
            return -1;
        }
        return elMasAcumulador.obtenerId();
    }

    public int elMasCanjeador() {
        if (elMasCanjeador == null){ // todavia nadie gasto
            return -1;
        }
        return elMasCanjeador.obtenerId();
    }

    public int[][] paresTransferidoresExclusivos() {
        RegistroActividad[] registrosOrdenadosPorId = new RegistroActividad[actividadPorUsuario.cantidad()];
        actividadPorUsuario.guardarValoresEnOrden(registrosOrdenadosPorId); // O(U)

        int cantidadDePares = 0; // primero cuento los pares para dimensionar el arreglo
        for (int i = 0; i < registrosOrdenadosPorId.length; i++){
            if (esElDeIdMasChicoDeUnParExclusivo(registrosOrdenadosPorId[i])){
                cantidadDePares++;
            }
        }

        int[][] paresExclusivos = new int[cantidadDePares][2];
        int posicionDelProximoPar = 0; // y despues los guardo
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

    private boolean esElDeIdMasChicoDeUnParExclusivo(RegistroActividad registro){ // asi cada par se cuenta una sola vez
        boolean esParExclusivo = false;
        RegistroActividad contraparte = registro.obtenerContraparte();

        if (contraparte != null && !registro.transfirioConVariosUsuarios() && !contraparte.transfirioConVariosUsuarios()){
            boolean laContraparteLoTieneComoContraparte = contraparte.obtenerContraparte() == registro; // se eligen entre los dos
            esParExclusivo = laContraparteLoTieneComoContraparte && registro.obtenerId() < contraparte.obtenerId();
        }

        return esParExclusivo;
    }

    public void actualizarElMasAcumulador(Usuario usuario){ // lo compara con el maximo actual
        if (usuario != null && (elMasAcumulador == null || usuario.millasTotales() > elMasAcumulador.millasTotales())){
            elMasAcumulador = usuario;
        }
    }

    private RegistroActividad registroDelUsuario(int idUsuario){ // si no tiene registro lo crea
        RegistroActividad registro = actividadPorUsuario.obtener(idUsuario);
        if (registro == null){
            registro = new RegistroActividad(idUsuario);
            actividadPorUsuario.agregar(idUsuario, registro);
        }
        return registro;
    }

    public void registrarGasto(Usuario usuario, double millasGastadas){ // suma al registro y mira si es el nuevo mas canjeador
        if (usuario != null && millasGastadas > 0){
            RegistroActividad registro = registroDelUsuario(usuario.obtenerId());
            registro.sumarMillasGastadas(millasGastadas);
            if (elMasCanjeador == null || registro.obtenerMillasGastadas() > elMasCanjeador.obtenerMillasGastadas()){
                elMasCanjeador = registro;
            }
        }
    }

    public void registrarTransferenciaEntre(Usuario origen, Usuario destino){ // la anota en los dos
        if (origen != null && destino != null){
            RegistroActividad registroDelOrigen = registroDelUsuario(origen.obtenerId());
            RegistroActividad registroDelDestino = registroDelUsuario(destino.obtenerId());
            registroDelOrigen.registrarTransferenciaCon(registroDelDestino);
            registroDelDestino.registrarTransferenciaCon(registroDelOrigen);
        }
    }
}
