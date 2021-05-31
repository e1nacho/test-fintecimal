package com.test_fintecimal

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.room.Room
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_maps.*
import kotlinx.android.synthetic.main.app_bar_maps.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val bundle = intent.extras


        if (bundle!!.getBoolean("visited")) {
            maps_cardView_visited.text = "Visitado"
            maps_cardView_visited.setTextColor(Color.parseColor("#3ac2c2"))
            maps_cardView_indicator.setImageResource(R.drawable.circle_status_true);
            button_navigation.visibility = View.GONE;
            button_check.visibility = View.GONE;
        } else {
            maps_cardView_visited.text = "Pendiente"
            maps_cardView_visited.setTextColor(Color.parseColor("#AAAAAA"))
            maps_cardView_indicator.setImageResource(R.drawable.circle_status_false);
            button_navigation.visibility = View.VISIBLE;
            button_check.visibility = View.VISIBLE;
        }

        maps_cardView_streetName.text = bundle.getString("streetName")
        maps_cardView_suburb.text = bundle.getString("suburb")


        imageView_home.setOnClickListener { v: View ->
            val intent = Intent(v.context, MainActivity::class.java)
            v.context.startActivity(intent)
            finish();

        }


        button_check.setOnClickListener { v: View ->

            val db = Room.databaseBuilder(this,
                    AppDataBase::class.java, "fintecimalDB").allowMainThreadQueries().build()

            val reg: Unit? = db.locationDao()?.update(true, bundle.getInt("id"))
            val intent = Intent(this@MapsActivity, MainActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            finish();
        }

        button_navigation.setOnClickListener { v: View ->

            val mapIntent: Intent = Uri.parse("geo:" + bundle.getDouble("longitude") + "," + bundle.getDouble("latitude") + "?z=14").let { location ->

                Intent(Intent.ACTION_VIEW, location)
            }
            startActivity(mapIntent)
        }


    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap

        mMap.setMinZoomPreference(14.0f);
        mMap.setMaxZoomPreference(14.0f);
        val bundle = intent.extras

        val sydney = LatLng(bundle!!.getDouble("longitude"), bundle!!.getDouble("latitude"))
        if (bundle!!.getBoolean("visited")) {
            mMap.addMarker(MarkerOptions().position(sydney).title("fintecimal")).setIcon(bitmapDescriptorFromVector(R.drawable.ic_visited_marker))

        }else{
            mMap.addMarker(MarkerOptions().position(sydney).title("fintecimal")).setIcon(bitmapDescriptorFromVector(R.drawable.ic_marker))

        }
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))


    }

    fun Context.bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor {
        val vectorDrawable = ContextCompat.getDrawable(this, vectorResId)
        vectorDrawable!!.setBounds(0, 0, vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth, vectorDrawable.intrinsicHeight, Bitmap.Config.ARGB_8888)
        vectorDrawable.draw(Canvas(bitmap))
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }


}