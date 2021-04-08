package com.example.restro.dashboard.dataModel

/*
*
* RestroModel consists of the data class based on the JSON response obtained from url*/
data class RestroModel(
    val html_attributions : List<String>,
    val next_page_token : String,
    val results : List<Results>,
    val status : String)

data class Results (
    val business_status : String,
    val geometry : Geometry,
    val icon : String,
    val name : String,
    val opening_hours : Opening_hours,
    val photos : List<Photos>,
    val place_id : String,
    val plus_code : Plus_code,
    val rating : Double,
    val reference : String,
    val scope : String,
    val types : List<String>,
    val user_ratings_total : Int,
    val vicinity : String
)

data class Geometry (
    val location : Location,
    val viewport : Viewport
)

data class Opening_hours (
    val open_now : Boolean
)
data class Location (
    val lat : Double,
    val lng : Double
)
data class Photos (
    val height : Int,
    val html_attributions : List<String>,
    val photo_reference : String,
    val width : Int
)

data class Plus_code (
    val compound_code : String,
    val global_code : String
)

data class Viewport (
    val northeast : Northeast,
    val southwest : Southwest
)

data class Southwest (val lat : Double,
                      val lng : Double
)
data class Northeast ( val lat : Double,
                       val lng : Double
)