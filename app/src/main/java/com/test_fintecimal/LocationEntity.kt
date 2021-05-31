package com.test_fintecimal

import androidx.room.ColumnInfo
import androidx.room.Entity

import androidx.room.PrimaryKey



@Entity
class LocationEntity(@field:ColumnInfo(name = "streetName") var streetName: String,
                     @field:ColumnInfo(name = "suburb") var suburb: String,
                     @field:ColumnInfo(name = "visited") var visited: Boolean,
                     @field:ColumnInfo(name = "latitude") var latitude: Double,
                     @field:ColumnInfo(name = "longitude") var longitude: Double) {
    @PrimaryKey(autoGenerate = true)
    var id = 0

}