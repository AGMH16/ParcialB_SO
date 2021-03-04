/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package clases;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Comparator;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

/**
 *
 * @author Miguel Matul <https://github.com/MigueMat4>
 */
public final class DigiWorld {

    private static final String DIGIMON_API_URL = "https://digimon-api.vercel.app/api/digimon/";
    private List<Digimon> digimons;
    private final DefaultTableModel tabla;
    char[] minusculas, mayusculas;

    public DigiWorld(DefaultTableModel model) {
        tabla = model;
    }

    public void letras() {
        minusculas = new char[26];
        for (int i = 0;
                i < 26; i++) {
            minusculas[i] = (char) ('a' + i);
        }
        mayusculas = new char[26];
        for (int i = 0;
                i < 26; i++) {
            mayusculas[i] = (char) ('A' + i);
        }
    }

    public void descargarDatos() throws IOException, InterruptedException {
        System.out.println("Conectando a la API...");
        // código para conectarse a la API y descargar los datos.
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .GET()
                .header("Accept", "application/json")
                .uri(URI.create(DIGIMON_API_URL))
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        System.out.println("¡Conexión exitosa! Descargando datos...");
        ObjectMapper mapper = new ObjectMapper();
        // obtener listado de Digimon
        List<Digimon> auxDigimon;
        digimons = mapper.readValue(response.body(), new TypeReference<List<Digimon>>() {
        });
        // ordenar listado de Digimon
        // <Inserte su código aquí>
        // agregar listado de Digimon a la 
        int i, j;
        Digimon aux, d, d2;
        String D, D2;
        for (i = 0; i < digimons.size() - 1; i++) {
            for (j = 0; j < digimons.size() - i - 1; j++) {
                d = digimons.get(j + 1);
                d2 = digimons.get(j);
                D = d.getName();
                D2 = d2.getName();
                if (D.charAt(0) < D2.charAt(0)) {
                    aux = d;
                    digimons.set(j + 1, d2);
                    digimons.set(j, aux);
                    d2 = aux;
                }
                if (D.charAt(0) == D2.charAt(0)) {
                    if (D.charAt(1) < D2.charAt(1)) {
                        aux = d;
                        digimons.set(j + 1, d2);
                        digimons.set(j, aux);
                        d2 = aux;
                    }

                }
                if (D.charAt(1) == D2.charAt(1)) {
                    if (D.charAt(2) < D2.charAt(2)) {
                        System.out.println("si entra");
                        aux = d;
                        digimons.set(j + 1, d2);
                        digimons.set(j, aux);
                        d2 = aux;
                    }
                }
            }
        }
        digimons.forEach((digimon) -> {
            tabla.addRow(new Object[]{digimon.getName(), digimon.getLevel()});
        });
        System.out.println("¡Datos descargados!");
    }

    public List<Digimon> getDigimons() {
        return digimons;
    }
}
