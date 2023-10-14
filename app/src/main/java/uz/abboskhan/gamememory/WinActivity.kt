package uz.abboskhan.gamememory

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import uz.abboskhan.gamememory.databinding.ActivityWinBinding

class WinActivity : AppCompatActivity() {
    lateinit var binding: ActivityWinBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWinBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.restartBtn.setOnClickListener {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
        binding.backBtn.setOnClickListener {
            finish()
        }

    }
}