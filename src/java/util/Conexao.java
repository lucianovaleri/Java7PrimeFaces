/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Conexao {

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            return DriverManager.getConnection(
                                        "jdbc:mysql://localhost/projetopoo", "root", "");
                    
        } catch (ClassNotFoundException e) {
            throw new SQLException(e.getMessage());
        }
    }

    public static void main(String[] args) {
        try {
            getConnection();
            System.out.println("CONECTADO COM SUCESSO!");
        } catch (SQLException ex) {
            System.out.println("ERRO!!");
            ex.printStackTrace();
        }
    }

}
