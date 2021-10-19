package com.example.registrosubsidio.data

import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
interface SubsidioDao {

    @Insert (onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(entidadSubsidio: EntidadSubsidio)

    @Update
    suspend fun update(entidadSubsidio: EntidadSubsidio)

    @Delete
    suspend fun delete(entidadSubsidio: EntidadSubsidio)

    @Query("SELECT * from datos_subsidio WHERE id = :id")
    fun getItem(id: Int): Flow<EntidadSubsidio>

    @Query("SELECT * from datos_subsidio  ORDER BY nombre ASC")
    fun getItems(): Flow<List<EntidadSubsidio>>

}