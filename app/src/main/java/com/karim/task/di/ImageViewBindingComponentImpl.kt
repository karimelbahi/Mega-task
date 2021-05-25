package com.karim.task.di

import androidx.databinding.DataBindingComponent
import com.karim.task.util.binding.ImageViewBindingAdapters
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ImageViewBindingComponentImpl @Inject constructor(private val imageViewBindingAdapters: ImageViewBindingAdapters) :
    DataBindingComponent {
    override fun getImageViewBindingAdapters() = imageViewBindingAdapters

}