package cl.duoc.pataverde.model;

/**
 * Perro atendido en la clínica. Participa en el programa de adopción.
 * Costo de consulta: base $15.000; si no está vacunado, recargo del 30%.
 */
public class Perro extends Mascota implements Adoptable {

    private static final double COSTO_BASE = 15000;
    private static final double RECARGO_NO_VACUNADO = 0.30;

    private String raza;
    private boolean vacunado;
    private boolean disponibleAdopcion;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    public Perro() {
    }

    public Perro(String nombre, int edad, double peso, String raza, boolean vacunado) {
        super(nombre, edad, peso);
        setRaza(raza);
        setVacunado(vacunado);
        this.disponibleAdopcion = false;
    }

    public String getRaza() {
        return raza;
    }

    public void setRaza(String raza) {
        this.raza = raza;
    }

    public boolean isVacunado() {
        return vacunado;
    }

    public void setVacunado(boolean vacunado) {
        this.vacunado = vacunado;
    }

    @Override
    public boolean estaDisponibleParaAdopcion() {
        return disponibleAdopcion;
    }

    @Override
    public void marcarDisponibleParaAdopcion() {
        this.disponibleAdopcion = true;
    }

    @Override
    public double calcularCostoConsulta() {
        if (vacunado) {
            return COSTO_BASE;
        }
        return COSTO_BASE + COSTO_BASE * RECARGO_NO_VACUNADO;
    }

    @Override
    public String getTipo() {
        return "Perro";
    }
}
