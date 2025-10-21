package com.CrudPark.app.dao.jdbc;

import com.CrudPark.app.config.DBconfig;
import com.CrudPark.app.dao.IOperatorDao;
import com.CrudPark.app.domain.Operator;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class OperatorDaoJdbc implements IOperatorDao {

    @Override
    public Operator findByEmailAndPassword(String email, String password) throws Exception {
        String sql = "SELECT * FROM operators WHERE email = ? AND password = ?";
        try (Connection conn = DBconfig.connect();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, email);
            stmt.setString(2, password);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Operator(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("document"),
                        rs.getString("password"),
                        rs.getString("email"),
                        rs.getBoolean("is_active")
                );
            }
            return null;
        }
    }
}
