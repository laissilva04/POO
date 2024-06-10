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
import models.Quarto;

public class QuartoDao implements Dao<Quarto> {
    private final String nomeDoArquivo = "src/txt/quartos.txt";
    Scanner scanner = new Scanner(System.in);
    CategoriaDao categoriaDao = new CategoriaDao();

    @Override
    public void cadastrar() {

        System.out.print("Digite o codigo: ");
        String codigo = scanner.nextLine();

        Categoria categoria = null;
        while (categoria == null) {
            System.out.println("Categorias disponiveis: ");

            List<Categoria> categorias = categoriaDao.listar();

            if (categorias.isEmpty()) {
                System.out.println("Nenhuma categoria cadastrada");
            } else {
                for (Categoria cat : categorias) {
                    System.out.println(cat);
                }
            }
            System.out.print("Digite o codigo da categoria: ");
            String categoriaCodigo = scanner.nextLine();

            categoria = categoriaDao.consultarPorCodigo(categoriaCodigo);

            if (categoria == null) {
                System.out.println("Categoria não encontrada, por favor digite uma categoria valida");
            }
        }

        System.out.print("Digite o status: ");
        String status = scanner.nextLine();

        scanner.nextLine();

        Quarto quarto = new Quarto(codigo, categoria, status);

        boolean validacao = true;

        Quarto quartoExistente = consultarPorCodigo(quarto.getCodigo());
        if (quartoExistente != null) {
            System.out.println("Quarto ja cadastrada com esse codigo");
            validacao = false;
        }

        if (validacao == true) {
            try {
                List<Quarto> quartos = listar();

                quartos.add(quarto);

                boolean sucesso = salvarEmArquivo(quartos);
                if (sucesso) {
                    System.out.println("Quarto cadastrada com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Error ao cadastrar quarto: " + e.getMessage());
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
            List<Quarto> quartos = listar();
            for (Quarto quarto : quartos) {
                if (quarto.getCodigo().equals(codigo)) {
                    System.out.println(quarto);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar quarto: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Quarto não encontrada");
        }
    }

    public Quarto consultarPorCodigo(String codigo) {
        try {
            List<Quarto> quartos = listar();
            for (Quarto quarto : quartos) {
                if (quarto.getCodigo().equals(codigo)) {
                    return quarto;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar quarto: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void editar() {

        System.out.print("Insira o codigo do quarto a ser editado: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Quarto quartoExistente = consultarPorCodigo(codigo);
        try {
            if (quartoExistente == null) {
                System.out.print("Quarto não encontrada");
            } else {

                Categoria categoria = null;

                while (categoria == null) {
                    System.out.println("Categorias disponiveis: ");
                    List<Categoria> categorias = categoriaDao.listar();

                    if (categorias.isEmpty()) {
                        System.out.println("Nenhuma categoria cadastrada");
                    } else {
                        for (Categoria cat : categorias) {
                            System.out.println(cat);
                        }
                    }
                    System.out.print("Digite o codigo da categoria (atual: " + quartoExistente.getCategoria() + "): ");
                    String categoriaCodigo = scanner.nextLine();

                    categoria = categoriaDao.consultarPorCodigo(categoriaCodigo);

                    if (categoria == null) {
                        System.out.println("Categoria não encontrada, por favor digite uma categoria valida");
                    }
                }

                System.out.print("Digite o novo status (atual: " + quartoExistente.getStatus() + "): ");
                String status = scanner.nextLine();
                scanner.nextLine();

                quartoExistente.setCategoria(categoria);
                quartoExistente.setStatus(status);

                List<Quarto> quartos = listar();
                for (int i = 0; i < quartos.size(); i++) {
                    if (quartos.get(i).getCodigo().equals(quartoExistente.getCodigo())) {
                        quartos.set(i, quartoExistente);
                        break;
                    }
                }
                salvarEmArquivo(quartos);
                System.err.println("Quarto editado com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar quarto: " + e.getMessage());
        }
    }

    @Override
    public void deletar() {

        System.out.print("digite o codigo: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Quarto quartoExistente = consultarPorCodigo(codigo);
        if (quartoExistente == null) {
            System.out.print("Quarto não encontrada");
        } else {
            List<Quarto> quartos = listar();
            for (int i = 0; i < quartos.size(); i++) {
                if (quartos.get(i).getCodigo().equals(quartoExistente.getCodigo())) {
                    quartos.remove(i);
                    break;
                }
            }
            salvarEmArquivo(quartos);
            System.out.print("Quarto deletada com sucesso!");
        }
    }

    @Override
    public List<Quarto> listar() {

        List<Quarto> quartos = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return quartos;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    Quarto quarto = new Quarto(dados[0], categoriaDao.consultarPorCodigo(dados[1]), dados[2]);
                    quartos.add(quarto);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return quartos;
    }

    private boolean salvarEmArquivo(List<Quarto> quartos) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
            for (Quarto quarto : quartos) {
                writer.write(quarto.getCodigo() + "," + quarto.getCategoria().getCodigo() + "," + quarto.getStatus());
                writer.newLine();
            }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
