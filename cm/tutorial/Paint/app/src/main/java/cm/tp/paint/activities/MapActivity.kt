package cm.tp.paint.activities

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import cm.tp.paint.listeners.FusedLocation
import cm.tp.paint.R
import cm.tp.paint.listeners.OnLocationChangedListener
import com.google.android.gms.location.LocationResult
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.vmadalin.easypermissions.EasyPermissions
import com.vmadalin.easypermissions.dialogs.SettingsDialog

const val PERMISSION_LOCATION_REQUEST_CODE = 1

class MapActivity : AppCompatActivity(), OnMapReadyCallback, EasyPermissions.PermissionCallbacks, OnLocationChangedListener
{
    private var latitude = 0.0
    private var longitude = 0.0

    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_map)

        if (hasLocationPermission()) {
            FusedLocation.start(this)
            FusedLocation.registerListener(this)
        }
        else
        {
            requestLocationPermission()
        }

    }

    private fun initMap()
    {
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment

        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(p0: GoogleMap) {
        p0.addMarker(MarkerOptions().position(LatLng(latitude, longitude))
            .title("Está aqui"))
    }

    override fun onLocationChanged(locationResult: LocationResult) {
        latitude = locationResult.lastLocation.latitude
        longitude = locationResult.lastLocation.longitude
        initMap()
    }

    private fun hasLocationPermission() =
        EasyPermissions.hasPermissions(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        )

    private fun requestLocationPermission() {
        EasyPermissions.requestPermissions(
            this,
            "This application cannot work without Location Permission.",
            PERMISSION_LOCATION_REQUEST_CODE,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
    }

    override fun onPermissionsDenied(requestCode: Int, perms: List<String>) {
        if (EasyPermissions.somePermissionPermanentlyDenied(this, perms)) {
            SettingsDialog.Builder(this).build().show()
        } else {
            requestLocationPermission()
        }
    }

    override fun onPermissionsGranted(requestCode: Int, perms: List<String>)
    {
        FusedLocation.start(this)
        FusedLocation.registerListener(this)
    }
}