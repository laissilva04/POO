package models;
;

public class Hospede extends Pessoa{
    
    private String enderecoCompleto;

    public Hospede(String cpf, String nome, String email, String endereCocompleto){
        super(cpf, nome, email);
        this.enderecoCompleto = endereCocompleto;
    }
    public String getEnderecoCompleto() {
        return enderecoCompleto;
    }
    public void setEnderecoCompleto(String enderecoCompleto) {
        this.enderecoCompleto = enderecoCompleto;
    }
    @Override
    public String toString(){
        return "Hospede{cpf='"+ getCpf()+"', nome='"+getNome() +"', email='"+ getEmail()+"', enderecoCompleto= '"+getEnderecoCompleto()+"'}";
    }
   

}
