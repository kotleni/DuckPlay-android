package kotleni.duckplay.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotleni.duckplay.R
import kotleni.duckplay.databinding.ItemGameBinding
import kotleni.duckplay.entities.Game
import kotleni.duckplay.repositories.LocalGamesRepository

class SavedListAdapter: RecyclerView.Adapter<SavedListAdapter.MyHolder>() {
    private var localGames: List<Game> = listOf()
    private var onItemClick: (pos: Int, game: Game) -> Unit = { _, _ ->}

    class MyHolder(val binding: ItemGameBinding): RecyclerView.ViewHolder(binding.root) {
        fun configureGame(game: Game) {
            binding.name.text = game.getName()
            binding.about.text = game.getAbout()

            Glide
                .with(binding.root.context)
                .load("file:///" + LocalGamesRepository.dirPath + "${game.id}/icon.png")
                .into(binding.icon)

            binding.save.visibility = View.GONE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyHolder {
        val binding = ItemGameBinding.inflate(LayoutInflater.from(parent.context))
        return MyHolder(binding)
    }

    override fun getItemCount(): Int {
        return localGames.count()
    }

    override fun onBindViewHolder(holder: MyHolder, position: Int) {
        val game = localGames[position]
        holder.configureGame(game)

        holder.binding.card.setOnClickListener {
            onItemClick.invoke(position, game)
        }
    }

    fun updateLocalGames(localGames: List<Game>) {
        this.localGames = localGames
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(l: (pos: Int, game: Game) -> Unit) {
        onItemClick = l
    }
}