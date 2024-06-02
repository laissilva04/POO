import java.util.List;
import java.util.Scanner;

import dao.CategoriaDao;
import models.Categoria;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoriaDao categoriaDao = new CategoriaDao();

    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            executarOpcao(opcao);
        } while (opcao != 2);
    }

    private static void exibirMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Categorias");
        System.out.println("2. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                executarCategoria();
                break;
            case 2:
                System.out.println("Saindo...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarCategoria() {
        int opcao;
        do {
            exibirMenuCategoria();
            opcao = scanner.nextInt();
            executarOpcaoCategoria(opcao);

        } while (opcao != 6);
    }

    private static void exibirMenuCategoria() {

        System.out.println("\n--- Menu Categoria ---");
        System.out.println("1. Cadastrar categoria");
        System.out.println("2. Listar categorias");
        System.err.println("3. Editar categoria");
        System.out.println("4. Consultar categoria");
        System.out.println("5. Excluir categoria");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");

    }

    private static void executarOpcaoCategoria(int opcao) {
        switch (opcao) {
            case 1:
                 categoriaDao.cadastrar();
                break;
            case 2:
              List<Categoria> categorias = categoriaDao.listar();
              if(categorias.isEmpty()){
                System.out.println("Nenhuma categoria cadastrada");
              } else {
                for(Categoria categoria : categorias){
                    System.out.println(categoria);
                }
              }
                break;
            case 3: 
                categoriaDao.editar();
                break;
            case 4: 
                categoriaDao.consultar();
                break;
            case 5:
                categoriaDao.deletar();
                break;
            case 6:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
}
