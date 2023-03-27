package com.game.localnotesappkotlin.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.game.localnotesappkotlin.LocalNotesApp
import com.game.localnotesappkotlin.R
import com.game.localnotesappkotlin.adapter.NotesAdapter
import com.game.localnotesappkotlin.database.NotesEntity
import com.game.localnotesappkotlin.databinding.FragmentMainBinding
import com.game.localnotesappkotlin.viewModels.NotesViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment  : Fragment() , NotesAdapter.NoteClickInterface {

    private var _binding : FragmentMainBinding? = null
    private val binding get() = _binding!!

//    private lateinit var notesViewModel: NotesViewModel
    private lateinit var notesAdapter : NotesAdapter

    private val notesViewModel by viewModels<NotesViewModel>()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater,container,false)
    /*    notesViewModel = ViewModelProvider(
            this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application = requireActivity().application as LocalNotesApp)
        )[NotesViewModel::class.java]*/

        setNotesAdapter()
        updateNotes()
        return binding.root
    }

    private fun updateNotes() {
        notesViewModel.allNotes.observe(requireActivity()) { list ->
            list?.let {
                notesAdapter.updateList(it)
            }
        }
    }

    private fun setNotesAdapter() {
        binding.noteList.layoutManager = LinearLayoutManager(context)
        notesAdapter = NotesAdapter(requireContext(), this)
        binding.noteList.adapter = notesAdapter
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.addNote.setOnClickListener{
            findNavController().navigate(R.id.action_mainFragment_to_notesFragment)
        }
    }

    override fun onNoteClick(note: NotesEntity) {
        val bundle = Bundle()
        bundle.putString("note",Gson().toJson(note))
        findNavController().navigate(R.id.action_mainFragment_to_notesFragment,bundle)
    }
}