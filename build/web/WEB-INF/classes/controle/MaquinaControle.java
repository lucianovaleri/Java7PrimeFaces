package controle;

import dao.CategoriaDAO;
import dao.MaquinaDAO;
import controle.UsuarioControle;
import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Marca;
import modelo.Maquina;
import modelo.Categoria;
import modelo.Usuario;
import util.SessionContext;

@ManagedBean
@SessionScoped
public class MaquinaControle {

    private int filtro = 1;
    private int valor1 = 0;
    private int valor2 = 0;
    private List<Maquina> maquinas = new ArrayList<Maquina>();
    private Maquina maquina = new Maquina();
    private Categoria categoria = new Categoria();
    private boolean salvar = false;
    private List<Maquina> maquinasAguardando = new ArrayList<Maquina>();
    private List<Maquina> maquinasCidade = new ArrayList<Maquina>();
    private List<Maquina> maquinasInativo = new ArrayList<Maquina>();
    private int idMaquina = 0;
    private int idUsuario = 0;
    private List<Maquina> minhasmaquinas = new ArrayList<Maquina>();
    private List<Maquina> filtromaquinas = new ArrayList<Maquina>();
    private List<Maquina> maquinasNaoAtivo = new ArrayList<Maquina>();    
    private List<Maquina> filtromaqAnunciante = new ArrayList<Maquina>();    
    private int idMarca = 0;
    private int idCategoria = 0;
    private String nomeCategoria = "%";
    private Usuario usuarioLogado;
    private String status = "%" ;
    private List<Maquina> relatorioMaquinaQtde = new ArrayList<Maquina>();
    private List<Maquina> relatorioMaqMar = new ArrayList<Maquina>();
    private List<Maquina> relatorioMaqAnun = new ArrayList<Maquina>();
    private Marca marca;

