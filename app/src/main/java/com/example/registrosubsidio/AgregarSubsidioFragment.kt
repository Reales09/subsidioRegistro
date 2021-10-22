package com.example.registrosubsidio

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.registrosubsidio.data.EntidadSubsidio
import com.example.registrosubsidio.databinding.FragmentAgregarSubsidioBinding


class AgregarSubsidioFragment : Fragment() {

    private val viewModel: SubsidioViewModel by activityViewModels{
        SubsidioDaoFactory(
            (activity?.application as SubsidioAplication).database
                .subsidioDao()
        )
    }


    lateinit var subsidio: EntidadSubsidio
    private val navigationArgs: SubsidioDetailFragmentArgs by navArgs()




    private var _binding: FragmentAgregarSubsidioBinding? = null
    private val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentAgregarSubsidioBinding.inflate(inflater, container, false)
        return binding.root
    }


    private fun isEntryValid(): Boolean{
        return viewModel.isEntryValid(
            binding.subsidioNombre.text.toString(),
            binding.subsidioValor.text.toString(),
            binding.subsidioNumeroPersonas.text.toString(),
            binding.subsidioNumeroHijos.text.toString()
        )

    }

    private fun addNewSubsidio(){
        if (isEntryValid()){
            viewModel.addNewSubsidio(
                binding.subsidioNombre.text.toString(),
                binding.subsidioValor.text.toString(),
                binding.subsidioNumeroPersonas.text.toString(),
                binding.subsidioNumeroHijos.text.toString()
            )
            val action = AgregarSubsidioFragmentDirections.actionAgregarSubsidioFragmentToSubsidioListFragment()
            findNavController().navigate(action)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val id = navigationArgs.subsidioId
        if (id > 0) {
            viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) { selectedItem ->
                subsidio = selectedItem
                bind(subsidio)
            }
        } else {
            binding.guardar.setOnClickListener {
                addNewSubsidio()
            }
        }

    }

    private fun updateItem() {
        if (isEntryValid()) {
            viewModel.updateSubsidio(
                this.navigationArgs.subsidioId,
                this.binding.subsidioNombre.text.toString(),
                this.binding.subsidioValor.text.toString(),
                this.binding.subsidioNumeroPersonas.text.toString(),
                this.binding.subsidioNumeroHijos.text.toString()
            )
            val action = AgregarSubsidioFragmentDirections.actionAgregarSubsidioFragmentToSubsidioListFragment()
            findNavController().navigate(action)
        }
    }

    private fun bind(subsidio: EntidadSubsidio) {

        val valor = "%.2f".format(subsidio.subsidioValor)

        binding.apply {
            subsidioNombre.setText(subsidio.subsidioNombre, TextView.BufferType.SPANNABLE)
            subsidioValor.setText(valor, TextView.BufferType.SPANNABLE)
            subsidioNumeroPersonas.setText(subsidio.subsidioNumeroPersonas.toString(), TextView.BufferType.SPANNABLE)
            subsidioNumeroHijos.setText(subsidio.subsidioNumeroHijos.toString(), TextView.BufferType.SPANNABLE)


            guardar.setOnClickListener { updateItem() }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        // Hide keyboard.
        val inputMethodManager = requireActivity().getSystemService(Context.INPUT_METHOD_SERVICE) as
                InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(requireActivity().currentFocus?.windowToken, 0)
        _binding = null
    }


}

