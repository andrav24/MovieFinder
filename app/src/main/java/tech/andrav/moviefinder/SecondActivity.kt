package tech.andrav.moviefinder

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import tech.andrav.moviefinder.MainActivity.Companion.MESSAGE_FROM_CHECKBOX
import tech.andrav.moviefinder.MainActivity.Companion.MESSAGE_FROM_COMMENT

class SecondActivity : AppCompatActivity() {

    private lateinit var mImageView : ImageView
    private lateinit var mButtonInvite : Button
    private lateinit var mCheckBox : CheckBox
    private lateinit var mEditText : EditText

    private var mMessageFromCheckbox = ""
    private var mMessageFromComment = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_second)

        mImageView = findViewById<View>(R.id.second_activity_imageview) as ImageView
        mImageView.setImageResource(intent.getIntExtra("film_extra", R.drawable.film_batman))

        mButtonInvite = findViewById<View>(R.id.button_invite) as Button
        mButtonInvite.setOnClickListener {
            val textMessage = "Text message for friend inviting"
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, textMessage)
            sendIntent.type = "text/plain"
            val title = "Invite friend"
            val chooser = Intent.createChooser(sendIntent, title)
            sendIntent.resolveActivity(packageManager)?.let {
                startActivity(chooser)
            }
        }

        mCheckBox = findViewById<View>(R.id.checkbox_isLike) as CheckBox
        mCheckBox.setOnClickListener{
            mMessageFromCheckbox = if (mCheckBox.isChecked) "checked" else "not checked"
        }

        mEditText = findViewById<View>(R.id.edittext_comment) as EditText
        mEditText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                mMessageFromComment = p0.toString()
            }
        })
    }

    override fun onBackPressed() {
        val intent = Intent()
        intent.putExtra(MESSAGE_FROM_CHECKBOX, mMessageFromCheckbox)
        intent.putExtra(MESSAGE_FROM_COMMENT, mMessageFromComment)
        setResult(Activity.RESULT_OK, intent)
        super.onBackPressed()
    }
}