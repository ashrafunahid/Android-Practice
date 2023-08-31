package com.ibssbd.gobeautify

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.core.content.FileProvider
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.ui.activity.PhotoEditorBuilder
import java.io.File

class OpenPhotoFromCamera(activity: Activity) : AppCompatActivity() {

    companion object {
        private const val CAMERA_REQUEST_CODE = 0x69
        private const val EDITOR_REQUEST_CODE = 123
    }

    private val file = File(activity.cacheDir, "imgly_photo.jpg")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_photo_from_camera)

        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        try {
            val photoURI: Uri = FileProvider.getUriForFile(this, "com.ibssbd.gobeautify.fileprovider", file)
            takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
            this.startActivityForResult(takePictureIntent, CAMERA_REQUEST_CODE)
        } catch (ex: ActivityNotFoundException) {
            showMessage("No Camera app installed")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        when (requestCode) {
            EDITOR_REQUEST_CODE -> {
                intent ?: return
                val result = EditorSDKResult(intent)
                when (result.resultStatus) {
                    EditorSDKResult.Status.CANCELED -> showMessage("Editor cancelled")
                    EditorSDKResult.Status.EXPORT_DONE -> showMessage("Result saved at ${result.resultUri}")
                    else -> {
                    }
                }
            }
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    showEditor(Uri.fromFile(file))
                }
            }
        }
    }

    private fun showEditor(uri: Uri) {
        // In this example, we do not need access to the Uri(s) after the editor is closed
        // so we pass false in the constructor
        val settingsList = PhotoEditorSettingsList(false)
                // Set the source as the Uri of the image to be loaded
                .configure<LoadSettings> {
                    it.source = uri
                }
        // Start the photo editor using PhotoEditorBuilder
        // The result will be obtained in onActivityResult() corresponding to EDITOR_REQUEST_CODE
        PhotoEditorBuilder(this)
                .setSettingsList(settingsList)
                .startActivityForResult(this, EDITOR_REQUEST_CODE)
        // Release the SettingsList once done
        settingsList.release()
    }

    private fun showMessage(s: String) {
        Toast.makeText(applicationContext, s, Toast.LENGTH_SHORT).show();
    }
}