package ve.develop.geographyquiz

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders

private const val TAG = "MainActivity"
private const val KEY_INDEX = "currentQuestionIndex"
private const val REQUEST_ANSWER = 0

class MainActivity : AppCompatActivity() {

    private val quizViewModel: QuizViewModel by lazy {
        ViewModelProviders.of(this).get(QuizViewModel::class.java)
    }

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var nextButton: Button
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d(TAG, "onCreate called!")

        val currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        quizViewModel.currentIndex = currentIndex


        questionTextView = findViewById(R.id.question_text_view)


        trueButton = findViewById(R.id.true_button)
        trueButton.setOnClickListener {
            checkAnswer(true)
        }
        falseButton = findViewById(R.id.false_button)
        falseButton.setOnClickListener {
            checkAnswer(false)
        }
        nextButton = findViewById(R.id.next_button)
        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton = findViewById(R.id.cheat_button)
        cheatButton.setOnClickListener {
            val intent =
                CheatActivity.newIntent(this@MainActivity, quizViewModel.currentQuestionAnswer)
            startActivityForResult(intent, REQUEST_ANSWER)
        }
        updateQuestion()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }
        if (requestCode == REQUEST_ANSWER) {
            quizViewModel.isCheater = data?.getBooleanExtra(ANSWER_IS_SHOWN, false) ?: false
        }

    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called!")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "onResume called!")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause called!")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "onStop called!")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called!")
    }

    private fun updateQuestion() {
        questionTextView.setText(quizViewModel.currentQuestionText)
    }

    private fun checkAnswer(userAnswer: Boolean) {
        val questionAnswer = quizViewModel.currentQuestionAnswer
        val messageResId = when {
            quizViewModel.isCheater -> R.string.judgment_toast
            userAnswer == questionAnswer -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }
        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show()
    }
}
