package com.CrudPark.app.service;

import com.CrudPark.app.dao.VehicleRegisterDao;
import com.CrudPark.app.dao.jdbc.RateDao;
import com.CrudPark.app.domain.Operator;
import com.CrudPark.app.domain.VehicleRegister;
import com.CrudPark.app.errors.*;
import com.CrudPark.app.util.LogUtil;
import com.CrudPark.app.util.TicketGenerator;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class VehicleRegisterService {
    private final VehicleRegisterDao dao;
    private final RateDao rateDao;
    public VehicleRegisterService(VehicleRegisterDao dao, RateDao rateDao) {
        this.dao = dao;
        this.rateDao = rateDao;
    }

    public VehicleRegister registerEntry(String plate, int operatorId, Operator operator)
            throws DataAccessException, BadRequestException, ServiceException {

        if (plate == null || plate.isBlank()) {
            throw new BadRequestException("La placa del vehículo es obligatoria.");
        }

        try {
            boolean isMember = dao.hasActiveMembership(plate);
            String document = "";
            if (isMember) {
                document = dao.getDataUserByPlate(plate);
            }
            if (!isMember || document == null || document.isBlank()) {
                document = "TCK-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
            }

            // Crear registro base
            VehicleRegister register = new VehicleRegister(plate, operatorId, isMember, document);
            register.setEntryDate(new Timestamp(System.currentTimeMillis()));

            // 1️⃣ Guardar en la BD primero
            VehicleRegister saved = dao.save(register);

            // 2️⃣ Generar el ticket usando el registro guardado (ya tiene ID)
            String ticketText = TicketGenerator.generateTicket(saved, operator);
            saved.setTicket(ticketText);

            //  guardar el ticket en la BD
            dao.updateTicket(saved.getId(), ticketText);

            // 3️⃣ Mostrar por consola
            LogUtil.log(operator.getName(), "INGRESO",
                    "Vehículo placa " + plate + " ingresó al parqueadero.");

            return saved;

        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al registrar la entrada", e);
        }
    }

    // --------------------------
    // REGISTRO DE SALIDA
    // --------------------------
    public VehicleRegister registerExit(String plate, int operatorId)
            throws DataAccessException, NotFoundException, ServiceException {

        if (plate == null || plate.isBlank()) {
            throw new BadRequestException("La placa del vehículo es obligatoria.");
        }

        try {
            // Buscar registro activo (sin salida)
            VehicleRegister active = dao.findActiveByPlate(plate);
            if (active == null) {
                throw new NotFoundException("No se encontró una entrada activa para la placa " + plate);
            }

            if (active.getOutDate() != null) {
                throw new BadRequestException("Este vehículo ya registró su salida.");
            }

            // Registrar hora de salida
            LocalDateTime now = LocalDateTime.now();
            active.setOutDate(Timestamp.valueOf(now));



            // Calcular tiempo total en minutos
            Duration duration = Duration.between(active.getEntryDate().toLocalDateTime(), now);
            long minutes = duration.toMinutes();

            // Buscar tarifa según tipo de vehículo
            String type = dao.getTypeVehicleByPlate(plate);
            int rateHour = rateDao.getRatePerHour(type);
            int rateFraction = rateDao.getRateFraction(type);

            // Cálculo básico por horas y fracciones
            long hours = minutes / 60;
            long remaining = minutes % 60;

            int subtotal = (int) (hours * rateHour);
            if (remaining > 0) subtotal += rateFraction;

            active.setSubtotal(subtotal);

            // Si es miembro activo → salida sin cobro
            if (active.isMembresy()) {
                active.setTotal(0);
                dao.updateExit(active);
                System.out.println("Salida registrada (membresía activa):\n" + TicketGenerator.generateExitTicket(active));
                return active;
            }

            // Tiempo de gracia (≤ 30 min)
            if (minutes <= 30) {
                active.setSubtotal(0);
                active.setTotal(0);
            } else {
                active.setTotal(subtotal);
            }

            // Actualizar en BD
            dao.updateExit(active);

            // Generar ticket de salida
            String ticket = TicketGenerator.generateExitTicket(active);
            dao.updateExitTicket(active.getId(), ticket);

            LogUtil.log(String.valueOf(operatorId), "SALIDA",
                    "Vehículo placa " + plate + " salió. Total cobrado: $ " + active.getTotal());

            return active;

        } catch (DataAccessException e) {
            throw e;
        } catch (Exception e) {
            throw new ServiceException("Error inesperado al registrar salida", e);
        }
    }

    public List<VehicleRegister> listAll() throws DataAccessException {
        return dao.findAll();
    }

    public List<VehicleRegister> findAllActive() throws DataAccessException {
        return dao.findAllActive();
    }

}
