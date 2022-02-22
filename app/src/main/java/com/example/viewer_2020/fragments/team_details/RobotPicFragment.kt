package com.example.viewer_2022.fragments.team_details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.example.viewer_2022.R
import com.example.viewer_2022.constants.Constants
import com.example.viewer_2022.getTeamDataValue
import kotlinx.android.synthetic.main.robot_pic.view.*
import kotlinx.android.synthetic.main.team_details.view.*
import kotlinx.android.synthetic.main.team_details.view.tv_team_name
import kotlinx.android.synthetic.main.team_details.view.tv_team_number
import java.io.File

class RobotPicFragment : Fragment() {
    private var teamNumber: String? = null
    private var teamName: String? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.robot_pic, container, false)
        populateTeamInfo(root)
        getTeamPic(root)

        return root
    }

    private fun populateTeamInfo(root: View) {
        // If a fragment intent (bundle arguments) exists from the previous activity (MainViewerActivity),
        // then set the team number display on TeamDetails to the team number provided with the intent.

        // If the team number from the MainViewerActivity's match schedule list view cell position
        // is null, the default display will show '0' for the team number on TeamDetails.
        arguments?.let {
            teamNumber = it.getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
            teamName = getTeamDataValue(teamNumber!!, "team_name")
        }
        root.tv_team_number.text = teamNumber.toString()
        root.tv_team_name.text = teamName.toString()
    }

    private fun rotateBitmap(source: Bitmap, degrees: Float): Bitmap {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            source, 0, 0, source.width, source.height, matrix, true
        )
    }

    private fun getTeamPic(root: View){
        val fileName = "${teamNumber}_full_robot"
        val file = File(
            "/storage/emulated/0/${Environment.DIRECTORY_DOWNLOADS}/",
            "$fileName.jpg"
        )

        if (file.exists()) {
            val myBitmap: Bitmap = BitmapFactory.decodeFile(file.absolutePath)
            val rotatedBitmap = rotateBitmap(myBitmap, 90f)
            root.team_pic.setImageBitmap(rotatedBitmap)
        }
    }
}