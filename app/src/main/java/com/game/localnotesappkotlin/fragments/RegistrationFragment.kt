package com.game.localnotesappkotlin.fragments

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.game.localnotesappkotlin.LocalNotesApp
import com.game.localnotesappkotlin.R
import com.game.localnotesappkotlin.database.UserEntity
import com.game.localnotesappkotlin.databinding.FragmentRegistrationBinding
import com.game.localnotesappkotlin.viewModels.AuthViewModel

class RegistrationFragment : Fragment() {

    private var _binding : FragmentRegistrationBinding? = null
    private val binding get() = _binding!!

    private lateinit var authViewModel : AuthViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentRegistrationBinding.inflate(inflater,container,false)

        authViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application = requireActivity().application as LocalNotesApp)
        )[AuthViewModel::class.java]

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnLogin.setOnClickListener{
            findNavController().navigate(R.id.action_registrationFragment_to_loginFragment)
        }


        binding.btnSignUp.setOnClickListener {
            binding.apply {
                val userName = txtUsername.text.toString()
                val password = txtPassword.text.toString()
                val email = txtEmail.text.toString()
                if (userName.isNotEmpty() && password.isNotEmpty() && email.isNotEmpty()) {
                    authViewModel.addUser(UserEntity(1, userName, email, password))
                    Toast.makeText(
                        requireContext(),
                        "Registered Successfully..!!",
                        Toast.LENGTH_SHORT
                    )
                        .show()

                    saveDataToSharedPref(userName)
                    findNavController().navigate(R.id.action_registrationFragment_to_mainFragment)
                } else {
                    Toast.makeText(
                        requireContext(),
                        "Fields cannot be empty..",
                        Toast.LENGTH_SHORT
                    )
                }
            }

        }
    }

    private fun saveDataToSharedPref(userName: String) {
        val sharedPref = requireActivity().getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        with(sharedPref?.edit()) {
            this?.putString("userId", getUserIdFromDatabase(userName))
            this?.apply()
        }
    }

    private fun getUserIdFromDatabase(userName: String): String {
        var userId = ""
        val userList : LiveData<List<UserEntity>> = authViewModel.allUserList
        // Observe the LiveData object and iterate over its list of User objects
        userList.observe(viewLifecycleOwner, Observer { userList ->
            for (user in userList) {
                // Do something with each User object, such as print the user's name
                if(user.userName == userName) {
                    userId = user.id.toString()
                }
            }
        })
        return userId
    }

    companion object {

    }
}