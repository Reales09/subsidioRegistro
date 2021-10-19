package com.example.registrosubsidio.data

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Timestamp
import java.text.NumberFormat


@Entity(tableName = "datos_subsidio")
data class EntidadSubsidio (

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo(name = "nombre")
    val subsidioNombre: String,
    @ColumnInfo(name = "valor")
    val subsidioValor: Double,
    @ColumnInfo(name = "numero_personas")
    val subsidioNumeroPersonas: Int,
    @ColumnInfo(name = "numero_hijos")
    val subsidioNumeroHijos: Int,
    @ColumnInfo(name = "total")
    val subsidioTotal: Double,


        )

fun EntidadSubsidio.getFormattedPriceValor(): String = java.text.NumberFormat.getCurrencyInstance().format(subsidioValor)

fun EntidadSubsidio.getFormattedPriceTotal(): String = java.text.NumberFormat.getCurrencyInstance().format(subsidioTotal)

