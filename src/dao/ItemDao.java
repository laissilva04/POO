package dao;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import models.Item;

public class ItemDao implements Dao<Item> {
    private final String nomeDoArquivo = "src/txt/itens.txt";
    Scanner scanner = new Scanner(System.in);

    @Override
    public void cadastrar() {

        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();

        System.out.print("Digite a descricao do Item: ");
        String descricao = scanner.nextLine();

        System.out.print("Digite o valor: ");
        double valor = scanner.nextDouble();

        scanner.nextLine();

        Item item = new Item(codigo, descricao, valor);
        
        boolean validacao = true;
        Item itemExistente = consultarPorDescricao(item.getDescricao());
        if (itemExistente != null) {
            System.out.println("Item ja cadastrada com essa descricao");
            validacao = false;
        }

        itemExistente = consultarPorCodigo(item.getCodigo());
        if (itemExistente != null) {
            System.out.println("Item ja cadastrada com esse codigo");
            validacao = false;
        }

        if (validacao == true) {
            try {
                List<Item> itens = listar();
                itens.add(item);

                boolean sucesso = salvarEmArquivo(itens);
                if (sucesso) {
                    System.out.println("Item cadastrado com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Error ao cadastrar item: " + e.getMessage());
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
            List<Item> itens = listar();
            for (Item item : itens) {
                if (item.getCodigo().equals(codigo)) {
                    System.out.println(item);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar item: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Item não encontrado");
        }
    }
    public Item consultarPorDescricao(String descricao) {
        try {
            List<Item> itens = listar();
            for (Item c : itens) {
                if (c.getDescricao().equals(descricao)) {
                    return c;
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao consultar item: " + e.getMessage());
        }
        return null;
    }

    public Item consultarPorCodigo(String codigo) {
        try {
            List<Item> itens = listar();
            for (Item item : itens) {
                if (item.getCodigo().equals(codigo)) {
                    return item;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar item: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void editar() {

        System.out.print("Insira o codigo do item a ser editada: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Item itemExistente = consultarPorCodigo(codigo);
        try {
            if (itemExistente == null) {
                System.out.print("Item não encontrada");
            } else {
                System.out.print("Digite a nova descricao (atual: " + itemExistente.getDescricao() + "): ");
                String descricao = scanner.nextLine();

                System.out.print("Digite o novo valor (atual: " + itemExistente.getValor() + "): ");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                itemExistente.setDescricao(descricao);
                itemExistente.setValor(valor);

                List<Item> itens = listar();
                for (int i = 0; i < itens.size(); i++) {
                    if (itens.get(i).getCodigo().equals(itemExistente.getCodigo())) {
                        itens.set(i, itemExistente);
                        break;
                    }
                }
                 salvarEmArquivo(itens);
                System.err.println("Item editada com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar item: " + e.getMessage());
        }
    }
    @Override
    public void deletar() {

        System.out.print("digite o codigo: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Item itemExistente = consultarPorCodigo(codigo);
        if (itemExistente == null) {
            System.out.print("Item não encontrada");
        } else {
            List<Item> itens = listar();
            for (int i = 0; i < itens.size(); i++) {
                if (itens.get(i).getCodigo().equals(itemExistente.getCodigo())) {
                    itens.remove(i);
                    break;
                }
            }
           salvarEmArquivo(itens);
            System.out.print("Item deletado com sucesso!");
        }
    }

    @Override
    public List<Item> listar() {
         List<Item> itens = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return itens;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    Item item = new Item(dados[0], dados[1], Double.valueOf(dados[2]));
                    itens.add(item);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return itens;
    }

    private boolean salvarEmArquivo(List<Item> itens) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
           for (Item item : itens) {
                writer.write(item.getCodigo() + "," + item.getDescricao() + "," + item.getValor());
                writer.newLine();
           }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
