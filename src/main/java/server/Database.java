package server;

import shared.city.City;
import shared.message.User;

import java.io.IOException;
import java.sql.*;
import java.time.LocalDate;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.PriorityQueue;

public class Database {
    private final String url;
    private final String user;
    private final String password;

    private Connection connection;

    public Database(String url, String user, String password) throws SQLException {
        this.url = url;
        this.user = user;
        this.password = password;
        establishConnection();
    }

    private void establishConnection() throws SQLException {
        System.out.println("establishConnection");
        connection = DriverManager.getConnection(url, user, password);
    }
    public PriorityQueue<City> readAllCities() {
        System.out.println("readAllCities");
        PriorityQueue<City> collection = new PriorityQueue<>();

        try (Statement statement = connection.createStatement()) {
            String sql = "select * from collection;";

            try (ResultSet rs = statement.executeQuery(sql)) {
                while (rs.next()) {
                    collection.add(DatabaseUtil.readCityFromResultSet(rs));
                }
            }

            return collection;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return null;
    }

    private int nextIdInSequence() throws SQLException {
        System.out.println("nextIdInSequence");
        try (Statement statement = connection.createStatement()) {
            String sql = "select nextval ('id_seq');";

            try (ResultSet rs = statement.executeQuery(sql)) {
                rs.next();

                return rs.getInt(1);
            }
        }
    }

    public boolean initializeAndInsertCity(City city, User user) {
        System.out.println("initializeAndInsertCity");
        try {
            city.setId(nextIdInSequence());
            city.setOwnerUsername(user.getName());
            city.setCreationDateTime(LocalDate.now());
            return insertCityWithoutInitialization(city);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    public boolean insertCityWithoutInitialization(City city) {
        System.out.println("insertCityWithoutInitialization");
        String sql = DatabaseUtil.getProductInsertionSql();

        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            DatabaseUtil.initPreparedStatement(preparedStatement, city);
            int updatedRows = preparedStatement.executeUpdate();
            return updatedRows == 1;
        } catch (SQLException | NullPointerException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }
    public boolean authorizeUser(User user) throws InvalidUsernameOrPasswordException {
        System.out.println("authorizeUser");
        User fromDb = getUserOrNull(user.getName());
        //System.out.println(fromDb.getPassword());
        //System.out.println(user.getPassword());
        if (fromDb == null || !fromDb.getPassword().equals(user.getPassword()))
            throw new InvalidUsernameOrPasswordException();

        return true;
    }

    public boolean removeCity(long CityId) {
        System.out.println("removeCity");
        try (Statement statement = connection.createStatement()) {
            String sql = "delete from collection where id = " + CityId + ";";
            int updatedRows = statement.executeUpdate(sql);
            return updatedRows == 1;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return false;
    }

    public boolean removeCity(City city) {
        System.out.println("removeCity"); return removeCity(city.getId());
    }

    public boolean registerUser(User user) throws UserAlreadyExistsException {
        System.out.println("registerUser");
        if (getUserOrNull(user.getName()) != null)
            throw new UserAlreadyExistsException();

        String sql = "insert into users (username, password) values (?, ?);";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, user.getName());
            statement.setString(2, user.getPassword());
            int updatedRows = statement.executeUpdate();
            return updatedRows == 1;
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        return false;
    }

    private User getUserOrNull(String username) {
        System.out.println("getUserOrNull");
        final String sql = "select * from users where username = ?;";

        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);

            try (ResultSet rs = statement.executeQuery()) {
                if (!rs.next())
                    return null;
                //System.out.println(rs.getString(2));
                return new User(rs.getString(2), rs.getString(3));
            }

        }
        catch (SQLException th) {
            th.printStackTrace();
        }
        return null;
    }
    public void close() throws IOException {
        try {
            connection.close();
        }
        catch (SQLException throwables) {}
    }



}
