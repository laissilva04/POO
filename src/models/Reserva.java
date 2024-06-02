package models;

public class Reserva extends Entidade {

    private Hospede hospede;
    private Quarto quarto;
    private Funcionario funcionarioReserva;
    private Funcionario funcionarioFechamento;
    private String dataEntradaReserva;
    private String dataSaidaReserva;
    private double valorReserva;
    private double valorPago;

    public Reserva(String codigo, Hospede hospede, Quarto quarto, Funcionario funcionarioReserva,
            Funcionario funcionarioFechamento, String dataEntradaReserva, String dataSaidaReserva, double valorReserva,
            double valorPago) {
        super(codigo);
        this.hospede = hospede;
        this.quarto = quarto;
        this.funcionarioReserva = funcionarioReserva;
        this.funcionarioFechamento = funcionarioFechamento;
        this.dataEntradaReserva = dataEntradaReserva;
        this.dataSaidaReserva = dataSaidaReserva;
        this.valorReserva = valorReserva;
        this.valorPago = valorPago;
    }

    public String getCodigo() {
        return getId();
    }

    public String getDataEntradaReserva() {
        return dataEntradaReserva;
    }

    public String getDataSaidaReserva() {
        return dataSaidaReserva;
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

    public void setDataEntradaReserva(String dataEntradaReserva) {
        this.dataEntradaReserva = dataEntradaReserva;
    }

    public void setDataSaidaReserva(String dataSaidaReserva) {
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
                + getDataEntradaReserva() + "', dataSaida='" + getDataSaidaReserva() + "', valorReserva='"
                + getValorReserva() + "', valorPago='" + getValorPago() + "'}";
    }
}
