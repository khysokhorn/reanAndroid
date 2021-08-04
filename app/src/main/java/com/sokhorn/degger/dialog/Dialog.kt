package com.sokhorn.degger.dialog

import android.content.Context
import android.content.DialogInterface
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class Dialog(val context: Context) {
    fun confirmDialog() {
        MaterialAlertDialogBuilder(context)
            .setTitle("Fake Json")
            .setMessage("This is the fake json from Fake Json")
            .setPositiveButton(
                "ok"
            ) { p0, p1 -> p0?.dismiss() }
            .setNegativeButton("cancel", object : DialogInterface.OnClickListener {
                override fun onClick(p0: DialogInterface?, p1: Int) {
                    p0?.dismiss()
                }
            })

            .show()
    }
}