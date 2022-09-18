package com.dmsh.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.dmsh.data.entities.AlbumItemEntity

@Database(entities = [AlbumItemEntity::class], version = 1, exportSchema = false)
abstract class AlbumDatabase : RoomDatabase() {

    abstract fun albumDao(): AlbumDao

    companion object {
        @Volatile
        private var instance: AlbumDatabase? = null

        fun getDatabase(appContext: Context): AlbumDatabase {
            val tempInstance = instance
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    appContext, AlbumDatabase::class.java,
                    AlbumDatabase::class.simpleName!!
                ).fallbackToDestructiveMigration().build()
                this.instance = instance
                return instance
            }
        }
    }
}