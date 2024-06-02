package models;


public class Quarto extends Entidade{

    private Categoria categoria;
    private String status;

    public Quarto(String codigo, Categoria categoria, String status){
        super(codigo);
        this.categoria = categoria;
        this.status = status;
    }

    public String getCodigo() {
        return getId();
    }
    public String getStatus() {
        return status;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public void setCodigo(String codigo) {
        setId(codigo);
    }
    public void setStatus(String status) {
        this.status = status;
    }
    @Override
    public String toString(){
        return "Servico{codigo='"+ getCodigo()+"', categoria='"+getCategoria().toString() +"', status='"+ getStatus()+"'}";
    }
    
}
