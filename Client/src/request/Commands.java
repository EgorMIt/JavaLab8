package request;

public enum Commands {

    REMOVE(             "remove"),
    AVARAGEABOVESEALVL( "average_of_meters_above_sea_level"),
    CLEAR(              "clear"),
    EXECUTESCRIPT(      "execute_script"),
    EXIT(               "exit"),
    HELP(               "help"),
    INFO(               "info"),
    ADD(                 "add"),
    MINBYCLIMATE(       "min_by_climate"),
    PRINTDESCENDING(    "print_descending"),
    REMOVEGREATER(      "remove_greater"),
    REMOVELOVER(        "remove_lower"),
    REPLACEIFGERATER(   "replace_if_greater"),
    SHOW(               "show"),
    UPDATEID(           "update_id");


    /**
     * commandName - название команды в системе
     */

    final private String    commandName;
    /**
     * Перечислены команды, которые имеют реализацию на сервере.
     * @param commandName - название команды в системе
     */

    Commands(String commandName) {
        this.commandName =  commandName;
    }

    public String getCommandName() {
        return commandName;
    }

}
