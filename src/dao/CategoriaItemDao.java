package dao;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import models.Categoria;
import models.CategoriaItem;
import models.Pessoa;



public class CategoriaItemDao implements Dao<CategoriaItem>{
    private final String fileName = "src/txt/categoriasItem.txt";

       @Override
    public void cadastrar(CategoriaItem t)  {
        List<CategoriaItem> itens = listar(); 
        itens.add(t); 
        salvarEmArquivo(itens);
    }
    

    @Override
    public CategoriaItem consultar(String id)  {
        throw new UnsupportedOperationException("Unimplemented method 'consultar'");
    }

    @Override
    public void editar(CategoriaItem t)  {
        throw new UnsupportedOperationException("Unimplemented method 'editar'");
    }

    @Override
    public void deletar(String id)  {
        throw new UnsupportedOperationException("Unimplemented method 'deletar'");
    }

    @Override
    public List<CategoriaItem> listar()  {
        throw new UnsupportedOperationException("Unimplemented method 'listar'");
    }


     private void salvarEmArquivo(List<CategoriaItem> categoriaItens)  {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            for (CategoriaItem categoria : categoriaItens) {
                writer.write(categoria.getId() + "," + categoria.getCategoria() + "," + categoria.getItem() + "," + categoria.getQuantidade());
                writer.newLine();
            }
        } catch (IOException e) {
            throw new Exception("Erro ao salvar arquivo!", e);
        }
    }
    
}
