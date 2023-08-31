package com.ibssbd.gobeautify.Activities

import android.app.Activity
import android.app.AlertDialog
import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.core.content.FileProvider
import com.ibssbd.gobeautify.R
import ly.img.android.pesdk.PhotoEditorSettingsList
import ly.img.android.pesdk.backend.model.EditorSDKResult
import ly.img.android.pesdk.backend.model.state.LoadSettings
import ly.img.android.pesdk.backend.model.state.PhotoEditorSaveSettings
import ly.img.android.pesdk.ui.activity.PhotoEditorBuilder
import java.io.File

class EditPhoto : AppCompatActivity() {

    private lateinit var imageUri: Uri
    private lateinit var backButton: ImageView
    private lateinit var downloadButton: ImageView
    private lateinit var shareButton: ImageView
    private lateinit var selectImage: CardView

    companion object {
        private const val CAMERA_REQUEST_CODE = 0x69
        private const val GALLERY_REQUEST_CODE = 0x70
        private const val EDITOR_REQUEST_CODE = 0x71
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_photo)

        backButton = findViewById(R.id.back_button)
        downloadButton = findViewById(R.id.download_button)
        shareButton = findViewById(R.id.share_button)
        selectImage = findViewById(R.id.select_image)

        downloadButton.visibility = View.GONE
        shareButton.visibility = View.GONE

        getPermission()

        backButton.setOnClickListener(View.OnClickListener {
            finish()
        })

        imageUri = createImageUri()!!

        selectImage.setOnClickListener(View.OnClickListener {

            val builder = AlertDialog.Builder(this)
            builder.setTitle("Choose image from")
            builder.setPositiveButton("Camera") { dialogInterface, which ->
                dialogInterface.dismiss()
                contract.launch(imageUri)
            }
            builder.setNegativeButton("Gallery") { dialogInterface, which ->
                dialogInterface.dismiss()
                val intent =
                    Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
                try {
                    startActivityForResult(intent, GALLERY_REQUEST_CODE)
                } catch (ex: ActivityNotFoundException) {
                    showMessage("No Gallery app installed")
                }
            }
            val alertDialog: AlertDialog = builder.create()
            alertDialog.setCancelable(true)
            alertDialog.show()

        })

    }

    private val contract = registerForActivityResult(ActivityResultContracts.TakePicture()) {
        showEditor(imageUri)
    }

    fun createImageUri(): Uri? {
        val file = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(
            applicationContext,
            "com.ibssbd.gobeautify.fileprovider",
            file
        )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intent: Intent?) {
        super.onActivityResult(requestCode, resultCode, intent)
        intent ?: return
        when (requestCode) {
            EDITOR_REQUEST_CODE -> {
                val result = EditorSDKResult(intent)
                Log.i("Editor Result", result.resultUri.toString())
                when (result.resultStatus) {
                    EditorSDKResult.Status.CANCELED -> {
                        showMessage("Editor cancelled")
                        finish()
                    }
                    EditorSDKResult.Status.EXPORT_DONE -> {
                        showMessage("Result saved at gallery")
                        finish()
                    }
                    else -> {
                    }
                }
            }
            GALLERY_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    intent.data?.let { showEditor(it) } ?: showMessage("Invalid Uri")
                } else if (resultCode == Activity.RESULT_CANCELED) {
                    showMessage("Something went wrong")
                    finish()
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        grantResults.forEach {
            if (it != PackageManager.PERMISSION_GRANTED) {
                getPermission()
            }
        }
    }

}