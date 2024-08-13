package fisiopro1;

public class Clientes {
    private int id;
    private String nome;
    private String cpf;
    private String data_agendamento;
    private String hora;
    private String observacao;

    public Clientes(int id, String nome, String cpf, String data_agendamento, String hora, String observacao) {
        this.id = id;
        this.nome = nome;
        this.cpf = cpf;
        this.data_agendamento = data_agendamento;
        this.hora = hora;
        this.observacao = observacao;
    }
    public int getId() {
        return id;
    }
    public String getNome() {
        return nome;
    }
    public String getCPF() {
        return cpf;
    }
    public String getDataAgendamento() {
        return data_agendamento;
    }
    public String getHora() {
        return hora;
    }
    public String getObservacao() {
        return observacao;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public void setCPF(String cpf) {
        this.cpf = cpf;
    }
    public void setDataAgendamento(String data_agendamento) {
        this.data_agendamento = data_agendamento;
    }
    public void setHora(String hora) {
        this.hora = hora;
    }
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
