package cl.duoc.pataverde.model;

/**
 * Representa a cualquier animal atendido en la Clínica Veterinaria PataVerde.
 * Define los datos comunes y delega en cada tipo de animal el cálculo del
 * costo de consulta.
 */
public abstract class Mascota {

    private String nombre;
    private int edad;
    private double peso;

    /**
     * Constructor sin parámetros, requerido para reconstruir objetos.
     */
    protected Mascota() {
    }

    public Mascota(String nombre, int edad, double peso) {
        setNombre(nombre);
        setEdad(edad);
        setPeso(peso);
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        if (nombre == null || nombre.trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre no puede ser nulo ni vacío.");
        }
        this.nombre = nombre.trim();
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        if (edad < 0 || edad > 30) {
            throw new IllegalArgumentException("La edad debe estar entre 0 y 30 años.");
        }
        this.edad = edad;
    }

    public double getPeso() {
        return peso;
    }

    public void setPeso(double peso) {
        if (peso <= 0) {
            throw new IllegalArgumentException("El peso debe ser mayor que cero.");
        }
        this.peso = peso;
    }

    /**
     * Calcula el costo de consulta según las reglas propias de cada tipo de animal.
     *
     * @return costo de la consulta en pesos
     */
    public abstract double calcularCostoConsulta();

    /**
     * Retorna el nombre del tipo de animal (por ejemplo, "Perro" o "Gato").
     *
     * @return tipo de animal
     */
    public abstract String getTipo();

    @Override
    public String toString() {
        return "Nombre: " + nombre + " | Edad: " + edad + " años";
    }
}
