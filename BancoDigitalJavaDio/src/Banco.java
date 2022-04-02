import java.util.ArrayList;

public class Banco {
    private final String nome;
    private final ArrayList<Cliente> clientes = new ArrayList<>();

    public Banco(String nome) {
        this.nome = nome;
    }

    public void setClientes(Cliente cliente) {
        this.clientes.add(cliente);
    }
    public String getNome() {
        return nome;
    }

    public void imprimiListaClientes(){
        System.out.println();
        System.out.printf("==== Banco %s ====", this.getNome());
        System.out.println("==== Clientes ====");
        clientes.forEach(cliente -> System.out.println(cliente.getNome()));
        System.out.println();
    }

}
