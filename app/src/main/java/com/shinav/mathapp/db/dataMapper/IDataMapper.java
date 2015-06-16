package com.shinav.mathapp.db.dataMapper;

public interface IDataMapper<C1> {
    void insert(C1 object);
    void update(C1 object, String objectKey);
    void delete(String objectKey);
}
