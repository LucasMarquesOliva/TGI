package com.faculdade.tgi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faculdade.tgi.constants.Constants

@Entity(tableName = Constants.CONTACT.TABLE_NAME)
class ContactModel {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.CONTACT.COLUMNS.ID)
    var id: Int = 0

    @ColumnInfo(name = Constants.CONTACT.COLUMNS.NAME)
    var name: String = ""

    @ColumnInfo(name = Constants.CONTACT.COLUMNS.CELL)
    var cell: String = ""

    @ColumnInfo(name = Constants.CONTACT.COLUMNS.MAIN)
    var main: Boolean = false

    @ColumnInfo(name = Constants.CONTACT.COLUMNS.ACTIVE)
    var active: Boolean = false

    @ColumnInfo(name = Constants.CONTACT.COLUMNS.MESSAGE)
    var message: Boolean = false

    @ColumnInfo(name = Constants.CONTACT.COLUMNS.LOCALIZATION)
    var localization: Boolean = false

    @ColumnInfo(name = Constants.CONTACT.COLUMNS.MESSAGE_TEXT)
    var messagetext: String = ""
}