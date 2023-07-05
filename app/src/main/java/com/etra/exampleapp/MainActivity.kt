package com.etra.exampleapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.etra.chargingpoints.EtraChargingPointsSDK
import com.etra.chargingpoints.domain.model.ChargingPoint
import com.etra.exampleapp.ui.theme.ExampleAppTheme
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var chargingPointsState by mutableStateOf<List<ChargingPoint>>(emptyList())


        EtraChargingPointsSDK.initEtraSDK(
            username = "sdkvlciws",
            password = "WADqdMCGZ1UsLg2n9J6y",
            lowSecurityKey = "9c27416861c148fca49f8b2c53f7eb84"
        )

        val etraSDK = EtraChargingPointsSDK.getInstance()
        GlobalScope.launch {
            etraSDK.chargingPoints(longitude = -0.401818, latitude = 39.495148, radius = 1000000,
                onSuccess = {
                    chargingPointsState = it
                }, onError = {

                })
        }

        setContent {
            ExampleAppTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    LazyColumn(modifier = Modifier.padding(16.dp)) {
                        items(chargingPointsState) { chargingPoint ->
                            ChargingPointItem(chargingPoint = chargingPoint)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ChargingPointItem(chargingPoint: ChargingPoint) {
    Row(modifier = Modifier.padding(vertical = 8.dp)) {
        // Mostrar información del ChargingPoint en filas
        Text(
            text = "Coordenadas",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = chargingPoint.location.coordinates.toString(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
    Row(modifier = Modifier.padding(vertical = 8.dp).padding(bottom = 16.dp)) {
        // Mostrar información del ChargingPoint en filas
        Text(
            text = "Numero de conectores",
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
        Text(
            text = chargingPoint.connectors.size.toString(),
            modifier = Modifier.weight(1f),
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    ExampleAppTheme {
        Greeting("Android")
    }
}