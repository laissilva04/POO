package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import models.Hospede;
import models.Quarto;
import models.Reserva;
import models.Funcionario;

public class ReservaDao implements Dao<Reserva> {

    private final String nomeDoArquivo = "src/txt/reservas.txt";
    Scanner scanner = new Scanner(System.in);
    HospedeDao hospedeDao = new HospedeDao();
    QuartoDao quartoDao = new QuartoDao();
    FuncionarioDao funcionarioDao = new FuncionarioDao();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void cadastrar() {

        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();

        Hospede hospede = null;
        while (hospede == null) {
            System.out.println("Hospedes disponiveis");

            List<Hospede> hospedes = hospedeDao.listar();

            if (hospedes.isEmpty()) {
                System.out.println("Nenhum Hospede cadastrado");
            } else {
                for (Hospede hosp : hospedes) {
                    System.out.println(hosp);
                }
            }
            System.out.println("Digite o cpf do Hospede");
            String cpf = scanner.nextLine();

            hospede = hospedeDao.consultarPorCpf(cpf);

            if (hospede == null) {
                System.out.println("Hospede não encontrado, por favor digite um hospede que ja esteja cadastrado");
            }
        }

        Quarto quarto = null;
        while (quarto == null) {
            System.out.println("Selecione um quarto Disponivel");

            List<Quarto> quartos = quartoDao.listar();

            if (quartos.isEmpty()) {
                System.out.println("Nenhum quarto cadastrado");
            } else {
                for (Quarto quart : quartos) {
                    System.out.println(quart);
                }
            }
            System.out.println("Digite o codigo do quarto");
            String quartoInserido = scanner.nextLine();

            quarto = quartoDao.consultarPorCodigo(quartoInserido);

            if (quarto == null) {
                System.out.println("Quarto não encontrado, por favor digite o codigo de um quarto cadastrado");
            }
        }

        Funcionario funcionario = null;
        while (funcionario == null) {
            System.out.println("Funcionario disponiveis");

            List<Funcionario> funcionarios = funcionarioDao.listar();

            if (funcionarios.isEmpty()) {
                System.out.println("Nenhum Funcionario cadastrado");
            } else {
                for (Funcionario func : funcionarios) {
                    System.out.println(func);
                }
            }
            System.out.println("Digite o cpf do Funcionario responsavel pela reserva");
            String cpf = scanner.nextLine();

            funcionario = funcionarioDao.consultarPorCpf(cpf);

            if (funcionario == null) {
                System.out.println(
                        "Funcionario não encontrado, por favor digite um funcionario que ja esteja cadastrado");
            }
        }

        Date entrada = null;

        while (entrada == null) {
            System.out.println("Digite a data da entrada da reserva no formato dd/MM/yyyy: ");
            String entradaString = scanner.nextLine();
            try {
                entrada = (Date) formatter.parse(entradaString);
            } catch (DateTimeParseException | ParseException e) {
                entrada = null;
                System.out.println("Formato de data inválido. Por favor, use o formato dd/MM/yyyy.");
            }
        }

        Date saida = null;

        while (saida == null) {
            System.out.println("Digite a data da saíde da reserva no formato dd/MM/yyyy: ");
            String saidaString = scanner.nextLine();

            try {
                saida = (Date) formatter.parse(saidaString);
            } catch (DateTimeParseException | ParseException e) {
                saida = null;
                System.out.println("Formato de data inválido. Por favor, use o formato dd/MM/yyyy.");
            }
        }

        long diferencaMillis = Math.abs(saida.getTime() - entrada.getTime());
        long diferencaDias = TimeUnit.DAYS.convert(diferencaMillis, TimeUnit.MILLISECONDS);
        double valorReserva = quarto.getCategoria().getValor() * diferencaDias;

        Reserva reserva = new Reserva(codigo, hospede, quarto, funcionario,
                funcionario, entrada, saida, valorReserva);

        try {
            List<Reserva> reservas = listar();

            reservas.add(reserva);

            boolean sucesso = salvarEmArquivo(reservas);
            if (sucesso) {
                System.out.println("Reserva cadastrada com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Error ao cadastrar reserva: " + e.getMessage());
        }
    }

    @Override
    public void consultar() {
        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        boolean encontrou = false;

        try {
            List<Reserva> reservas = listar();
            for (Reserva reserva : reservas) {
                if (reserva.getCodigo().equals(codigo)) {
                    System.out.println(reserva);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar reserva: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Reserva não encontrada");
        }
    }

    public Reserva consultarPorCodigo(String codigo) {
        try {
            List<Reserva> reservas = listar();
            for (Reserva reserva : reservas) {
                if (reserva.getCodigo().equals(codigo)) {
                    return reserva;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar reserva: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void editar() {

        System.out.print("Insira o codigo da reserva a ser editada: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Reserva reservaExistente = consultarPorCodigo(codigo);

        try {
            if (reservaExistente == null) {
                System.out.print("Reserva não encontrada");
            } else {

                Funcionario funcionarioReserva = null;
                while (funcionarioReserva == null) {
                    System.out.println("Funcionario disponiveis");

                    List<Funcionario> funcionarios = funcionarioDao.listar();

                    if (funcionarios.isEmpty()) {
                        System.out.println("Nenhum Funcionario cadastrado");
                    } else {
                        for (Funcionario func : funcionarios) {
                            System.out.println(func);
                        }
                    }
                    System.out.println("Digite o novo funcionario responsavel pela reserva (atual: "
                            + reservaExistente.getFuncionarioReserva() + "): ");
                    String cpf = scanner.nextLine();

                    funcionarioReserva = funcionarioDao.consultarPorCpf(cpf);

                    if (funcionarioReserva == null) {
                        System.out.println(
                                "Funcionario não encontrado, por favor digite um funcionario que ja esteja cadastrado");
                    }
                }

                Quarto quarto = null;
                while (quarto == null) {
                    System.out.println("Selecione um quarto Disponivel");

                    List<Quarto> quartos = quartoDao.listar();

                    if (quartos.isEmpty()) {
                        System.out.println("Nenhum quarto cadastrado");
                    } else {
                        for (Quarto quart : quartos) {
                            System.out.println(quart);
                        }
                    }
                    System.out.println("Digite o codigo do quarto (atual: " + reservaExistente.getQuarto() + "): ");
                    String quartoInserido = scanner.nextLine();

                    quarto = quartoDao.consultarPorCodigo(quartoInserido);

                    if (quarto == null) {
                        System.out.println("Quarto não encontrado, por favor digite o codigo de um quarto cadastrado");
                    }
                }

                Date checkIn = null;

                while (checkIn == null) {
                    System.out.println("Digite a nova data de CheckIn no formato dd/MM/yyyy (atual"
                            + reservaExistente.getCheckIn() + "): ");
                    String checkInString = scanner.nextLine();

                    try {
                        checkIn = (Date) formatter.parse(checkInString);
                    } catch (DateTimeParseException | ParseException e) {
                        checkIn = null;
                        System.out.println("Formato de data inválido. Por favor, use o formato dd/MM/yyyy.");
                    }
                }

                Date checkOut = null;

                while (checkOut == null) {
                    System.out.println("Digite a nova data de CheckOut no formato dd/MM/yyyy (atual"
                            + reservaExistente.getCheckOut() + "): ");
                    String checkOutString = scanner.nextLine();

                    try {
                        checkOut = (Date) formatter.parse(checkOutString);
                    } catch (DateTimeParseException | ParseException e) {
                        checkOut = null;
                        System.out.println("Formato de data inválido. Por favor, use o formato dd/MM/yyyy.");
                    }
                }

                reservaExistente.setFuncionarioReserva(funcionarioReserva);
                reservaExistente.setCheckIn(checkIn);
                reservaExistente.setCheckOut(checkOut);
                reservaExistente.setQuarto(quarto);

                List<Reserva> reservas = listar();
                for (int i = 0; i < reservas.size(); i++) {
                    if (reservas.get(i).getCodigo().equals(reservaExistente.getCodigo())) {
                        reservas.set(i, reservaExistente);
                        break;
                    }
                }
                salvarEmArquivo(reservas);
                System.err.println("Reserva editada com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar reserva: " + e.getMessage());
        }
    }

    @Override
    public void deletar() {
        throw new UnsupportedOperationException("Unimplemented method 'deletar'");
    }

    @Override
    public List<Reserva> listar() {

        List<Reserva> reservas = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return reservas;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 6) {
                    Reserva reserva = new Reserva(dados[0], hospedeDao.consultarPorCpf(dados[1]), quartoDao.consultarPorCodigo(dados[2]), funcionarioDao.consultarPorCpf((dados[3])), funcionarioDao.consultarPorCpf((dados[4])), (Date) formatter.parse(dados[5]), (Date) formatter.parse(dados[6]), Double.valueOf(dados[7]), (Date) formatter.parse(dados[8]), (Date) formatter.parse(dados[9]), Double.valueOf(dados[10]));
                    reservas.add(reserva);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return reservas;
    }

    private boolean salvarEmArquivo(List<Reserva> reservas) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
            for (Reserva reserva : reservas) {
                writer.write(reserva.getCodigo() + "," + reserva.getHospede().getCpf() + "," + reserva.getQuarto().getCodigo() + "," + reserva.getFuncionarioReserva().getCpf() + "," + reserva.getFuncionarioFechamento().getCpf() + "," + reserva.getDataEntradaReserva() + "," + reserva.getDataSaidaReserva() + "," + reserva.getCheckIn() + "," + reserva.getCheckOut() + "," + reserva.getValorReserva() + "," + reserva.getValorPago());
                writer.newLine();
            }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}