package com.example.frontend.Models;

import com.example.frontend.Views.ViewFactory;

import java.sql.ResultSet;

public class Model {
    private static Model model;
    private final ViewFactory viewFactory;
    private final DatabaseDriver databaseDriver;
    private boolean LoginsuccessFlag;
    public final User user;

    public static synchronized Model getInstance(){
        if(model == null){
            model = new Model();
        }
        return model;
    }

    public ViewFactory getViewFactory() {
        return viewFactory;
    }

    public DatabaseDriver getDatabaseDriver() {
        return databaseDriver;
    }

    public Model() {
        this.databaseDriver = new DatabaseDriver();
        this.viewFactory = new ViewFactory();
        this.user = new User("");
        this.LoginsuccessFlag = false;
    }

    public boolean getSuccessFlag() {
        return this.LoginsuccessFlag;
    }

    public void setSuccessFlag(boolean flag) {
        this.LoginsuccessFlag = flag;
    }

    /*    public User getUser(){
            return this.user;
        }
    */
    public void evaluateAdminCred(String username, String password){
        ResultSet resultSet = databaseDriver.getData(username, password);
        try{
            if(resultSet.isBeforeFirst()){
                this.user.usernameProperty().set(resultSet.getString("Username"));
                this.LoginsuccessFlag = true;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}

