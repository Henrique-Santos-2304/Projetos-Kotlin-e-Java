public class Endereco {
    protected String rua;
    protected int numero;
    protected String bairro;
    protected String cidade;
    protected String estado;

    public Endereco(String rua, int numero, String bairro, String cidade, String estado) {
        this.rua= rua;
        this.numero = numero;
        this.bairro = bairro;
        this.cidade = cidade;
        this.estado = estado;
    }

    protected String getRua() {
        return rua;
    }

    protected int getNumero() {
        return numero;
    }

    protected String getBairro() {
        return bairro;
    }

    protected String getCidade() {
        return cidade;
    }

    protected String getEstado() {
        return estado;
    }

}
