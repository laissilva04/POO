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

import models.Categoria;

public class CategoriaDao implements Dao<Categoria> {
    private final String nomeDoArquivo = "src/txt/categorias.txt";
    Scanner scanner = new Scanner(System.in);

    @Override
    public void cadastrar() {

        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();

        System.out.print("Digite a descricao: ");
        String descricao = scanner.nextLine();

        System.out.print("Digite o valor: ");
        double valor = scanner.nextDouble();

        scanner.nextLine();

        Categoria categoria = new Categoria(codigo, descricao, valor);
        
        boolean validacao = true;
        Categoria categoriaExistente = consultarPorDescricao(categoria.getDescricao());
        if (categoriaExistente != null) {
            System.out.println("Categoria ja cadastrada com essa descricao");
            validacao = false;
        }

        categoriaExistente = consultarPorCodigo(categoria.getCodigo());
        if (categoriaExistente != null) {
            System.out.println("Categoria ja cadastrada com esse codigo");
            validacao = false;
        }

        if (validacao == true) {
            try {//editar
                List<Categoria> categorias = listar();

                categorias.add(categoria);

                boolean sucesso = salvarEmArquivo(categorias);
                if (sucesso) {
                    System.out.println("Categoria cadastrada com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Error ao cadastrar categoria: " + e.getMessage());
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
            List<Categoria> categorias = listar();
            for (Categoria categoria : categorias) {
                if (categoria.getCodigo().equals(codigo)) {
                    System.out.println(categoria);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar categoria: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Categoria não encontrada");
        }
    }

    public Categoria consultarPorDescricao(String descricao) {
        try {
            List<Categoria> categorias = listar();
            for (Categoria c : categorias) {
                if (c.getDescricao().equals(descricao)) {
                    return c;
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao consultar categoria: " + e.getMessage());
        }
        return null;
    }

    public Categoria consultarPorCodigo(String codigo) {
        try {
            List<Categoria> categorias = listar();
            for (Categoria categoria : categorias) {
                if (categoria.getCodigo().equals(codigo)) {
                    return categoria;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar categoria: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void editar() {
        System.out.print("Insira o codigo da categoria a ser editada: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Categoria categoriaExistente = consultarPorCodigo(codigo);
        try {
            if (categoriaExistente == null) {
                System.out.print("Categoria não encontrada");
            } else {
                System.out.print("Digite a nova descricao (atual: " + categoriaExistente.getDescricao() + "): ");
                String descricao = scanner.nextLine();

                System.out.print("Digite o novo valor (atual: " + categoriaExistente.getValor() + "): ");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                categoriaExistente.setDescricao(descricao);
                categoriaExistente.setValor(valor);

                List<Categoria> categorias = listar();
                for (int i = 0; i < categorias.size(); i++) {
                    if (categorias.get(i).getCodigo().equals(categoriaExistente.getCodigo())) {
                        categorias.set(i, categoriaExistente);
                        break;
                    }
                }
                // salvarEmArquivo(categorias);
                System.err.println("Categoria editada com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar categoria: " + e.getMessage());
        }
    }

    @Override
    public void deletar() {
        System.out.print("digite o codigo: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Categoria categoriaExistente = consultarPorCodigo(codigo);
        if (categoriaExistente == null) {
            System.out.print("Categoria não encontrada");
        } else {
            List<Categoria> categorias = listar();
            for (int i = 0; i < categorias.size(); i++) {
                if (categorias.get(i).getCodigo().equals(categoriaExistente.getCodigo())) {
                    categorias.remove(i);
                    break;
                }
            }
           salvarEmArquivo(categorias);
            System.out.print("Categoria deletada com sucesso!");
        }
    }

    @Override
    public List<Categoria> listar() {
        List<Categoria> categorias = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return categorias;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    Categoria categoria = new Categoria(dados[0], dados[1], Double.valueOf(dados[2]));
                    categorias.add(categoria);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return categorias;
    }

    private boolean salvarEmArquivo(List<Categoria> categorias) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
           for (Categoria categoria : categorias) {
                writer.write(categoria.getCodigo() + "," + categoria.getDescricao() + "," + categoria.getValor());
                writer.newLine();
           }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}