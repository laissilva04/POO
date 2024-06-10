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

import models.Funcionario;

public class FuncionarioDao implements Dao<Funcionario>{
    private final String nomeDoArquivo = "src/txt/funcionario.txt";
    Scanner scanner = new Scanner(System.in);

    @Override
    public void cadastrar() {

        System.out.print("Digite o CPF: ");
        String cpf = scanner.nextLine();

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite o setor: ");
        String setor = scanner.nextLine();

        scanner.nextLine();

        Funcionario funcionario = new Funcionario(cpf, nome, email, setor);
        
        boolean validacao = true;
        Funcionario funcionarioExistente = consultarPorCpf(funcionario.getCpf());
        if (funcionarioExistente != null) {
            System.out.println("Funcionario ja cadastrado com esse cpf");
            validacao = false;
        }

        funcionarioExistente = consultarPorEmail(funcionario.getEmail());
        if (funcionarioExistente != null) {
            System.out.println("Funcionario ja cadastrado come esse email");
            validacao = false;
        }

        if (validacao == true) {
            try {
                List<Funcionario> funcionarios = listar();

                funcionarios.add(funcionario);

                boolean sucesso = salvarEmArquivo(funcionarios);
                if (sucesso) {
                    System.out.println("Funcionario cadastrado com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Erro ao cadastrar funcionario: " + e.getMessage());
            }
        }
    }
    
    @Override
    public void consultar() {

        System.out.print("Digite o cpf: ");
        String cpf = scanner.nextLine();
        scanner.nextLine();

        boolean encontrou = false;

        try {
            List<Funcionario> funcionarios = listar();
            for (Funcionario funcionario : funcionarios) {
                if (funcionario.getCpf().equals(cpf)) {
                    System.out.println(funcionario);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar funcionario: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Funcionario não encontrada");
        }
    }


        public Funcionario consultarPorCpf(String cpf) {
            try {
                List<Funcionario> funcionarios = listar();
                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getCpf().equals(cpf)) {
                        return funcionario;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao consultar funcionario: " + e.getMessage());
            }
            return null;
        }

        public Funcionario consultarPorEmail(String email) {
            try {
                List<Funcionario> funcionarios = listar();
                for (Funcionario funcionario : funcionarios) {
                    if (funcionario.getCpf().equals(email)) {
                        return funcionario;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao consultar funcionario: " + e.getMessage());
            }
            return null;
        }
        
    @Override
    public void editar() {

        System.out.print("Insira o cpf do Funcionario a ser editado: ");
        String cpf = scanner.nextLine();
        scanner.nextLine();

        Funcionario funcionarioExistente = consultarPorCpf(cpf);
        try {
            if (funcionarioExistente == null) {
                System.out.print("Funcionario não encontrada");
            } else {
                System.out.print("Digite o novo email (atual: " + funcionarioExistente.getEmail() + "): ");
                String email = scanner.nextLine();

                System.out.print("Digite o novo setor (atual: " + funcionarioExistente.getSetor() + "): ");
                String setor = scanner.nextLine();
                scanner.nextLine();

                funcionarioExistente.setEmail(email);
                funcionarioExistente.setSetor(setor);

                List<Funcionario> funcionarios = listar();
                for (int i = 0; i < funcionarios.size(); i++) {
                    if (funcionarios.get(i).getCpf().equals(funcionarioExistente.getCpf())) {
                        funcionarios.set(i, funcionarioExistente);
                        break;
                    }
                }
                 salvarEmArquivo(funcionarios);
                System.err.println("Funcionario editada com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar funcionario: " + e.getMessage());
        }
    }
    @Override
    public void deletar() {

        System.out.print("digite o cpf: ");
        String cpf = scanner.nextLine();
        scanner.nextLine();

        Funcionario funcionarioExistente = consultarPorCpf(cpf);
        if (funcionarioExistente == null) {
            System.out.print("Funcionario não encontrada");
        } else {
            List<Funcionario> funcionarios = listar();
            for (int i = 0; i < funcionarios.size(); i++) {
                if (funcionarios.get(i).getCpf().equals(funcionarioExistente.getCpf())) {
                    funcionarios.remove(i);
                    break;
                }
            }
           salvarEmArquivo(funcionarios);
            System.out.print("Funcionario deletado com sucesso!");
        }
    }
    @Override
    public List<Funcionario> listar() {

         List<Funcionario> funcionarios = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return funcionarios;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    Funcionario funcionario = new Funcionario(dados[0], dados[1], dados[2], dados[3]);
                   funcionarios.add(funcionario);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return funcionarios;
    }
    
    private boolean salvarEmArquivo(List<Funcionario> funcionarios) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
           for (Funcionario funcionario : funcionarios) {
                writer.write(funcionario.getCpf() + "," + funcionario.getNome() + "," + funcionario.getEmail() + "," + funcionario.getSetor());
                writer.newLine();
           }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
