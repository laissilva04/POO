package models;

import java.sql.Date;

public class Reserva extends Entidade {

    private Hospede hospede;
    private Quarto quarto;
    private Funcionario funcionarioReserva;
    private Funcionario funcionarioFechamento;
    private Date dataEntradaReserva;
    private Date dataSaidaReserva;
    private Date checkIn;
    private Date checkOut;
    private double valorReserva;
    private double valorPago;

    public Reserva(String codigo, Hospede hospede, Quarto quarto, Funcionario funcionarioReserva,
            Funcionario funcionarioFechamento, Date dataEntradaReserva, Date dataSaidaReserva, double valorReserva) {
        super(codigo);
        this.hospede = hospede;
        this.quarto = quarto;
        this.funcionarioReserva = funcionarioReserva;
        this.funcionarioFechamento = funcionarioFechamento;
        this.dataEntradaReserva = dataEntradaReserva;
        this.dataSaidaReserva = dataSaidaReserva;
        this.valorReserva = valorReserva;
    }

    public Reserva(String codigo, Hospede hospede, Quarto quarto, Funcionario funcionarioReserva,
            Funcionario funcionarioFechamento, Date dataEntradaReserva, Date dataSaidaReserva, double valorReserva,
            Date checkIn, Date checkOut,
            double valorPago) {
        super(codigo);
        this.hospede = hospede;
        this.quarto = quarto;
        this.funcionarioReserva = funcionarioReserva;
        this.funcionarioFechamento = funcionarioFechamento;
        this.dataEntradaReserva = dataEntradaReserva;
        this.dataSaidaReserva = dataSaidaReserva;
        this.valorReserva = valorReserva;
        this.checkIn = checkIn;
        this.checkOut = checkOut;
        this.valorPago = valorPago;
    }

    public String getCodigo() {
        return getId();
    }

    public Date getDataEntradaReserva() {
        return dataEntradaReserva;
    }

    public Date getDataSaidaReserva() {
        return dataSaidaReserva;
    }

    public Date getCheckIn() {
        return checkIn;
    }

    public void setCheckIn(Date checkIn) {
        this.checkIn = checkIn;
    }

    public Date getCheckOut() {
        return checkOut;
    }

    public void setCheckOut(Date checkOut) {
        this.checkOut = checkOut;
    }

    public Funcionario getFuncionarioFechamento() {
        return funcionarioFechamento;
    }

    public Funcionario getFuncionarioReserva() {
        return funcionarioReserva;
    }

    public Hospede getHospede() {
        return hospede;
    }

    public Quarto getQuarto() {
        return quarto;
    }

    public double getValorPago() {
        return valorPago;
    }

    public double getValorReserva() {
        return valorReserva;
    }

    public void setCodigo(String codigo) {
        setId(codigo);
    }

    public void setDataEntradaReserva(Date dataEntradaReserva) {
        this.dataEntradaReserva = dataEntradaReserva;
    }

    public void setDataSaidaReserva(Date dataSaidaReserva) {
        this.dataSaidaReserva = dataSaidaReserva;
    }

    public void setFuncionarioFechamento(Funcionario funcionarioFechamento) {
        this.funcionarioFechamento = funcionarioFechamento;
    }

    public void setFuncionarioReserva(Funcionario funcionarioReserva) {
        this.funcionarioReserva = funcionarioReserva;
    }

    public void setHospede(Hospede hospede) {
        this.hospede = hospede;
    }

    public void setQuarto(Quarto quarto) {
        this.quarto = quarto;
    }

    public void setValorPago(double valorPago) {
        this.valorPago = valorPago;
    }

    public void setValorReserva(double valorReserva) {
        this.valorReserva = valorReserva;
    }

    @Override
    public String toString() {
        return "Reserva{id='" + getId() + "'Hospede='" + getHospede().toString() + "', quarto='"
                + getQuarto().toString() + "', funcionarioReserva='" + getFuncionarioReserva().toString()
                + "', funcionarioFechamento='" + getFuncionarioFechamento().toString() + "', dataEntrada='"
                + getDataEntradaReserva() + "', dataSaida='" + getDataSaidaReserva() + "', dataCheckIn='" + getCheckIn()
                + "', dataCheckOut='" + getCheckOut() + "', valorReserva='"
                + getValorReserva() + "', valorPago='" + getValorPago() + "'}";
    }
}
