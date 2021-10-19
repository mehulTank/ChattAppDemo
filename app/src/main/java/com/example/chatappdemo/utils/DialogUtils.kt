package com.example.chatappdemo.utils

import android.app.Dialog
import android.content.Context
import android.view.Gravity
import android.view.View
import android.view.Window
import android.view.WindowManager
import androidx.core.text.HtmlCompat
import com.example.chatappdemo.R

import kotlinx.android.synthetic.main.dialog_confirmation_layout.*


/**
 * @purpose commonly used functions
 * @purpose
 */
class DialogUtils {
    companion object {
        fun showConfirmationDialog(
            context: Context,
            title: String,
            message: String,
            btnText: String,
            callback: OnDialogListener,
            isCloseOn: Boolean = true
        ) {


            var dialogUserConfrimation = Dialog(context, R.style.picture_dialog_style)
            dialogUserConfrimation.requestWindowFeature(Window.FEATURE_NO_TITLE)
            dialogUserConfrimation.setContentView(R.layout.dialog_confirmation_layout)
            val wlmp = dialogUserConfrimation.window!!.attributes
            wlmp.gravity = Gravity.CENTER
            wlmp.width = WindowManager.LayoutParams.MATCH_PARENT
            wlmp.height = WindowManager.LayoutParams.WRAP_CONTENT
            dialogUserConfrimation.window!!.attributes = wlmp

            if (!isCloseOn) {
                dialogUserConfrimation.ivClose.visibility = View.GONE
            }

            dialogUserConfrimation.setCancelable(isCloseOn)
            dialogUserConfrimation.tvTitle.text = title

            dialogUserConfrimation.tvDetailText.text =
                HtmlCompat.fromHtml(message, HtmlCompat.FROM_HTML_MODE_LEGACY)

            dialogUserConfrimation.btn1.text = btnText
            dialogUserConfrimation.btn1.setOnClickListener(View.OnClickListener {

                dialogUserConfrimation.dismiss()
                callback.onDialogButtonClicked(1)
            }
            )
            dialogUserConfrimation.ivClose.setOnClickListener(View.OnClickListener {
                dialogUserConfrimation.dismiss()
            }
            )
            dialogUserConfrimation.show()

        }

    }
}

interface OnDialogListener {
    fun onDialogButtonClicked(btnPosition: Int)
}


interface OnQRGenerateDialogListener {
    fun onCreateClicked(dialog: Dialog, email: String)
}
