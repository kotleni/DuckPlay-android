package kotleni.duckplay.activities

import android.annotation.SuppressLint
import android.content.res.Resources
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.ConsoleMessage
import android.webkit.WebChromeClient
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.lifecycle.viewmodel.compose.viewModel
import kotleni.duckplay.MyWebChromeClient
import kotleni.duckplay.R
import kotleni.duckplay.databinding.ActivityGameBinding
import kotleni.duckplay.network.DuckplayAPI
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import kotleni.duckplay.viewmodels.GameViewModel
import kotleni.duckplay.viewmodels.GameViewModelProviderFactory
import kotleni.duckplay.viewmodels.GamesViewModel
import kotleni.duckplay.viewmodels.GamesViewModelProviderFactory

class GameActivity : AppCompatActivity() {
    private val binding: ActivityGameBinding by lazy { ActivityGameBinding.inflate(layoutInflater) }
    private val gamesRepository: GamesRepository by lazy { GamesRepository() }
    private val localGamesRepository: LocalGamesRepository by lazy { LocalGamesRepository() }
    private val viewModel: GameViewModel by lazy {
        ViewModelProvider(this, GameViewModelProviderFactory(gamesRepository, localGamesRepository)).get()
    }

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