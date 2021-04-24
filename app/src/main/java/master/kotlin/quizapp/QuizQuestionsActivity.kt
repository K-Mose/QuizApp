package master.kotlin.quizapp

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.core.content.ContextCompat
import master.kotlin.quizapp.databinding.ActivityQuizQuestionsBinding

class QuizQuestionsActivity : AppCompatActivity(), View.OnClickListener {

    private var mCurrentPosition:Int = 1
    private var mQuestionList:ArrayList<Question>? = null
    private var mSelectedOptionPosition:Int = 0
    private var mCorrectAnswers: Int = 0
    private var mUserName:String? = null

    private lateinit var tvOptionOne:TextView
    private lateinit var tvOptionTwo:TextView
    private lateinit var tvOptionThree:TextView
    private lateinit var tvOptionFour:TextView
    private lateinit var btnSubmit:Button

    private lateinit var binding:ActivityQuizQuestionsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityQuizQuestionsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        tvOptionOne = binding.tvOptionOne
        tvOptionTwo = binding.tvOptionTwo
        tvOptionThree = binding.tvOptionThree
        tvOptionFour = binding.tvOptionFour
        btnSubmit = binding.btnSubmit

        mQuestionList = Constants.getQuestions()


        setQuestion()

        tvOptionOne.setOnClickListener(this)
        tvOptionTwo.setOnClickListener(this)
        tvOptionThree.setOnClickListener(this)
        tvOptionFour.setOnClickListener(this)
        btnSubmit.setOnClickListener(this)
    }

    private fun  setQuestion(){

        val question = mQuestionList!![mCurrentPosition-1]

        defaultOptionsView()

        mUserName = intent.getStringExtra(Constants.USER_NAME)

        val progressBar = findViewById<ProgressBar>(R.id.progressBar)
        progressBar.progress = mCurrentPosition
        Log.d("Progrres :: ", "${progressBar.progress}")
        val tvProgress = findViewById<TextView>(R.id.tv_progress)
        tvProgress.text = "${mCurrentPosition}/${progressBar.max}"

        val tvQuestion = findViewById<TextView>(R.id.tvQuestionId)
        tvQuestion.text = question!!.question

        val ivImg = findViewById<ImageView>(R.id.ivImg)
        ivImg.setImageResource(question.image)

        tvOptionOne.text = question.optionOne
        tvOptionTwo.text = question.optionTwo
        tvOptionThree.text = question.optionThree
        tvOptionFour.text = question.optionFour
        if(mCurrentPosition == mQuestionList!!.size){
            btnSubmit.text = "FINISH"
        }else{
            btnSubmit.text = "SUBMIT"
        }
    }

    //button layout
    private fun defaultOptionsView(){
        val options = ArrayList<TextView>()
        options.add(0, tvOptionOne)
        options.add(1, tvOptionTwo)
        options.add(2, tvOptionThree)
        options.add(3, tvOptionFour)

        for( option in options){
            option.setTextColor(Color.parseColor("#7A8089"))
            option.typeface = Typeface.DEFAULT // = style
            option.background = ContextCompat.getDrawable(this, R.drawable.default_option_border_bg)
        }
    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.tv_option_one->{
                selectedOptionView(tvOptionOne, 1)
            }
            R.id.tv_option_two->{
                selectedOptionView(tvOptionTwo, 2)
            }
            R.id.tv_option_three->{
                selectedOptionView(tvOptionThree, 3)
            }
            R.id.tv_option_four->{
                selectedOptionView(tvOptionFour, 4)
            }
            R.id.btn_submit->{
                if(mSelectedOptionPosition == 0){
                    mCurrentPosition ++
                    when{
                        mCurrentPosition <= mQuestionList!!.size->{
                            setQuestion()
                        }
                        else->{
                            val intent = Intent(this, ResultActivity::class.java)
                            intent.putExtra(Constants.USER_NAME, mUserName)
                            intent.putExtra(Constants.CORRECT_ANSWERS, mCorrectAnswers)
                            intent.putExtra(Constants.TOTAL_QUESTIONS, mQuestionList!!.size)
                            startActivity(intent)
                            finish()
                            Toast.makeText(this, "You have successfully completed the Quiz", Toast.LENGTH_SHORT).show()
                        }
                    }
                }else{
                    val question = mQuestionList?.get(mCurrentPosition-1)
                    if(question!!.correctAnswer != mSelectedOptionPosition){
                        answerView(mSelectedOptionPosition, R.drawable.worng_option_border_bg)
                    }else{
                        mCorrectAnswers++
                    }
                    answerView(question.correctAnswer, R.drawable.correct_option_border_bg)

                    if(mCurrentPosition == mQuestionList!!.size){
                        btnSubmit.text = "Finish"
                    }else{
                        btnSubmit.text = "GO TO NEXT QUESTION"
                    }
                    mSelectedOptionPosition=0
                }
            }
        }
    }

    private fun answerView(answer:Int, drawableView:Int){
        when(answer){
            1->{
                tvOptionOne.background = ContextCompat.getDrawable(this, drawableView)
            }
            2->{
                tvOptionTwo.background = ContextCompat.getDrawable(this, drawableView)
            }
            3->{
                tvOptionThree.background = ContextCompat.getDrawable(this, drawableView)
            }
            4->{
                tvOptionFour.background = ContextCompat.getDrawable(this, drawableView)
            }
        }
    }

    private fun selectedOptionView(tv:TextView, selectedOptionNum:Int){
        defaultOptionsView()
        mSelectedOptionPosition = selectedOptionNum
        tv.setTextColor(Color.parseColor("#363A34"))
        tv.setTypeface(tv.typeface, Typeface.BOLD)
        tv.background = ContextCompat.getDrawable(this, R.drawable.selected_option_border_bg)
    }
}