package com.CrudPark.app;

import com.CrudPark.app.config.DBconfig;
import com.CrudPark.app.errors.DataAccessException;
import org.junit.jupiter.api.Test;
import java.sql.Connection;


import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

public class DBconfigTest {

    @Test
    public void testConexionBaseDatos() {
        try (Connection conn = DBconfig.connect()) {
            assertNotNull(conn);
            System.out.println("Conexión a la base de datos establecida correctamente.");
        } catch (Exception e) {
            fail("Excepción inesperada: " + e.getMessage());
        }
    }
}
