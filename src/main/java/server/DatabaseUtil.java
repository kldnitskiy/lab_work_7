package server;

import shared.city.*;

import java.sql.*;
import java.time.Clock;
import java.time.ZonedDateTime;

public class DatabaseUtil {

    public static City readCityFromResultSet(ResultSet rs) throws SQLException {
        City city = new City();
        city.setOwnerUsername(rs.getString(1));
        city.setId(rs.getInt(2));
        city.setName(rs.getString(3));
        city.setCoordinates(new Coordinates(rs.getFloat(4), rs.getLong(5)));
        Timestamp ts = rs.getTimestamp(6);
        city.setCreationDateTime(ts.toLocalDateTime().toLocalDate());
        city.setArea(rs.getInt(7));
        city.setPopulation(rs.getInt(8));
        city.setMetersAboveSeaLevel(rs.getInt(9));
        city.setPopulationDensity(rs.getInt(10));
        city.setClimate(Climate.valueOf(rs.getString(11)));
        city.setGovernment(Government.valueOf(rs.getString(12)));
        city.setGovernor(new Human(rs.getString(13),rs.getFloat(14)));
        return city;
    }

    public static void initPreparedStatement(PreparedStatement ps, City city) throws SQLException {
        ps.setString(1, city.getOwnerUsername());
        ps.setLong(2, city.getId());
        ps.setString(3,city.getName());
        ps.setFloat(4,city.getCoordinates().getX());
        ps.setLong(5,city.getCoordinates().getY());
        ps.setDate(6, Date.valueOf(city.getCreationDateTime()));
        ps.setInt(7,city.getArea());
        ps.setInt(8, city.getPopulation());
        ps.setInt(9,city.getMetersAboveSeaLevel());
        ps.setInt(10, city.getPopulationDensity());
        ps.setString(11,city.getClimate().toString());
        ps.setString(12, city.getGovernment().toString());
        ps.setString(13, city.getGovernor().getName());
        ps.setFloat(14, city.getGovernor().getHeight());

    }

    public static String getProductInsertionSql() {
        return String.format(
                "insert into collection (%s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s, %s) values(?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);",
                "owner_username",
                "id",
                "name",
                "coordinates_x",
                "coordinates_y",
                "creation_date",
                "area",
                "population",
                "meters_above_sea_level",
                "population_density",
                "climate",
                "government",
                "governor_name",
                "governor_height"
        );
    }
}
