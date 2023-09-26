package kotleni.duckplay.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotleni.duckplay.activities.GameActivity
import kotleni.duckplay.adapters.GamesListAdapter
import kotleni.duckplay.createViewModel
import kotleni.duckplay.databinding.FragmentGamesBinding
import kotleni.duckplay.isNetworkAvailable
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.repositories.LocalGamesRepository
import kotleni.duckplay.viewmodels.GamesViewModel
import javax.inject.Inject

@AndroidEntryPoint
class GamesFragment: Fragment() {
    private val binding: FragmentGamesBinding by lazy { FragmentGamesBinding.inflate(layoutInflater) }
    // private val viewModel: GamesViewModel by lazy { createViewModel(GamesViewModel::class.java) }
    @Inject lateinit var viewModel: GamesViewModel

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
            setLoading(false)
        }

        viewModel.getLocalGames().observe(this) {
            gamesListAdapter.updateLocalGames(it)
        }

        if(isNetworkAvailable(requireContext())) {
            viewModel.loadGames()
            setLoading(true)
        } else {
            binding.offlinemode.visibility = View.VISIBLE
            setLoading(false)
        }
    }

    private fun setLoading(isLoading: Boolean) {
        binding.progress.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}