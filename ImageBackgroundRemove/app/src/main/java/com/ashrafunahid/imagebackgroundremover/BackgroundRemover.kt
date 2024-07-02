package com.ashrafunahid.imagebackgroundremover

import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.ashrafunahid.imagebackgroundremover.databinding.ActivityBackgroundRemoverBinding
import dev.eren.removebg.RemoveBg
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class BackgroundRemover : AppCompatActivity() {

    lateinit var binding: ActivityBackgroundRemoverBinding
    private lateinit var remover: RemoveBg
    lateinit var bitmap: Bitmap
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityBackgroundRemoverBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        remover = RemoveBg(this)
        bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, intent.data)
        binding.resultImage.setImageBitmap(bitmap)

        binding.removeBgBtn.setOnClickListener(View.OnClickListener {
            removeBackground(bitmap)
        })

    }

    private fun removeBackground(data: Bitmap) {
        runBlocking {
            launch {
                remover.clearBackground(data).collect { output ->
                    if (output != null) {
                        val bitmap1: Bitmap = output
                        binding.resultImage.setImageBitmap(bitmap1)
                    }
                }
            }
        }


    }
}
