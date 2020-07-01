package app.executors;


import app.collection.City;
import app.commands.*;

import java.io.File;
import java.io.IOException;
import java.util.TreeMap;

public class Invoker {

    private Receiver receiver;

    public Invoker(Receiver receiver) {
        this.receiver = receiver;

        Command remove =                    new RemoveCommand(receiver);
        Command clear =                     new ClearCommand(receiver);
        Command averageMetersAboveSeaLeers = new AvarageOfMetersAboveSeaLevelCommand(receiver);
        Command help =                      new HelpCommand(receiver);
        Command info =                      new InfoCommand(receiver);
        Command add =                    new AddCommand(receiver);
        Command minByClimate =              new MinByClimateCommand(receiver);
        Command printDescending =           new PrintDescendingCommand(receiver);
        Command removeGreater =             new RemoveGreaterCommand(receiver);
        Command removeLower =               new RemoveLowerCommand(receiver);
        Command replaceIfGreater =          new ReplaceIfGreaterCommand(receiver);
        Command show =                      new ShowCommand(receiver);
        Command updateId =                  new UpdateIdCommand(receiver);
        Command exit =                      new ExitCommand(receiver);
        Command executeScript =             new ExecuteScriptCommand(receiver);
        this.setCommandMap(remove.getName(), remove);
        this.setCommandMap(clear.getName(), clear);
        this.setCommandMap(averageMetersAboveSeaLeers.getName(), averageMetersAboveSeaLeers);
        this.setCommandMap(help.getName(), help);
        this.setCommandMap(info.getName(), info);
        this.setCommandMap(add.getName(), add);
        this.setCommandMap(minByClimate.getName(), minByClimate);
        this.setCommandMap(printDescending.getName(), printDescending);
        this.setCommandMap(removeGreater.getName(), removeGreater);
        this.setCommandMap(removeLower.getName(), removeLower);
        this.setCommandMap(replaceIfGreater.getName(), replaceIfGreater);
        this.setCommandMap(show.getName(), show);
        this.setCommandMap(updateId.getName(), updateId);
        this.setCommandMap(exit.getName(), exit);
        this.setCommandMap(executeScript.getName(), executeScript);

    }

    public Receiver getReceiver() {
        return receiver;
    }

    public void setReceiver(Receiver receiver) {
        this.receiver = receiver;
    }

    private final TreeMap<String, Command> commandMap = new TreeMap<>();

    public void setCommandMap(String commandName, Command command) {
        commandMap.put(commandName, command);

    }
    /*{
        receiver = new Receiver();

    }*/

    /**
     * Метод выполняет команду, с заданным ему ее названием commandName
     *
     * @param commandName      название команды
     * @param commandMap       карта команды
     * @param args             аргументы команды
     * @throws IOException ошибка ввода
     */

    public void execute(String commandName, TreeMap<String, Command> commandMap, String args) throws IOException {
        Command command = commandMap.get(commandName);
        if (command == null) throw new IllegalStateException("Команды с названием " + commandName + " не существует");
        command.execute(commandMap, this, args);
    }

    public void execute(String commandName, TreeMap<String, Command> commandMap, City city) {
        Command command = commandMap.get(commandName);
        command.execute(commandMap, this, city);
    }

    public void execute(String commandName, TreeMap<String, Command> commandMap)  {
        Command command = commandMap.get(commandName);
        if (command == null) throw new IllegalStateException("Команды с названием " + commandName + " не существует");
        command.execute(commandMap, this);
    }

    public void execute(String commandName, TreeMap<String, Command> commandMap,  City city, String... args) throws IOException {
        Command command = commandMap.get(commandName);
        command.execute(commandMap, this, city, args);
    }
    public void execute(String commandName, TreeMap<String, Command> commandMap, File file) throws IOException {
        Command command = commandMap.get(commandName);
        command.execute(commandMap, this, file);
    }


    public TreeMap<String, Command> getCommandMap() {
        return commandMap;
    }


    @Override
    public String toString() {
        return "Switch{" +
                "commandMap=" + commandMap +
                '}';
    }
}

