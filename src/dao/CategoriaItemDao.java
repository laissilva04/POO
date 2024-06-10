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
import models.CategoriaItem;
import models.Item;


public class CategoriaItemDao implements Dao<CategoriaItem>{
    private final String nomeDoArquivo = "src/txt/categoriaItem.txt";
    Scanner scanner = new Scanner(System.in);
    CategoriaDao categoriaDao = new CategoriaDao();
    ItemDao itemDao = new ItemDao();
    
    @Override
    public void cadastrar() {

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

        System.out.print("Digite a quantidade: ");
        int quantidade = scanner.nextInt();

        scanner.nextLine();

        CategoriaItem categoriaItem = new CategoriaItem("001", item, categoria, quantidade);

        boolean validacao = true;

        CategoriaItem categoriaItemExistente = consultarPorCategoriaEItem(categoria.getCodigo(), item.getCodigo());

        if(categoriaItemExistente != null){
            validacao = false;
            System.out.println("Categoria item ja cadastrada com essa categoria e esse item");;
        }

        if (validacao == true) {
            try {
                List<CategoriaItem> categoriaItems = listar();

                categoriaItems.add(categoriaItem);

                boolean sucesso = salvarEmArquivo(categoriaItems);
                if (sucesso) {
                    System.out.println("Categoria Item cadastrada com sucesso!");
                }
            } catch (Exception e) {
                System.out.println("Error ao cadastrar Item: " + e.getMessage());
            }
        }
    }



    @Override
    public void consultar() {
        
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

        scanner.nextLine();

        boolean encontrou = false;

        try {
            List<CategoriaItem> categoriaItems = listar();
            for (CategoriaItem categoriaItem : categoriaItems) {
                if (categoriaItem.getCategoria().getCodigo().equals(categoria.getCodigo()) && categoriaItem.getItem().getCodigo().equals(item.getCodigo())) {
                    System.out.println(categoriaItem);
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

    public CategoriaItem consultarPorCategoriaEItem(String categoriaCodigo, String itemCodigo){
        try {
            List<CategoriaItem> categoriaItens = listar();
            for (CategoriaItem categoriaItem : categoriaItens) {
                if (categoriaItem.getCategoria().getCodigo().equals(categoriaCodigo) && categoriaItem.getItem().getCodigo().equals(itemCodigo)) {
                    return categoriaItem;
                    //Verifica se existe um mesmo item com a mesma categoria
                }
            }
        } catch (Exception e) {
            System.out.println("Erro ao consultar categoria Item: " + e.getMessage());
        }
        return null;
    }


    @Override
    public void editar() {

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

        scanner.nextLine();

        CategoriaItem categoriaItemExistente = consultarPorCategoriaEItem(categoria.getCodigo(), item.getCodigo());
        try {
            if (categoriaItemExistente == null) {
                System.out.print("CategoriaItem não encontrado");
            } else {

                System.out.print("Digite a nova quantidade (atual: " + categoriaItemExistente.getQuantidade() + "): ");
                int quantidade = scanner.nextInt();
                scanner.nextLine();

                categoriaItemExistente.setQuantidade(quantidade);

                List<CategoriaItem> categoriaItens = listar();
                for (int i = 0; i < categoriaItens.size(); i++) {
                    if (categoriaItens.get(i).getId().equals(categoriaItemExistente.getId())) {
                        categoriaItens.set(i, categoriaItemExistente);
                        break;
                    }
                }
                salvarEmArquivo(categoriaItens);
                System.err.println("CategoriaItem editada com sucesso!");
            }
        } catch (Exception e) {
            System.err.println("Erro ao editar categoriaItem: " + e.getMessage());
        }
    }

    @Override
    public void deletar() {

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

        scanner.nextLine();

        CategoriaItem categoriaItemExistente = consultarPorCategoriaEItem(categoria.getCodigo(), item.getCodigo());
        if (categoriaItemExistente == null) {
            System.out.print("CategoriaItem não encontrada");
        } else {
            List<CategoriaItem> categoriaItens = listar();
            for (int i = 0; i < categoriaItens.size(); i++) {
                if (categoriaItens.get(i).getId().equals(categoriaItemExistente.getId())) {
                    categoriaItens.remove(i);
                    break;
                }
            }
            salvarEmArquivo(categoriaItens);
            System.out.print("CategoriaItem deletada com sucesso!");
        }
    }



    @Override
    public List<CategoriaItem> listar() {

       List<CategoriaItem> categoriaItens = new ArrayList<>();
        File arquivo = new File(nomeDoArquivo);
        if (!arquivo.exists()) {
            return categoriaItens;
        }
        try (BufferedReader reader = new BufferedReader(new FileReader(arquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(",");
                if (dados.length == 4) {
                    CategoriaItem categoriaItem = new CategoriaItem(dados[0], itemDao.consultarPorCodigo(dados[1]), categoriaDao.consultarPorCodigo(dados[2]), Integer.valueOf(dados[3]));
                    categoriaItens.add(categoriaItem);
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo: " + e.getMessage());
        }
        return categoriaItens;
    }

    private boolean salvarEmArquivo(List<CategoriaItem> categoriaItens)  {
        boolean sucesso = true;
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeDoArquivo))) {
            for (CategoriaItem categoria : categoriaItens) {
                writer.write(categoria.getId() + "," + categoria.getItem().getCodigo() + "," + categoria.getCategoria().getCodigo() + "," + categoria.getQuantidade());
                writer.newLine();
            }
        }
         catch (IOException e) {
            sucesso = false;
            System.out.println("Erro ao salvar arquivo: " + e.getMessage());
        }
        return sucesso;
    }
}
