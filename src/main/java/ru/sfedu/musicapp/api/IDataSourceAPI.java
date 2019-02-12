package ru.sfedu.musicapp.api;
import ru.sfedu.musicapp.models.BaseClasses;
import ru.sfedu.musicapp.models.User;

import java.io.IOException;

public interface IDataSourceAPI {

    boolean addRecord(Object obj) throws IOException;
    void removeRecordById(int id, Class T) throws Exception;
    void updateRecord(int id, Object obj) throws Exception;
    <T> T getRecordById(int id, BaseClasses clazz) throws Exception;
    <T> T readData(BaseClasses clazz) throws Exception;
}
