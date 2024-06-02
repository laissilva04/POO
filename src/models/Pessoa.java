package models;


public abstract class Pessoa extends Entidade{

    private String nome;
    private String email;

    public Pessoa(String cpf, String nome, String email){
        super(cpf);
    
        this.nome = nome;
        this.email = email;
    }
    public String getCpf() {
        return getId();
    }
    public String getEmail() {
        return email;
    }
    public String getNome() {
        return nome;
    }
    public void setCpf(String cpf) {
        setId(cpf);
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
}