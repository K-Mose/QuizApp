package master.kotlin.quizapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import master.kotlin.quizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // https://thdev.tech/android/2020/10/07/Remove-kotlinx-synthetic/
        // https://developer.android.com/topic/libraries/view-binding
        // 바인딩 객체 사용해서 변경
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) // activity를 위한 layout은 root에 저장되어 있어 불러온다

        // 전체 화면 모드 설정 https://developer.android.com/training/system-ui/immersive
        window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN

        binding.btnStart.setOnClickListener {

            if(binding.etName.text.toString().isEmpty()){
                Toast.makeText(this, "Please enter your name", Toast.LENGTH_SHORT).show()
            }else{
                val intent = Intent(this, QuizQuestionsActivity::class.java)
                intent.putExtra(Constants.USER_NAME, binding.etName.text.toString())
                startActivity(intent)
                finish()
            }
        }
    }
}