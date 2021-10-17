package com.example.registrosubsidio.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [EntidadSubsidio::class], version = 1, exportSchema = false)

abstract class SubsidioRoomDatabase : RoomDatabase() {

    abstract fun subsidioDao(): SubsisioDao

    companion object {
        @Volatile
        private var INSTANCE: SubsidioRoomDatabase? = null
        fun getDatabase(context: Context): SubsidioRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SubsidioRoomDatabase::class.java,
                    "subsidio_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }

}