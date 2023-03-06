package com.vikmanz.shpppro.utilits

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.vikmanz.shpppro.R
import com.vikmanz.shpppro.databinding.ButtonsBinding

class CustomGoogleButton(
    context: Context,
    attrs: AttributeSet?,
    defStyleAttr: Int,
    defStyleRes: Int
) : ConstraintLayout(context, attrs, defStyleAttr, defStyleRes) {

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : this (context, attrs, 0, 0)
    constructor(context: Context, attrs: AttributeSet?) : this (context, attrs, 0)
    constructor(context: Context) : this (context, null)

    private val binding: ButtonsBinding

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.buttons, this, true)
        binding = ButtonsBinding.bind(this)
        initAttributes(attrs, defStyleAttr, defStyleRes)

    }

    @SuppressLint("CustomViewStyleable")
    private fun initAttributes(attrs: AttributeSet?,
                               defStyleAttr: Int,
                               defStyleRes: Int) {
        if (attrs == null) return
        var typedArray = context.obtainStyledAttributes(attrs, R.styleable.customGoogleButtonStyle, defStyleAttr, defStyleRes)

        with(binding) {
            val positiveButtonText = typedArray.getString(R.styleable.customGoogleButtonStyle_bottomPositiveButtonText)
            binding.buttonPositive.text = positiveButtonText ?: "Ok"
            val negativeButtonText = typedArray.getString(R.styleable.customGoogleButtonStyle_bottomNegativeButtonText)
            binding.buttonNegative.text = positiveButtonText ?: "Cancel"
        }

//        bottomPositiveButtonText
//        bottomNegativeButtonText
//        bottomPositiveButtonBackgroundColor
//        bottomNegativeButtonBackgroundColor
//        bottomProgressMode


        typedArray.recycle()
    }

}