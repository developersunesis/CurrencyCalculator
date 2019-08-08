package com.android.assessment.currencycalculator.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.android.assessment.currencycalculator.interfaces.SymbolsDao;
import com.android.assessment.currencycalculator.models.Symbols;

/*Signature: Uche Emmanuel
* Developersunesis*/

/**
 * Creates the app database using Room
 * Sets the version to 1
 * and table Symbols using Symbols.class as the entity
 * declare SymbolsDao symbolsDao
 */

@Database(entities = {Symbols.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {
    public abstract SymbolsDao symbolsDao();
}
