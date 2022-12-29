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
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.data.NotesApi
import kotlinx.android.synthetic.main.fragment_notes.view.*
import kotlinx.coroutines.launch

// Finds the lifecycle of the Fragment
//fun View.findViewTreeLifecycleOwner(): LifecycleOwner? = ViewTreeLifecycleOwner.get(this)

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
                    getNotes(root)
                }
            }
        }

        setupListeners(root)
        getNotes(root)

        return root
    }

    private fun setupListeners(root: View) {
        root.btn_edit_notes.setOnClickListener {
                mode = when (mode) {
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

    private fun setupEditMode(root: View) {
        root.btn_edit_notes.setImageResource(R.drawable.ic_baseline_save_24)
        root.et_notes.isEnabled = true
    }

    private fun setupViewMode(root: View) {
        root.btn_edit_notes.setImageResource(R.drawable.ic_baseline_edit_24)
        root.et_notes.isEnabled = false
        root.btn_edit_notes.isEnabled = false
        try {
            lifecycleScope.launch {
                teamNumber?.let {
                    val notes = root.et_notes.text.toString()
                    MainViewerActivity.notesCache[teamNumber!!] = notes
                    NotesApi.set(Constants.EVENT_KEY, it, notes)
                    root.btn_edit_notes.isEnabled = true
                }
            }

        } catch (e: Exception) {
            Log.e("NOTES", "FAILED TO SAVE NOTES. WE JUST LOST DATA. FIX IMMEDIATELY")
        }

    }

    private fun getNotes(root: View) {
        root.btn_edit_notes.isEnabled = false
        try {
            teamNumber?.let {
                lifecycleScope.launch {
                    val response = NotesApi.get(Constants.EVENT_KEY, it)
                    root.et_notes.setText(response.notes)
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

