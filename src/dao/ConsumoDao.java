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

public class ConsumoDao implements Dao<Consumo> {
    private final String nomeDoArquivo = "src/txt/consumos.txt";
    Scanner scanner = new Scanner(System.in);
    ItemDao itemDao = new ItemDao();
    ReservaDao reservaDao = new ReservaDao();
    SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    @Override
    public void cadastrar() {

        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();

        Item item = null;
        while (item == null) {
            System.out.println("Itens disponiveis");

            List<Item> itens = itemDao.listar();

            if (itens.isEmpty()) {
                System.out.println("Nenhum item cadastrado");
                return;
            } else {
                for (Item it : itens) {
                    System.out.println(it);
                }
            }
            System.out.println("Digite o codigo do item");
            String itemCodigo = scanner.nextLine();

            item = itemDao.consultarPorCodigo(itemCodigo);

            if (item == null) {
                System.out.println("Item não encontrado, por favor digite um item valido");
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

        Consumo consumo = new Consumo(codigo, item, reserva, quantidade, dataConsumo);

        Consumo consumoExistente = consultarPorCodigo(consumo.getId());

        if (consumoExistente != null) {
            System.out.println("Consumo já cadastrado com esse codigo.");
            return;
        }

        try {
            List<Consumo> consumos = listar();
            consumos.add(consumo);
            boolean sucesso = salvarEmArquivo(consumos);
            if (sucesso) {
                System.out.println("Consumo cadastrado com sucesso!");
            }
        } catch (Exception e) {
            System.out.println("Erro ao cadastrar consumo: " + e.getMessage());
        }
    }

    @Override
    public void consultar() {
        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        boolean encontrou = false;

        try {
            List<Consumo> consumos = listar();
            for (Consumo consumo : consumos) {
                if (consumo.getId().equals(codigo)) {
                    System.out.println(consumo);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao realizar consumo: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Consumo não encontrado");
        }
    }

    public Consumo consultarPorCodigo(String codigo) {
        try {
            List<Consumo> consumos = listar();
            for (Consumo consumo : consumos) {
                if (consumo.getId().equals(codigo)) {
                    return consumo;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar consumo: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void editar() {
        System.out.print("Insira o codigo do consumo a ser editado: ");
        String codigo = scanner.nextLine();

        Consumo consumoExistente = consultarPorCodigo(codigo);

        if (consumoExistente == null) {
            System.out.println("Consumo não encontrado.");
            return;
        }

        Item item = null;
        while (item == null) {
            System.out.println("Itens disponíveis");

            List<Item> itens = itemDao.listar();

            if (itens.isEmpty()) {
                System.out.println("Nenhum item cadastrado");
                return;
            } else {
                for (Item it : itens) {
                    System.out.println(it);
                }
            }
            System.out.println("Digite o codigo do item");
            String itemCodigo = scanner.nextLine();

            item = itemDao.consultarPorCodigo(itemCodigo);

            if (item == null) {
                System.out.println("Item não encontrado, por favor digite um item válido");
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

        consumoExistente.setItem(item);
        consumoExistente.setReserva(reserva);

        List<Consumo> consumos = listar();
        for (int i = 0; i < consumos.size(); i++) {
            if (consumos.get(i).getId().equals(consumoExistente.getId())) {
                consumos.set(i, consumoExistente);
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
    public List<Consumo> listar() {

        List<Consumo> consumos = new ArrayList<>();
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

                    Consumo consumo = new Consumo(dados[0], itemDao.consultarPorCodigo(dados[1]),
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

    private boolean salvarEmArquivo(List<Consumo> consumos) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
            for (Consumo consumo : consumos) {
                String dataConsumo = formatter.format(consumo.getdataConsumo());
                writer.write(consumo.getId() + "," + consumo.getItem().getCodigo() + "," + consumo.getReserva().getCodigo() + ","
                        + consumo.getQuantidadeSolicitada() + "," + dataConsumo);
                writer.newLine();
            }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
