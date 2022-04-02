package com.example.cloneinstagram.common.view

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.Window
import android.widget.LinearLayout
import android.widget.TextView
import com.example.cloneinstagram.R

class CustomDialog(context: Context): Dialog(context) {

    private lateinit var dialogLinearLayout: LinearLayout
    private lateinit var txtButton: Array<TextView>
    private lateinit var textTitle: TextView
    private  var titleId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_custom)

        dialogLinearLayout = findViewById(R.id.dialog_content_photo)
        textTitle = findViewById(R.id.dialog_title)
    }

    override fun setTitle(titleId: Int) {
        this.titleId = titleId
    }

    fun addButton(vararg texts: Int, listener: View.OnClickListener){
        createTextsViewsForDialog(texts)
        captureEventsOfEachDialogItem(texts, listener)
    }

    private fun createTextsViewsForDialog(texts: IntArray) {
        txtButton = Array(texts.size) { TextView(context) }
    }

    private fun captureEventsOfEachDialogItem(texts: IntArray, listener: View.OnClickListener) {
        texts.forEachIndexed { index, textId ->
            with(txtButton[index]) {
                this.id = textId
                this.setText(textId)
                this.setOnClickListener {
                    listener.onClick(it)
                    dismiss()
                }
            }
        }
    }

    override fun show() {
        requestWindowFeature(Window.FEATURE_NO_TITLE)
        super.show()

        setTitleDialog()
        setStylesAndConfigDialog()
    }

    private fun setStylesAndConfigDialog() {
        for (textView in txtButton) {
            val layoutParams = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )

            layoutParams.setMargins(30, 40, 30, 40)
            dialogLinearLayout.addView(textView, layoutParams)
        }
    }

    private fun setTitleDialog() {
        titleId?.let {
            textTitle.setText(it)
        }
    }
}