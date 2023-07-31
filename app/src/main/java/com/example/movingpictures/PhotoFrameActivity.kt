package com.example.movingpictures

import android.net.Uri
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import kotlin.concurrent.timer

class PhotoFrameActivity : AppCompatActivity() {

    private val photoList = mutableListOf<Uri>()

    private var currentPosition = 0

    private var timer: Timer? = null;

    private val photoImageView: ImageView by lazy {
        findViewById(R.id.photoImageView)
    }

    private val backImageView: ImageView by lazy {
        findViewById(R.id.backgroundPhotoImageView)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_photoframe)

        getPhotoUriFromIntent()

        startTimer()
        Log.d("포토","onCreate! startTimer")
    }

    private fun getPhotoUriFromIntent() {
        val size = intent.getIntExtra("photoListSize", 0)
        for (i in 0..size) {
            intent.getStringExtra("photo$i")?.let {
                photoList.add(Uri.parse(it))
            }
        }
    }

    private fun startTimer() {
        timer = timer(period = 5 * 1000) {
            runOnUiThread {
                val current = currentPosition
                val next = if (photoList.size <= currentPosition + 1) 0 else currentPosition + 1

                backImageView.setImageURI(photoList[current])

                photoImageView.alpha = 0f
                photoImageView.setImageURI(photoList[next])
                photoImageView
                    .animate().alpha(1.0f)
                    .setDuration(1000)
                    .start()

                currentPosition = next
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.d("포토","onStop! timer cancel")
        timer?.cancel()
    }

    override fun onStart() {
        super.onStart()
        Log.d("포토","onStart! startTimer")
        startTimer()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("포토","onDestroy! timer cancel")
        timer?.cancel()
    }
}