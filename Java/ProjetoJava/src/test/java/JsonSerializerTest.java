import com.github.mgcvale.projetojava.controller.serializer.JsonSerializer;
import com.github.mgcvale.projetojava.controller.ProdutoService;
import com.github.mgcvale.projetojava.model.Cor;
import com.github.mgcvale.projetojava.model.Produto;

import java.io.IOException;

public class JsonSerializerTest {
    public static void main(String[] args) throws IOException {
        ProdutoService produtoService = new ProdutoService();
        Produto prod1 = new Produto(1, "Thinkpad T480", "Notebook bacanudo", 1200.00, Cor.PRETO);
        produtoService.add(prod1);

        JsonSerializer.exportToJson(produtoService);
        ProdutoService importedPS = JsonSerializer.importJson(produtoService.getObjectName(), ProdutoService.class);
        for(Produto produto : importedPS.getAll()) {
            System.out.println(produto.toString());
        }
    }
}
