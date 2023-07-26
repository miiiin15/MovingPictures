package com.example.movingpictures

import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {
    private val buttonAddPhoto: Button by lazy {
        findViewById<Button>(R.id.buttonAddPhoto)
    }
    private val buttonStart: Button by lazy {
        findViewById<Button>(R.id.buttonStart)
    }

    private val imageViewList: List<ImageView> by lazy {
        mutableListOf<ImageView>().apply {
            add(findViewById(R.id.imageView11))
            add(findViewById(R.id.imageView12))
            add(findViewById(R.id.imageView13))
            add(findViewById(R.id.imageView21))
            add(findViewById(R.id.imageView22))
            add(findViewById(R.id.imageView23))
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initAddPhotobutton()
        initStartPhotoButton()
    }

    private fun initAddPhotobutton() {
        buttonAddPhoto.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this,
                    android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    naviagtePhoto()
                }

                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    showPermissionPopup()
                }
                PackageManager.PERMISSION_DENIED == -1 -> {
                    showPermissionPopup()
                }
                else -> {
                    requestPermissions(
                        arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                        1000
                    )
                }
            }
        }
    }

    private fun initStartPhotoButton() {

    }

    private fun naviagtePhoto() {
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        // 이미지만 필터링하기 위함
        intent.type = "image/*"
        startActivityForResult(intent, 2000)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            1000 -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    naviagtePhoto()
                }
            }
            else -> {

            }
        }
    }

    private fun showPermissionPopup() {
        AlertDialog.Builder(this).setTitle("안내").setMessage("갤러리 권한이 필요한 작업입니다.")
            .setPositiveButton("동의", { dialog, i ->
                requestPermissions(
                    arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),
                    1000
                )
            })
            .setNegativeButton("취소", { dialog, _ -> })
            .create()
            .show()
    }
}