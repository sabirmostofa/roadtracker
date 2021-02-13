package bd.hellofood.roadtracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import  bd.hellofood.roadtracker.R
import bd.hellofood.roadtracker.db.Track
import bd.hellofood.roadtracker.other.TrackingUtility
import bd.hellofood.roadtracker.ui.viewmodels.MainViewModel
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.item_run.view.*
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

/**
 * Adapter class to list all tracks
 */

class TrackAdapter : RecyclerView.Adapter<TrackAdapter.RunViewHolder>()  {

    private lateinit var listener: ItemListener

    interface ItemListener {
        fun onItemClicked(address: Track, action: String)
    }

    fun setListener(listener: ItemListener) {
        this.listener = listener;
    }

    inner class RunViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)

    val diffCallback = object : DiffUtil.ItemCallback<Track>() {
        override fun areItemsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Track, newItem: Track): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    val differ = AsyncListDiffer(this, diffCallback)

    fun submitList(list: List<Track>) = differ.submitList(list)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RunViewHolder {
        return RunViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_run,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int { 
       return differ.currentList.size
    }

    /**
     * Bind vars to HOlder
     */

    override fun onBindViewHolder(holder: RunViewHolder, position: Int) {
        val run = differ.currentList[position]
        holder.itemView.apply {
            Glide.with(this).load(run.img).into(ivRunImage)

            val calendar = Calendar.getInstance().apply {
                timeInMillis = run.timestamp
            }
            val dateFormat = SimpleDateFormat("dd.MM.yy", Locale.getDefault())
            tvDate.text = dateFormat.format(calendar.time)

            val avgSpeed = "${run.avgSpeedInKMH}km/h"
            tvAvgSpeed.text = avgSpeed

            val distanceInKm = "${run.distanceInMeters / 1000f}km"
            tvDistance.text = distanceInKm

            tvTime.text = TrackingUtility.getFormattedStopWatchTime(run.timeInMillis)

            //tvButton.setOnClickListener(r)

            val caloriesBurned = "${run.caloriesBurned}kcal"
            tvCalories.text = caloriesBurned


            //delete track
            tvDelete.setOnClickListener{
                Timber.d("Deleting items")
                listener.onItemClicked(run,"delete")
                notifyDataSetChanged();
            }

            //upload track to Mongodb using graphql api if not uploaded

            tvExport.setOnClickListener{
                listener.onItemClicked(run, "upload")
            }

        }
    }
}














