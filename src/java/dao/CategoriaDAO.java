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
import modelo.Categoria;
import util.Conexao;

/**
 *
 * @author USER
 */
public class CategoriaDAO {
    public static void inserir(Categoria categoria) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "insert into categoria (nomeCategoria)values (?)";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, categoria.getNomeCategoria());                
        stmt.execute();
        stmt.close();
        con.close();
    }

    public static void alterar(Categoria categoria) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "update categoria set nomeCategoria=? where idCategoria = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setString(1, categoria.getNomeCategoria());
        stmt.setInt(2, categoria.getIdCategoria());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static void excluir(Categoria categoria) throws SQLException {
        Connection con = Conexao.getConnection();
        String sql
                = "DELETE FROM categoria WHERE  idCategoria = ?";
        PreparedStatement stmt = con.prepareStatement(sql);
        stmt.setInt(1, categoria.getIdCategoria());
        stmt.executeUpdate();
        stmt.close();
        con.close();
    }

    public static List<Categoria> getLista() throws SQLException {
        List<Categoria> lista = new ArrayList<Categoria>();
        Connection con = Conexao.getConnection();
        String sql = "SELECT * FROM categoria ORDER BY nomeCategoria";
        PreparedStatement stmt = con.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();
        while (rs.next()) {                        

            Categoria categoria = new Categoria();
            categoria.setIdCategoria(rs.getInt("idCategoria"));
            categoria.setNomeCategoria(rs.getString("nomeCategoria"));
                                   
            lista.add(categoria);
        }
        stmt.close();
        rs.close();
        con.close();

        return lista;
}}
