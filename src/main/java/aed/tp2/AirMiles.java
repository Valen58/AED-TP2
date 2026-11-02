package aed.tp2;

public class AirMiles implements SistemaAirMiles {
    DiccionarioABB usuarios; // El ABB de los usuarios.
    Heap maxHeap; // El heap respecto a las millas disponibles.
    Usuario elMasAcumulador; // Guardamos el usuario que mas millas totales tiene.
    Usuario elMasCanjeador; // Guardamos el usuario que mas millas totales gastó.

    public AirMiles() {
        throw new UnsupportedOperationException("Sin implementar");
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
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void acumularMillas(int id, double distancia, String nombrePromo) {
        throw new UnsupportedOperationException("Sin implementar");
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
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocion(String nombre, double factor) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void iniciarPromocionConTope(String nombre, double factor, double tope) {
        throw new UnsupportedOperationException("Sin implementar");
    }

    public void finalizarPromocion(String nombre) {
        throw new UnsupportedOperationException("Sin implementar");
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
