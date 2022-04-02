package com.example.cloneinstagram.common.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.example.cloneinstagram.R

class LoadingButton : FrameLayout {
    private var text: String? = null
    private lateinit var button: Button
    private lateinit var progressBar: ProgressBar

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs){
        setup(context, attrs)
    }
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr : Int) : super(context, attrs, defStyleAttr){
        setup(context, attrs)
    }

    private fun setup(context: Context, attrs: AttributeSet?) {
        inflateLayoutButtonCustomize(context)

        configAttributesOfTheButton(context, attrs)
    }

    private fun configAttributesOfTheButton(context: Context, attrs: AttributeSet?) {
        val typeArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton, 0, 0)
        text = typeArray.getString(R.styleable.LoadingButton_text)

        button = getChildAt(0) as Button
        progressBar = getChildAt(1) as ProgressBar

        button.text = text
        button.isEnabled = false

        typeArray.recycle()
    }

    private fun inflateLayoutButtonCustomize(context: Context) {
        val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        inflater.inflate(R.layout.button_loading, this)
    }

    override fun setOnClickListener(l: OnClickListener?) {
        button.setOnClickListener(l)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        button.isEnabled = enabled
    }

     fun showProgessBar(enabled: Boolean){
        if(enabled){
            button.text = ""
            button.isEnabled= false
            progressBar.visibility = View.VISIBLE
        }else{
            button.text = text
            button.isEnabled= true
            progressBar.visibility = View.GONE
        }
    }
}