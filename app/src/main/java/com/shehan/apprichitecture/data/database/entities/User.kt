package com.shehan.apprichitecture.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
class User(
    @PrimaryKey
    var userId: String,

    @ColumnInfo(name = "clinicID")
    var clinicID: String,

    @ColumnInfo(name = "locationId")
    var locationId: String? = null,

    @ColumnInfo(name = "location")
    var location: String? = null,

    @ColumnInfo(name = "firstName")
    var firstName: String? = null,

    @ColumnInfo(name = "lastName")
    var lastName: String? = null,

    @ColumnInfo(name = "dateOfBirth")
    var dateOfBirth: String? = null,

    @ColumnInfo(name = "email")
    var email: String? = null,

    @ColumnInfo(name = "isLogged")
    var isLogged: Boolean = false,

    @ColumnInfo(name = "token")
    var token: String? = null,

    @ColumnInfo(name = "password")
    var password: String? = null,

    @ColumnInfo(name = "userRoleID")
    var userRoleID: Int = 0


) {


}