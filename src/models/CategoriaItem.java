package models;

public class CategoriaItem extends Entidade{
    
    private Item item;
    private Categoria categoria;
    private int quantidade;

    public CategoriaItem(String id, Item item, Categoria categoria, int quantidade){
        super(id);
        this.item = item;
        this.categoria = categoria;
        this.quantidade = quantidade;
    }
    public Categoria getCategoria() {
        return categoria;
    }
    public Item getItem() {
        return item;
    }
    public int getQuantidade() {
        return quantidade;
    }
    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }
    public void setItem(Item item) {
        this.item = item;
    }
    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
    @Override
    public String toString(){
        return "CategoriaItem{id='"+ getId()+"', categoria='"+getCategoria().toString() +"', item='"+getItem().toString()+"', quantidade='"+ getQuantidade()+"'}";
    }
}
