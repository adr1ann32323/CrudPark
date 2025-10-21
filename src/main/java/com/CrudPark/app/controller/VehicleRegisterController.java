package com.CrudPark.app.controller;

import com.CrudPark.app.domain.Operator;
import com.CrudPark.app.domain.VehicleRegister;
import com.CrudPark.app.errors.*;
import com.CrudPark.app.service.VehicleRegisterService;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class VehicleRegisterController {
    private final VehicleRegisterService service;

    public VehicleRegisterController(VehicleRegisterService service) {
        this.service = service;
    }

    public Map<String, Object> registerVehicleEntry(Map<String, String> data, Operator operador) {
        Map<String, Object> response = new HashMap<>();
        try {
            String plate = data.get("plate");
            int operatorId = Integer.parseInt(data.get("operatorId"));
            var created = service.registerEntry(plate, operatorId, operador);

            response.put("status", 200);
            response.put("data", created);

        } catch (BadRequestException e) {
            response.put("status", 400);
            response.put("error", e.getMessage());

        } catch (NotFoundException e) {
            response.put("status", 404);
            response.put("error", e.getMessage());

        } catch (ConflictException e) {
            response.put("status", 409);
            response.put("error", e.getMessage());

        } catch (ServiceException e) {
            response.put("status", 500);
            response.put("error", "Error del servicio: " + e.getMessage());

        } catch (DataAccessException e) {
            response.put("status", 500);
            response.put("error", "Error de base de datos: " + e.getMessage());

        } catch (Exception e) {
            response.put("status", 500);
            response.put("error", "Error inesperado: " + e.getMessage());
        }

        return response;
    }
    // -----------------------------------------
    // 🚙 SALIDA DE VEHÍCULO
    // -----------------------------------------
    public Map<String, Object> registerVehicleExit(String plate, int operatorId) {
        Map<String, Object> response = new HashMap<>();

        try {
            VehicleRegister updated = service.registerExit(plate, operatorId);
            response.put("status", 200);
            response.put("data", updated);

        } catch (BadRequestException e) {
            response.put("status", 400);
            response.put("error", e.getMessage());
        } catch (NotFoundException e) {
            response.put("status", 404);
            response.put("error", e.getMessage());
        } catch (ServiceException e) {
            response.put("status", 500);
            response.put("error", "Error interno del servidor: " + e.getMessage());
        } catch (DataAccessException e) {
            response.put("status", 500);
            response.put("error", "Error de acceso a datos: " + e.getMessage());
        } finally {
            System.out.println("→ Operación de salida finalizada");
        }

        return response;
    }

    public List<VehicleRegister> listAll() throws DataAccessException {
        return service.listAll();
    }

    public List<VehicleRegister> findAllActive() throws DataAccessException {
        return service.findAllActive();
    }
}
