package com.ashrafunahid.imglyloadimagefromremoteurl

import android.app.Activity
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.ashrafunahid.imglyloadimagefromremoteurl.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.ui.activity.PhotoEditorBuilder
import java.io.File
import java.net.URL

class OpenPhotoFromRemoteURL : AppCompatActivity() {
    val EDITOR_REQUEST_CODE = 100
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_open_photo_from_remote_url)
        val imagePath = intent.getStringExtra("imagePath")
        if (imagePath != null) {
            saveFileInCacheDir(this, imagePath)
        }
    }

    private fun saveFileInCacheDir(activity: Activity, url: String){
        this.lifecycleScope.launch {
            val file = withContext(Dispatchers.IO) {
                runCatching {
                    val file = File.createTempFile("test", ".jpg")
                    URL(url).openStream().use { input ->
                        file.outputStream().use { output->
                            input.copyTo(output)
                        }
                    }
                    file
                }.getOrNull()
            }
            showEditor(Uri.fromFile(file))
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        intent ?: return
        if (requestCode == EDITOR_REQUEST_CODE) {
            val result = EditorSDKResult(intent)
            when (result.resultStatus) {
                EditorSDKResult.Status.CANCELED -> showMessage("Editor cancelled")
                EditorSDKResult.Status.EXPORT_DONE -> showMessage("Result saved at ${result.resultUri}")
                else -> {
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
        Toast.makeText(this, s,Toast.LENGTH_SHORT).show()
    }
}
