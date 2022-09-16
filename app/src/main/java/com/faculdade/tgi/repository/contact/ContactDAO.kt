package com.faculdade.tgi.repository.contact

import androidx.room.*
import com.faculdade.tgi.model.ContactModel

@Dao
interface ContactDAO {

    @Insert
    fun insert(contact: ContactModel): Long

    @Update
    fun update(contact: ContactModel): Int

    @Delete
    fun delete(contact: ContactModel)

    @Query("SELECT * FROM Contact WHERE id = :id")
    fun get(id: Int): ContactModel

    @Query("SELECT * FROM Contact WHERE main = :main")
    fun getMain(main: Boolean = true): ContactModel

    @Query("SELECT * FROM Contact WHERE active = :active")
    fun getActive(active: Boolean = true): List<ContactModel>

    @Query("SELECT * FROM Contact")
    fun getAll(): List<ContactModel>
}