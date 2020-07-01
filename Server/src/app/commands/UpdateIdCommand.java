package app.commands;

import app.collection.City;
import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class UpdateIdCommand extends Command {


    private Receiver receiver;


    public UpdateIdCommand(Receiver receiver) {
        setName("update_id");
        setDescription("обновить элменет по заданному id - [key]");
        this.receiver = receiver;
    }


    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager, City city, String... args) {
            int id = Integer.parseInt(args[0]);
            receiver.updateId(city, id);
    }
    public void execute(TreeMap<String, Command> commandTreeMap,
                        Invoker commandManager,String args){
        try {
            int id = Integer.parseInt(args);
            receiver.updateId(id);
        }catch (NumberFormatException e){
            System.out.println("Аргумент имеет недопустимое значение.");
        }

    }
}
