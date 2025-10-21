package com.CrudPark.app.dao.jdbc;

import com.CrudPark.app.config.DBconfig;
import com.CrudPark.app.errors.DataAccessException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RateDao {
    public int getRatePerHour(String type) throws DataAccessException {
        String sql = "SELECT amount_hour FROM rates WHERE type_vehicle = ? LIMIT 1";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("amount_hour");
            return 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error obteniendo tarifa por hora", e);
        }
    }

    public int getRateFraction(String type) throws DataAccessException {
        String sql = "SELECT amount_fraction FROM rates WHERE type_vehicle = ? LIMIT 1";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, type);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt("amount_fraction");
            return 0;
        } catch (SQLException e) {
            throw new DataAccessException("Error obteniendo tarifa fraccional", e);
        }
    }
}
