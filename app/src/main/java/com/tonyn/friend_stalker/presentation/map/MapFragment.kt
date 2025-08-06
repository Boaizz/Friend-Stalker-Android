package com.tonyn.friend_stalker.presentation.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.tonyn.friend_stalker.R

class MapFragment : Fragment(), OnMapReadyCallback {

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_map, container, false)
        mapView = view.findViewById(R.id.mapView)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
        return view
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map

        // Cho phép xoay và nghiêng
        googleMap?.uiSettings?.isRotateGesturesEnabled = true
        googleMap?.uiSettings?.isTiltGesturesEnabled = true

        // Bật hiển thị toà nhà 3D
        googleMap?.isBuildingsEnabled = true

        // Vị trí bạn muốn hiển thị 3D (ví dụ Sydney)
        val latLng = LatLng(-33.8688, 151.2093)

        // Tạo camera với góc nghiêng để hiển thị 3D
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17f)
            .tilt(45f)
            .bearing(0f)
            .build()

        googleMap?.moveCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))

        // Thêm Marker
        googleMap?.addMarker(
            MarkerOptions()
                .position(latLng)
                .title("Sydney in 3D")
        )
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        super.onPause()
        mapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView?.onSaveInstanceState(outState)
    }
}
