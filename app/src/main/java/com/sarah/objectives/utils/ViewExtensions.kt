package com.sarah.objectives.utils

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.DrawableRes
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.sarah.objectives.R

fun Fragment.showToast(message: String) {
    Toast.makeText(requireContext(), message, Toast.LENGTH_LONG).show()

}

fun Activity.showToast(message: String) {
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}

fun View.hide() {
    this.visibility = View.GONE

}

fun View.show() {
    this.visibility = View.VISIBLE

}

fun Fragment.routeTo(actionId: Int) {
    findNavController().navigate(actionId)
}

fun Fragment.routeTo(actionId: Int, bundle: Bundle) {
    findNavController().navigate(actionId, bundle)
}

fun EditText.value(): String {
    return this.text.toString()
}

fun TextView.value(data: Any) {
    this.text = data.toString()
}

fun EditText.value(data: Any) {
    this.setText(data.toString())
}

fun View.disable() {
    this.isEnabled = false
}

fun View.enable() {
    this.isEnabled = true
}

fun View.makeNonClickable() {
    this.isClickable = false
}

fun View.makeClickable(){
    this.isClickable = true
}

fun EditText.clear() {
    this.setText("")
}

@SuppressLint("SetTextI18n")
fun TextView.setGreetingText(data: Any){
    this.text = "Hi $data"
}

fun ImageView.applyImage(context:Context,url:String){
    Glide.with(context)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(R.drawable.placeholder)
        .into(this)
}

fun ImageView.applyResource( @DrawableRes drawable:Int){
    this.setImageResource(drawable)
}

fun RecyclerView.init(context: Context, recyclerAdapter: RecyclerView.Adapter<*>, isHorizontal:Boolean = false){
    this.apply {
        layoutManager = if (isHorizontal){
            LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false)

        } else {
            LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        }
        adapter = recyclerAdapter

    }
}


