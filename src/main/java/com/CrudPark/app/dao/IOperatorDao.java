package com.CrudPark.app.dao;

import com.CrudPark.app.domain.Operator;

public interface IOperatorDao {
    Operator findByEmailAndPassword(String email, String password) throws Exception;
}
