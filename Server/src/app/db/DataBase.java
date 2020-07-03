package app.db;

import app.collection.*;
import app.treatment.SendToClient;
import org.postgresql.util.PSQLException;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.TreeMap;

public class DataBase {

    private static final String URL = "jdbc:postgresql://localhost:5432/postgres"; // тут будет наш локальный постгресс
    private static final String LOGIN = "postgres";
    private static final String PASS = "12345";
    private Connection connection;
    private Statement statement;

    private String owner = "";


    private final String deleteCity = "DELETE FROM cities WHERE owner = ?;";

    private final String deleteCityById = "DELETE FROM cities WHERE owner = ? AND id = ?;";

    private final String addCity =
            "INSERT INTO cities (name, owner, " +
                    "x, y, creationDate, area, population, " +
                    "metersabovesealevel, climate, government, standardOfLiving, governorBirthday, governorAge) " +
                    "VALUES (?, ?, ?, ?, ?, ? ,?,?,?, ?, ?, ?, ?) RETURNING id;";

    private final String addCityWithId =
            "INSERT INTO cities (id, name, owner, " +
                    "x, y, creationDate, area, population, " +
                    "metersabovesealevel, climate, government, standardOfLiving, governorBirthday, governorAge) " +
                    "VALUES (?,?, ?, ?, ?, ?, ? ,?,?,?, ?, ?, ?, ?);";

    private final String load = "SELECT * FROM cities";

    private final String createCitiesTable =
            "CREATE TABLE IF NOT EXISTS cities " +
                    "(id serial primary key not null," +
                    " owner TEXT NOT NULL , " +
                    " name TEXT NOT NULL , " +
                    " x INTEGER NOT NULL, " +
                    " y DOUBLE PRECISION NOT NULL, " +
                    " creationDate TEXT NOT NULL , " +
                    " area INTEGER NOT NULL," +
                    " population INTEGER NOT NULL , " +
                    " metersAboveSeaLevel INTEGER , " +
                    " climate VARCHAR , " +
                    " government VARCHAR , " +
                    " standardOfLiving VARCHAR NOT NULL , " +
                    " governorAge INTEGER," +
                    " governorBirthday VARCHAR)";


    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getOwner() {
        return owner;
    }

    public DataBase() throws SQLException {
        try {
            connection = DriverManager.getConnection(URL, LOGIN, PASS);
            statement = connection.createStatement();
        } catch (PSQLException e) {
            System.out.println("Ошибка соединения с базой данных. Поправьте настройки или попробуйте позднее..");
            System.exit(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        createSecretBase();
        createCitiesDB();

    }

    public void createCitiesDB() throws SQLException {
        this.statement = connection.createStatement();
        statement.execute(createCitiesTable);
    }

    public int addCityInCitiesDB(City city) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            String cityCreationDate = city.getCreationDate().format(formatter);
            String governorBirthday = city.getGovernor().getDateOfBirthday().format(formatter);

            PreparedStatement ps = connection.prepareStatement(addCity);
            ps.setString(1, city.getName());
            ps.setString(2, owner);
            ps.setInt(3, city.getCoordinates().getX());
            ps.setFloat(4, city.getCoordinates().getY());
            ps.setString(5, cityCreationDate);
            ps.setLong(6, city.getArea());
            ps.setInt(7, city.getPopulation());
            ps.setLong(8, city.getMetersAboveSeaLevel());
            if (city.getClimate() != null) {
                ps.setString(9, city.getClimate().getRussianName());
            } else {
                ps.setNull(9, Types.VARCHAR);
            }
            if (city.getClimate() != null) {
                ps.setString(10, city.getGovernment().getRussianName());
            } else {
                ps.setNull(10, Types.VARCHAR);
            }
            ps.setString(11, city.getStandardOfLiving().getRussianName());
            ps.setString(12, governorBirthday);
            ps.setFloat(13, city.getGovernor().getAge());
            ps.execute();
            ResultSet last_upd_city = ps.getResultSet();
            last_upd_city.next();
            return last_upd_city.getInt(1);

        } catch (SQLException e) {
            System.out.println("Соси");
            e.printStackTrace();
            return 0;
        }
    }

    public DataBase getDataBase() {
        return this;
    }


