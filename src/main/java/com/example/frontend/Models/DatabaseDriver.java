package com.example.frontend.Models;

import java.sql.*;

public class DatabaseDriver {

    private Connection conn;

    public DatabaseDriver() {
        try {
            this.conn = DriverManager.getConnection("jdbc:sqlite:Frontend1.db");
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

    public ResultSet getDataByUsername(String username) {
        ResultSet resultSet = null;
        try {
            // Préparer la requête SQL pour récupérer les données de l'utilisateur par son username
            String sql = "SELECT * FROM admins WHERE Username = ?";
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, username);  // Remplacer ? par le username de l'utilisateur
            resultSet = statement.executeQuery();
        } catch (SQLException e) {
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

    public boolean updateUserProfile(String lastName, String firstName, String email,
                                     String country, String city, String address,
                                     String phone, String dob, String imagePath) {
        try {
            String sql = "UPDATE admins SET Name = ?, Surname = ?, Email = ?, Country = ?, Town = ?, " +
                    "Adress_Home = ?, Phone_Number = ?, Birthday = ?, Image = ? WHERE Email = ?";

            PreparedStatement statement = conn.prepareStatement(sql);
            statement.setString(1, firstName);
            statement.setString(2, lastName);
            statement.setString(3, email);
            statement.setString(4, country);
            statement.setString(5, city);
            statement.setString(6, address);
            statement.setString(7, phone);
            statement.setString(8, dob);
            statement.setString(9, imagePath);
            statement.setString(10, email); // Utiliser l'email comme identifiant

            int rowsAffected = statement.executeUpdate();
            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

}

