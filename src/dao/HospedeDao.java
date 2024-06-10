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

import models.Hospede;

public class HospedeDao implements Dao<Hospede>{
    private final String nomeDoArquivo = "src/txt/hospedes.txt";
    Scanner scanner = new Scanner(System.in);

    @Override
    public void cadastrar() {

        System.out.print("Digite o cpf: ");
        String codigo = scanner.nextLine();

        System.out.print("Digite o nome: ");
        String nome = scanner.nextLine();

        System.out.print("Digite o email: ");
        String email = scanner.nextLine();

        System.out.print("Digite o endereço: ");
        String endereco = scanner.nextLine();

        scanner.nextLine();

        Hospede hospede = new Hospede(codigo, nome, email, endereco);
        
        boolean validacao = true;
        Hospede hospedeExistente = consultarPorCpf(hospede.getId());
        if (hospedeExistente != null) {
            System.out.println("hospede ja cadastrada com esse CPF");
            validacao = false;
        }

        hospedeExistente = consultarPorEmail(hospede.getEmail());
        if (hospedeExistente != null) {
            System.out.println("Hospede ja cadastrada com esse Email");
            validacao = false;
        }

        if (validacao == true) {
            try {
                List<Hospede> hospedes = listar();

                hospedes.add(hospede);

                boolean sucesso = salvarEmArquivo(hospedes);
                if (sucesso) {
                    System.out.println("Hospede cadastrada com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Error ao cadastrar Hospede: " + e.getMessage());
            }
        }
    }


    @Override
    public void consultar() {
        System.out.print("Digite o cpf: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        boolean encontrou = false;

        try {
            List<Hospede> hospedes = listar();
            for (Hospede hospede : hospedes) {
                if (hospede.getId().equals(codigo)) {
                    System.out.println(hospede);
                    encontrou = true;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar Hospede: " + e.getMessage());
        }
        if (!encontrou) {
            System.err.println("Hospede não encontrada");
        }
    }

    public Hospede consultarPorCpf(String codigo) {
         try {
                List<Hospede> hospedes = listar();
                for (Hospede hospede : hospedes) {
                    if (hospede.getId().equals(codigo)) {
                        return hospede;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro ao consultar hospede: " + e.getMessage());
            }
            return null;   
    }

    public Hospede consultarPorEmail(String email) {
        try {
            List<Hospede> hospedes = listar();
            for (Hospede hospede : hospedes) {
                if (hospede.getId().equals(email)) {
                    return hospede;
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar Hospede: " + e.getMessage());
        }
        return null;
    }


    @Override
    public void editar() {
        System.out.print("Insira o cpf do Hospede a ser editado: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Hospede hospedeExistente = consultarPorCpf(codigo);
        try {
            if (hospedeExistente == null) {
                System.out.print("Hospede não encontrada");
            } else {
                System.out.print("Digite o novo email (atual: " + hospedeExistente.getEmail() + "): ");
                String email = scanner.nextLine();

                System.out.print("Digite o novo endereço (atual: " + hospedeExistente.getEnderecoCompleto() + "): ");
                String endereco = scanner.nextLine();
                scanner.nextLine();

                hospedeExistente.setEmail(email);
                hospedeExistente.setEnderecoCompleto(endereco);

                List<Hospede> hospedes = listar();
                for (int i = 0; i < hospedes.size(); i++) {
                    if (hospedes.get(i).getId().equals(hospedeExistente.getId())) {
                        hospedes.set(i, hospedeExistente);
                        break;
                    }
                }
                 salvarEmArquivo(hospedes);
                System.err.println("Hospede editada com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar hospede: " + e.getMessage());
        }
    }

    @Override
    public void deletar() {
        System.out.print("digite o cpf: ");
        String codigo = scanner.nextLine();
        scanner.nextLine();

        Hospede hospedeExistente = consultarPorCpf(codigo);
        if (hospedeExistente == null) {
            System.out.print("Hospede não encontrada");
        } else {
            List<Hospede> hospedes = listar();
            for (int i = 0; i < hospedes.size(); i++) {
                if (hospedes.get(i).getId().equals(hospedeExistente.getId())) {
                    hospedes.remove(i);
                    break;
                }
            }
           salvarEmArquivo(hospedes);
            System.out.print("Hospede deletado com sucesso!");
        }
    }

    @Override
    public List<Hospede> listar() {

        List<Hospede> hospedes = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return hospedes;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    Hospede hospede = new Hospede(dados[0], dados[1], dados[2], dados[3]);
                   hospedes.add(hospede);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return hospedes;
    }

    private boolean salvarEmArquivo(List<Hospede> hospedes) {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
           for (Hospede hospede : hospedes) {
                writer.write(hospede.getId() + "," + hospede.getNome() + "," + hospede.getEmail() + "," + hospede.getEnderecoCompleto());
                writer.newLine();
           }
        } catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
