package com.example.frontend.Models;

import java.sql.*;

public class DatabaseDriver {

    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:Frontend.db");
        }catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ResultSet getData(String username, String password) {
        Statement statement;
        ResultSet resultSet = null;
        try{
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM admins WHERE Username='"+username+"' AND Password='"+password+"';");
        }catch (SQLException e) {
            e.printStackTrace();
        }
        return resultSet;
    }

    public boolean isEmailUsed(String Email) {
        Statement statement;
        ResultSet resultSet;
        try {
            statement = this.conn.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM admins WHERE Email='" + Email + "';");
            return resultSet.next(); // Retourne true si un utilisateur avec cet email est trouvé
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false; // Retourne false si aucune erreur et aucun utilisateur trouvé
    }

    public void createUser(String Email, String username, String password) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("INSERT INTO " + "admins (Email, Username, Password)" +
                    "VALUES ('"+Email+"', '"+username+"', '"+password+"');");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void deleteUser(String email) {
        try (PreparedStatement statement = this.conn.prepareStatement("DELETE FROM admins WHERE Email = ?")) {
            statement.setString(1, email);
            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUser(String Email, String username) {
        Statement statement;
        try {
            statement = this.conn.createStatement();
            statement.executeUpdate("UPDATE admins SET Email = '"+Email+"', Username = '"+username+"' WHERE Email = '"+Email+"';");
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
