package com.ashrafunahid.camerausing

import android.content.pm.PackageManager
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.FileProvider
import java.io.File

class MainActivity : AppCompatActivity() {

    private lateinit var button: Button
    private lateinit var imageHolder: ImageView
    private lateinit var imageUri: Uri

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
        Log.i("Nahid", imageUri.toString())
        imageHolder.setImageURI(imageUri)
    }

    fun createImageUri(): Uri? {
        val file = File(applicationContext.filesDir, "camera_photo.png")
        return FileProvider.getUriForFile(applicationContext, "com.ashrafunahid.camerausing.fileprovider", file)
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