    public boolean isValue(String word, String value) throws SQLException {
        try {
            ResultSet rs = statement.executeQuery("SELECT " + word + " FROM users");
            while (rs.next())
                if (value.equals(rs.getString(1)))
                    return true;
            return false;
        } catch (SQLException e) {
            System.out.println("SQLEXCEPTION");
        }
        return false;
    }

    public void addUserToTheBase(String login, String password) throws SQLException {
        boolean access = true;
        ResultSet rs = statement.executeQuery("SELECT login FROM users");
        while (rs.next()) {
            if (login.equals(rs.getString(1))) {
                access = false;
            }
        }
        if (access) {
            String sql = "INSERT INTO users (login, password) VALUES (?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, login);
            preparedStatement.setString(2, (password));
            preparedStatement.execute();
            SendToClient.write("110100011000000011010000101101011101000010110011");
        } else {
            SendToClient.write("Пользователь с логином " + login + " уже зарегистрирован." + System.lineSeparator());
        }

    }

    public boolean authForExecuteCommand(String login, String password) {
        boolean access = false;
        try {
            ResultSet rs = statement.executeQuery("SELECT * FROM users");
            while (rs.next()) {
                if (login.equals(rs.getString("login")) && password.equals(rs.getString("password"))) {
                    access = true;
                }
            }
        } catch (PSQLException e) {
            System.out.println("Произошла ошибка при подключении к БД. Проверьие подключени и отправьте запрос еще раз!");
            System.exit(0);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return access;
    }

    public void authorizationUser(String login, String password) throws SQLException {
        boolean access = false;
        ResultSet rs = statement.executeQuery("SELECT * FROM users");
        while (rs.next()) {
            if (login.equals(rs.getString("login")) && password.equals(rs.getString("password"))) {
                access = true;
            }
        }
        if (access) {
            SendToClient.write("110100001011000011010000101100101101000110000010");
        } else {
            SendToClient.write("Вы ввели неверный логин или пароль. Попробуйте снова!" + System.lineSeparator());
        }
    }


    public void createSecretBase() throws SQLException {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS  users " +
                "(login TEXT, " +
                " password TEXT)";
        statement.execute(createTableSQL);
    }

    public boolean clearCities(String username) {
        try {
            int count = 0;
            Long localId = 1L;
            boolean isAccess = false;

            PreparedStatement ps = connection.prepareStatement(deleteCity);
            ps.setString(1, username);
            if (ps.executeUpdate() == 0) System.out.println("UPADETR == 0");
            else {
                System.out.println("UPADETR != 0");
                ResultSet rs = statement.executeQuery("Select * from cities;");
                while (rs.next()){
                    count += 1;
                }
                if (count == 0){
                    PreparedStatement statement = connection.prepareStatement("alter sequence cities_id_seq restart with " + localId.toString() + ";");
                    statement.executeUpdate();
                }
                isAccess = true;
            }
            return isAccess;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean removeById(long id, String username) {
        try {
            boolean isAccess;
            PreparedStatement ps = connection.prepareStatement(deleteCityById);
            ps.setString(1, username);
            ps.setLong(2, id);
            if (ps.executeUpdate() == 0) isAccess = false;
            else{
                isAccess = true;
            }
            return isAccess;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean replace_if_greater(long id, City city, String username) {
        boolean access = false;
        try {
            String request1 = "SELECT * FROM cities WHERE id = ?";
            PreparedStatement ps = connection.prepareStatement(request1);
            ps.setLong(1, id);
            if (ps.execute()) {
                System.out.println(access + " execute..");
                ResultSet resultSet = ps.executeQuery();
                resultSet.next();
                if (resultSet.getInt("population") < city.getPopulation()) {
                    System.out.println(access + " compare populations!");
                    if (removeById(resultSet.getLong(1), owner)) {
                        System.out.println(access + "remove by id");
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                        String cityCreationDate = city.getCreationDate().format(formatter);
                        String governorBirthday = city.getGovernor().getDateOfBirthday().format(formatter);

                        PreparedStatement _ps = connection.prepareStatement(addCityWithId);
                        _ps.setLong(1, id);
                        _ps.setString(2, city.getName());
                        _ps.setString(3, owner);
                        _ps.setInt(4, city.getCoordinates().getX());
                        _ps.setFloat(5, city.getCoordinates().getY());
                        _ps.setString(6, cityCreationDate);
                        _ps.setLong(7, city.getArea());
                        _ps.setInt(8, city.getPopulation());
                        _ps.setLong(9, city.getMetersAboveSeaLevel());
                        if (city.getClimate() != null) {
                            _ps.setString(10, city.getClimate().getRussianName());
                        } else {
                            _ps.setNull(10, Types.VARCHAR);
                        }
                        if (city.getClimate() != null) {
                            _ps.setString(11, city.getGovernment().getRussianName());
                        } else {
                            _ps.setNull(11, Types.VARCHAR);
                        }
                        _ps.setString(12, city.getStandardOfLiving().getRussianName());
                        _ps.setString(13, governorBirthday);
                        _ps.setFloat(14, city.getGovernor().getAge());
                        if (_ps.executeUpdate() == 0) {
                            System.out.println(access + "update = 0");
                            return false;
                        }
                        System.out.println(access + "update = true");
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return false;
    }


    public boolean updateId(long id, City city, String username) {
        if (removeById(id, username)) {
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String cityCreationDate = city.getCreationDate().format(formatter);
                String governorBirthday = city.getGovernor().getDateOfBirthday().format(formatter);

                PreparedStatement ps = connection.prepareStatement(addCityWithId);
                ps.setLong(1, id);
                ps.setString(2, owner);
                ps.setString(3, city.getName());
                ps.setInt(4, city.getCoordinates().getX());
                ps.setFloat(5, city.getCoordinates().getY());
                ps.setString(6, cityCreationDate);
                ps.setLong(7, city.getArea());
                ps.setInt(8, city.getPopulation());
                ps.setLong(9, city.getMetersAboveSeaLevel());
                if (city.getClimate() != null) {
                    ps.setString(10, city.getClimate().getRussianName());
                } else {
                    ps.setNull(10, Types.VARCHAR);
                }
                if (city.getClimate() != null) {
                    ps.setString(11, city.getGovernment().getRussianName());
                } else {
                    ps.setNull(11, Types.VARCHAR);
                }
                ps.setString(12, city.getStandardOfLiving().getRussianName());
                ps.setString(13, governorBirthday);
                ps.setFloat(14, city.getGovernor().getAge());
                if (ps.executeUpdate() == 0) return false;
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public void loadCollection(TreeMap<Integer, City> repositoryOfCities) throws SQLException {

        ResultSet resultSet = statement.executeQuery(load);
        repositoryOfCities.clear();
        while (resultSet.next()) {

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

            repositoryOfCities.put(resultSet.getInt(1),
                    new City(resultSet.getInt(1),
                            resultSet.getString(2),
                            resultSet.getString(3),
                            new Coordinates(resultSet.getInt(4),
                                    resultSet.getFloat(5)),
                            LocalDateTime.parse(resultSet.getString(6), formatter),
                            resultSet.getLong(7),
                            resultSet.getInt(8),
                            resultSet.getLong(9),

                            Climate.StringNameToClimateObj(resultSet.getString(10)),
                            Government.StringNameToGovernmentObj(resultSet.getString(11)),
                            StandardOfLiving.StringNameToStandardOfLivingObj(resultSet.getString(12)),

                            new Human(resultSet.getInt(13),
                                    LocalDateTime.parse(resultSet.getString(14), formatter))));
        }

    }

    public boolean removeLower(City city) {
        try {
            int index = city.getPopulation();
            String request1 = "SELECT * FROM cities WHERE population < ?";
            PreparedStatement ps = connection.prepareStatement(request1);
            ps.setInt(1, index);
            if (ps.execute()) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    removeById(resultSet.getLong(1), owner);
                }
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean removeGreater(City city) {
        try {
            int index = city.getPopulation();
            String request1 = "SELECT * FROM cities WHERE population < ?";
            PreparedStatement ps = connection.prepareStatement(request1);
            ps.setInt(1, index);
            if (ps.execute()) {
                ResultSet resultSet = ps.executeQuery();
                while (resultSet.next()) {
                    removeById(resultSet.getLong(1), owner);
                }
                return true;
            } else {
                return false;
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
