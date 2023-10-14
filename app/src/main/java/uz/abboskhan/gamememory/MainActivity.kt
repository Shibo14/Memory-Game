package uz.abboskhan.gamememory

import android.app.AlertDialog
import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.GridView
import android.widget.ImageView
import android.widget.Toast
import com.google.android.material.card.MaterialCardView
import uz.abboskhan.gamememory.databinding.ActivityMainBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Random
import java.util.Timer
import java.util.TimerTask

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var buttons: List<MaterialCardView>
    private lateinit var imagesViews: List<ImageView>

    private lateinit var cards: List<MemoryData>

    private var indexOfSingleSelectedCard: Int? = null
    private var count = 0
    private val imagesFruits = mutableListOf(
        R.drawable.bananas,
        R.drawable.kiwi, R.drawable.lemon, R.drawable.pineapple,
        R.drawable.grapes, R.drawable.orange
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        timeData()

        imagesViews = listOf(
            binding.imgFruit1,
            binding.imgFruit2,
            binding.imgFruit3,
            binding.imgFruit4,
            binding.imgFruit5,
            binding.imgFruit6,
            binding.imgFruit7,
            binding.imgFruit8,
            binding.imgFruit9,
            binding.imgFruit10,
            binding.imgFruit11,
            binding.imgFruit12
        )


        imagesFruits.addAll(imagesFruits)
        imagesFruits.shuffle()
        cards = imagesViews.indices.map { index ->
            MemoryData(imagesFruits[index])
        }

        buttons = listOf(
            binding.cardFruit1,
            binding.cardFruit2,
            binding.cardFruit3,
            binding.cardFruit4,
            binding.cardFruit5,
            binding.cardFruit6,
            binding.cardFruit7,
            binding.cardFruit8,
            binding.cardFruit9,
            binding.cardFruit10,
            binding.cardFruit11,
            binding.cardFruit12
        )


        buttons.forEachIndexed { index, button ->
            button.setOnClickListener {

                updateModels(index)

                updateViews()
            }
        }

    }

    private fun updateViews() {
        cards.forEachIndexed { index, card ->
            val button = buttons[index]
            val images = imagesViews[index]

            if (card.isMatched) {
                //   button.alpha = 0.1f
                images.alpha = 0.3f
            }
            images.setImageResource(if (card.isFaceUp) card.identifier else R.drawable.baseline_question_mark_24)
        }
    }

    private fun updateModels(position: Int) {
        val card = cards[position]

        if (card.isFaceUp) {

            return
        }

        if (indexOfSingleSelectedCard == null) {
            // 0 or 2 selected cards previously
            restoreCards()
            indexOfSingleSelectedCard = position
        } else {
            // exactly 1 card was selected previously
            checkForMatch(indexOfSingleSelectedCard!!, position)
            indexOfSingleSelectedCard = null
        }
        card.isFaceUp = !card.isFaceUp
    }

    private fun checkForMatch(position1: Int, position2: Int) {
        if (cards[position1].identifier == cards[position2].identifier) {
            count++
            cards[position1].isMatched = true
            cards[position2].isMatched = true


        }
        if (count == cards.size / 2) {
            Toast.makeText(this, "win", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, WinActivity::class.java))
            finish()
        }


    }

    private fun restoreCards() {
        for (card in cards) {
            if (!card.isMatched) {
                card.isFaceUp = false
            }
        }

    }

    fun timeData() {
        val startTime = System.currentTimeMillis()
        val endTime = startTime + 20000
        val handler = Handler()

        handler.postDelayed({

            val intent = Intent(this, DefeatedActivity::class.java)
            // intent.putExtra("count", count)
            startActivity(intent)
            finish()


        }, 20000) // 10 sekunddan so'ng ikkinchi Activityga o'tish

        val timer = Timer()
        timer.scheduleAtFixedRate(object : TimerTask() {
            override fun run() {
                val currentTime = System.currentTimeMillis()
                val remainingTime = (endTime - currentTime) / 1000

                runOnUiThread {
                    val formattedTime = SimpleDateFormat("ss", Locale.getDefault()).format(
                        Date(remainingTime * 1000)
                    )

                    binding.time.text = "$formattedTime"


                }
            }
        }, 0, 1000) // Her 1 s


    }


    private fun resetGame() {
        shuffleImages()
        count = 0

    }


    private fun shuffleImages() {
        imagesFruits.shuffle()

    }


}