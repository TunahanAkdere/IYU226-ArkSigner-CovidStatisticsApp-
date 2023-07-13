package com.tunahanakdere.covidvakauygulama.model

import java.io.Serializable
//Model (VeriModeli)
//DataModelItem.kt

data class DataModelItem(
    val country: String,
    val infected: Int,
    val deceased: String,
    val tested: String,
    val recovered: String,
    val lastUpdatedApify: String,
    val lastUpdatedSource: String,
    val sourceUrl: String
) : Serializable