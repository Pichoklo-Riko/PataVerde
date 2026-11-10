package cl.duoc.pataverde.dao;

import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Perro;

import java.util.List;

public interface MascotaDao {
    List<Perro> cargarPerros();
    List<Gato> cargarGatos();
    void guardarPerros(List<Perro> perros);
    void guardarGatos(List<Gato> gatos);
}