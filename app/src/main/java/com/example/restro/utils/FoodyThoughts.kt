package com.example.restro.utils

/*
* Foodythoughts class responsible for generating foody quotes to engage customers while loading UI
* */
object FoodyThoughts {

    fun displayFoodThoughts():String{
        val list = arrayListOf<String>()
        list.apply {
            add("WAKE UP !! Its Food o'clock....")
            add("FOOD is the ingredient to bind us together....")
            add("Sugar , spice and everything nice....")
            add("HOME is where FOOD is....")
            add("EAT , DRINK , ORDER , REPEAT ....")
            add("There is no sincerer love, than love of FOOD....")
        }
        list.shuffle()
        return list[0]
    }

}