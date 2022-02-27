package com.example.viewer_2022.fragments.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.viewer_2022.R
import com.example.viewer_2022.data.PostRequestTask
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_notes.view.*
import java.net.HttpURLConnection
import java.net.URL

class NotesFragment : Fragment() {

    var mode = Mode.VIEW

    var team_number: String? = "1678"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notes, container, false)

        setupListeners(root)

        return root
    }

    private fun setupListeners(root: View) {
        root.btn_edit_notes.setOnClickListener {
            mode = when(mode) {
                Mode.VIEW -> {
                    setupEditMode(root)
                    Mode.EDIT
                }
                Mode.EDIT -> {
                    setupViewMode(root)
                    Mode.VIEW
                }
            }
        }
    }

    private fun setupEditMode(root: View){
        root.btn_edit_notes.setImageResource(R.drawable.ic_baseline_save_24)
        root.et_notes.isEnabled = true
    }

    private fun setupViewMode(root: View){
        root.btn_edit_notes.setImageResource(R.drawable.ic_baseline_edit_24)
        root.et_notes.isEnabled = false
        val data = SetNotesData(team_number!!, root.et_notes.text.toString())
        Log.d("notes", Gson().toJson(data))
        PostRequestTask("notes/", Gson().toJson(data)).execute()
    }

    enum class Mode {
        EDIT,
        VIEW
    }
}

data class SetNotesData(val team_number: String, val notes: String)