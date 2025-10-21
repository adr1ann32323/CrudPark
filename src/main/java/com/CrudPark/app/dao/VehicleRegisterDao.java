package com.CrudPark.app.dao;

import com.CrudPark.app.domain.VehicleRegister;
import com.CrudPark.app.errors.DataAccessException;

import java.util.List;

public interface VehicleRegisterDao {
    VehicleRegister save(VehicleRegister register) throws DataAccessException;
    boolean hasActiveMembership(String plate) throws DataAccessException;
    String getDataUserByPlate(String plate) throws DataAccessException;
    void updateTicket(int id, String ticketText) throws DataAccessException;

    VehicleRegister findActiveByPlate(String plate)throws DataAccessException;

    void updateExit(VehicleRegister active)throws DataAccessException;

    String getTypeVehicleByPlate(String plate)throws DataAccessException;

    void updateExitTicket(int id, String ticket)throws DataAccessException;

    List<VehicleRegister> findAllActive() throws DataAccessException;

    List<VehicleRegister> findAll() throws DataAccessException;
}

