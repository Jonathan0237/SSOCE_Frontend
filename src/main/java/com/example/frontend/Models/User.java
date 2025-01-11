package com.example.frontend.Models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class User {
    public final StringProperty username;

    public User(String username) {
        this.username = new SimpleStringProperty(this, "Username", username);
    }

    public StringProperty usernameProperty() {
        return username;
    }
}
