package org.hyperskill.simplebankmanager

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.setFragmentResult

class CorrectBillCodeDialogFragment: DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bundle = arguments //billName, billCode, billAmount
        val dialog = AlertDialog.Builder(getActivity()).setTitle(getString(R.string.pay_bill_title_bill_info))
            .setMessage(getString(R.string.pay_bill_message_info, bundle?.getString(getString(R.string.pay_bill_key_bill_info)).toString(),
                bundle?.getString(getString(R.string.pay_bill_key_bill_code)).toString(), bundle?.getDouble(getString(R.string.pay_bill_key_bill_amount))
                    .toString().toDouble()))
            .setPositiveButton(android.R.string.ok, {_, _ -> bundle?.let {
                val result = getString(R.string.pay_bill_confirm)
                setFragmentResult(getString(R.string.pay_bill_request_key), bundleOf(getString(R.string.pay_bill_result) to result))
                //onPositiveButtonClick(it)
                this.dismiss()}})
            .setNegativeButton(android.R.string.cancel, {_, _ ->
                val result = getString(R.string.pay_bill_cancel)
                setFragmentResult(getString(R.string.pay_bill_request_key), bundleOf(getString(R.string.pay_bill_result) to result))
                this.dismiss()}).create()

        return dialog
    }
    companion object{
        const val TAG = "CorrectBillCodeDialog"
    }
}