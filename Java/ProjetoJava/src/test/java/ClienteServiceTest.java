import com.github.mgcvale.projetojava.controller.ClienteService;
import com.github.mgcvale.projetojava.model.Cliente;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;

public class ClienteServiceTest {
    public static void main(String[] args) {
        ClienteService clienteService = new ClienteService();

        Cliente cliente1 = new Cliente(1, "Joao", 18, false, Calendar.getInstance().getTime());
        Cliente cliente2 = new Cliente(2, "Ana", 22, true, Calendar.getInstance().getTime());
        Cliente cliente3 = new Cliente(3, "Carlos", 30, false, Calendar.getInstance().getTime());
        Cliente cliente4 = new Cliente(4, "Mariana", 25, true, Calendar.getInstance().getTime());
        Cliente cliente5 = new Cliente(5, "Mateus", 14, true, Calendar.getInstance().getTime());
        Cliente cliente6 = new Cliente(6, "Paula", 28, false, Calendar.getInstance().getTime());
        Cliente cliente7 = new Cliente(7, "Felipe", 35, false, Calendar.getInstance().getTime());
        Cliente cliente8 = new Cliente(8, "Clara", 20, true, Calendar.getInstance().getTime());
        Cliente cliente9 = new Cliente(9, "Lucas", 18, false, Calendar.getInstance().getTime());
        Cliente cliente10 = new Cliente(10, "Fernanda", 23, true, Calendar.getInstance().getTime());

        clienteService.add(cliente1);
        clienteService.add(cliente2);
        clienteService.add(cliente3);
        clienteService.add(cliente4);
        clienteService.add(cliente5);
        clienteService.add(cliente6);
        clienteService.add(cliente7);
        clienteService.add(cliente8);
        clienteService.add(cliente9);
        clienteService.add(cliente10);

        List<Cliente> expected = List.of(cliente4, cliente5);
        List<Cliente> result = clienteService.nameStartsWith("Ma");
        assert(expected.equals(result));

        expected = List.of(cliente1, cliente3);
        result = clienteService.findByName("0");
        assert(expected.equals(result));

        Optional<Cliente> id3Cliente = clienteService.getById(3);
        assert(id3Cliente.isPresent() && id3Cliente.get().equals(cliente3));

        Optional<Cliente> nomeLucasCliente = clienteService.getByName("Lucas");
        assert(nomeLucasCliente.isPresent() && nomeLucasCliente.get().equals(cliente9));
    }
}
