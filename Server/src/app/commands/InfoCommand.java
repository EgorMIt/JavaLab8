package app.commands;

import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class InfoCommand extends Command {


    private Receiver receiver;


    public InfoCommand(Receiver receiver) {
        setName("info");
        setDescription("вывести информацию о коллекции");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager) {
            receiver.info();
    }
}
