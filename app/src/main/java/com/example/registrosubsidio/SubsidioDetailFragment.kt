package com.example.registrosubsidio
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.registrosubsidio.data.EntidadSubsidio
import com.example.registrosubsidio.data.getFormattedPriceValor
import com.example.registrosubsidio.databinding.FragmentSubsidioDetailBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder

lateinit var subsidio: EntidadSubsidio

class SubsidioDetailFragment : Fragment() {


private val viewModel: SubsidioViewModel by activityViewModels{
    SubsidioDaoFactory(
        (activity?.application as SubsidioAplication).database.subsidioDao()
    )
}
    private val navigationArgs: SubsidioDetailFragmentArgs by navArgs()

    private var _binding: FragmentSubsidioDetailBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSubsidioDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun bind(subsidio: EntidadSubsidio) {
        binding.apply {
            subsidioNombre.text = subsidio.subsidioNombre
            subsidioValor.text = subsidio.getFormattedPriceValor()
            numeroPersonas.text = subsidio.subsidioNumeroPersonas.toString()
            numeroHijos.text =subsidio.subsidioNumeroHijos.toString()


            deleteItem.setOnClickListener { showConfirmationDialog() }
            editItem.setOnClickListener { editItem() }
        }
    }

    private fun showConfirmationDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(android.R.string.dialog_alert_title))
            .setMessage(getString(R.string.delete_question))
            .setCancelable(false)
            .setNegativeButton(getString(R.string.no)) { _, _ -> }
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                deleteSubsidio()
            }
            .show()
    }

    private fun deleteSubsidio() {
        viewModel.deleteItem(subsidio)
        findNavController().navigateUp()
    }


    private fun editItem() {
        val action = SubsidioDetailFragmentDirections.actionSubsidioDetailFragmentToAgregarSubsidioFragment(
            getString(R.string.edit_fragment_title),
            subsidio.id
        )
        this.findNavController().navigate(action)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = navigationArgs.subsidioId
        viewModel.retrieveItem(id).observe(this.viewLifecycleOwner) {selectedItem ->
            subsidio = selectedItem
            bind(subsidio)
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}