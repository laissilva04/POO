package models;

public class Funcionario extends Pessoa{

    private String setor;
    
    public Funcionario(String cpf, String nome, String email, String setor){
        super(cpf, nome, email);
        this.setor = setor;
    }
    public String getSetor() {
        return setor;
    }
    public void setSetor(String setor) {
        this.setor = setor;
    }
    @Override
    public String toString(){
        return "Funcionario{cpf='"+ getCpf()+"', nome='"+getNome() +"', email='"+ getEmail()+"', setor='"+getSetor()+"'}";
    }
}
