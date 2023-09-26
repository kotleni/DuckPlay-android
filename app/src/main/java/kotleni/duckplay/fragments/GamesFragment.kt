package kotleni.duckplay.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotleni.duckplay.LoadingDialog
import kotleni.duckplay.R
import kotleni.duckplay.activities.GameActivity
import kotleni.duckplay.adapters.GamesListAdapter
import kotleni.duckplay.databinding.FragmentGamesBinding
import kotleni.duckplay.isNetworkAvailable
import kotleni.duckplay.viewmodels.GamesViewModel
import javax.inject.Inject

@AndroidEntryPoint
class GamesFragment: Fragment() {
    private val binding: FragmentGamesBinding by lazy { FragmentGamesBinding.inflate(layoutInflater) }
    @Inject lateinit var viewModel: GamesViewModel
    private val loadingDialog by lazy { LoadingDialog.create(requireContext()) }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gamesListAdapter = GamesListAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = gamesListAdapter
        gamesListAdapter.setOnItemClickListener { pos, game ->
            val intent = Intent(requireActivity(), GameActivity::class.java)
            intent.putExtra("id", game.id)
            intent.putExtra("isoffline", false)
            startActivity(intent)
        }
        gamesListAdapter.setOnItemSaveClickListener { game, isSaved ->
            if(isSaved)
                viewModel.removeSavedGame(game)
            else
                viewModel.downloadGame(game)
        }

        viewModel.getGames().observe(this) {
            gamesListAdapter.updateGames(it)
        }

        viewModel.getLocalGames().observe(this) {
            gamesListAdapter.updateLocalGames(it)
        }

        viewModel.uiState.observe(this) {
            setLoadingByUIState(it)
        }

        if(isNetworkAvailable(requireContext())) {
            viewModel.loadGames()
        } else {
            binding.offlinemode.visibility = View.VISIBLE
        }
    }

    private fun setLoadingByUIState(uiState: GamesViewModel.UIState) {
        if(uiState == GamesViewModel.UIState.IDLE) {
            loadingDialog.hide()
        } else {
            val textRes = when(uiState) {
                GamesViewModel.UIState.DOWNLOADING -> R.string.downloading
                GamesViewModel.UIState.REMOVING -> R.string.removing
                else -> R.string.loading
            }

            loadingDialog.show(textRes)
        }
    }
}