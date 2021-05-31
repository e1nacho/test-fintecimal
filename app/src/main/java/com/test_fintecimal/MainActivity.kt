package com.test_fintecimal

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.room.Room
import kotlinx.android.synthetic.main.activity_main.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlinx.android.synthetic.main.card_view_locations.*


class MainActivity : AppCompatActivity(), LocationAdapter.Listener {
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var locationAdapter: RecyclerView.Adapter<LocationAdapter.ViewHolder>? = null
    private var locationEntityArrayList: ArrayList<LocationEntity>? = null
    var latitude = 0.0
    var longitude = 0.0
    var streetName = ""
    var suburb = ""
    var visited = true
    var id=0
    var locationCount=0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        layoutManager = LinearLayoutManager(this)
        recyclerView_locations.layoutManager = layoutManager


        val retrofit = Retrofit.Builder()
                .baseUrl("https://fintecimal-test.herokuapp.com/api/interview/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
        val service: ApiService = retrofit.create(ApiService::class.java)


        val repos: Call<List<ApiServiceData>> = service.getLocations()

        repos.enqueue(object : Callback<List<ApiServiceData>> {
            override fun onFailure(call: Call<List<ApiServiceData>>, t: Throwable) {
                Log.e("", "Fail Respone: " + t)
            }

            override fun onResponse(call: Call<List<ApiServiceData>>, response: Response<List<ApiServiceData>>) {
                val apiServiceData: ApiServiceData? = response.body() as ApiServiceData?
                Log.e("", "successfully response")
            }

        })

        val db = Room.databaseBuilder(this,
                AppDataBase::class.java, "fintecimalDB").allowMainThreadQueries().build()
        val locations = LocationEntity(streetName = "Av. de la Paz 2599", suburb = "Arcos Vallarta", visited = false, longitude = 20.6721825, latitude = -103.3844292)
        val reg: Long? = db.locationDao()?.insert(locations)



        val listLocation: List<LocationEntity> = db.locationDao()?.getAll() as List<LocationEntity>;
        locationCount=listLocation.size


        for (i in listLocation.indices) {
            latitude = listLocation[i].latitude
            longitude = listLocation[i].longitude
            streetName = listLocation[i].streetName
            suburb = listLocation[i].suburb
            visited = listLocation[i].visited
            id = listLocation[i].id

            locationEntityArrayList = ArrayList(listLocation)
            locationAdapter = LocationAdapter(locationEntityArrayList!!, this)
            recyclerView_locations.adapter = locationAdapter

        }



    }


    override fun onItemClick(locationData: LocationEntity) {

        val bundle = Bundle()

        bundle.putDouble("latitude", latitude)
        bundle.putDouble("longitude", longitude)
        bundle.putString("streetName", streetName)
        bundle.putString("suburb", suburb)
        bundle.putBoolean("visited", visited)
        bundle.putInt("id", id)
        bundle.putInt("locationCount", locationCount)

        val intent = Intent(this@MainActivity, MapsActivity::class.java)
        intent.putExtras(bundle)
        startActivity(intent)


    }


}


