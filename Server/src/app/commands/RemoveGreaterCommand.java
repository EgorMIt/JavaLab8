package app.commands;

import app.collection.City;
import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class RemoveGreaterCommand extends Command {


    private Receiver receiver;


    public RemoveGreaterCommand(Receiver receiver) {
        setName("remove_greater");
        setDescription("удалить из коллекции элементы, превышающие заданный");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager, City city) {
        receiver.removeGreater(city);

    }

    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager) {
        receiver.removeGreater();

    }
}
