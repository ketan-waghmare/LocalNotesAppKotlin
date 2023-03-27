package com.game.localnotesappkotlin.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.game.localnotesappkotlin.LocalNotesApp
import com.game.localnotesappkotlin.R
import com.game.localnotesappkotlin.database.UserEntity
import com.game.localnotesappkotlin.databinding.FragmentLoginBinding
import com.game.localnotesappkotlin.viewModels.AuthViewModel
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

//    private lateinit var authViewModel: AuthViewModel

    private val authViewModel by viewModels<AuthViewModel>()
    private lateinit var userList : LiveData<List<UserEntity>>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener {
            binding.apply {

                if(txtEmail.text.toString().isNotEmpty() && txtPassword.text.toString().isNotEmpty()) {
                    userList = authViewModel.allUserList
                    userList.observe(viewLifecycleOwner) { userList ->
                        for (user in userList) {
                            // Do something with each User object, such as print the user's name
                            if (user.email == txtEmail.text.toString()) {
                                saveDataToPref(txtEmail.text.toString())
                                findNavController().navigate(R.id.action_loginFragment_to_mainFragment)
                            }
                        }
                        Toast.makeText(
                            requireContext(),
                            "Please create account first.",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Fields cannot be empty",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }



        }

        binding.btnSignUp.setOnClickListener {
            findNavController().navigate(R.id.logintoregister)
        }
    }

    private fun saveDataToPref(email: String) {
        val sharedPref = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.putString("userId", getUserIdFromDatabase(email))
            this?.apply()
        }
    }

    private fun getUserIdFromDatabase(userEmail: String): String {
        var userId = ""
        val userList : LiveData<List<UserEntity>> = authViewModel.allUserList
        // Observe the LiveData object and iterate over its list of User objects
        userList.observe(viewLifecycleOwner) { userList ->
            for (user in userList) {
                // Do something with each User object, such as print the user's name
                if (user.email == userEmail) {
                    userId = user.id.toString()
                }
            }
        }
        return userId
    }

}