package com.enesuzumcu.tabu.domain.repository;

import com.enesuzumcu.tabu.data.local.database.DatabaseAccess;
import com.enesuzumcu.tabu.data.model.Words;

import javax.inject.Inject;

public class DatabaseRepository {
    private final DatabaseAccess databaseAccess;

    @Inject
    public DatabaseRepository(DatabaseAccess databaseAccess) {
        this.databaseAccess = databaseAccess;
    }

    public Words getWords(Integer id) {
        return databaseAccess.getWords(id);
    }

    public int databaseLength() {
        return databaseAccess.databaseLength();
    }
}
