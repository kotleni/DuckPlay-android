package kotleni.duckplay.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.bumptech.glide.Glide
import kotleni.duckplay.R
import kotleni.duckplay.databinding.FragmentSavedBinding
import kotleni.duckplay.databinding.ItemGameBinding
import kotleni.duckplay.entities.Game

class GamesListAdapter: RecyclerView.Adapter<GamesListAdapter.MyHolder>() {
    private var games: List<Game> = listOf()
    private var localGames: List<Game> = listOf()
    private var onItemClick: (pos: Int, game: Game) -> Unit = {_, _ ->}
    private var onItemSaveClick: (game: Game, isSaved: Boolean) -> Unit = {_, _ ->}

    class MyHolder(val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root) {
        fun configureGame(game: Game, localGames: List<Game>) {
            binding.name.text = game.getName()
            binding.about.text = game.getAbout()

            Glide
                .with(binding.root.context)
                .load(game.getIconUrl())
                .into(binding.icon)

            if(findLocalGame(game.id, localGames) != null) {
                binding.save.setImageResource(R.drawable.ic_baseline_favorite_24)
            } else {
                binding.save.setImageResource(R.drawable.ic_outline_favorite_border_24)
            }
        }

        fun findLocalGame(id: String, localGames: List<Game>): Game? {
            localGames.forEach {
                if(it.id == id)
                    return it
            }

            return null
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
        holder.configureGame(game, localGames)

        holder.binding.card.setOnClickListener {
            onItemClick.invoke(position, game)
        }

        holder.binding.save.setOnClickListener {
            onItemSaveClick.invoke(game, holder.findLocalGame(game.id, localGames) != null)
        }
    }

    fun updateGames(games: List<Game>) {
        this.games = games
        notifyDataSetChanged()
    }

    fun updateLocalGames(localGames: List<Game>) {
        this.localGames = localGames
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(l: (pos: Int, game: Game) -> Unit) {
        onItemClick = l
    }

    fun setOnItemSaveClickListener(l: (game: Game, isSaved: Boolean) -> Unit) {
        onItemSaveClick = l
    }
}