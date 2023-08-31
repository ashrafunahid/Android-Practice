package com.ashrafunahid.imglycamera

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.PhotoEditorSaveSettings
import ly.img.android.pesdk.ui.activity.PhotoEditorBuilder
import java.io.File

class MainActivity: AppCompatActivity() {


    private lateinit var button: Button
    private lateinit var imageHolder: ImageView
    private lateinit var imageUri: Uri

    companion object {
        private const val CAMERA_REQUEST_CODE = 0x69
        private const val EDITOR_REQUEST_CODE = 0x70
    }

//    val file = File(applicationContext.filesDir, "camera_photo.png")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        getPermission()

        button = findViewById(R.id.capture_button)
        imageHolder = findViewById(R.id.image_holder)
        imageUri = createImageUri()!!
        button.setOnClickListener {
            contract.launch(imageUri)
        }

    }

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        showEditor(imageUri)
    }

    fun createImageUri(): Uri? {
        val file = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(applicationContext, "com.ashrafunahid.imglycamera.fileprovider", file)
    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
//        super.onActivityResult(requestCode, resultCode, intent)
//        when (requestCode) {
//            EDITOR_REQUEST_CODE -> {
//                intent ?: return
//                val result = EditorSDKResult(intent)
//                when (result.resultStatus) {
//                    EditorSDKResult.Status.CANCELED -> showMessage("Editor cancelled")
//                    EditorSDKResult.Status.EXPORT_DONE -> showMessage("Result saved at ${result.resultUri}")
//                    else -> {
//                    }
//                }
//            }
//            CAMERA_REQUEST_CODE -> {
//                if (resultCode == Activity.RESULT_OK) {
//                    showEditor(imageUri)
//                }
//            }
//        }
//    }

    private fun showEditor(uri: Uri) {
        // In this example, we do not need access to the Uri(s) after the editor is closed
        // so we pass false in the constructor
        val settingsList = PhotoEditorSettingsList(false)
            // Set the source as the Uri of the image to be loaded
            .configure<LoadSettings> {
                it.source = uri
                it
            }
            .configure<PhotoEditorSaveSettings> {
                it.setOutputToGallery()
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

    private fun getPermission() {
        var permissionList = mutableListOf<String>()

        if (checkSelfPermission(android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.CAMERA)

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)

        if (checkSelfPermission(android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED)
            permissionList.add(android.Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permissionList.size > 0) {
            requestPermissions(permissionList.toTypedArray(), 101)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) {
                getPermission()
            }
        }
    }
}