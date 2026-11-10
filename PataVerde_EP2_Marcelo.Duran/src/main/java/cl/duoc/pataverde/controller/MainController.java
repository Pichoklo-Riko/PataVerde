package cl.duoc.pataverde.controller;

import cl.duoc.pataverde.dao.MascotaDaoJson;
import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import cl.duoc.pataverde.repository.MascotaRepository;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.UncheckedIOException;
import java.text.NumberFormat;
import java.util.Locale;

public class MainController {

    @FXML private ComboBox<String> cmbTipo;
    @FXML private TextField txtNombre, txtEdad, txtPeso, txtRaza;
    @FXML private CheckBox chkVacunado, chkAdopcion, chkExterior;
    @FXML private TableView<Mascota> tabla;
    @FXML private TableColumn<Mascota, String> colTipo, colNombre, colEdad, colPeso, colCosto;
    @FXML private Label lblMensaje;

    private MascotaRepository repo;
    private final NumberFormat formatoMiles = NumberFormat.getIntegerInstance(Locale.of("es", "CL"));

    @FXML
    public void initialize() {
        cmbTipo.setItems(FXCollections.observableArrayList("Perro", "Gato"));

        // Solo quedan habilitados los campos del tipo elegido
        BooleanBinding noEsPerro = cmbTipo.valueProperty().isNotEqualTo("Perro");
        BooleanBinding noEsGato = cmbTipo.valueProperty().isNotEqualTo("Gato");
        txtRaza.disableProperty().bind(noEsPerro);
        chkVacunado.disableProperty().bind(noEsPerro);
        chkAdopcion.disableProperty().bind(noEsPerro);
        chkExterior.disableProperty().bind(noEsGato);

        // Enlace de columnas con el modelo
        colTipo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTipo()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colEdad.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getEdad())));
        colPeso.setCellValueFactory(c -> new SimpleStringProperty(String.valueOf(c.getValue().getPeso())));
        colCosto.setCellValueFactory(c -> new SimpleStringProperty(
                "$" + formatoMiles.format(c.getValue().calcularCostoConsulta())));

        // Carga inicial desde los archivos
        try {
            repo = new MascotaRepository(new MascotaDaoJson());
            tabla.setItems(repo.getMascotas());
        } catch (UncheckedIOException e) {
            mostrarError("Error al leer los archivos: " + e.getMessage());
        }
    }

    @FXML
    private void onRegistrar() {
        if (repo == null) {
            mostrarError("No se puede registrar: hubo un error al leer los archivos.");
            return;
        }
        try {
            String tipo = cmbTipo.getValue();
            if (tipo == null) {
                mostrarError("Tipo: debe seleccionar Perro o Gato.");
                return;
            }

            String nombre = txtNombre.getText() == null ? "" : txtNombre.getText().trim();
            if (nombre.isEmpty()) {
                mostrarError("Nombre: no puede estar vacío ni contener solo espacios.");
                return;
            }

            int edad;
            try {
                edad = Integer.parseInt(txtEdad.getText().trim());
            } catch (NumberFormatException e) {
                mostrarError("Edad: debe ser un número entero entre 0 y 30.");
                return;
            }
            if (edad < 0 || edad > 30) {
                mostrarError("Edad: debe ser un número entero entre 0 y 30.");
                return;
            }

            double peso;
            try {
                peso = Double.parseDouble(txtPeso.getText().trim().replace(',', '.'));
            } catch (NumberFormatException e) {
                mostrarError("Peso: debe ser un número mayor que cero.");
                return;
            }
            if (peso <= 0) {
                mostrarError("Peso: debe ser un número mayor que cero.");
                return;
            }

            Mascota nueva;
            if (tipo.equals("Perro")) {
                String raza = txtRaza.getText() == null ? "" : txtRaza.getText().trim();
                if (raza.isEmpty()) {
                    mostrarError("Raza: no puede estar vacía ni contener solo espacios.");
                    return;
                }
                Perro perro = new Perro(nombre, edad, peso, raza, chkVacunado.isSelected());
                if (chkAdopcion.isSelected()) {
                    perro.marcarDisponibleParaAdopcion();
                }
                nueva = perro;
            } else {
                nueva = new Gato(nombre, edad, peso, chkExterior.isSelected());
            }

            repo.agregar(nueva);
            mostrarOk(nombre + " (" + tipo + ") registrado correctamente.");
            limpiarFormulario();

        } catch (IllegalArgumentException e) {      // por si el modelo rechaza algo
            mostrarError(e.getMessage());
        } catch (UncheckedIOException e) {
            mostrarError("Error al guardar los archivos: " + e.getMessage());
        }
    }

    @FXML
    private void onEliminar() {
        Mascota seleccionada = tabla.getSelectionModel().getSelectedItem();
        if (seleccionada == null) {
            mostrarError("Debe seleccionar una mascota de la tabla para eliminarla.");
            return;
        }
        try {
            repo.eliminar(seleccionada);
            mostrarOk(seleccionada.getNombre() + " (" + seleccionada.getTipo() + ") eliminado correctamente.");
        } catch (UncheckedIOException e) {
            mostrarError("Error al guardar los archivos: " + e.getMessage());
        }
    }

    private void limpiarFormulario() {
        cmbTipo.setValue(null);
        txtNombre.clear();
        txtEdad.clear();
        txtPeso.clear();
        txtRaza.clear();
        chkVacunado.setSelected(false);
        chkAdopcion.setSelected(false);
        chkExterior.setSelected(false);
    }

    private void mostrarError(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: red;");
        lblMensaje.setText(mensaje);
    }

    private void mostrarOk(String mensaje) {
        lblMensaje.setStyle("-fx-text-fill: green;");
        lblMensaje.setText(mensaje);
    }
}