    @PostConstruct
    public void atualizaMaquinas() {
        System.out.println("Passou aqui");
        try {
            maquinas = MaquinaDAO.getLista();
            filtromaquinas = MaquinaDAO.getLista();
            maquinasNaoAtivo = MaquinaDAO.getListaMaqNaoAtiva();
            
            for (Maquina m : maquinas) {
                System.out.println("m " + m.getIdMaquina());
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        buscarMinhasMaquinas();
        filtromaquinaqtde();
        filtromaquinaqtdeAnunciante();
    }
    
    public void zerarMaquina(){
        maquina = new Maquina();
    }

    public void buscarMinhasMaquinas() {
        try {
            minhasmaquinas = new ArrayList();

            usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
            idUsuario = usuarioLogado.getIdUsuario();

            minhasmaquinas = MaquinaDAO.getMinhaLista(idUsuario);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void buscarFiltroMaquinas() {
       
            try {
                if(valor1 == 0 && valor2 == 0){
                    filtromaquinas = MaquinaDAO.getFiltro(nomeCategoria);

                }else{
                                 
                filtromaquinas = MaquinaDAO.getFiltroValor(nomeCategoria,valor1,valor2);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } 

        

    

    public String buscarMaquinas(int filtro) {
        try {
////            this.filtro = filtro;
            maquinas = new ArrayList();
//            maquinas = MaquinaDAO.getListabuscar(this.filtro);
            maquinas = MaquinaDAO.getListaFiltro(filtro);
            System.out.println("ffff " + filtro);
            return "/faces/telaInicial.xhtml?faces-redirect=true";

        } catch (SQLException e) {
            e.printStackTrace();
            return "/faces/telaInicial.xhtml?faces-redirect=true";
        }

    }

    public int getIdMarca() {
        return idMarca;
    }

    public void setIdMarca(int idMarca) {
        this.idMarca = idMarca;
    }

    public int getIdCategoria() {
        return idCategoria;
    }

    public void setIdCategoria(int idCategoria) {
        this.idCategoria = idCategoria;
    }

    public void atualizaMaquinasAguardando() {
        try {
            maquinasAguardando = MaquinaDAO.getListaAguardando(status);
            maquinasNaoAtivo = MaquinaDAO.getListaAguardando(status);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void filtromaquinaqtde() {
        try {
            relatorioMaquinaQtde = MaquinaDAO.getRelatorioNumeroMaqMar();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void filtromaquinaqtdeAnunciante() {
        try {
            filtromaqAnunciante = MaquinaDAO.getRelatorioNumeroMaqAnun();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void filtromaqnaoativa() {
        try {
            maquinasNaoAtivo = MaquinaDAO.getListaMaqNaoAtiva();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void filtromaquinaMarca(Marca marca) {
        System.out.println("hdfhnfvnfg "+marca.getIdMarca());
        int idMarca = marca.getIdMarca();
        try {
            relatorioMaqMar = MaquinaDAO.getRelatorioMaqMar(idMarca);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void filtromaquinaAnunciante(Usuario usuario) {
        System.out.println("hdfhnfvnfg "+usuario.getIdUsuario());
        int idUsuario = usuario.getIdUsuario();
        try {
            relatorioMaqAnun = MaquinaDAO.getRelatorioMaqAnun(idUsuario);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void atualizaMaquinasCidade() {
        try {
            maquinasAguardando = MaquinaDAO.getListaCidade();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void atualizaMaquinasInativo() {
        try {
            maquinasInativo = MaquinaDAO.getListaInativos();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void ativarMaquinas() {
        try {
            MaquinaDAO.alterarAtivo(maquina);
            System.out.println("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
            atualizaMaquinasAguardando();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void desativarMaquinas() {
        try {
            MaquinaDAO.alterarInativo(maquina);
            atualizaMaquinasAguardando();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public void venderMaquinas() {
        try {
            MaquinaDAO.alterarVendido(maquina);
            buscarMinhasMaquinas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    

    public void preparaIncluir() {
        System.out.println("Setou");
        salvar = true;
        maquina = new Maquina();
        idMarca = 0;
        idCategoria = 0;
    }

    public void preparaAlterar() {
        salvar = false;
        idMarca = maquina.getMarca().getIdMarca();
        idCategoria = maquina.getCategoria().getIdCategoria();
    }

    public void testar() {
        System.out.println("=============");
    }

    public String incluir() throws SQLException {
        Marca marca = new Marca();
        marca.setIdMarca(idMarca);
        maquina.setMarca(marca);

        usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
        maquina.setUsuario(usuarioLogado);
        
        Categoria categoria1 = new Categoria();
        categoria1.setIdCategoria(idCategoria);
        maquina.setCategoria(categoria1);

        try {
            String teste = maquina.getUsuario().getNome();
            System.out.println("Nome do usuário:" + teste);
            MaquinaDAO.inserir(maquina);
            return "/faces/telaInicialUsuario.xhtml";

        } catch (SQLException e) {
            e.printStackTrace();
        }
            return "/faces/telaInicialUsuario.xhtml";


    }


    public void salvar() {
        Marca marca = new Marca();
        marca.setIdMarca(idMarca);
        maquina.setMarca(marca);

        usuarioLogado = SessionContext.getInstance().getUsuarioLogado();
        maquina.setUsuario(usuarioLogado);

        Categoria categoria1 = new Categoria();
        categoria1.setIdCategoria(idCategoria);
        maquina.setCategoria(categoria1);

        if (salvar) {
            try {
                String teste = maquina.getUsuario().getNome();
                System.out.println("Nome do usuário:" + teste);
                MaquinaDAO.inserir(maquina);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                MaquinaDAO.alterar(maquina);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        atualizaMaquinas();
    }

    public void excluir() {
        try {
            MaquinaDAO.excluir(maquina);
            atualizaMaquinas();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Maquina> getMaquinas() {
        return maquinas;
    }

    public void setMaquinas(List<Maquina> maquinas) {
        this.maquinas = maquinas;
    }

    public Maquina getMaquina() {
        return maquina;
    }

    public void setMaquina(Maquina maquina) {
        this.maquina = maquina;
    }

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public int getFiltro() {
        return filtro;
    }

    public void setFiltro(int filtro) {
        this.filtro = filtro;
    }

    public List<Maquina> getMaquinasAguardando() {
        return maquinasAguardando;
    }

    public void setMaquinasAguardando(List<Maquina> maquinasAguardando) {
        this.maquinasAguardando = maquinasAguardando;
    }

    public int getIdMaquina() {
        return idMaquina;
    }

    public void setIdMaquina(int idMaquina) {
        this.idMaquina = idMaquina;
    }

    public List<Maquina> getMaquinasInativo() {
        return maquinasInativo;
    }

    public void setMaquinasInativo(List<Maquina> maquinasInativo) {
        this.maquinasInativo = maquinasInativo;
    }

    public List<Maquina> getMaquinasCidade() {
        return maquinasCidade;
    }

    public void setMaquinasCidade(List<Maquina> maquinasCidade) {
        this.maquinasCidade = maquinasCidade;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public List<Maquina> getMinhasmaquinas() {
        return minhasmaquinas;
    }

    public void setMinhasmaquinas(List<Maquina> minhasmaquinas) {
        this.minhasmaquinas = minhasmaquinas;
    }

    public List<Maquina> getFiltromaquinas() {
        return filtromaquinas;
    }

    public void setFiltromaquinas(List<Maquina> filtromaquinas) {
        this.filtromaquinas = filtromaquinas;
    }

    public int getValor1() {
        return valor1;
    }

    public void setValor1(int valor1) {
        this.valor1 = valor1;
    }

    public int getValor2() {
        return valor2;
    }

    public void setValor2(int valor2) {
        this.valor2 = valor2;
    }

    public String getNomeCategoria() {
        return nomeCategoria;
    }

    public void setNomeCategoria(String nomeCategoria) {
        this.nomeCategoria = nomeCategoria;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Maquina> getRelatorioMaquinaQtde() {
        return relatorioMaquinaQtde;
    }

    public void setRelatorioMaquinaQtde(List<Maquina> relatorioMaquinaQtde) {
        this.relatorioMaquinaQtde = relatorioMaquinaQtde;
    }

    public List<Maquina> getRelatorioMaqMar() {
        return relatorioMaqMar;
    }

    public void setRelatorioMaqMar(List<Maquina> relatorioMaqMar) {
        this.relatorioMaqMar = relatorioMaqMar;
    }

    public Marca getMarca() {
        return marca;
    }

    public void setMarca(Marca marca) {
        this.marca = marca;
    }

    public List<Maquina> getMaquinasNaoAtivo() {
        return maquinasNaoAtivo;
    }

    public void setMaquinasNaoAtivo(List<Maquina> maquinasNaoAtivo) {
        this.maquinasNaoAtivo = maquinasNaoAtivo;
    }

    public List<Maquina> getFiltromaqAnunciante() {
        return filtromaqAnunciante;
    }

    public void setFiltromaqAnunciante(List<Maquina> filtromaqAnunciante) {
        this.filtromaqAnunciante = filtromaqAnunciante;
    }

    public List<Maquina> getRelatorioMaqAnun() {
        return relatorioMaqAnun;
    }

    public void setRelatorioMaqAnun(List<Maquina> relatorioMaqAnun) {
        this.relatorioMaqAnun = relatorioMaqAnun;
    }

    
}
