package io;

import app.collection.City;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Класс, объект которого клиент отправляет серверу
 * Объект хранит в себе наименование команды и аргументы. А также логин и пароль user'а
 */

public class Message implements Serializable {
    private User                user;
    private String              commandName;
    private String              args;
    private City                city;
    private File                executionFile;
    private static final long   serialVersionUID = 5435345435342444242L;
    private boolean             isRegistration;

    public Message(String commandName, User user, boolean isRegistration) {
        this.commandName = commandName;
        this.user =             user;
        this.isRegistration =   isRegistration;
    }
    public Message(String commandName, User user) {
        this.commandName =      commandName;
        this.user =             user;
    }

    public Message(String commandName, String args, User user) {
        this.commandName =      commandName;
        this.args =             args;
        this.user =             user;
    }

    public Message(String commandName, City objectCity, User user) {
        this.commandName = commandName;
        city =                  objectCity;
        this.user =             user;
    }

    public Message(String commandName, String args, City objectCity, User user) {
        this.commandName =      commandName;
        this.args =             args;
        city =                  objectCity;
        this.user =             user;
    }

    public Message(String commandName, File file, User user) {
        this.commandName =      commandName;
        executionFile =         file;
        this.user =             user;
    }


    public String getCommandName() {
        return commandName;
    }

    public String getArgs() {
        return args;
    }

    public City getCity() {
        return city;
    }

    public File getExecutionFile() {
        return executionFile;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public boolean isRegistration() {
        return isRegistration;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setArgs(String args) {
        this.args = args;
    }

    public void setCity(City city) {
        this.city = city;
    }

    public void setExecutionFile(File executionFile) {
        this.executionFile = executionFile;
    }

    public void setRegistration(boolean registration) {
        isRegistration = registration;
    }

    public User getUser() {
        return user;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    @Override
    public String toString() {
        return "Message{" +
                "user=" + user +
                ", commandName='" + commandName + '\'' +
                ", args='" + args + '\'' +
                ", city=" + city +
                ", executionFile=" + executionFile +
                ", isRegistration=" + isRegistration +
                '}';
    }
}
