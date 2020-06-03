package ru.example.db.dao;

import ru.example.db.entities.CommonEntity;

import java.util.List;

public interface CommonDao<T extends CommonEntity> {
    void createTable();
    T save(T t);
    void update(T t);
    void delete(T t);
    List<T> findAll();
    T findOne(Long id);
}
