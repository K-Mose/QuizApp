package master.kotlin.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class ResultActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val userName = intent.getStringExtra(Constants.USER_NAME)
        val tvName= findViewById<TextView>(R.id.tv_name)
        tvName.text = userName
        val tvScore = findViewById<TextView>(R.id.tv_score)
        val correct = intent.getIntExtra(Constants.CORRECT_ANSWERS, 0)
        val totalQuestion = intent.getIntExtra(Constants.TOTAL_QUESTIONS, 10)
        val score = "Your Score is $correct out of $totalQuestion"
        tvScore.text = score

        val btnFinish = findViewById<Button>(R.id.btn_finish)
        btnFinish.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }
    }
}