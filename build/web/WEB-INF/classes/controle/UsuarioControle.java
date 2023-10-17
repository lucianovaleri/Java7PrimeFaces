package controle;

import dao.UsuarioDAO;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import modelo.Usuario;
import util.SessionContext;

@ManagedBean
@SessionScoped
public class UsuarioControle {

    private List<Usuario> usuarios = new ArrayList<Usuario>();
    private Usuario usuario = new Usuario();
    private boolean salvar = false;
    private Usuario usuarioLogado;
    private String novaSenha;
    private String senhaAtual;

    @PostConstruct
    public void atualizaUsuarios() {
        try {
            usuarios = UsuarioDAO.getLista();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void preparaIncluir() {
        salvar = true;
        usuario = new Usuario();
    }

    public void preparaAlterar() {
        salvar = false;
    }
    
    public void zerarUsuario(){
        usuario = new Usuario();
    }

    public String incluir() throws SQLException{
       
       System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAADEUUU");
       UsuarioDAO.inserir(usuario);
       zerarUsuario();
       return "/faces/login2.xhtml?faces-redirect=true";
    }
    
    public void salvar() {
        if (salvar) {
            try {
                UsuarioDAO.inserir(usuario);
                
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                UsuarioDAO.alterar(usuario);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        atualizaUsuarios();
    }

    public void excluir() {
        try {
            UsuarioDAO.excluir(usuario);
            atualizaUsuarios();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String login() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            usuarioLogado = UsuarioDAO.getUsuarioLogado(usuario);            
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (usuarioLogado != null) {
            if (usuarioLogado.getTipoUsuario().equals("administrador")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                System.out.println("-----------AQUI!!!---------");
                return "/faces/telaInicial.xhtml?faces-redirect=true";                
            }   else if (usuarioLogado.getTipoUsuario().equals("comum")) {
                SessionContext.getInstance().setAttribute("usuarioLogado", usuarioLogado);
                return "/faces/telaInicialUsuario.xhtml?faces-redirect=true";
            }
             else {
                usuarioLogado = null;
                doLogout();
                return "/faces/loginFeioDaPorra.xhtml?faces-redirect=true";
            }
        } else {
            usuarioLogado = null;
            usuario = new Usuario();
            doLogout();
            FacesMessage mensagem = new FacesMessage("Usuário/senha inválidos! Tente novamente.");
            mensagem.setSeverity(FacesMessage.SEVERITY_ERROR);
            context.addMessage(null, mensagem);
            return null;
        }
     }
        public String alterarUsuarioLogado() {
        try {
            UsuarioDAO.alterar(usuarioLogado);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return "/ProjetoPOO/faces/manutencaoMaquina.xhtml";
    }

    public String salvarUsuario() {
        FacesContext context = FacesContext.getCurrentInstance();
        try {
            UsuarioDAO.inserir(usuario);
            FacesMessage mensagem = new FacesMessage("Usuário cadastrado com sucesso");
            mensagem.setDetail(FacesMessage.FACES_MESSAGES);
            context.addMessage(null, mensagem);
        } catch (com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException e) {
            System.out.println("ERRO AO SALVAR NOVO USUÁRIO!!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
        usuario = new Usuario();
        return null;
    }
        
    
    public String doLogout() {
        SessionContext.getInstance().encerrarSessao();
        return "/ProjetoPOO/faces/newLogin.xhtml?faces-redirect=true";
    }
    
    public void sair(){
        String a = doLogout();
    }

    public List<Usuario> getUsuarios() {
        return usuarios;
    }

    public void setUsuarios(List<Usuario> usuarios) {
        this.usuarios = usuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioLogado() {
        return usuarioLogado;
    }

    public void setUsuarioLogado(Usuario usuarioLogado) {
        this.usuarioLogado = usuarioLogado;
    }

    public String getNovaSenha() {
        return novaSenha;
    }

    public void setNovaSenha(String novaSenha) {
        this.novaSenha = novaSenha;
    }

    public String getSenhaAtual() {
        return senhaAtual;
    }

    public void setSenhaAtual(String senhaAtual) {
        this.senhaAtual = senhaAtual;
    }


}
