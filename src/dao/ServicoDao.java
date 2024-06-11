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

import models.Servico;

public class ServicoDao implements Dao<Servico>{
    private final String nomeDoArquivo = "src/txt/servicos.txt";
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

        Servico servico = new Servico(codigo, descricao, valor);
        
        boolean validacao = true;
        Servico servicoExistente = consultarPorDescricao(servico.getDescricao());
        if (servicoExistente != null) {
            System.out.println("Servico ja cadastrada com essa descricao");
            validacao = false;
        }

        servicoExistente = consultarPorCodigo(servico.getCodigo());
        if (servicoExistente != null) {
            System.out.println("Servico ja cadastrada com esse codigo");
            validacao = false;
        }

        if (validacao == true) {
            try {
                List<Servico> servicos = listar();

                servicos.add(servico);

                boolean sucesso = salvarEmArquivo(servicos);
                if (sucesso) {
                    System.out.println("Servico cadastrada com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Error ao cadastrar servico: " + e.getMessage());
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
            List<Servico> servicos = listar();
            for (Servico servico : servicos) {
                if (servico.getCodigo().equals(codigo)) {
                    System.out.println(servico);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar servico: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Servico não encontrada");
        }
    }

    public Servico consultarPorDescricao(String descricao) {
        try {
            List<Servico> servicos = listar();
            for (Servico c : servicos) {
                if (c.getDescricao().equals(descricao)) {
                    return c;
                }
            }
        } catch (Exception e) {
            System.out.println("Ocorreu um erro ao consultar servico: " + e.getMessage());
        }
        return null;
    }

    public Servico consultarPorCodigo(String codigo) {
        try {
            List<Servico> servicos = listar();
            for (Servico servico : servicos) {
                if (servico.getCodigo().equals(codigo)) {
                    return servico;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar servico: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void editar() {
        System.out.print("Insira o codigo do servico a ser editada: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Servico servicoExistente = consultarPorCodigo(codigo);
        try {
            if (servicoExistente == null) {
                System.out.print("Servico não encontrada");
            } else {
                System.out.print("Digite a nova descricao (atual: " + servicoExistente.getDescricao() + "): ");
                String descricao = scanner.nextLine();

                System.out.print("Digite o novo valor (atual: " + servicoExistente.getValor() + "): ");
                double valor = scanner.nextDouble();
                scanner.nextLine();

                servicoExistente.setDescricao(descricao);
                servicoExistente.setValor(valor);

                List<Servico> servicos = listar();
                for (int i = 0; i < servicos.size(); i++) {
                    if (servicos.get(i).getCodigo().equals(servicoExistente.getCodigo())) {
                        servicos.set(i, servicoExistente);
                        break;
                    }
                }
                 salvarEmArquivo(servicos);
                System.err.println("Servico editado com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar servico: " + e.getMessage());
        }
    }

    @Override
    public void deletar() {
        throw new UnsupportedOperationException("Unimplemented method 'deletar'");
    }

    @Override
    public List<Servico> listar() {
        List<Servico> servicos = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return servicos;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 3) {
                    Servico servico = new Servico(dados[0], dados[1], Double.valueOf(dados[2]));
                    servicos.add(servico);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return servicos;
    }

    private boolean salvarEmArquivo(List<Servico> servicos) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
           for (Servico servico : servicos) {
                writer.write(servico.getCodigo() + "," + servico.getDescricao() + "," + servico.getValor());
                writer.newLine();
           }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
