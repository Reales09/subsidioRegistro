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

    private fun getNewSubsidioEntry(subsidioNombre: String, subsidioValor: Double,subsidioNumeroPersonas: Int, subsidioNumeroHijos: Int, subsidioTotal:Double): EntidadSubsidio {
        return EntidadSubsidio(
            subsidioNombre = subsidioNombre,
            subsidioValor=subsidioValor.toDouble(),
            subsidioNumeroPersonas = subsidioNumeroPersonas.toInt(),
            subsidioNumeroHijos = subsidioNumeroHijos.toInt(),
            subsidioTotal = subsidioTotal.toDouble()

        )
    }

    fun addNewSubsidio(subsidioNombre:String,subsidioValor: Double,subsidioNumeroPersonas: Int,subsidioNumeroHijos: Int,subsidioTotal:Double   ){

        val newSubsidio = getNewSubsidioEntry(subsidioNombre,subsidioValor,subsidioNumeroPersonas,subsidioNumeroHijos,subsidioTotal)
        insertSubsidio(newSubsidio)

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