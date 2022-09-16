package com.faculdade.tgi.repository.personaldata

import androidx.room.*
import com.faculdade.tgi.model.PersonalDataModel

@Dao
interface PersonalDataDAO {

    @Insert
    fun insert(person: PersonalDataModel): Long

    @Update
    fun update(person: PersonalDataModel): Int

    @Delete
    fun delete(person: PersonalDataModel)

    @Query("SELECT * FROM PersonalData WHERE id = :id")
    fun get(id: Int): PersonalDataModel

}