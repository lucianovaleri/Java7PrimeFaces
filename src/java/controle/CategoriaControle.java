/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controle;

import dao.CategoriaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Categoria;


/**
 *
 * @author USER
 */

    @ManagedBean
@SessionScoped
public class CategoriaControle {

    private List<Categoria> categorias = new ArrayList<Categoria>();
    private Categoria categoria = new Categoria();
    private boolean salvar = false;
    private int idCategoria = 0;

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }
    
    

    @PostConstruct
    public void atualizaCategorias() {
        try {
            categorias = CategoriaDAO.getLista();
            for(Categoria s:categorias){
                System.out.println(s.getNomeCategoria());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        categoria = new Categoria();        
    }

    public void preparaAlterar() {
        salvar = false;        
    }

    public void salvar() {
         if (salvar) {
            try {
                CategoriaDAO.inserir(categoria);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                CategoriaDAO.alterar(categoria);
            } catch (SQLException e) {
                e.printStackTrace();
            }
         }
         atualizaCategorias();
    }

    public void excluir() {
        try {
            CategoriaDAO.excluir(categoria);
            atualizaCategorias();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }


}
