package app;


import java.net.BindException;

public class RunServer {

    private static int PORT = 0;


    public static void main(String[] args) {
        try {
            setPORT(8080);
            System.out.println("Сервер занял порт номер " + getPORT());
            //setPORT(new Integer(args[0]));
            ApplicationManager applicationManager = new ApplicationManager();
            applicationManager.begin();
        } catch (NumberFormatException e) {
            System.out.println("Номер порта имеет неправильный формат, введите порт корректно.");
            System.exit(0);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Порт подключения не указан!");
            System.exit(0);
        } catch (BindException e) {
            System.out.println("Этот порт уже занят. Запустите сервер на другом порте");
        } catch (Exception e) {
            System.out.println("Возможно этот порт уже занят для подключения. " +
                    "Попробуйте ввести другой порт и запустить приложения заново");
        }
    }

    public static int getPORT() {
        return PORT;
    }

    public static void setPORT(int PORT) {
        RunServer.PORT = PORT;
    }
}
