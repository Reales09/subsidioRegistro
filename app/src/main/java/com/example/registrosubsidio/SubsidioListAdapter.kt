package com.example.registrosubsidio

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.registrosubsidio.data.EntidadSubsidio
import com.example.registrosubsidio.data.getFormattedPriceValor
import com.example.registrosubsidio.data.getFormattedPriceValorTotal
import com.example.registrosubsidio.data.getTotal
import com.example.registrosubsidio.databinding.SubsidioListItemBinding

class SubsidioListAdapter (private val onItemClicked: (EntidadSubsidio) -> Unit) :
    ListAdapter<EntidadSubsidio, SubsidioListAdapter.SubsidioViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SubsidioViewHolder {
        return SubsidioViewHolder(
            SubsidioListItemBinding.inflate(
                LayoutInflater.from(
                    parent.context
                )
            )
        )
    }

    override fun onBindViewHolder(holder: SubsidioViewHolder, position: Int) {
        val current = getItem(position)
        holder.itemView.setOnClickListener {
            onItemClicked(current)
        }
        holder.bind(current)
    }

    class SubsidioViewHolder(private var binding: SubsidioListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(subsidio: EntidadSubsidio) {
            binding.apply {
                subsidioNombre.text = subsidio.subsidioNombre
                subsidioValor.text = subsidio.getFormattedPriceValor()
                subsidioNumeroPersonas.text = subsidio.subsidioNumeroPersonas.toString()
                subsidioNumeroHijos.text = subsidio.subsidioNumeroHijos.toString()
                subsidioTotal.text = subsidio.getFormattedPriceValorTotal()
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<EntidadSubsidio>() {
            override fun areItemsTheSame(oldItem: EntidadSubsidio, newItem: EntidadSubsidio): Boolean {
                return oldItem === newItem
            }

            override fun areContentsTheSame(oldItem: EntidadSubsidio, newItem: EntidadSubsidio): Boolean {
                return oldItem.subsidioNombre == newItem.subsidioNombre
            }
        }
    }

}



