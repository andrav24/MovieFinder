package dev.andrav.moviefinder

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.DialogFragment
import java.lang.ClassCastException


class CustomDialogFragment : DialogFragment() {

    private lateinit var listener: CustomDialogListener

    interface CustomDialogListener {
        fun onDialogPositiveClick(dialog: DialogFragment)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as CustomDialogListener
        } catch (e : ClassCastException) {
            throw ClassCastException((context.toString() +
                    " must implement CustomDialogListener"))
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return activity?.let {
            Log.d(MainActivity.TAG, "onCreateDialog")
            val builder = AlertDialog.Builder(it)
            val inflater = requireActivity().layoutInflater
            val view = inflater.inflate(R.layout.dialog,null)
            val btn = view.findViewById<Button>(R.id.yes_button)
            btn.setOnClickListener{
                Log.d(MainActivity.TAG, "onClick_btn")
                listener.onDialogPositiveClick(this@CustomDialogFragment)
            }
            builder.setView(view)

            builder.create()
        } ?: throw IllegalStateException("Activity cannot be null")
    }
}