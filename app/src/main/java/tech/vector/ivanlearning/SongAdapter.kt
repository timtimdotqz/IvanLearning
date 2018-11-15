package tech.vector.ivanlearning

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class SongAdapter(val songs: List<String>): RecyclerView.Adapter<SongAdapter.ViewHolder>() {

    private var listener: ((position: Int) -> Unit)? = null

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val itemText: TextView = itemView.findViewById(R.id.tvSongName)

        fun bind(song: String) {
            itemText.text = song
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, position: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_song, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(songs[position])
        viewHolder.itemView.setOnClickListener { listener?.invoke(position) }
    }

    override fun getItemCount(): Int {
        return songs.size
    }

    fun setOnItemClickListener(listener: (position: Int) -> Unit) {
        this.listener = listener
    }

}