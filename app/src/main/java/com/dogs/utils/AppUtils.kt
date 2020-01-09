package com.dogs.utils

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.dogs.R


fun Context.toast(msg:String)
{
    Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
}

fun Context.log(msg:String)
{
    Log.d("DEF_LOG",msg)
}



// for loding images

fun getProgressDrawable(context: Context):CircularProgressDrawable{
    return CircularProgressDrawable(context)
        .apply {
            strokeWidth = 10f
            centerRadius = 50f
            start()
        }
}



// extension function for loading image

fun ImageView.loadImage(uri:String?,progressDrawable: CircularProgressDrawable){

    val options: RequestOptions  = RequestOptions()
        .placeholder(progressDrawable)
        .error(R.mipmap.ic_img)

    Glide.with(context)
        .setDefaultRequestOptions(options)
        .load(uri)
        .into(this)


}