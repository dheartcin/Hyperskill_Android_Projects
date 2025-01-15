package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.provider.ContactsContract
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.databinding.FragmentCalculateExchangeBinding

class CalculateExchangeFragment : Fragment() {
    private val sharedViewModel: CurrencyExchangeSharedViewModel by activityViewModels()
    private var _binding: FragmentCalculateExchangeBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCalculateExchangeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Register callback for backbutton pressed
        val activity = requireActivity() as MainActivity
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, activity.callback)

        //get map data from sharedviewmodel
        setupSpinner()
        sharedViewModel.exchangeMap.observe(viewLifecycleOwner, { data ->
            calculate(data)
        })
    }

    fun setupSpinner(){
        binding.apply {
            //add adapter to spinners
            ArrayAdapter.createFromResource(
                requireContext(), R.array.exchange_countries_array,
                android.R.layout.simple_spinner_item
            ).also { adapter ->
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                calculateExchangeFromSpinner.adapter = adapter
                calculateExchangeToSpinner.adapter = adapter
            }

            //set default selection
            calculateExchangeFromSpinner.setSelection(0)
            calculateExchangeToSpinner.setSelection(1)

            val listener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long
                ) {
                    if (calculateExchangeFromSpinner.selectedItem == calculateExchangeToSpinner.selectedItem) {
                        Toast.makeText(requireContext(), getString(R.string.exchange_toast_spinner_same_selection),
                            Toast.LENGTH_SHORT).show()
                        calculateExchangeToSpinner.setSelection((pos + 1) % parent.count)
                    }
                }
                override fun onNothingSelected(parent: AdapterView<*>) {
                }
            }
            calculateExchangeFromSpinner.onItemSelectedListener = listener
            calculateExchangeToSpinner.onItemSelectedListener = listener
        }
    }
    fun calculate(currencyMap: Map<String, Map<String, Double>>) {
        binding.apply {
            calculateExchangeButton.setOnClickListener() {
                calculateExchangeAmountEditText.text.let {
                    if(it.isNullOrEmpty()) {
                        Toast.makeText(requireContext(), getString(R.string.exchange_toast_no_amount),
                            Toast.LENGTH_SHORT).show()
                    } else {
                        try{
                            calculateExchangeDisplayTextView.text = calculateExchange(it.toString().toDouble(), currencyMap, createCurrencySymbolMap())
                        } catch (e: Exception) {
                            Toast.makeText(requireContext(), getString(R.string.exception), Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        }
    }
    fun calculateExchange(amount: Double, currencyMap: Map<String, Map<String, Double>>, currencySymbolMap: Map< String, String>): String {
        val selectedFrom: String = binding.calculateExchangeFromSpinner.selectedItem.toString()
        val selectedTo: String = binding.calculateExchangeToSpinner.selectedItem.toString()

        val result = amount * (currencyMap[selectedFrom]?.get(selectedTo) ?: 0.0)

        //return formatted string
        return getString(R.string.exchange_text_view_result,
            currencySymbolMap[selectedFrom],amount, currencySymbolMap[selectedTo], result)
    }
    fun createCurrencySymbolMap(): Map<String, String> {
        val countriesArray = resources.getStringArray(R.array.exchange_countries_array)
        val currencySymbolArray = resources.getStringArray(R.array.exchange_currency_symbol_array)

        val currencySymbolMap = mutableMapOf<String, String>()
        for(i in countriesArray.indices) {
            currencySymbolMap.put(countriesArray[i], currencySymbolArray[i])
        }
        return currencySymbolMap
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}