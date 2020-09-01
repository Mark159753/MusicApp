package com.example.musicapp.binding

import androidx.databinding.BindingConversion

@BindingConversion
fun convertLongToInt(value:Long) = value.toInt()