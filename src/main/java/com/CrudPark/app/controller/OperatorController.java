package com.CrudPark.app.controller;

import com.CrudPark.app.domain.Operator;
import com.CrudPark.app.service.OperatorService;

public class OperatorController {
    private final OperatorService service;

    public OperatorController() {
        this.service = new OperatorService();
    }

    public Operator login(String email, String password) {
        try {
            return service.login(email, password);
        } catch (Exception e) {
            System.out.println("Error en login: " + e.getMessage());
            return null;
        }
    }
}
