package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Categoria;
import modelo.Marca;
import modelo.Maquina;
import modelo.Usuario;
import util.Conexao;

public class MaquinaDAO {

    public static void inserir(Maquina modelo) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into modelo (descricao, horasTrabalhadas, kilometragem, motorcv, cidade, estado, valor, ano, idMarca,idCategoria,idUsuario) values (?,?,?,?,?,?,?,?,?,?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, modelo.getDescricao());
        stmt.setString(2, modelo.getHorastrabalhadas());
        stmt.setString(3, modelo.getKilometragem());
        stmt.setString(4, modelo.getMotorcv());
        stmt.setString(5, modelo.getCidade());
        stmt.setString(6, modelo.getEstado());
        stmt.setInt(7, modelo.getValor());
        stmt.setInt(8, modelo.getAno());
        stmt.setInt(9, modelo.getMarca().getIdMarca());
        stmt.setInt(10, modelo.getCategoria().getIdCategoria());
        stmt.setInt(11, modelo.getUsuario().getIdUsuario());

        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Maquina modelo) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update modelo set descricao=?, horasTrabalhadas=?, kilometragem=?, motorcv=?, cidade=?, estado=?, valor=?, ano=?, idMarca=?, idCategoria=? where idModelo = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, modelo.getDescricao());
        stmt.setString(2, modelo.getHorastrabalhadas());
        stmt.setString(3, modelo.getKilometragem());
        stmt.setString(4, modelo.getMotorcv());
        stmt.setString(5, modelo.getCidade());
        stmt.setString(6, modelo.getEstado());
        stmt.setInt(7, modelo.getValor());
        stmt.setInt(8, modelo.getAno());
        stmt.setInt(9, modelo.getMarca().getIdMarca());
        stmt.setInt(10, modelo.getCategoria().getIdCategoria());
        stmt.setInt(11, modelo.getIdMaquina());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Maquina modelo) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM modelo WHERE  idModelo = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, modelo.getIdMaquina());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterarAtivo(Maquina maquina) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update modelo set status=('ativo') where idModelo = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, maquina.getIdMaquina());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterarInativo(Maquina maquina) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update modelo set status=('inativo') where idModelo = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, maquina.getIdMaquina());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void alterarVendido(Maquina maquina) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update modelo set status=('vendido') where idModelo = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, maquina.getIdMaquina());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Maquina> getLista() throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca, usuario us where \n"
                + "mo.idMarca = ma.idMarca AND \n"
                + "ca.idCategoria = mo.idCategoria AND \n"
                + "mo.idUsuario = us.idUsuario\n"
                + "AND mo.status= ('ativo')";
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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
//            System.out.println("Nome do usuário" + modelo.getUsuario().getTipoUsuario());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getListaMaqNaoAtiva() throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca, usuario us where \n"
                + "mo.idMarca = ma.idMarca AND \n"
                + "ca.idCategoria = mo.idCategoria AND \n"
                + "mo.idUsuario = us.idUsuario\n"
                + "AND mo.status != ('ativo')";
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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
//            System.out.println("Nome do usuário" + modelo.getUsuario().getTipoUsuario());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getListabuscar(int filtro) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        System.out.println(filtro);
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca where mo.idMarca = ma.idMarca AND ca.idCategoria = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, filtro);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setCategoria(categoria);
            modelo.setMarca(marca);
            lista.add(modelo);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }

    public static List<Maquina> getListaFiltro(int filtro) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        System.out.println("dao f " + filtro);
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca where mo.idMarca = ma.idMarca and mo.idCategoria = ca.idCategoria and ca.idCategoria = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, filtro);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setCategoria(categoria);
            modelo.setMarca(marca);
            lista.add(modelo);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }

    public static List<Maquina> getListaAguardando(String status) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca\n"
                + "                 where mo.idMarca = ma.idMarca \n"
                + "                 AND ca.idCategoria = mo.idCategoria\n"
                + "                 AND mo.`status` = ('" + status + "') \n"
                + "                 GROUP BY mo.idModelo";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));
            modelo.setCategoria(categoria);
            modelo.setMarca(marca);
            System.out.println("Qualquer coisa" + modelo.getIdMaquina());
            lista.add(modelo);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }

    public static List<Maquina> getListaInativos() throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca, usuario us where \n"
                + "                mo.idMarca = ma.idMarca AND\n"
                + "                ca.idCategoria = mo.idCategoria AND\n"
                + "                mo.idUsuario = us.idUsuario\n"
                + "                AND mo.status= ('inativo')";
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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
            System.out.println(" AAAAAAAAAAAAAAAAA Nome do usuário" + modelo.getUsuario().getTipoUsuario());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getListaCidade() throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca, usuario us where\n"
                + "mo.idMarca = ma.idMarca AND\n"
                + "ca.idCategoria = mo.idCategoria AND\n"
                + "mo.idUsuario = us.idUsuario\n"
                + "AND mo.status= ('ativo')\n"
                + "order by cidade";
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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
            System.out.println("Nome do usuário" + modelo.getUsuario().getTipoUsuario());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getMinhaLista(int idUsuario) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca, usuario us where "
                + "mo.idMarca = ma.idMarca AND  "
                + "ca.idCategoria = mo.idCategoria AND  "
                + "mo.idUsuario = us.idUsuario  "
                + "AND us.idUsuario= ? ";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));
            modelo.setStatus(rs.getString("status"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getFiltro(String nomeCategoria) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM \n"
                + "					modelo mo, \n"
                + "					marca ma, \n"
                + "					categoria ca, \n"
                + "					usuario us \n"
                + "         where mo.idMarca = ma.idMarca AND  \n"
                + "			      ca.idCategoria = mo.idCategoria AND  \n"
                + "					mo.idUsuario = us.idUsuario AND \n"
                + "					ca.nomeCategoria like '%" + nomeCategoria + "%' AND \n"
                + "					mo.`status` = ('ativo') \n"
                + "					 \n"
                + "	\n"
                + "				";

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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getFiltroValor(String nomeCategoria, int valor1, int valor2) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM \n"
                + "					modelo mo, \n"
                + "					marca ma, \n"
                + "					categoria ca, \n"
                + "					usuario us \n"
                + "         where mo.idMarca = ma.idMarca AND  \n"
                + "			      ca.idCategoria = mo.idCategoria AND  \n"
                + "					mo.idUsuario = us.idUsuario AND \n"
                + "					ca.nomeCategoria like '%" + nomeCategoria + "%' AND \n"
                + "					mo.`status` = ('ativo') AND \n"
                + "					mo.valor between ? AND ? \n"
                + "	\n"
                + "				";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, valor1);
        stmt.setInt(2, valor2);
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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getRelatorioMaqMar(int idMarca) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca, usuario us where \n"
                + "                mo.idMarca = ma.idMarca AND  \n"
                + "                ca.idCategoria = mo.idCategoria AND  \n"
                + "                mo.idUsuario = us.idUsuario  \n"
                + "                AND mo.idMarca = ma.idMarca AND \n"
                + "                ma.idMarca = ? AND \n"
                + "                mo.`status` = 'ativo' ";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idMarca);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    
     public static List<Maquina> getRelatorioMaqAnun(int idUsuario) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM modelo mo, marca ma, categoria ca, usuario us where \n"
                + "                mo.idMarca = ma.idMarca AND  \n"
                + "                ca.idCategoria = mo.idCategoria AND  \n"
                + "                mo.idUsuario = us.idUsuario  \n"
                + "                AND mo.idMarca = ma.idMarca AND \n"
                + "                us.idUsuario = ? AND \n"
                + "                mo.`status` = 'ativo' ";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idUsuario);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }
    
    
    
    
    
    public static List<Maquina> getRelatorioNumeroMaqMar() throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT ma.nome, ma.paisOrigem, ma.idMarca, \n"
                + "					count(mo.idMarca) as total \n"
                + "					FROM modelo mo,\n"
                + "						  marca ma \n"
                + "					where \n"
                + "							mo.idMarca = ma.idMarca AND\n"
                + "							mo.`status` = ('ativo')\n"
                + "               GROUP BY ma.idMarca \n"
                + "					 \n"
                + "					   	\n"
                + "					 								                 \n"
                + "                 ";

        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Maquina modelo = new Maquina();

            modelo.setQuantidade(rs.getInt("total"));
            modelo.setMarca(marca);
            lista.add(modelo);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getRelatorioNumeroMaqAnun() throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT us.nome, us.tipoUsuario, us.telefone, us.idUsuario, \n"
                + "count(mo.idUsuario) as total \n"
                + "FROM modelo mo,\n"
                + "	  usuario us \n"
                + "where \n"
                + "mo.idUsuario = us.idUsuario AND\n"
                + "mo.`status` = ('ativo')\n"
                + "                   GROUP BY us.idUsuario ";

        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Usuario usuario = new Usuario();
            usuario.setIdUsuario(rs.getInt("idUsuario"));
            usuario.setNome(rs.getString("nome"));
            usuario.setTelefone(rs.getString("telefone"));            
            usuario.setTipoUsuario(rs.getString("tipoUsuario"));
            

            Maquina modelo = new Maquina();

            modelo.setQuantidade(rs.getInt("total"));
            modelo.setUsuario(usuario);
            lista.add(modelo);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }

    public static List<Maquina> getRelatorioMaqCategoria(int idMarca) throws SQLException {
        List<Maquina> lista = new ArrayList<Maquina>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM "
                + "         modelo mo,"
                + "         categoria ca"
                + "WHERE mo.idCategoria = ca.idCategoria AND"
                + "         mo.`status` = ('ativo') AND mo.idMarca = ? "
                + "ORDER BY ca.nomeCategoria";

        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, idMarca);
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

            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));

            Maquina modelo = new Maquina();
            modelo.setIdMaquina(rs.getInt("idModelo"));
            modelo.setDescricao(rs.getString("descricao"));
            modelo.setHorastrabalhadas(rs.getString("horastrabalhadas"));
            modelo.setKilometragem(rs.getString("kilometragem"));
            modelo.setMotorcv(rs.getString("motorcv"));
            modelo.setCidade(rs.getString("cidade"));
            modelo.setEstado(rs.getString("estado"));
            modelo.setAno(rs.getInt("ano"));
            modelo.setValor(rs.getInt("valor"));

            modelo.setUsuario(usuario);
            modelo.setMarca(marca);
            modelo.setCategoria(categoria);
            lista.add(modelo);
//            System.out.println("dao " + modelo.getStatus());
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;

    }
}
