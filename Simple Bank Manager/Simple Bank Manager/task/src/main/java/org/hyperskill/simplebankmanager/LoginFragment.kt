package org.hyperskill.simplebankmanager

import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import org.hyperskill.simplebankmanager.databinding.FragmentLoginBinding

class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val loginSharedViewModel: LoginViewModel by activityViewModels()
    private val loginBundle = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            //hadle login shared viewmodel data here
            loginSharedViewModel.username.observe(viewLifecycleOwner, { data ->
                loginBundle.putString(getString(R.string.username), data)
            })
            loginSharedViewModel.password.observe(viewLifecycleOwner, { data ->
                loginBundle.putString(getString(R.string.password), data)
            })
            //handle user login prompt
            loginButton.setOnClickListener() {
                val userInputBundle = bundleOf(
                    getString(R.string.username) to loginUsername.getText().toString(),
                    getString(R.string.password) to loginPassword.getText().toString()
                )
                val loginResult = authentication(loginBundle, userInputBundle, getString(R.string.username), getString(R.string.password))
                showLoginResult(loginResult)
                //handle login result
                if(loginResult){
                    //switch to user menu fragment and send username as bundle
                    findNavController().navigate(R.id.action_loginFragment_to_userMenuFragment,
                        createUsernameBundle(userInputBundle.getString(getString(R.string.username))))
                }
            }
        }
    }

    fun authentication(firstBundle: Bundle, secondBundle: Bundle, firstKey: String, secondKey: String): Boolean {
        return ((firstBundle.getString(firstKey) == secondBundle.getString(firstKey))&&
                (firstBundle.getString(secondKey) == secondBundle.getString(secondKey)))
    }

    fun showLoginResult(result: Boolean){
        val duration = Toast.LENGTH_SHORT
        if(result) Toast.makeText(requireContext(), getString(R.string.login_success_message), duration).show()
        else Toast.makeText(requireContext(), getString(R.string.login_fail_message), duration).show()
    }

    fun createUsernameBundle(username: String?):Bundle{
        return Bundle().apply {putString(getString(R.string.username), username) }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}