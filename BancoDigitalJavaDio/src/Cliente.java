public class Cliente extends Endereco{
    private final String nome;

    public Cliente(String nome, String rua, int numero, String bairro, String cidade, String estado) {
        super(rua, numero, bairro, cidade, estado);
        this.nome = nome;
    }


    public String getNome() {
        return nome;
    }

    public void pegaDadosCliente(){
        System.out.println();
        System.out.println("==== Dados do Cliente =====");
        System.out.printf("Nome: %s%n",this.getNome());
        System.out.println();
        System.out.println("==== Endereço completo ====");
        System.out.printf("Estado: %s%n",this.getEstado());
        System.out.printf("Cidade: %s%n",super.getCidade());
        System.out.printf("Bairro: %s%n",super.getBairro());
        System.out.printf("Rua: %s,%s",super.getRua(),super.getNumero());
        System.out.println();
    }
}

