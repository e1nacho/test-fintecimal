package com.test_fintecimal

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.util.Log
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
import kotlinx.android.synthetic.main.activity_maps.*


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
        val buttonNavigation = findViewById<Button>(R.id.button_navigation)
        val buttonCheck = findViewById<Button>(R.id.button_check)


        if (bundle!!.getBoolean("visited")) {
            visited.text = "Visitado"
            visited.setTextColor(Color.parseColor("#3ac2c2"))
            indicator.setImageResource(R.drawable.circle_status_true);
            buttonNavigation.visibility = View.GONE;
            buttonCheck.visibility = View.GONE;
        } else {
            visited.text = "Pendiente"
            visited.setTextColor(Color.parseColor("#AAAAAA"))
            indicator.setImageResource(R.drawable.circle_status_false);
            buttonNavigation.visibility = View.VISIBLE;
            buttonCheck.visibility = View.VISIBLE;
        }

        streetName.text = bundle.getString("streetName")
        suburb.text = bundle.getString("suburb")

        val homeButton = findViewById<ImageView>(R.id.imageView_home)
        homeButton.setOnClickListener { v: View ->
            val intent = Intent(v.context, MainActivity::class.java)
            v.context.startActivity(intent)
            finish();

        }


        button_check.setOnClickListener { v: View ->

            val db = Room.databaseBuilder(this,
                    AppDataBase::class.java, "fintecimalDB").allowMainThreadQueries().build()

            val reg: Unit? = db.locationDao()?.update(true,bundle.getInt("id"))
            val intent = Intent(this@MapsActivity, MainActivity::class.java)
            intent.putExtras(bundle)
            startActivity(intent)
            finish();
        }

        buttonNavigation.setOnClickListener { v: View ->

            val mapIntent: Intent = Uri.parse("geo:"+bundle.getDouble("longitude")+","+bundle.getDouble("latitude")+"?z=14").let { location ->

                Intent(Intent.ACTION_VIEW, location)
            }
            startActivity(mapIntent)
        }


    }


    override fun onMapReady(googleMap: GoogleMap) {

        mMap = googleMap
        val bundle = intent.extras

        val sydney = LatLng(bundle!!.getDouble("longitude"), bundle!!.getDouble("latitude"))
        if (bundle!!.getBoolean("visited")) {
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")).setIcon(bitmapDescriptorFromVector(R.drawable.ic_visited_marker))

        }else{
            mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney")).setIcon(bitmapDescriptorFromVector(R.drawable.ic_marker))

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