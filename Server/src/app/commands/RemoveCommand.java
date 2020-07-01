package app.commands;

import app.executors.Invoker;
import app.executors.Receiver;


import java.util.TreeMap;

public class RemoveCommand extends Command {

    private Receiver receiver;


    public RemoveCommand(Receiver receiver) {
        setName("remove");
        setDescription("удаляет элемент из коллекции по заданному id");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager,String args) {
        int key = Integer.parseInt(args);
        receiver.remove(key);
    }
}
