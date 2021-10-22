package com.example.registrosubsidio

import androidx.lifecycle.*
import com.example.registrosubsidio.data.EntidadSubsidio
import com.example.registrosubsidio.data.SubsidioDao
import kotlinx.coroutines.launch
import java.sql.Timestamp

class SubsidioViewModel (private val subsidioDao: SubsidioDao): ViewModel() {

    val allItems: LiveData<List<EntidadSubsidio>> = subsidioDao.getItems().asLiveData()

    private fun insertSubsidio(entidadSubsidio: EntidadSubsidio){
        viewModelScope.launch {
            subsidioDao.insert(entidadSubsidio)
        }
    }

    fun retrieveItem(id: Int): LiveData<EntidadSubsidio> {
        return subsidioDao.getItem(id).asLiveData()
    }

    private fun updateSubsidio(subsidio: EntidadSubsidio) {
        viewModelScope.launch {
            subsidioDao.update(subsidio)
        }
    }
    private fun getNewSubsidioEntry(subsidioNombre: String, subsidioValor: String,subsidioNumeroPersonas: String, subsidioNumeroHijos: String): EntidadSubsidio {
        return EntidadSubsidio(
            subsidioNombre = subsidioNombre,
            subsidioValor=subsidioValor.toDouble(),
            subsidioNumeroPersonas = subsidioNumeroPersonas.toInt(),
            subsidioNumeroHijos = subsidioNumeroHijos.toInt(),


            )
    }

    fun total (subsidioNombre: String,subsidioValor: Double,subsidioNumeroPersonas: Int, subsidioNumeroHijos: Int):EntidadSubsidio{


        return EntidadSubsidio(
            subsidioNombre = subsidioNombre,
            subsidioValor=subsidioValor,
            subsidioNumeroPersonas = subsidioNumeroPersonas,
            subsidioNumeroHijos = subsidioNumeroHijos


            )
        var total = subsidioValor / subsidioNumeroPersonas * subsidioNumeroHijos
    }



    fun deleteItem(subsidio: EntidadSubsidio) {
        viewModelScope.launch {
            subsidioDao.delete(subsidio)
        }
    }

    private fun getUpdatedSubsidioEntry(
        id: Int,
        subsidioNombre: String,
        subsidioValor: String,
        subsidioNumeroPersonas: String,
        subsidioNumeroHijos: String,


    ): EntidadSubsidio {
        return EntidadSubsidio(
            id = id,
            subsidioNombre = subsidioNombre,
            subsidioValor = subsidioValor.toDouble(),
            subsidioNumeroPersonas = subsidioNumeroPersonas.toInt(),
            subsidioNumeroHijos = subsidioNumeroHijos.toInt()

        )
    }

    fun updateSubsidio(
        id: Int,
        subsidioNombre: String,
        subsidioValor: String,
        subsidioNumeroPersonas: String,
        subsidioNumeroHijos: String,
    ) {

        val updatedSubsidio= getUpdatedSubsidioEntry(id, subsidioNombre, subsidioValor, subsidioNumeroPersonas,subsidioNumeroHijos)
        updateSubsidio(updatedSubsidio)
    }

    fun addNewSubsidio(subsidioNombre:String,subsidioValor: String,subsidioNumeroPersonas: String,subsidioNumeroHijos: String ) {

        val newSubsidio = getNewSubsidioEntry(subsidioNombre,subsidioValor,subsidioNumeroPersonas,subsidioNumeroHijos)
        insertSubsidio(newSubsidio)

    }

    fun isEntryValid(subsidioNombre: String, subsidioValor: String, subsidioNumeroPersonas: String, subsidioNumeroHijos: String): Boolean {
        if (subsidioNombre.isBlank() || subsidioValor.isBlank() || subsidioNumeroPersonas.isBlank() || subsidioNumeroHijos.isBlank() ) {
            return false
        }
        return true
    }

}


class SubsidioDaoFactory(private val subsidioDao: SubsidioDao) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SubsidioViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SubsidioViewModel(subsidioDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}