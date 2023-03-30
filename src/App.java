import java.io.InputStream;
import java.net.URI;
import java.net.URL;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.util.List;
import java.util.Map;

public class App {
    public static void main(String[] args) throws Exception {

        // fazer uma conexão HTTP e buscar os 250 filmes
        String url = "https://raw.githubusercontent.com/alura-cursos/imersao-java-2-api/main/TopMovies.json";
        URI endereco = URI.create(url);
        HttpClient client = HttpClient.newHttpClient(); // poderia usar var que o java reconhece
        HttpRequest request = HttpRequest.newBuilder(endereco).GET().build();
        HttpResponse<String> response = client.send(request, BodyHandlers.ofString());
        String body = response.body();

        // extrair os dados que interessam (titulo, poster, classificação) ---//nao usei
        // JACSON mas poderia
        // JsonParser mas usei var
        var parser = new JsonParser();
        List<Map<String, String>> listaDeFilmes = parser.parse(body);

        // exibir e manipular os dados
        //criando a fabrica de fora por otimização
        var fabrica = new FabricaDeFigurinhas();

        for (Map<String, String> filme : listaDeFilmes) {

            //gerando figurinhas de todos os filmes
            String urlImage = filme.get("image");
            String titulo = filme.get("title");

            InputStream inputStream = new URL(urlImage).openStream();
            String nomeArquivo = titulo + ".png";

            fabrica.cria(inputStream, nomeArquivo);
            
            //imprimindo dados de todos os filmes
            System.out.println("\u001b[1mTítulo:\u001b[m " + filme.get("title"));
            System.out.println("\u001b[1mPoster:\u001b[m " + filme.get("image"));
            System.out.println("\u001b[0;103m \u001b[30mClassificação: " + filme.get("imDbRating") + " \u001b[m");
            System.out.println();

            //exibir estrelas de acordo com a avaliação
            String ratingString = filme.get("imDbRating");
			double ratingNumber = Double.parseDouble(ratingString);
			int ratingRoundedUp = (int) Math.round(ratingNumber);
			for(int i = 0; i < ratingRoundedUp; i ++){
				System.out.print("⭐");
			}
			System.out.println(" ");
			System.out.println(" ");

        }
    }
}
