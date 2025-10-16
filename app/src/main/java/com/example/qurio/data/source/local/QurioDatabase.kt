package com.example.qurio.data.source.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Database(entities = [UserCharacterEntity::class, GameEntity::class, QurioEntity::class], version = 1)
abstract class QurioDatabase: RoomDatabase() {
    abstract val qurioDao: QurioDao

    @Module
    object DatabaseModule {
        @Singleton
        @Provides
        fun provideDatabase( context: Context): QurioDatabase{
            return Room.databaseBuilder(
                context.applicationContext,
                QurioDatabase::class.java,
                "MyQurio.db"
            ).fallbackToDestructiveMigration()
                .build()
        }

        @Provides
        fun provideNoteDao(db: QurioDatabase): QurioDao = db.qurioDao
    }

}