package dev.andrav.moviefinder

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment

class MainActivity : AppCompatActivity(), CustomDialogFragment.CustomDialogListener {

    private lateinit var mTextViewBatman : TextView
    private lateinit var mTextViewJoker : TextView

    private lateinit var mButtonJokerDetails: Button
    private lateinit var mButtonBatmanDetails: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(MainActivity.TAG, "onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mTextViewBatman = findViewById(R.id.textview_batman)
        mTextViewJoker = findViewById(R.id.textview_joker)
        mButtonBatmanDetails = findViewById(R.id.button_batman_details)
        mButtonJokerDetails = findViewById(R.id.button_joker_details)


        mButtonBatmanDetails.setOnClickListener {
            if (mTextViewBatman.currentTextColor == Color.BLACK) {
                mTextViewBatman.setTextColor(Color.RED)
            }
            else {
                mTextViewBatman.setTextColor(Color.BLACK)
            }
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("film_extra", R.drawable.film_batman)
            startActivityForResult(intent, FILM_REQUEST_CODE)
        }

        mButtonJokerDetails.setOnClickListener {
            if (mTextViewJoker.currentTextColor == Color.BLACK) {
                mTextViewJoker.setTextColor(Color.RED)
            } else {
                mTextViewJoker.setTextColor(Color.BLACK)
            }
            val intent = Intent(this@MainActivity, SecondActivity::class.java)
            intent.putExtra("film_extra", R.drawable.film_joker)
            startActivityForResult(intent, FILM_REQUEST_CODE)
        }

        savedInstanceState?.apply {
            mTextViewBatman.setTextColor(getInt(KEY_BATMAN))
            mTextViewJoker.setTextColor(getInt(KEY_JOKER))
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        savedInstanceState.apply {
            mTextViewBatman.setTextColor(getInt(KEY_BATMAN))
            mTextViewJoker.setTextColor(getInt(KEY_JOKER))
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        outState.putInt(KEY_BATMAN, mTextViewBatman.currentTextColor)
        outState.putInt(KEY_JOKER, mTextViewJoker.currentTextColor)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == FILM_REQUEST_CODE) {
            var answerFromCheckbox : String? = null
            var answerFromComment : String? = null
            if (resultCode == Activity.RESULT_OK) {
                data?.let {
                    answerFromCheckbox = it.getStringExtra(MESSAGE_FROM_CHECKBOX)
                    answerFromComment = it.getStringExtra(MESSAGE_FROM_COMMENT)
                }
            }
            Log.i(TAG, "the checkbox is : $answerFromCheckbox")
            Log.i(TAG, "the comment is : $answerFromComment")
        }
    }

    override fun onBackPressed() {
        val dialogFragment = CustomDialogFragment()
        dialogFragment.show(supportFragmentManager, "CustomDialogFragment")
    }

    override fun onDialogPositiveClick(dialog: DialogFragment) {
        super.onBackPressed()
    }

    companion object {
        val TAG = MainActivity::class.java.simpleName
        const val KEY_JOKER    = "joker"
        const val KEY_BATMAN   = "batman"
        const val FILM_REQUEST_CODE = 42
        const val MESSAGE_FROM_CHECKBOX = "message_from_checkbox"
        const val MESSAGE_FROM_COMMENT = "message_from_comment"
    }
}

