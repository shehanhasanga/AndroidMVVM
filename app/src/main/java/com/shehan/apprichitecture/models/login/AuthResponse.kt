package com.shehan.apprichitecture.models.login

class AuthResponse(
    val Token:String,
    val UserID : String,
    val ClinicID : String,
    val FirstName : String,
    val MiddleName :String,
    val LastName : String,
    val DateOfBirth : String,
    val Email : String,
    val Phone:String,
    val UserRoleID : Int
) {

}