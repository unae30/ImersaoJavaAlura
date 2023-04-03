import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ExtratorDeConteudoIMDB implements ExtratorDeConteudo{
    public List<Conteudo> extraiConteudos(String json){
         //extrair so os dados que interessam
         var parser = new JsonParser();
         List<Map<String, String>> listaDeAtributos = parser.parse(json);
         List<Conteudo> ListaDeConteudos = new ArrayList<>();
 
         //popular a lista de conteudos
         for(Map<String, String> atributos : listaDeAtributos){
 
             String titulo = atributos.get("title");
             String urlImage = atributos.get("image").replaceAll("(@+)(.*).jpg$", "$1.jpg");
 
             var conteudo = new Conteudo(titulo, urlImage);
             ListaDeConteudos.add(conteudo);
         }
 
         return ListaDeConteudos;
    }

}
