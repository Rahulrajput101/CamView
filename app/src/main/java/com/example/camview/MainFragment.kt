package com.example.camview

import android.annotation.SuppressLint

import android.graphics.Bitmap

import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.launch
import androidx.core.view.drawToBitmap
import androidx.fragment.app.Fragment
import com.example.camview.databinding.FragmentMainBinding
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream

import java.io.IOException
import java.text.SimpleDateFormat
import java.time.Duration


import java.util.*


class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding;



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(inflater)

        val takePhoto = registerForActivityResult(ActivityResultContracts.TakePicturePreview()){
            if (it != null) {
                saveImage(it)
            }else{
                Toast.makeText(context," not saved",Toast.LENGTH_LONG).show()
            }
        }
        binding.button.setOnClickListener{

            takePhoto.launch()

        }

        
        return binding.root
    }


    @SuppressLint("SimpleDateFormat")
    fun saveImage(bitmap: Bitmap){

        val file = getDisk()
        if (!file.exists() && !file.mkdirs()) {
            file.mkdir()

        }

        val simpleDateFormat = SimpleDateFormat("yyyymmsshhmmss")
        val date = simpleDateFormat.format(Date())
        val name = "IMG" + date +".jpg"
        val fileName = file.absolutePath + "/" + name
        val newFile = File(fileName)


            try {
                val fileOutPutStream = FileOutputStream(newFile)
                bitmap.compress(Bitmap.CompressFormat.PNG, 95, fileOutPutStream)
                fileOutPutStream.flush()
                fileOutPutStream.close()
            } catch (e: Exception) {
                e.printStackTrace()

            }




    }
    fun getDisk() : File{

        val file = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM)
        return File(file,"Camera")
    }




}