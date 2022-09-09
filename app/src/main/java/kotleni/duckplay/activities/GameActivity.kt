package kotleni.duckplay.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotleni.duckplay.R
import kotleni.duckplay.databinding.ActivityGameBinding

class GameActivity : AppCompatActivity() {
    private val binding: ActivityGameBinding by lazy { ActivityGameBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        supportActionBar?.hide()

        binding.webview.settings.apply {
            javaScriptEnabled = true
        }
        binding.webview.loadUrl(intent!!.getStringExtra("url")!!)
    }
}