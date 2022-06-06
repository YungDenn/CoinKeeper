package com.example.coinkeeper.data

import android.app.Application
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FinanceItemDbModel::class, CategoryOperationDbModel::class], version = 3, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {

    abstract fun financeListDao(): FinanceListDao

    companion object{

        private var INSTANCE: AppDatabase? = null
        private var LOCK = Any()
        private const val DB_NAME = "finance_item.db"

        //Double Check singleton
        // Если два потока одновременно воспользовались БД ->
        // один из них зашел в INSTANCE Check, а второй в ожидании

        fun getInstance(application: Application): AppDatabase{
            INSTANCE?.let{
                return it
            }
            synchronized(LOCK){
                INSTANCE?.let{
                    return it
                }
                val db = Room.databaseBuilder(
                    application,
                    AppDatabase::class.java,
                    DB_NAME
                ).fallbackToDestructiveMigration().build()
                INSTANCE = db
                return db
            }
        }
    }
}