package com.android.assessment.currencycalculator.interfaces;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.android.assessment.currencycalculator.models.Symbols;

import java.util.List;

/*Signature: Uche Emmanuel
 * Developersunesis*/

/**
 * SymbolsDao
 * to carry out SQLite operations CRUD
 * insert, delete and update
 */
@Dao
public interface SymbolsDao {

    @Query("SELECT * FROM symbols")
    List<Symbols> getAllSymbols();

    /**
     * This inserts a Symbols model to the database
     * @param symbol = a model
     */
    @Insert
    void insertSymbol(Symbols symbol);

    /**
     * This delete a Symbols model to the database
     * @param symbol = a model
     */
    @Delete
    void deleteSymbol(Symbols symbol);

    /**
     * This update a Symbols model to the database
     * @param symbol = a model
     */
    @Update
    void updateSymbol(Symbols symbol);
}
