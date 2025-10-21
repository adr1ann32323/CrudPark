package com.CrudPark.app.dao.jdbc;

import com.CrudPark.app.config.DBconfig;
import com.CrudPark.app.dao.VehicleRegisterDao;
import com.CrudPark.app.domain.VehicleRegister;
import com.CrudPark.app.errors.DataAccessException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleRegisterDaoJdbc implements VehicleRegisterDao {

    @Override
    public VehicleRegister save(VehicleRegister register) throws DataAccessException {
        String sql = """
                    INSERT INTO registers_vehicles (plate, operator_id, entry_date, membresy, ticket, document_user)
                    VALUES (?, ?, NOW(), ?, ?, ?)
                    RETURNING id, entry_date;
                """;

        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, register.getPlate());
            ps.setInt(2, register.getOperatorId());
            ps.setBoolean(3, register.isMembresy());
            ps.setString(4, register.getTicket());
            ps.setString(5, register.getDocumentUser());

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                register.setId(rs.getInt("id"));
                register.setEntryDate(rs.getTimestamp("entry_date"));
            }

            return register;

        } catch (SQLException e) {
            throw new DataAccessException("Error al guardar el registro de vehículo", e);
        }
    }

    @Override
    public boolean hasActiveMembership(String plate) throws DataAccessException {
        String sql = """
                    SELECT u.id 
                    FROM users u
                    JOIN users_vehicles v ON v.user_id = u.id
                    WHERE v.plate = ? 
                      AND u.is_active = TRUE 
                      AND u.date_membership_expire >= NOW();
                """;

        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();
            return rs.next();

        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar la membresía del vehículo", e);
        }


    }

    @Override
    public String getDataUserByPlate(String plate) throws DataAccessException {

        String sql = """
                SELECT u.document
                FROM users u
                JOIN users_vehicles v ON v.user_id = u.id
                WHERE v.plate = ? 
                """;

        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();
            if (rs.next())
            {
                String document = rs.getString("document");
                System.out.println("Document fetched: " + document);
                return document;
            } else {
                return "";
            }

        } catch (SQLException e) {
            throw new DataAccessException("Error al verificar la membresía del vehículo", e);
        }
    }
    public void updateTicket(int id, String ticket) throws DataAccessException {
        String sql = "UPDATE registers_vehicles SET ticket = ? WHERE id = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, ticket);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar el ticket del registro: " + e.getMessage());
        }
    }

    public VehicleRegister findActiveByPlate(String plate) throws DataAccessException {
        String sql = "SELECT * FROM registers_vehicles WHERE plate = ? AND out_date IS NULL LIMIT 1";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return mapToVehicleRegister(rs);
            return null;
        } catch (SQLException e) {
            throw new DataAccessException("Error buscando registro activo", e);
        }
    }

    public String getTypeVehicleByPlate(String plate) throws DataAccessException {
        String sql = """
        SELECT uv.type_vehicle 
        FROM users_vehicles uv 
        WHERE uv.plate = ? LIMIT 1
    """;
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, plate);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getString("type_vehicle");
            return "Carro"; // Valor por defecto
        } catch (SQLException e) {
            throw new DataAccessException("Error obteniendo tipo de vehículo", e);
        }
    }

    @Override
    public void updateExitTicket(int id, String ticket) throws DataAccessException {
        String sql = "UPDATE registers_vehicles SET ticket = ? WHERE id = ?";

        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, ticket);
            ps.setInt(2, id);
            ps.executeUpdate();

        } catch (SQLException e) {
            throw new DataAccessException("Error al actualizar el ticket de salida en la base de datos", e);
        }
    }


    public void updateExit(VehicleRegister register) throws DataAccessException {
        String sql = """
        UPDATE registers_vehicles 
        SET out_date = ?, subtotal = ?, total = ?
        WHERE id = ?
    """;
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setTimestamp(1, register.getOutDate());
            ps.setInt(2, register.getSubtotal());
            ps.setInt(3, register.getTotal());
            ps.setInt(4, register.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException("Error actualizando salida", e);
        }
    }
    @Override
    public List<VehicleRegister> findAllActive() throws DataAccessException {
        List<VehicleRegister> list = new ArrayList<>();
        String sql = "SELECT * FROM registers_vehicles WHERE out_date IS NULL ORDER BY entry_date DESC";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToVehicleRegister(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DataAccessException("Error listando vehículos activos", e);
        }
    }
    @Override
    public List<VehicleRegister> findAll() throws DataAccessException {
        List<VehicleRegister> list = new ArrayList<>();
        String sql = "SELECT * FROM registers_vehicles ORDER BY id DESC";
        try (Connection conn = DBconfig.connect();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                list.add(mapToVehicleRegister(rs));
            }
            return list;

        } catch (SQLException e) {
            throw new DataAccessException("Error listando todos los registros", e);
        }
    }



    private VehicleRegister mapToVehicleRegister(ResultSet rs) throws SQLException {
        VehicleRegister v = new VehicleRegister();
        v.setId(rs.getInt("id"));
        v.setPlate(rs.getString("plate"));
        v.setOperatorId(rs.getInt("operator_id"));
        v.setDocumentUser(rs.getString("document_user"));
        v.setEntryDate(rs.getTimestamp("entry_date"));
        v.setOutDate(rs.getTimestamp("out_date"));
        v.setMembresy(rs.getBoolean("membresy"));
        v.setTicket(rs.getString("ticket"));
        v.setSubtotal(rs.getInt("subtotal"));
        v.setTotal(rs.getInt("total"));
        return v;
    }


}