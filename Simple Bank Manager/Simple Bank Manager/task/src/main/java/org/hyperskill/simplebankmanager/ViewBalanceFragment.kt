package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import java.util.Locale
import org.hyperskill.simplebankmanager.databinding.FragmentViewBalanceBinding


class ViewBalanceFragment : Fragment() {
    private var _binding: FragmentViewBalanceBinding? = null
    private val binding get() = _binding!!
    private val sharedViewModel: CurrentBalanceSharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentViewBalanceBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Register callback for backbutton pressed
        val activity = requireActivity() as MainActivity
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, activity.callback)

        binding.apply {
            //Format balance value and update the textview in the fragment
            sharedViewModel.balance.observe(viewLifecycleOwner, {data ->
                viewBalanceAmountTextView.text = String.format(Locale.US, "$%.2f", data)})

        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}