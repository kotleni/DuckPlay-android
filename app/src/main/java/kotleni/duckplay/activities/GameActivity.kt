package kotleni.duckplay.activities

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotleni.duckplay.MyWebChromeClient
import kotleni.duckplay.createViewModel
import kotleni.duckplay.databinding.ActivityGameBinding
import kotleni.duckplay.network.DuckplayAPI
import kotleni.duckplay.repositories.LocalGamesRepository
import kotleni.duckplay.viewmodels.GameViewModel

class GameActivity : AppCompatActivity() {
    private val binding: ActivityGameBinding by lazy { ActivityGameBinding.inflate(layoutInflater) }
    private val viewModel: GameViewModel by lazy { createViewModel(GameViewModel::class.java) }

    @SuppressLint("SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        setSupportActionBar(binding.appBar)
        supportActionBar?.hide()

        binding.webview.webChromeClient = MyWebChromeClient()
        binding.webview.settings.apply {
            javaScriptEnabled = true
            allowFileAccess = true
        }

        binding.webview.addJavascriptInterface(MyWebChromeClient.GameJSInterface(), "DuckPlay")

        val id = intent!!.getStringExtra("id")!!
        val isOffline = intent!!.getBooleanExtra("isoffline", false)

        setLoading(true)
        if(isOffline) {
            viewModel.loadOfflineGame(id)

            viewModel.getLocalGame().observe(this) {
                setLoading(false)
                binding.webview.loadUrl("file:///" + "${LocalGamesRepository.dirPath}/${it.id}/index.html")
            }
        } else {
            viewModel.loadGame(id)

            viewModel.getGameInfo().observe(this) {
                binding.webview.loadUrl("${DuckplayAPI.baseUrl}/duckplay/games/${id}/")
                setLoading(false)
            }
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progress.visibility = if(isLoading) View.VISIBLE else View.GONE
        binding.webview.visibility = if(isLoading) View.INVISIBLE else View.VISIBLE
    }
}