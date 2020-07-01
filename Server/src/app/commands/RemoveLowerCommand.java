package app.commands;



import app.collection.City;
import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class RemoveLowerCommand extends Command {


    private Receiver receiver;


    public RemoveLowerCommand(Receiver receiver) {
        setName("remove_lower");
        setDescription("удалить из коллекции элементы, меньшие, чем заданный");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager, City city) {
            receiver.removeLower(city);

    }
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager) {
        receiver.removeLower();

    }

}
