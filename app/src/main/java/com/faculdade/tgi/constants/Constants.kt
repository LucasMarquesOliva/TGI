package com.faculdade.tgi.constants

class Constants private constructor() {

    object KEY {
       const val USER_NAME = "USER_NAME"
    }

    object DATABASE {
        const val NAME = "projectdb"
    }

    object CONTACT {
        const val TABLE_NAME = "Contact"
        const val ID = "contactid"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val CELL = "cell"
            const val MAIN = "main"
            const val ACTIVE = "active"
            const val MESSAGE = "message"
            const val LOCALIZATION = "localization"
            const val MESSAGE_TEXT = "messagetext"
        }
    }

    object PERSONAL_DATA {
        const val TABLE_NAME = "PersonalData"
        const val ID = "personid"

        object COLUMNS {
            const val ID = "id"
            const val NAME = "name"
            const val AGE = "age"
            const val GENDER = "gender"
            const val WEIGHT = "weight"
            const val HEIGHT = "height"
            const val BMI = "bmi"
            const val GLUCOSE = "glucose"
            const val INSULIN = "insulin"
            const val BLOOD_PRESSURE = "bloodpressure"
            const val SKIN_THICKNESS = "skinthickness"
            const val DIABETES_PEDIGREE = "diabetespedigree"
            const val PREGNANCIES = "pregnancies"
            const val OUTCOME = "outcome"
        }
    }

    object PERMISSION {
        const val SMS = "SMS"
        const val GPS = "GPS"
    }
}