package app.commands;

import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class HelpCommand extends Command {

    private Receiver receiver;

    public HelpCommand(Receiver receiver) {
        this.receiver = receiver;
        setName("help");
        setDescription("вывести справку по доступным командам");
    }

    @Override
    public void execute(TreeMap<String, Command> treeMap, Invoker commandManager) {
            receiver.help(treeMap);
    }
}
