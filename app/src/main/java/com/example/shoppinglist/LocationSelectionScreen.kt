package com.example.shoppinglist

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberMarkerState

@Composable
fun LocationSelectionScreen(
    location: LocationData,
    onLocationSelected: (LocationData) -> Unit,
    modifier: Modifier = Modifier,
) {
    val userLocation = remember {
        mutableStateOf(LatLng(location.latitude, location.longitude))
    }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(userLocation.value, 10f)
    }
    Column(modifier = modifier.fillMaxSize()) {
        GoogleMap(
            cameraPositionState = cameraPositionState,
            onMapClick = { latLng ->
                userLocation.value = latLng
                onLocationSelected(LocationData(latLng.latitude, latLng.longitude))
            },
            modifier = Modifier
                .weight(1f)
                .padding(top = 16.dp),
        ) {
            Marker(state = rememberMarkerState(position = userLocation.value))
        }
        var newLocation: LocationData
        Button(
            onClick = {
                newLocation =
                    LocationData(userLocation.value.latitude, userLocation.value.longitude)
                onLocationSelected(newLocation)
            },
        ) {
            Text(text = "Set Location")
        }
    }
}