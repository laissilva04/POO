package dao;
import java.util.List;

public interface Dao<T> {
    void cadastrar();
    void consultar();
    void editar();
    void deletar();
    List<T> listar();
}

