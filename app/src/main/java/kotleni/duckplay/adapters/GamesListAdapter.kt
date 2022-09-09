package kotleni.duckplay.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.bumptech.glide.Glide
import kotleni.duckplay.databinding.FragmentSavedBinding
import kotleni.duckplay.databinding.ItemGameBinding
import kotleni.duckplay.entities.Game

class GamesListAdapter: RecyclerView.Adapter<GamesListAdapter.MyHolder>() {
    private var games: List<Game> = listOf()
    private var onItemClick: (pos: Int, game: Game) -> Unit = {_, _ ->}

    class MyHolder(val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root) {
        fun configureGame(game: Game) {
            binding.name.setText(game.getName())
            binding.about.setText(game.getAbout())

            Glide
                .with(binding.root.context)
                .load(game.getIconUrl())
                .into(binding.icon)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context))
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return games.count()
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val game = games[position]
        holder.configureGame(game)

        holder.binding.card.setOnClickListener {
            onItemClick.invoke(position, game)
        }
    }

    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(l: (pos: Int, game: Game) -> Unit) {
        onItemClick = l
    }
}