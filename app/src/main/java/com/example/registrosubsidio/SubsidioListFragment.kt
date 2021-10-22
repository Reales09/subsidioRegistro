package com.example.registrosubsidio
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.registrosubsidio.databinding.FragmentSubsidioListBinding



class SubsidioListFragment : Fragment() {



    private val viewModel: SubsidioViewModel by activityViewModels {
        SubsidioDaoFactory(
            (activity?.application as SubsidioAplication).database.subsidioDao()
        )
    }

    private var _binding: FragmentSubsidioListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentSubsidioListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val adapter = SubsidioListAdapter {
            val action = SubsidioListFragmentDirections.actionSubsidioListFragmentToSubsidioDetailFragment(it.id)
            this.findNavController().navigate(action)
        }


        binding.recyclerView.adapter = adapter
        viewModel.allItems.observe(this.viewLifecycleOwner) { items ->
            items.let {
                adapter.submitList(it)
            }
        }

        binding.recyclerView.layoutManager = LinearLayoutManager(this.context)
        binding.floatingActionButton.setOnClickListener {
            val action = SubsidioListFragmentDirections.actionSubsidioListFragmentToAgregarSubsidioFragment(getString(R.string.add_fragment_title))
            this.findNavController().navigate(action)
        }
    }

}