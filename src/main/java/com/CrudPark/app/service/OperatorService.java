package com.CrudPark.app.service;

import com.CrudPark.app.dao.IOperatorDao;
import com.CrudPark.app.dao.jdbc.OperatorDaoJdbc;
import com.CrudPark.app.domain.Operator;

public class OperatorService {
    private final IOperatorDao operatorDao;

    public OperatorService() {
        this.operatorDao = new OperatorDaoJdbc();
    }

    public Operator login(String email, String password) throws Exception {
        Operator operador = operatorDao.findByEmailAndPassword(email, password);
        if (operador == null) {
            throw new Exception("Credenciales inválidas");
        }
        if (!operador.isActive()) {
            throw new Exception("El operador está inactivo");
        }
        return operador;

    }
}
