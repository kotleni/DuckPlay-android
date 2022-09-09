package kotleni.duckplay.fragments

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.get
import androidx.recyclerview.widget.LinearLayoutManager
import kotleni.duckplay.activities.GameActivity
import kotleni.duckplay.adapters.GamesListAdapter
import kotleni.duckplay.databinding.FragmentGamesBinding
import kotleni.duckplay.repositories.GamesRepository
import kotleni.duckplay.viewmodels.GamesViewModel
import kotleni.duckplay.viewmodels.GamesViewModelProviderFactory

class GamesFragment: Fragment() {
    private val binding: FragmentGamesBinding by lazy { FragmentGamesBinding.inflate(layoutInflater) }
    private val gamesRepository: GamesRepository by lazy { GamesRepository() }
    private val viewModel: GamesViewModel by lazy {
        ViewModelProvider(requireActivity(), GamesViewModelProviderFactory(gamesRepository)).get()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val gamesListAdapter = GamesListAdapter()
        binding.recycler.layoutManager = LinearLayoutManager(requireContext())
        binding.recycler.adapter = gamesListAdapter
        gamesListAdapter.setOnItemClickListener { pos, game ->
            val intent = Intent(requireActivity(), GameActivity::class.java)
            intent.putExtra("url", game.url)
            startActivity(intent)
        }

        viewModel.getGames().observe(this) {
            gamesListAdapter.updateGames(it)
            setLoading(false)
        }

        viewModel.loadGames()
        setLoading(true)
    }

    fun setLoading(isLoading: Boolean) {
        binding.progress.visibility = if(isLoading) View.VISIBLE else View.GONE
    }
}