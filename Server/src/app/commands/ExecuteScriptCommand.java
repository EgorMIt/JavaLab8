package app.commands;

import app.executors.Invoker;
import app.executors.Receiver;
import app.executors.RepositoryOfCity;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.TreeMap;

public class ExecuteScriptCommand extends Command {


    private Receiver receiver;
    HashSet<String> files = new HashSet<>();


    public ExecuteScriptCommand(Receiver receiver) {
        setName("execute_script");
        setDescription("[file name] - выполнить файл с командами - [key] - абсолютный путь файла");
        this.receiver = receiver;
    }

    @Override
    public void execute(TreeMap<String, Command> hashMap,
                        Invoker commandManager, File file) throws IOException {
        receiver.executeScript(file, commandManager);

    }

}
