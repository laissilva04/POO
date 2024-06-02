package models;


public class ConsumoServico extends Entidade{
    
    private Servico servico;
    private Reserva reserva;
    private int quantidadeSolicitada;
    private String dataConsumo;
    
    public ConsumoServico(String id, Servico servico, Reserva reserva, int quantidadeSolicitada, String dataConsumo){
        super(id);
        this.servico = servico;
        this.reserva = reserva;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.dataConsumo = dataConsumo;
    }
    public String getDataConsumo() {
        return dataConsumo;
    }
    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
    public Reserva getReserva() {
        return reserva;
    }
    public Servico getServico() {
        return servico;
    }
    public void setDataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }
    public void setQuantidadeSolicitada(int quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }
    public void setServico(Servico servico) {
        this.servico = servico;
    }

    @Override
    public String toString(){
        return "ConsumoServico{id='"+getId()+"'servico='"+getServico().toString()+"', reserva='"+getReserva().toString()+"', quantidadeSolicitada='"+getQuantidadeSolicitada()+"', dataConsumo='"+getDataConsumo()+"'}";
    }  

    
}
