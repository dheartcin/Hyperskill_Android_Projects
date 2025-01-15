package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import org.hyperskill.simplebankmanager.databinding.FragmentPayBillsBinding

class PayBillsFragment : Fragment() {
    private val billInfoSharedViewModel: BillInfoSharedViewModel by activityViewModels()
    private val currentBalanceSharedViewModel: CurrentBalanceSharedViewModel by activityViewModels()
    private var _binding: FragmentPayBillsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentPayBillsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Register callback for backbutton pressed
        val activity = requireActivity() as MainActivity
        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, activity.callback)

        binding.apply {
            payBillsShowBillInfoButton.setOnClickListener() {
                billInfoSharedViewModel.billInfo.observe(viewLifecycleOwner, { data ->
                    /*for(key in data.keys){
                        println("$key: ${data[key]?.first},${data[key]?.second}, ${data[key]?.third.toString()} ")
                    }*/
                    payBills(data)
                })
            }
        }
    }
    fun payBills(billInfo: Map<String, Triple<String, String, Double>>){
        binding.apply {
            if (!validateBillCode(billInfo)) {
                IncorrectBillCodeDialogFragment().show(childFragmentManager, IncorrectBillCodeDialogFragment.TAG)
            } else {
                payBillsCodeInputEditText.text.toString().let {
                    val bundle = Bundle()
                    bundle.putString(getString(R.string.pay_bill_key_bill_info), billInfo[it]?.first)
                    bundle.putString(getString(R.string.pay_bill_key_bill_code), billInfo[it]?.second)
                    billInfo[it]?.third?.let { it1 -> bundle.putDouble(getString(R.string.pay_bill_key_bill_amount), it1) }

                    val correctBillCodeDialogFragment = CorrectBillCodeDialogFragment()
                    correctBillCodeDialogFragment.arguments = bundle
                    correctBillCodeDialogFragment.show(childFragmentManager, CorrectBillCodeDialogFragment.TAG)

                    childFragmentManager.setFragmentResultListener(getString(R.string.pay_bill_request_key), viewLifecycleOwner) { key, bundle ->
                        val result = bundle.getString(getString(R.string.pay_bill_result))
                        if(result == getString(R.string.pay_bill_confirm))
                            billInfo[it]?.let { it1 -> makePayment(it1) }
                    }
                }
            }
        }
    }
    fun validateBillCode(billInfo: Map<String, Triple<String, String, Double>>): Boolean{
        binding.apply{
            payBillsCodeInputEditText.text.let {
                if(it.isNullOrEmpty()) return false
                else return (billInfo.containsKey(it.toString()))
            }
        }
    }
    fun makePayment(billInfo: Triple<String, String, Double>){
        var currentBalance: Double = 0.0
        currentBalanceSharedViewModel.balance.observe(viewLifecycleOwner, {data ->
            currentBalance = data
        })
        billInfo.let {
            if(it.third <= currentBalance){
                currentBalanceSharedViewModel.updateBalance(currentBalance - it.third)
                Toast.makeText(requireContext(), getString(R.string.pay_bill_successful, it.first), Toast.LENGTH_SHORT).show()
            } else {
                InsufficientFundDialogFragment().show(childFragmentManager, InsufficientFundDialogFragment.TAG)
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}