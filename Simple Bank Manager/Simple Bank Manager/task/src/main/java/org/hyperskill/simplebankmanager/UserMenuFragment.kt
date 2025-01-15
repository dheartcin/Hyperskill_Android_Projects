package org.hyperskill.simplebankmanager

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.databinding.FragmentUserMenuBinding

class UserMenuFragment : Fragment() {
    private var _binding: FragmentUserMenuBinding? = null
    private val binding get() = _binding!!
    private val loginSharedViewModel: LoginViewModel by activityViewModels()

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentUserMenuBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            loginSharedViewModel.username.observe(viewLifecycleOwner, { data ->
                userMenuWelcomeTextView.text = getString(R.string.welcome_message, data)
            })
            //View Balance Button
            userMenuViewBalanceButton.setOnClickListener() {
                findNavController().navigate(R.id.action_userMenuFragment_to_viewBalanceFragment)
            }
            //Transfer Button
            userMenuTransferFundsButton.setOnClickListener() {
                findNavController().navigate(R.id.action_userMenuFragment_to_transferFundsFragment)
            }
            //Exchange Button
            userMenuExchangeCalculatorButton.setOnClickListener() {
                findNavController().navigate(R.id.action_userMenuFragment_to_calculateExchangeFragment)
            }
            //Pay Bill Button
            userMenuPayBillsButton.setOnClickListener() {
                findNavController().navigate(R.id.action_userMenuFragment_to_payBillsFragment)
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}