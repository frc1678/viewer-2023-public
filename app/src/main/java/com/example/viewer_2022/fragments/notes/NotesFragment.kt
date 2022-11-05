package com.example.viewer_2022.fragments.notes

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ViewTreeLifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.example.viewer_2022.MainViewerActivity
import com.example.viewer_2022.NotesApi
import com.example.viewer_2022.NotesApi.getNote
import com.example.viewer_2022.NotesApi.setNote
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.google.gson.Gson
import kotlinx.android.synthetic.main.fragment_notes.view.*
import kotlinx.coroutines.launch
import java.lang.Exception

// Finds the lifecycle of the Fragment
fun View.findViewTreeLifecycleOwner(): LifecycleOwner? = ViewTreeLifecycleOwner.get(this)

/**
 * Page that displays strategist notes
 */
class NotesFragment : Fragment() {

    var mode = Mode.VIEW

    var teamNumber: String? = null

    var refreshId: String? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notes, container, false)
        arguments?.let {
            teamNumber = it.getString(Constants.TEAM_NUMBER)
        }
        if (refreshId == null) {
            refreshId = MainViewerActivity.refreshManager.addRefreshListener {
                if (this.mode == Mode.VIEW) {
//                    getNotes(root)
                }
            }
        }

        setupListeners(root)
        getNotes(root)

        return root
    }

    private fun setupListeners(root: View) {
        root.btn_edit_notes.setOnClickListener {
            root.findViewTreeLifecycleOwner()?.lifecycleScope?.let {
                mode = when (mode) {
                    Mode.VIEW -> {
                        setupEditMode(root)
                        Mode.EDIT
                    }
                    Mode.EDIT -> {
                        it.launch {setupViewMode(root)  }
                        Mode.VIEW
                    }
                }
            }

        }

    }
    private fun setupEditMode(root: View) {
        root.btn_edit_notes.setImageResource(R.drawable.ic_baseline_save_24)
        root.et_notes.isEnabled = true
    }

    private suspend fun setupViewMode(root: View) {
        root.btn_edit_notes.setImageResource(R.drawable.ic_baseline_edit_24)
        root.et_notes.isEnabled = false
        val data = SetNotesData(teamNumber!!, root.et_notes.text.toString())
        Log.d("notes", Gson().toJson(data))
        root.btn_edit_notes.isEnabled = false
        try {
            MainViewerActivity.notesCache[teamNumber!!] = root.et_notes.text.toString()
//            setNote(data)
            root.btn_edit_notes.isEnabled = true
        } catch (e: Exception) {
            Log.e("NOTES", "FAILED TO SAVE NOTES. WE JUST LOST DATA. FIX IMMEDIATELY")
        }

    }

    private fun getNotes(root: View) {
        root.btn_edit_notes.isEnabled = false
        try {
            teamNumber?.let {
                root.findViewTreeLifecycleOwner()?.lifecycleScope?.launch {
                    var response = getNote(it)
                    if (response.success) {
                        root.et_notes.setText(response.notes)
                    }
                    root.btn_edit_notes.isEnabled = true
                }
            }
        } catch (e: Exception) {
            Log.e("notes", "FAILED TO FETCH NOTES FOR $teamNumber.")
        }
    }


    override fun onDestroy() {
        super.onDestroy()
        MainViewerActivity.refreshManager.removeRefreshListener(refreshId)
    }

    enum class Mode {
        EDIT,
        VIEW
    }
}

typealias SetNotesData = NotesApi.NotesData

