package com.ibssbd.gobeautify

import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.PhotoEditorSaveSettings
import ly.img.android.pesdk.ui.activity.PhotoEditorBuilder

class OpenPhotoFromGallery : AppCompatActivity() {

    companion object {
        private const val GALLERY_REQUEST_CODE = 0x69
        private const val EDITOR_REQUEST_CODE = 100
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_photo_from_gallery)

        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        try {
            startActivityForResult(intent, GALLERY_REQUEST_CODE)
        } catch (ex: ActivityNotFoundException) {
            showMessage("No Gallery app installed")
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        intent ?: return
        when (requestCode) {
            EDITOR_REQUEST_CODE -> {
                val result = EditorSDKResult(intent)
                Log.i("Editor Result", result.resultUri.toString())
                when (result.resultStatus) {
                    EditorSDKResult.Status.CANCELED -> showMessage("Editor cancelled")
                    EditorSDKResult.Status.EXPORT_DONE -> showMessage("Result saved at ${result.resultStatus}")
                    EditorSDKResult.Status.EXPORT_DONE -> finish()
                    else -> {
                    }
                }
            }
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    intent.data?.let { showEditor(it) } ?: showMessage("Invalid Uri")
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    showMessage("User cancelled selection")
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
                .configure<PhotoEditorSaveSettings> {
//                    it.setOutputToGallery("GoBeautify", "beautify_<yyMMddHHmmss>")
                    it.setOutputToGallery("Pictures", "beautify_<yyMMddHHmmss>")
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