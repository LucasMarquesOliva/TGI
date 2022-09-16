package com.faculdade.tgi.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.faculdade.tgi.constants.Constants

@Entity(tableName = Constants.PERSONAL_DATA.TABLE_NAME)
class PersonalDataModel {

    @PrimaryKey
    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.ID)
    var id: Int = 1

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.NAME)
    var name: String = ""

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.AGE)
    var age: Int = 0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.GENDER)
    var gender: Int = 0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.WEIGHT)
    var weight: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.HEIGHT)
    var height: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.BMI)
    var bmi: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.GLUCOSE)
    var glucose: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.INSULIN)
    var insulin: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.BLOOD_PRESSURE)
    var bloodPressure: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.SKIN_THICKNESS)
    var skinThickness: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.DIABETES_PEDIGREE)
    var diabetesPedigree: Double = 0.0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.PREGNANCIES)
    var pregnancies: Int = 0

    @ColumnInfo(name = Constants.PERSONAL_DATA.COLUMNS.OUTCOME)
    var outcome: Int = 0

}