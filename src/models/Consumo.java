package models;

import java.sql.Date;

public class Consumo extends Entidade{
    
    private Item item;
    private Reserva reserva;
    private int quantidadeSolicitada;
    private Date dataConsumo;

    public Consumo(String id, Item item, Reserva reserva, int quantidadeSolicitada, Date dataConsumo){
        super(id);
        this.item = item;
        this.reserva = reserva;
        this.quantidadeSolicitada = quantidadeSolicitada;
        this.dataConsumo = dataConsumo;
    }
    public Date getdataConsumo() {
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
    public void setdataConsumo(Date dataConsumo) {
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
