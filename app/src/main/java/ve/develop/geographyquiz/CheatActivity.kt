package ve.develop.geographyquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

private const val EXTRA_ANSWER = "extra_answer"
const val ANSWER_IS_SHOWN = "answer_is_shown"

class CheatActivity : AppCompatActivity() {

    private var answerIsTrue: Boolean = false
    private lateinit var showAnswerTextView: TextView
    private lateinit var showAnswerButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER, false)

        showAnswerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)
        showAnswerButton.setOnClickListener {
            val answerText = when (answerIsTrue) {
                answerIsTrue -> R.string.correct_toast
                else -> R.string.incorrect_toast
            }
            showAnswerTextView.setText(answerText)
            setAnswerShownResult(true)
        }
    }

    private fun setAnswerShownResult(answerShown: Boolean) {
        val intent = Intent().apply { putExtra(ANSWER_IS_SHOWN, answerShown) }
        setResult(Activity.RESULT_OK,intent)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER, answerIsTrue)
            }

        }
    }
}
