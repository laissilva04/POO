package models;

public class Consumo extends Entidade{
    
    private Item item;
    private Reserva reserva;
    private int quantidadeSolicitada;
    private String dataConsumo;

    public Consumo(String id, Item item, Reserva reserva, int quantidadeSolicitada, String dataConsumo){
        super(id);
        this.item = item;
        this.reserva = reserva;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.dataConsumo = dataConsumo;
    }
    public String getdataConsumo() {
        return dataConsumo;
    }
    public Item getItem() {
        return item;
    }
    public int getQuantidadeSolicitada() {
        return quantidadeSolicitada;
    }
    public Reserva getReserva() {
        return reserva;
    }
    public void setdataConsumo(String dataConsumo) {
        this.dataConsumo = dataConsumo;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public void setQuantidadeSolicitada(int quantidadeSolicitada) {
        this.quantidadeSolicitada = quantidadeSolicitada;
    }
    public void setReserva(Reserva reserva) {
        this.reserva = reserva;
    }

    @Override
    public String toString(){
        return "Consumo{id='"+getId()+"'item='"+getItem().toString()+"', reserva='"+getReserva().toString()+"', quantidadeSolicitada='"+getQuantidadeSolicitada()+"', dataConsumo='"+getdataConsumo()+"'}";
    }   
}
