package cl.duoc.pataverde.repository;

import cl.duoc.pataverde.dao.MascotaDao;
import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Mascota;
import cl.duoc.pataverde.model.Perro;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.List;

public class MascotaRepository {
    private final MascotaDao dao;
    private final ObservableList<Mascota> mascotas = FXCollections.observableArrayList();

    public MascotaRepository(MascotaDao dao) {
        this.dao = dao;
        mascotas.addAll(dao.cargarPerros());
        mascotas.addAll(dao.cargarGatos());
    }

    public ObservableList<Mascota> getMascotas() {
        return mascotas;
    }

    public void agregar(Mascota m) {
        mascotas.add(m);
        persistir();
    }

    public void eliminar(Mascota m) {
        mascotas.remove(m);
        persistir();
    }

    private void persistir() {
        List<Perro> perros = new ArrayList<>();
        List<Gato> gatos = new ArrayList<>();
        for (Mascota m : mascotas) {
            if (m instanceof Perro p) perros.add(p);
            else if (m instanceof Gato g) gatos.add(g);
        }
        dao.guardarPerros(perros);
        dao.guardarGatos(gatos);
    }
}