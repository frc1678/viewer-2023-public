package org.citruscircuits.viewer.fragments.team_details

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import org.citruscircuits.viewer.R
import org.citruscircuits.viewer.constants.Constants
import org.citruscircuits.viewer.getTeamDataValue
import kotlinx.android.synthetic.main.robot_pic.view.*
import kotlinx.android.synthetic.main.team_details.view.tv_team_name
import kotlinx.android.synthetic.main.team_details.view.tv_team_number
import java.io.File


/**
 * Page for showing robot pictures
 * Navigate to this page by clicking on the Picture button in the top right of Team Details
 * The Picture button will only appear if the phone has a picture for the robot
 * Pictures are stored in the file path:
 * storage -> self -> primary -> Android -> data -> com.example.viewer -> files
 */
class RobotPicFragment : Fragment() {
    private var teamNumber: String? = null
    private var teamName: String? = null
    private var picFileFull: File? = null
    private var picFileFront: File? = null
    private var picFileSide: File? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.robot_pic, container, false)
        populateTeamInfo(root)
        getPicFiles()
        showPictures(root)
        return root
    }

    // Sets the image header for the team number and name
    private fun populateTeamInfo(root: View) {
        teamNumber = arguments?.getString(Constants.TEAM_NUMBER, Constants.NULL_CHARACTER)
        teamName = getTeamDataValue(teamNumber!!, "team_name")
        root.tv_team_number.text = teamNumber.toString()
        root.tv_team_name.text = teamName.toString()
    }

    // Gets the picture files from the phone's Viewer data folder
    private fun getPicFiles() {
        picFileFull = File(
            Constants.STORAGE_FOLDER,
            "${teamNumber}_full_robot.jpg"
        )
        picFileFront = File(
            Constants.STORAGE_FOLDER,
            "${teamNumber}_front.jpg"
        )
        picFileSide = File(
            Constants.STORAGE_FOLDER,
            "${teamNumber}_side.jpg"
        )
    }

    // Displays a robot picture for each picture added to the bitmap
    private fun showPictures(root: View) {
        val pictureFilesList = listOf(picFileFull, picFileFront, picFileSide)
        val bitmapsToDisplay = mutableListOf<Bitmap>()

        for (file in pictureFilesList) {
            if (file!!.exists()) {
                bitmapsToDisplay.add(BitmapFactory.decodeFile(file!!.absolutePath).rotated())
            }
        }

        for (bitmap in bitmapsToDisplay) {
            root.ll_robot_pics.addView(ImageView(context).also {
                it.scaleType = ImageView.ScaleType.FIT_START
                it.adjustViewBounds = true
                it.setImageBitmap(bitmap)
            })
        }
    }

    // Rotates the robot pictures to be displayed correctly
    private fun Bitmap.rotated() = Bitmap.createBitmap(
        this, 0, 0, width, height, Matrix().also { it.postRotate(90F) }, true
    )
}
