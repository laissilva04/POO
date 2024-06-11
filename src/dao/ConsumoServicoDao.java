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
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.ConsumoServico;
import models.Reserva;
import models.Servico;

public class ConsumoServicoDao implements Dao<ConsumoServico> {
    private final String nomeDoArquivo = "src/txt/consumosServicos.txt";
    Scanner scanner = new Scanner(System.in);
    ServicoDao servicoDao = new ServicoDao();
    ReservaDao reservaDao = new ReservaDao();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void cadastrar() {

        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();

        Servico servico = null;
        while (servico == null) {
            System.out.println("Servicos disponiveis");

            List<Servico> servicos = servicoDao.listar();

            if (servicos.isEmpty()) {
                System.out.println("Nenhum servico cadastrado");
                return;
            } else {
                for (Servico it : servicos) {
                    System.out.println(it);
                }
            }
            System.out.println("Digite o codigo do item");
            String itemCodigo = scanner.nextLine();

            servico = servicoDao.consultarPorCodigo(itemCodigo);

            if (servico == null) {
                System.out.println("Servico não encontrado, por favor digite um item valido");
            }
        }

        Reserva reserva = null;
        while (reserva == null) {
            System.out.println("Reservas disponiveis: ");

            List<Reserva> reservas = reservaDao.listar();

            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva cadastrada");
                return;
            } else {
                for (Reserva res : reservas) {
                    System.out.println(res);
                }
            }
            System.out.print("Digite o codigo da reserva: ");
            String reservaCodigo = scanner.nextLine();

            reserva = reservaDao.consultarPorCodigo(reservaCodigo);

            if (reserva == null) {
                System.out.println("Reserva não encontrada, por favor digite uma reserva valida");
            }
        }

        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();
        scanner.nextLine();

        Date dataConsumo = null;

        while (dataConsumo == null) {
            System.out.print("Digite a data de consumo no formato dd/MM/yyyy: ");
            String entradaString = scanner.nextLine();
            try {
                dataConsumo = new Date(formatter.parse(entradaString).getTime());
            } catch (ParseException e) {
                System.out.println("Formato de data inválido. Por favor, use o formato dd/MM/yyyy.");
            }
        }

        ConsumoServico consumoServico = new ConsumoServico(codigo, servico, reserva, quantidade, dataConsumo);
        ConsumoServico consumoServicoExistente = consultarPorCodigo(consumoServico.getId());

        if (consumoServicoExistente != null) {
            System.out.println("Consumo de Servico já cadastrado com esse codigo.");
            return;
        }

        try {
            List<ConsumoServico> consumos = listar();
            consumos.add(consumoServico);
            boolean sucesso = salvarEmArquivo(consumos);
            if (sucesso) {
                System.out.println("Consumo de servico cadastrado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar consumo de servico: " + e.getMessage());
        }
    }

    @Override
    public void consultar() {
        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        boolean encontrou = false;

        try {
            List<ConsumoServico> consumos = listar();
            for (ConsumoServico consumoServico : consumos) {
                if (consumoServico.getId().equals(codigo)) {
                    System.out.println(consumoServico);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar consumo de servico: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Consumo não encontrado");
        }
    }

    public ConsumoServico consultarPorCodigo(String codigo) {
        try {
            List<ConsumoServico> consumos = listar();
            for (ConsumoServico consumoServico : consumos) {
                if (consumoServico.getId().equals(codigo)) {
                    return consumoServico;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar consumo de servico: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void editar() {
        System.out.print("Insira o codigo do consumo de servico a ser editado: ");
        String codigo = scanner.nextLine();

        ConsumoServico consumoServicoExistente = consultarPorCodigo(codigo);

        if (consumoServicoExistente == null) {
            System.out.println("Consumo de servico não encontrado.");
            return;
        }

        Servico servico = null;
        while (servico == null) {
            System.out.println("servicos disponíveis");

            List<Servico> servicos = servicoDao.listar();

            if (servicos.isEmpty()) {
                System.out.println("Nenhum servico cadastrado");
                return;
            } else {
                for (Servico it : servicos) {
                    System.out.println(it);
                }
            }
            System.out.println("Digite o codigo do servico");
            String servicoCodigo = scanner.nextLine();

            servico = servicoDao.consultarPorCodigo(servicoCodigo);

            if (servico == null) {
                System.out.println("Servico não encontrado, por favor digite um item válido");
            }
        }
        Reserva reserva = null;
        while (reserva == null) {
            System.out.println("Reservas disponíveis");

            List<Reserva> reservas = reservaDao.listar();

            if (reservas.isEmpty()) {
                System.out.println("Nenhuma reserva cadastrada");
                return;
            } else {
                for (Reserva res : reservas) {
                    System.out.println(res);
                }
            }
            System.out.println("Digite o codigo da reserva");
            String reservaCodigo = scanner.nextLine();

            reserva = reservaDao.consultarPorCodigo(reservaCodigo);

            if (reserva == null) {
                System.out.println("Reserva não encontrada, por favor digite uma reserva válida");
            }
        }

        consumoServicoExistente.setServico(servico);
        consumoServicoExistente.setReserva(reserva);

        List<ConsumoServico> consumos = listar();
        for (int i = 0; i < consumos.size(); i++) {
            if (consumos.get(i).getId().equals(consumoServicoExistente.getId())) {
                consumos.set(i, consumoServicoExistente);
                break;
            }
        }

        salvarEmArquivo(consumos);
        System.out.println("Consumo editado com sucesso!");
    }

    @Override
    public void deletar() {
        throw new UnsupportedOperationException("Unimplemented method 'deletar'");
    }

    @Override
    public List<ConsumoServico> listar() {
        List<ConsumoServico> consumos = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return consumos;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                Date dataConsumo = null;
                String[] dados = linha.split(",");

                java.util.Date utilDateConsumo = formatter.parse(dados[4]);
                java.sql.Date sqlDateConsumo = new java.sql.Date(utilDateConsumo.getTime());
                dataConsumo = sqlDateConsumo;

                if (dados.length == 5) {

                    ConsumoServico consumo = new ConsumoServico(dados[0], servicoDao.consultarPorCodigo(dados[1]),
                            reservaDao.consultarPorCodigo(dados[2]), Integer.valueOf(dados[3]),
                            dataConsumo);
                    consumos.add(consumo);
                }
            }
        } catch (IOException | ParseException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return consumos;
    }

    private boolean salvarEmArquivo(List<ConsumoServico> consumos) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
            for (ConsumoServico consumoServico : consumos) {
                String dataConsumo = formatter.format(consumoServico.getDataConsumo());
                writer.write(consumoServico.getId() + "," + consumoServico.getServico().getCodigo() + "," + consumoServico.getReserva().getCodigo() + ","
                        + consumoServico.getQuantidadeSolicitada() + "," + dataConsumo);
                writer.newLine();
            }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }

}
