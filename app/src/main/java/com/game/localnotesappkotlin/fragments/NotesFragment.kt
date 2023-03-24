package com.game.localnotesappkotlin.fragments

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import android.view.ViewGroup
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.game.localnotesappkotlin.LocalNotesApp
import androidx.navigation.fragment.findNavController
import com.game.localnotesappkotlin.database.NotesEntity
import com.game.localnotesappkotlin.viewModels.NotesViewModel
import com.game.localnotesappkotlin.databinding.FragmentNotesBinding
import com.google.gson.Gson

class NotesFragment : Fragment() {

    private var _binding: FragmentNotesBinding? = null
    private val binding get() = _binding!!

    private lateinit var notesEntity: NotesEntity
    private lateinit var notesViewModel: NotesViewModel



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentNotesBinding.inflate(inflater, container, false)

        notesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application = requireActivity().application as LocalNotesApp)
        )[NotesViewModel::class.java]

        setIntialData()

        return binding.root
    }

    private fun setIntialData() {
        val jsonNote = arguments?.getString("note")

        if (jsonNote != null) {
            binding.btnDelete.visibility = View.VISIBLE
            notesEntity = Gson().fromJson(jsonNote, NotesEntity::class.java)
            notesEntity?.let {
                binding.apply {
                    txtTitle.setText(it.noteTitle)
                    txtDescription.setText(it.noteDescription)
                }
            }
        } else {
            binding.btnDelete.visibility = View.INVISIBLE
            binding.addEditText.text = "Add Note"
        }
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.apply {
            btnSubmit.setOnClickListener {
                val operation = (addEditText.text.toString() == "Add Note")
                val tile = txtTitle.text.toString()
                val description = txtDescription.text.toString()
                if (tile.isNotEmpty() || description.isNotEmpty()) {
                    if(operation) {
                        val note = NotesEntity(0, getUserIDFromPref()!!,tile, description)
                        notesViewModel.addNote(note)
                    } else {
                        val note = NotesEntity(notesEntity.id,getUserIDFromPref()!!, tile, description)
                        notesViewModel.updateNote(note)
                    }
                    handleSubmitButtonClick("Note saved successfully..!!")
                    findNavController().popBackStack()
                } else {
                    Toast.makeText(context, "Fields cannot be empty", Toast.LENGTH_SHORT).show()
                }
            }

            btnDelete.setOnClickListener{
                notesViewModel.deleteNote(notesEntity)
                handleSubmitButtonClick("Note Deleted successfully..!!")
                findNavController().popBackStack()
            }
        }
    }

    private fun getUserIDFromPref(): String? {
        val sharedPref = context?.getSharedPreferences("MyApp", Context.MODE_PRIVATE)
        return sharedPref?.getString("userId", "")
    }

    private fun handleSubmitButtonClick(message : String) {
        Toast.makeText(context, "$message", Toast.LENGTH_SHORT).show()
    }

}