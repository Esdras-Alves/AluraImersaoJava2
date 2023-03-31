import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

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

            String urlImagem = filme.get("image");
            String titulo = filme.get("fullTitle");

            InputStream inputStream = new URL(urlImagem).openStream();
            String nomeArquivo = titulo + ".png";

            var geradora = new GeradoraDeFigurinhas();
            geradora.cria(nomeArquivo, inputStream);
            System.out.println(filme.get("image"));
            System.out.println(count + "º" + " lugar");
            System.out.println("Título: " + titulo);
            System.out.println("Classificação: " + filme.get("imDbRating"));
            System.out.println("⭐".repeat((int) Double.parseDouble(filme.get("imDbRating"))));
            System.out.println();
            count++;
        }
    }
}
