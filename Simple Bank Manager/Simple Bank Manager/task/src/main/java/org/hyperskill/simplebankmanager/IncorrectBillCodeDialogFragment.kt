package org.hyperskill.simplebankmanager

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class IncorrectBillCodeDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog =
        AlertDialog.Builder(getActivity())
            .setTitle(getString(R.string.pay_bill_title_error))
            .setMessage(getString(R.string.pay_bill_message_wrong_code))
            .setPositiveButton(android.R.string.ok, {_,_ ->
                this.dismiss()
            }).create()

    companion object {
        const val TAG = "IncorrectBillDialog"
    }
}