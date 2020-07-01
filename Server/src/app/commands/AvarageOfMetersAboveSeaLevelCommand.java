package app.commands;

import app.executors.Invoker;
import app.executors.Receiver;

import java.util.TreeMap;

public class AvarageOfMetersAboveSeaLevelCommand extends Command {
    private Receiver receiver;

    public AvarageOfMetersAboveSeaLevelCommand(Receiver receiver) {
        setName("average_of_meters_above_sea_level");
        setDescription("вывести среднее значение поля metersAboveSeaLevel для всех элементов коллекции");
        this.receiver = receiver;
    }

    @Override
    public void execute(TreeMap<String, Command> treeMap, Invoker commandManager) {
        receiver.averageMetersAboveSeaLevel();
    }
}
