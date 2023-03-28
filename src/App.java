import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws IOException, InterruptedException {

        // fazer uma conexão HTTP e buscar os top 10 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        String body = response.body();

        // pegar só os dados que interessem (titulo, poster e classificação)
        JsonParser parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        System.out.println("Seja Bem-Vindo! Esses são os 10 filmes mais bem avaliados:\n");
        int count = 1;
        for (Map<String, String> filme : listaDeFilmes) {
            System.out.println(count + "º" + " lugar");
            System.out.println("Título: " + filme.get("title"));
            System.out.println("Poster: " + filme.get("image"));
            System.out.println("Classificação: " + filme.get("imDbRating"));
            System.out.println("⭐".repeat((int) Double.parseDouble(filme.get("imDbRating"))));
            System.out.println();
            count++;
        }
    }
}
