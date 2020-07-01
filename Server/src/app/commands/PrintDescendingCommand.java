package app.commands;

import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class PrintDescendingCommand extends Command {

    private Receiver receiver;


    public PrintDescendingCommand(Receiver receiver) {
        setName("print_descending");
        setDescription("вывести коллекцию в порядке убывания");
        this.receiver = receiver;
    }


    @Override
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager) {
            receiver.printDescending();
    }


}
