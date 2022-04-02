public abstract class Conta implements IConta{
    protected static final int AGENCIA_PADRAO = 2304;
    private static final int SEQUENCIAL = 1;
    protected final Cliente cliente;

    protected int agencia;
    protected int numero;
    protected double saldo;

    public Conta(Cliente cliente) {
        this.agencia = AGENCIA_PADRAO;
        this.numero = SEQUENCIAL;
        this.cliente = cliente;
    }

    public int getAgencia() {
        return agencia;
    }

    public int getNumero() {
        return numero;
    }

    public double getSaldo() {
        return saldo;
    }

    @Override
    public void sacar(double valor) {
        if(this.saldo >= valor){
            this.saldo += valor;
        }
    }


    @Override
    public void depositar(double valor) {
        this.saldo += valor;
    }

    protected void imprimiDadosComunsConta(){
        System.out.printf("Agencia  %d%n", this.agencia);
        System.out.printf("Número da conta %d%n", this.numero);
        System.out.printf("Saldo em Conta %.2f%n", this.saldo);
    }

    @Override
    public void transferir(double valor, Conta contaDestino) {
        this.sacar(valor);
        contaDestino.depositar(valor);
    }
}
