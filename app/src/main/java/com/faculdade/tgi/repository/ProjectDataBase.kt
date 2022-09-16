package com.faculdade.tgi.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.faculdade.tgi.constants.Constants
import com.faculdade.tgi.model.ContactModel
import com.faculdade.tgi.model.PersonalDataModel
import com.faculdade.tgi.repository.contact.ContactDAO
import com.faculdade.tgi.repository.personaldata.PersonalDataDAO

@Database(entities = [ContactModel::class, PersonalDataModel::class], version = 1)
abstract class ProjectDataBase : RoomDatabase() {

    abstract fun contactDAO(): ContactDAO
    abstract fun personalDataDAO(): PersonalDataDAO

    companion object {
        private lateinit var INSTANCE: ProjectDataBase

        fun getDataBase(context: Context): ProjectDataBase {
            if (!Companion::INSTANCE.isInitialized) {
                synchronized(ProjectDataBase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context,
                        ProjectDataBase::class.java,
                        Constants.DATABASE.NAME,
                    ).addMigrations(MIGRATION_1_2).allowMainThreadQueries().build()
                }
            }
            return INSTANCE
        }

        private val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("DELETE FROM Contact")
                database.execSQL("DELETE FROM PersonalData")
            }
        }
    }

}