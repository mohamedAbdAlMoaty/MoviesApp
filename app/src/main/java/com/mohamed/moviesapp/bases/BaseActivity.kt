package com.mohamed.moviesapp.bases

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    fun showConfirmationDialog(title: String?, message: String?, posActionText: String?, NegActionText: String?, posAction: DialogInterface.OnClickListener?, NegAction: DialogInterface.OnClickListener?): AlertDialog.Builder? {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle(title)
        alertDialog.setMessage(message)
        alertDialog.setPositiveButton(posActionText, posAction)
        alertDialog.setNegativeButton(NegActionText, NegAction)
        alertDialog.show()
        return alertDialog
    }
}