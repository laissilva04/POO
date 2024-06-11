import java.util.List;
import java.util.Scanner;

import dao.CategoriaDao;
import dao.CategoriaItemDao;
import dao.ConsumoDao;
import dao.ConsumoServicoDao;
import dao.FuncionarioDao;
import dao.HospedeDao;
import dao.ItemDao;
import dao.QuartoDao;
import dao.ReservaDao;
import dao.ServicoDao;
import models.Categoria;
import models.Funcionario;
import models.Hospede;
import models.Item;
import models.Quarto;
import models.Reserva;
import models.Servico;
import models.CategoriaItem;
import models.Consumo;
import models.ConsumoServico;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static CategoriaDao categoriaDao = new CategoriaDao();
    private static FuncionarioDao funcionarioDao = new FuncionarioDao();
    private static QuartoDao quartoDao = new QuartoDao();
    private static HospedeDao hospedeDao = new HospedeDao();
    private static CategoriaItemDao categoriaItemDao = new CategoriaItemDao();
    private static ReservaDao reservaDao = new ReservaDao();
    private static ItemDao itemDao = new ItemDao();
    private static ConsumoDao consumoDao = new ConsumoDao();
    private static ServicoDao servicoDao = new ServicoDao();
    private static ConsumoServicoDao consumoServicoDao = new ConsumoServicoDao();


    public static void main(String[] args) {
        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcao(opcao);
        } while (opcao != 6);
    }

    private static void exibirMenu() {
        System.out.println("\n--- Menu ---");
        System.out.println("1. Categorias");
        System.out.println("2. Funcionarios");
        System.out.println("3. Quartos");
        System.out.println("4. Hospedes");
        System.out.println("5. Item");
        System.out.println("6. Categoria de item");
        System.out.println("7. Reservas");
        System.out.println("8. Consumos");
        System.out.println("9. Servicos");
        System.out.println("10. Consumo de Servicos");
        System.out.println("11. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcao(int opcao) {
        switch (opcao) {
            case 1:
                executarCategoria();
                break;
            case 2:
                executarFuncionario();
                break;
            case 3:
                executarQuarto();
                break;
            case 4:
                executarHospede();
                break;
            case 5:
                executarItem();
                break;
            case 6:
                executarCategoriaItem();
                break;
            case 7:
                executarReserva();
                break;
            case 8:
                executarConsumo();
                break;
            case 9:
                executarServico();
            case 10:
                executarConsumoServico();
            case 11:
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
            scanner.nextLine();
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
                if (categorias.isEmpty()) {
                    System.out.println("Nenhuma categoria cadastrada");
                } else {
                    for (Categoria categoria : categorias) {
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

    private static void executarFuncionario() {
        int opcao;
        do {
            exibirMenuFuncionario();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoFuncionario(opcao);
        } while (opcao != 6);
    }

    private static void exibirMenuFuncionario() {
        System.out.println("\n--- Menu Funcionario ---");
        System.out.println("1. Cadastrar funcionario");
        System.out.println("2. Listar funcionarios");
        System.err.println("3. Editar funcionario");
        System.out.println("4. Consultar funcionario");
        System.out.println("5. Excluir funcionario");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoFuncionario(int opcao) {
        switch (opcao) {
            case 1:
                funcionarioDao.cadastrar();
                break;
            case 2:
                List<Funcionario> funcionarios = funcionarioDao.listar();
                if (funcionarios.isEmpty()) {
                    System.out.println("Nenhuma funcionario cadastrada");
                } else {
                    for (Funcionario funcionario : funcionarios) {
                        System.out.println(funcionario);
                    }
                }
                break;
            case 3:
                funcionarioDao.editar();
                break;
            case 4:
                funcionarioDao.consultar();
                break;
            case 5:
                funcionarioDao.deletar();
                break;
            case 6:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarQuarto() {
        int opcao;
        do {
            exibirMenuQuarto();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoQuarto(opcao);
        } while (opcao != 6);
    }

    private static void exibirMenuQuarto() {
        System.out.println("\n--- Menu Quarto ---");
        System.out.println("1. Cadastrar quarto");
        System.out.println("2. Listar quartos");
        System.err.println("3. Editar quarto");
        System.out.println("4. Consultar quarto");
        System.out.println("5. Excluir quarto");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoQuarto(int opcao) {
        switch (opcao) {
            case 1:
                quartoDao.cadastrar();
                break;
            case 2:
                List<Quarto> quartos = quartoDao.listar();
                if (quartos.isEmpty()) {
                    System.out.println("Nenhuma quarto cadastrado");
                } else {
                    for (Quarto quarto : quartos) {
                        System.out.println(quarto);
                    }
                }
                break;
            case 3:
                funcionarioDao.editar();
                break;
            case 4:
                funcionarioDao.consultar();
                break;
            case 5:
                funcionarioDao.deletar();
                break;
            case 6:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarHospede() {
        int opcao;
        do {
            exibirMenuHospede();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoHospede(opcao);
        } while (opcao != 6);
    }

    private static void exibirMenuHospede() {
        System.out.println("\n--- Menu Hospede ---");
        System.out.println("1. Cadastrar hospede");
        System.out.println("2. Listar hospedes");
        System.err.println("3. Editar hospede");
        System.out.println("4. Consultar hospede");
        System.out.println("5. Excluir hospede");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoHospede(int opcao) {
        switch (opcao) {
            case 1:
                hospedeDao.cadastrar();
                break;
            case 2:
                List<Hospede> hospedes = hospedeDao.listar();
                if (hospedes.isEmpty()) {
                    System.out.println("Nenhuma quarto cadastrado");
                } else {
                    for (Hospede hospede : hospedes) {
                        System.out.println(hospede);
                    }
                }
                break;
            case 3:
                hospedeDao.editar();
                break;
            case 4:
                hospedeDao.consultar();
                break;
            case 5:
                hospedeDao.deletar();
                break;
            case 6:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarCategoriaItem() {
        int opcao;
        do {
            exibirMenuCategoriaItem();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoCategoriaItem(opcao);
        } while (opcao != 6);
    }

    private static void exibirMenuCategoriaItem() {
        System.out.println("\n--- Menu Categoria Item ---");
        System.out.println("1. Cadastrar categoria Item");
        System.out.println("2. Listar categorias itens");
        System.err.println("3. Editar categoria item");
        System.out.println("4. Consultar categoria item");
        System.out.println("5. Excluir categoria item");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoCategoriaItem(int opcao) {
        switch (opcao) {
            case 1:
                categoriaItemDao.cadastrar();
                break;
            case 2:
                List<CategoriaItem> categoriaItens = categoriaItemDao.listar();
                if (categoriaItens.isEmpty()) {
                    System.out.println("Nenhuma categoria item cadastrada");
                } else {
                    for (CategoriaItem categoriaItem : categoriaItens) {
                        System.out.println(categoriaItem);
                    }
                }
                break;
            case 3:
                categoriaItemDao.editar();
                break;
            case 4:
                categoriaItemDao.consultar();
                break;
            case 5:
                categoriaItemDao.deletar();
                break;
            case 6:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarItem() {
        int opcao;
        do {
            exibirMenuItem();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoItem(opcao);
        } while (opcao != 6);
    }

    private static void exibirMenuItem() {
        System.out.println("\n--- Menu Item ---");
        System.out.println("1. Cadastrar Item");
        System.out.println("2. Listar itens");
        System.err.println("3. Editar item");
        System.out.println("4. Consultar item");
        System.out.println("5. Excluir item");
        System.out.println("6. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoItem(int opcao) {
        switch (opcao) {
            case 1:
                itemDao.cadastrar();
                break;
            case 2:
                List<Item> itens = itemDao.listar();
                if (itens.isEmpty()) {
                    System.out.println("Nenhum item cadastrado");
                } else {
                    for (Item item : itens) {
                        System.out.println(item);
                    }
                }
                break;
            case 3:
                itemDao.editar();
                break;
            case 4:
                itemDao.consultar();
                break;
            case 5:
                itemDao.deletar();
                break;
            case 6:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarReserva() {
        int opcao;
        do {
            exibirMenuReserva();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoReserva(opcao);
        } while (opcao != 5);
    }

    private static void exibirMenuReserva() {
        System.out.println("\n--- Menu Reserva ---");
        System.out.println("1. Cadastrar reserva");
        System.out.println("2. Listar reservas");
        System.err.println("3. Editar reserva");
        System.out.println("4. Consultar reserva");
        System.out.println("5. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoReserva(int opcao) {
        switch (opcao) {
            case 1:
                reservaDao.cadastrar();
                break;
            case 2:
                List<Reserva> reservas = reservaDao.listar();
                if (reservas.isEmpty()) {
                    System.out.println("Nenhuma reserva cadastrado");
                } else {
                    for (Reserva reserva : reservas) {
                        System.out.println(reserva);
                    }
                }
                break;
            case 3:
                reservaDao.editar();
                break;
            case 4:
                reservaDao.consultar();
                break;
            case 5:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarConsumo() {
        int opcao;
        do {
            exibirMenuConsumo();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoConsumo(opcao);
        } while (opcao != 5);
    }

    private static void exibirMenuConsumo() {
        System.out.println("\n--- Menu Consumo ---");
        System.out.println("1. Cadastrar consumo");
        System.out.println("2. Listar consumos");
        System.err.println("3. Editar consumo");
        System.out.println("4. Consultar consumo");
        System.out.println("5. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoConsumo(int opcao) {
        switch (opcao) {
            case 1:
                consumoDao.cadastrar();
                break;
            case 2:
                List<Consumo> consumos = consumoDao.listar();
                if (consumos.isEmpty()) {
                    System.out.println("Nenhum consumo cadastrado");
                } else {
                    for (Consumo consumo : consumos) {
                        System.out.println(consumo);
                    }
                }
                break;
            case 3:
                consumoDao.editar();
                break;
            case 4:
                consumoDao.consultar();
                break;
            case 5:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarServico() {
        int opcao;
        do {
            exibirMenuServico();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoServico(opcao);
        } while (opcao != 5);
    }

    private static void exibirMenuServico() {
        System.out.println("\n--- Menu Servico ---");
        System.out.println("1. Cadastrar servico");
        System.out.println("2. Listar servicos");
        System.err.println("3. Editar servico");
        System.out.println("4. Consultar servico");
        System.out.println("5. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoServico(int opcao) {
        switch (opcao) {
            case 1:
                servicoDao.cadastrar();
                break;
            case 2:
                List<Servico> servicos = servicoDao.listar();
                if (servicos.isEmpty()) {
                    System.out.println("Nenhum servico cadastrado");
                } else {
                    for (Servico servico : servicos) {
                        System.out.println(servico);
                    }
                }
                break;
            case 3:
                servicoDao.editar();
                break;
            case 4:
                servicoDao.consultar();
                break;
            case 5:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }

    private static void executarConsumoServico() {
        int opcao;
        do {
            exibirMenuConsumoServico();
            opcao = scanner.nextInt();
            scanner.nextLine();
            executarOpcaoConsumoServico(opcao);
        } while (opcao != 5);
    }

    private static void exibirMenuConsumoServico() {
        System.out.println("\n--- Menu Consumo de Servico ---");
        System.out.println("1. Cadastrar consumo de servico");
        System.out.println("2. Listar consumo de servicos");
        System.err.println("3. Editar consumo de servico");
        System.out.println("4. Consultar consumo servico");
        System.out.println("5. Voltar");
        System.out.print("Escolha uma opção: ");
    }

    private static void executarOpcaoConsumoServico(int opcao) {
        switch (opcao) {
            case 1:
                consumoServicoDao.cadastrar();
                break;
            case 2:
                List<ConsumoServico> servicos = consumoServicoDao.listar();
                if (servicos.isEmpty()) {
                    System.out.println("Nenhum servico cadastrado");
                } else {
                    for (ConsumoServico servico : servicos) {
                        System.out.println(servico);
                    }
                }
                break;
            case 3:
                consumoServicoDao.editar();
                break;
            case 4:
                consumoServicoDao.consultar();
                break;
            case 5:
                System.out.println("Voltando...");
                break;
            default:
                System.out.println("Opção inválida! Tente novamente.");
        }
    }
}