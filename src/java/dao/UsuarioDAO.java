/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Usuario;
import util.Conexao;

public class UsuarioDAO {

    public static void inserir(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into usuario (nome, telefone, documento, email,tipoUsuario, dataNasc, senha, local) values (?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getTelefone());
        stmt.setString(3, usuario.getDocumento());
        stmt.setString(4, usuario.getEmail());
        stmt.setString(5, "comum");
        stmt.setString(6, usuario.getDataNasc());
        stmt.setString(7, usuario.getSenha());
        stmt.setString(8, usuario.getLocal());
        
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update usuario set nome =?, telefone =?, documento =?, email =?,tipoUsuario=?, dataNasc =?, senha =?, local =? where idUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, usuario.getNome());
        stmt.setString(2, usuario.getTelefone());
        stmt.setString(3, usuario.getDocumento());
        stmt.setString(4, usuario.getEmail());
        stmt.setString(5, usuario.getTipoUsuario());
        stmt.setString(6, usuario.getDataNasc());
        stmt.setString(7, usuario.getSenha());
        stmt.setString(8, usuario.getLocal());                
        stmt.setInt(9, usuario.getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Usuario usuario) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM usuario WHERE  idUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, usuario.getIdUsuario());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Usuario> getLista() throws SQLException {
        List<Usuario> lista = new ArrayList<Usuario>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM usuario ORDER BY nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setDocumento(rs.getString("documento"));
            usuario.setEmail(rs.getString("email"));
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));
            usuario.setDataNasc(rs.getString("dataNasc"));
            usuario.setSenha(rs.getString("senha"));
            usuario.setLocal(rs.getString("local"));
                        
            lista.add(usuario);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }
    
    public static Usuario getUsuarioLogado(Usuario us) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "SELECT * FROM usuario us WHERE us.email = ? and us.senha = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, us.getEmail());
        stmt.setString(2, us.getSenha());
        ResultSet result = stmt.executeQuery();

        Usuario usuario = null;
        if (result.next()) {
            usuario = new Usuario();            
            usuario.setIdUsuario(result.getInt("idUsuario"));
            usuario.setNome(result.getString("nome"));
            usuario.setTelefone(result.getString("telefone"));
            usuario.setDocumento(result.getString("documento"));
            usuario.setEmail(result.getString("email"));
            usuario.setTipoUsuario(result.getString("tipoUsuario"));
            usuario.setDataNasc(result.getString("dataNasc"));
            usuario.setSenha(result.getString("senha"));
            usuario.setLocal(result.getString("local"));
        } else {
            usuario = null;
        }
        result.close();
        stmt.close();
        con.close();
        return usuario;
    }

    // *******VERIFICA A SENHA DO USUÁRIO LOGADO NO SISTEMA*******
    public static boolean verificaSenhaAtual(Usuario u, String senhaAtual) throws SQLException {
        int i;
        boolean verifica;
        Connection con = Conexao.getConnection();
        String sql = "SELECT \n"
                + "	count(*)\n"
                + "FROM \n"
                + "	usuario "
                + "WHERE \n"
                + "     senhaUsuario = PASSWORD(MD5(?)) \n"
                + "     AND emailUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, senhaAtual);
        stmt.setString(2, u.getEmail());
        ResultSet rs = stmt.executeQuery();

        //LogDAO.inserir(new LogBean(stmt.toString()));
        Usuario usuario = null;

        rs.first();
        i = rs.getInt("count(*)");

        rs.close();
        stmt.close();
        con.close();

        if (i != 1) {
            verifica = false;
        } else {
            verifica = true;
        }
        rs.close();
        stmt.close();
        con.close();

        return verifica;
    }

    // *******ALTERA A SENHA DO USUÁRIO LOGADO NO SISTEMA*******
    public static void alterarSenha(Usuario u) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql = "UPDATE usuario SET senhaUsuario = PASSWORD(MD5(?)) WHERE emailUsuario = ? AND idUsuario = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, u.getSenha());
        stmt.setString(2, u.getEmail());
        stmt.setInt(3, u.getIdUsuario());
        stmt.executeUpdate();
        //LogDAO.inserir(new LogBean(stmt.toString()));
        stmt.close();
        con.close();
    }

    
    
}