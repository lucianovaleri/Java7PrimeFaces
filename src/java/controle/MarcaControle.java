package controle;

import dao.MarcaDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import modelo.Marca;

@ManagedBean
@SessionScoped
public class MarcaControle {

    private List<Marca> marcas = new ArrayList<Marca>();
    private Marca marca = new Marca();
    private boolean salvar = false;
    
    

    @PostConstruct
    public void atualizaMarcas() {
        try {
            marcas = MarcaDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        marca = new Marca();
    }

    public void preparaAlterar() {
        salvar = false;
    }

    public void salvar() {
        if (salvar) {
            try {
                MarcaDAO.inserir(marca);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                MarcaDAO.alterar(marca);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        atualizaMarcas();
    }

    public void excluir() {
        try {
            MarcaDAO.excluir(marca);
            atualizaMarcas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Marca> getMarcas() {
        return marcas;
    }

    public void setMarcas(List<Marca> marcas) {
        this.marcas = marcas;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }


}
