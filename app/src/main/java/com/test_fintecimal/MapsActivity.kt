package com.test_fintecimal

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
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
import kotlinx.android.synthetic.main.card_view_locations.view.*


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maps)

        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        val bundle = intent.extras


        val visited = findViewById<TextView>(R.id.maps_cardView_visited)
        val indicator = findViewById<ImageView>(R.id.maps_cardView_indicator)
        val streetName = findViewById<TextView>(R.id.maps_cardView_streetName)
        val suburb = findViewById<TextView>(R.id.maps_cardView_suburb)


        if (bundle!!.getBoolean("visited")) {
            visited.text = "Visitado"
            visited.setTextColor(Color.parseColor("#3ac2c2"))
            indicator.setImageResource(R.drawable.circle_status_true);
        } else {
            visited.text = "Pendiente"
            visited.setTextColor(Color.parseColor("#AAAAAA"))
            indicator.setImageResource(R.drawable.circle_status_false);
        }

        streetName.text = bundle.getString("streetName")
        suburb.text = bundle.getString("suburb")

        val homeButton = findViewById<ImageView>(R.id.imageView_home)
        homeButton.setOnClickListener { v: View ->
            val intent = Intent(v.context, MainActivity::class.java)
            v.context.startActivity(intent)
        }
    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val bundle = intent.extras

        val sydney = LatLng(bundle!!.getDouble("latitude"), bundle!!.getDouble("longitude"))
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")).setIcon(bitmapDescriptorFromVector(R.drawable.ic_marker))
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