package com.dev.randomcityapp.util


import com.google.android.gms.maps.model.LatLng

fun getLatLng(city: String): LatLng = when (city) {
    "New York" -> LatLng(40.7128, -74.0060)
    "Los Angeles" -> LatLng(34.0522, -118.2437)
    "Scranton" -> LatLng(41.4089, -75.6624)
    "Philadelphia" -> LatLng(39.9526, -75.1652)
    "Nashville" -> LatLng(36.1627, -86.7816)
    "Saint Louis" -> LatLng(38.6270, -90.1994)
    "Miami" -> LatLng(25.7617, -80.1918)
    else -> LatLng(0.0, 0.0)
}
