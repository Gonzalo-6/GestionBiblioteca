package biblioteca.simple.servicios;

import biblioteca.simple.modelo.Usuario;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class GestorUsuarios {
    private static final String ARCHIVO_JSON = "usuarios.json";
    private static final Gson gson = new Gson();

    // Cargar usuarios desde JSON
    public static List<Usuario> cargarUsuarios() {
        try (FileReader reader = new FileReader(ARCHIVO_JSON)) {
            Type tipoLista = new TypeToken<List<Usuario>>() {}.getType();
            List<Usuario> lista = gson.fromJson(reader, tipoLista);
            return (lista != null) ? lista : new ArrayList<>();
        } catch (IOException e) {
            return new ArrayList<>(); // si no existe, lista vacía
        }
    }

    // Guardar usuarios en JSON
    public static void guardarUsuarios(List<Usuario> usuarios) {
        try (FileWriter writer = new FileWriter(ARCHIVO_JSON)) {
            gson.toJson(usuarios, writer);
        } catch (IOException e) {
            System.out.println("Error al guardar usuarios: " + e.getMessage());
        }
    }
}
