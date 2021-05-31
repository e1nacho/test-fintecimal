package com.test_fintecimal


import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.card_view_locations.view.*

class LocationAdapter (private val locationList : ArrayList<LocationEntity>, private val listener :



Listener) : RecyclerView.Adapter<LocationAdapter.ViewHolder>() {
    interface Listener {
        fun onItemClick(locationData : LocationEntity)



    }



    private val colors : Array<String> = arrayOf("#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff", "#ffffff" , "#ffffff" , "#ffffff")




    override fun onBindViewHolder(holder: ViewHolder, position: Int) {



        holder.bind(locationList[position], listener, colors, position)



    }



    override fun getItemCount(): Int = locationList.count()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.card_view_locations, parent, false)
        return ViewHolder(view)

    }



    class ViewHolder(view : View) : RecyclerView.ViewHolder(view) {



        fun bind(locationEntity: LocationEntity, listener: Listener, colors : Array<String>, position: Int) {

            val status = itemView.findViewById<TextView>(R.id.cardView_status)
            val indicator = itemView.findViewById<ImageView>(R.id.cardView_indicator)

            itemView.setOnClickListener{ listener.onItemClick(locationEntity) }
            itemView.setBackgroundColor(Color.parseColor(colors[position % 8]))

            if(locationEntity.visited){
                itemView.cardView_status.text = "Visitado"
                status.setTextColor(Color.parseColor("#3ac2c2"))
                indicator.setImageResource(R.drawable.circle_status_true);
            }else{
                itemView.cardView_status.text = "Pendiente"
                status.setTextColor(Color.parseColor("#AAAAAA"))
                indicator.setImageResource(R.drawable.circle_status_false);
            }

            itemView.cardView_streetName.text = locationEntity.streetName
            itemView.cardView_suburb.text = locationEntity.suburb

        }

    }

}