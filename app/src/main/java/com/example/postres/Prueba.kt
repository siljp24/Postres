package com.example.postres

fun main (){
   recibirFuncion(::parseStringToInt, 8.5)
    recibirFuncion({ myString, myFloat ->
         myString.toInt()
    }, 8.5)
}


fun recibirFuncion(parametro1: (String, Float) -> Int, parametro2: Double) {
    // Invocar instancia del tipo función
    val numero: Int = parametro1("23", 4.6f)

    println(numero)
}

fun parseStringToInt (value:String, value2:Float): Int{
    return value.toInt() + 100
}