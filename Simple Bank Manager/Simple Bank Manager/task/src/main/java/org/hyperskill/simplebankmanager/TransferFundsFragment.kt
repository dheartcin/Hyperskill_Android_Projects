package org.hyperskill.simplebankmanager

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.text.isDigitsOnly
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.databinding.FragmentTransferFundsBinding

private const val SAVINGS_ACCOUNT_START = "sa"
private const val CHECKINGS_ACCOUNT_START = "ca"

class TransferFundsFragment : Fragment() {
    private val sharedViewModel: CurrentBalanceSharedViewModel by activityViewModels()
    private var _binding: FragmentTransferFundsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentTransferFundsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        //Register callback for backbutton pressed
        val activity = requireActivity() as MainActivity
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, activity.callback)

        binding.apply {
            var currentBalance: Double = 0.0
            transferFundsButton.setOnClickListener() {
                sharedViewModel.balance.observe(viewLifecycleOwner, {data ->
                    currentBalance = data })

                if(validateAccount() && validateAmount()) {
                    transferFunds(currentBalance)
                }
            }
        }
    }
    fun validateAccount(): Boolean {
        binding.apply {
            transferFundsAccountEditText.text.toString().let {
                if(((it.startsWith(SAVINGS_ACCOUNT_START)) || (it.startsWith(CHECKINGS_ACCOUNT_START))) &&
                    (it.length == 6) && it.substring(2).isDigitsOnly()){
                    return true
                } else {
                    transferFundsAccountEditText.error = getString(R.string.error_invalid_account_number)
                    return false
                }
            }
        }
    }
    fun validateAmount(): Boolean {
        binding.apply {
            transferFundsAmountEditText.text.toString().let {
                if(!TextUtils.isEmpty(it) && it.toDouble() > 0.0){
                    return true
                } else {
                    transferFundsAmountEditText.error = getString(R.string.error_invalid_amount)
                    return false
                }
            }
        }
    }
    fun transferFunds(currentBalance: Double) {
        //check current balance value in shared view model
        binding.apply{
            val account = transferFundsAccountEditText.text.toString()
            val amount = transferFundsAmountEditText.text.toString().toDouble()

            amount.let{
                if(currentBalance < it) {
                    Toast.makeText(requireContext(), getString(R.string.error_not_enough_funds, it),
                        Toast.LENGTH_SHORT).show()
                } else {
                    sharedViewModel.updateBalance(currentBalance - it)
                    Toast.makeText(requireContext(), getString(R.string.success_transfer, it, account), Toast.LENGTH_SHORT).show()
                    findNavController().navigate(R.id.action_transferFundsFragment_to_userMenuFragment)
                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

