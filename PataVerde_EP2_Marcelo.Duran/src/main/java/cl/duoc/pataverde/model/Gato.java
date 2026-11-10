package cl.duoc.pataverde.model;

/**
 * Gato atendido en la clínica.
 * Costo de consulta: base $12.000; si vive en exterior, recargo del 15%.
 */
public class Gato extends Mascota {

    private static final double COSTO_BASE = 12000;
    private static final double RECARGO_EXTERIOR = 0.15;

    private boolean exterior;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    public Gato() {
    }

    public Gato(String nombre, int edad, double peso, boolean exterior) {
        super(nombre, edad, peso);
        setExterior(exterior);
    }

    public boolean isExterior() {
        return exterior;
    }

    public void setExterior(boolean exterior) {
        this.exterior = exterior;
    }

    @Override
    public double calcularCostoConsulta() {
        if (exterior) {
            return COSTO_BASE + COSTO_BASE * RECARGO_EXTERIOR;
        }
        return COSTO_BASE;
    }

    @Override
    public String getTipo() {
        return "Gato";
    }
}
