public class Main {
    public static void main(String[] args){
        Cliente cliente1 = new Cliente(
                "Henrique", "Rua Romano", 68, "Rio pequeno", "São paulo", "SP"
        );
        Cliente cliente2 = new Cliente(
                "João", "Rua Giulio ", 68, "Rio grande", "São paulo", "SP"
        );
        Cliente cliente3 = new Cliente(
                "Pedro", "Rua Cardoso Romano", 68, "Rio medio", "São paulo", "SP"
        );

        Banco bancoDevJava = new Banco("Banco Dev Java");
        bancoDevJava.setClientes(cliente1);
        bancoDevJava.setClientes(cliente2);
        bancoDevJava.setClientes(cliente3);


        Conta cc1 = new ContaCorrente(cliente1);
        Conta cp1 = new ContaPoupanca(cliente1);

        cc1.depositar(220);
        cc1.transferir(100,cp1);
        cp1.sacar(20);


        cc1.imprimirSaldo();
        cp1.imprimirSaldo();
        cliente1.pegaDadosCliente();
        bancoDevJava.imprimiListaClientes();
    }
}
