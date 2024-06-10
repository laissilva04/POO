package dao;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Categoria;
import models.CategoriaItem;
import models.Consumo;
import models.Item;
import models.Reserva;

public class ConsumoDao implements Dao<Consumo> {
    private final String nomeDoArquivo = "src/txt/consumos.txt";
    Scanner scanner = new Scanner(System.in);
    ItemDao itemDao = new ItemDao();
    ReservaDao reservaDao = new ReservaDao();

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
            } else {
                for(Item it : itens){
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
            } else {
                for (Reserva res : reservas) {
                    System.out.println(res);
                }
            }
            System.out.print("Digite o codigo da reserva: ");
            String reservaCodigo = scanner.nextLine();

            reserva = reservaDao.consultarPorCodigo(reservaCodigo);

            if (reserva == null) {
                System.out.println("Reserva não encontrada, por favor digite uma categoria valida");
            }
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
    public List<Consumo> listar() {

        List<Consumo> consumos = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return consumos;
        }
        
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
        
                    Consumo consumo = new Consumo(dados[0], dados[1], Integer.valueOf(dados[2]), dateFormat.parse(dados[03]));
                    consumos.add(consumo);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return consumos;
    }

    private boolean salvarEmArquivo(List<Consumo> consumos) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
            for (Consumo consumo : consumos) {
                writer.write(consumo.getId() + "," + consumo.getItem() + "," + consumo.getReserva() + "," + consumo.getQuantidadeSolicitada() + "," + consumo.getdataConsumo());
                writer.newLine();
            }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
