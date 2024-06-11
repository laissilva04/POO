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

import models.Consumo;
import models.Item;
import models.Reserva;
import models.Servico;

public class ConsumoServico implements Dao<ConsumoServico> {
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'consultar'");
    }

    @Override
    public void editar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'editar'");
    }

    @Override
    public void deletar() {
        throw new UnsupportedOperationException("Unimplemented method 'deletar'");
    }

    @Override
    public List<ConsumoServico> listar() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }

    private boolean salvarEmArquivo(List<ConsumoServico> consumos) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
            for (ConsumoServico consumoServico : consumos) {
                String dataConsumo = formatter.format(consumoServico.getdataConsumo());
                writer.write(consumoServico.getId() + "," + consumoServico.getItem().getCodigo() + "," + consumoServico.getReserva().getCodigo() + ","
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
