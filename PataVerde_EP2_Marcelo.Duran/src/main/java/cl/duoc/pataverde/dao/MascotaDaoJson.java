package cl.duoc.pataverde.dao;

import cl.duoc.pataverde.model.Gato;
import cl.duoc.pataverde.model.Perro;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.io.Reader;
import java.io.UncheckedIOException;
import java.io.Writer;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class MascotaDaoJson implements MascotaDao {
    private final Path archivoPerros = Path.of("perros.json");
    private final Path archivoGatos = Path.of("gatos.json");
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    @Override
    public List<Perro> cargarPerros() {
        return leer(archivoPerros, new TypeToken<List<Perro>>() {}.getType());
    }

    @Override
    public List<Gato> cargarGatos() {
        return leer(archivoGatos, new TypeToken<List<Gato>>() {}.getType());
    }

    @Override
    public void guardarPerros(List<Perro> perros) {
        escribir(archivoPerros, perros);
    }

    @Override
    public void guardarGatos(List<Gato> gatos) {
        escribir(archivoGatos, gatos);
    }

    private <T> List<T> leer(Path archivo, Type tipo) {
        if (!Files.exists(archivo)) {
            return new ArrayList<>();          // primera ejecución: sin error
        }
        try (Reader r = Files.newBufferedReader(archivo)) {
            List<T> datos = gson.fromJson(r, tipo);
            return datos != null ? datos : new ArrayList<>();
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo leer " + archivo.getFileName(), e);
        }
    }

    private void escribir(Path archivo, List<?> datos) {
        try (Writer w = Files.newBufferedWriter(archivo)) {
            gson.toJson(datos, w);
        } catch (IOException e) {
            throw new UncheckedIOException("No se pudo guardar " + archivo.getFileName(), e);
        }
    }
}