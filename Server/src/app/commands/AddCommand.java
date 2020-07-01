package app.commands;

import app.collection.City;
import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class AddCommand extends Command {


    private Receiver receiver;


    public AddCommand(Receiver receiver) {
        setName("add");
        setDescription("добавить новый элемент в коллекцию");
        this.receiver = receiver;
    }

    @Override
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager, City city) {
        receiver.add(city);
    }

    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager) {
        receiver.add();
    }
}

