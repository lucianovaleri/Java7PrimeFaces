package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelo.Marca;
import util.Conexao;

public class MarcaDAO {

    public static void inserir(Marca marca) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into marca (nome, paisOrigem) values (?,?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, marca.getNome());
        stmt.setString(2, marca.getPaisOrigem());
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Marca marca) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update marca set nome = ?, paisOrigem = ? where idMarca = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, marca.getNome());
        stmt.setString(2, marca.getPaisOrigem());
        stmt.setInt(3, marca.getIdMarca());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Marca marca) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM marca WHERE  idMarca = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, marca.getIdMarca());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Marca> getLista() throws SQLException {
        List<Marca> lista = new ArrayList<Marca>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM marca ORDER BY nome";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {
            Marca marca = new Marca();
            marca.setIdMarca(rs.getInt("idMarca"));
            marca.setNome(rs.getString("nome"));
            marca.setPaisOrigem(rs.getString("paisOrigem"));
            lista.add(marca);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
    }

    public static void main(String[] args) {

        try {
            List<Marca> lista = getLista();
            
            for (Marca m : lista) {
                System.out.println("CÓDIGO....: "+m.getIdMarca());
                System.out.println("NOME......: "+m.getNome());
                System.out.println("PAÍS......: "+m.getPaisOrigem());
                System.out.println("-----------------------------------");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
