# ETRA Charging Points SDK

Librería para obtener los puntos de carga de las estaciones eléctricas dada una coordenada [longitud, latitud, altitud]

## Dependencia

Añadir en el fichero .gradle del módulo app
```kotlin
implementation 'com.github.Dekalabs:etra-chargingpoints-sdk-android:1.0.0'
```
Añadir en el fichero .settings del proyecto
```kotlin
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url 'https://jitpack.io' }
    }
}
```
## Ejemplo de uso
En primer lugar hay que inicializar la librería con los datos necesarios para la autenticación: usuario, contraseña y lowSecuritykey 

```kotlin
EtraChargingPointsSDK.initEtraSDK(
            username = "username",
            password = "password",
            lowSecurityKey = "lowSecurityKey"
        )
```
        
Una vez inicializada se podrá obtener acceso a una instancia para poder recuperar los puntos de carga de las estaciones eléctricas.

```kotlin
 val chargingPointsSDK = EtraChargingPointsSDK.getInstance()
```

```kotlin
suspend fun chargingPoints(
        longitude: Double,
        latitude: Double,
        altitude: Double?,
        radius: Int,
        offsetItems: Int,
        maxItems: Int,
        onSuccess: (List<ChargingPoint>) -> Unit, onError: (String) -> Unit
    )
```

### Parámetros de la llamada
- **longitude**: longitud de las coordenada del punto donde se realiza la búsqueda de puntos de carga
- **latitude**: latitud de las coordenada del punto donde se realiza la búsqueda de puntos de carga
- **altitude**: altitud de las coordenada del punto donde se realiza la búsqueda de puntos de carga (parámetro opcional)
- **radius**: radio de busqueda en metros (por defecto 1000 metros)
- **offsetItems**: indica la posición desde la cual se recuperan los siguientes elementos. Se utiliza para paginar la respuesta. (por defecto 0)
- **maxItems**: cantidad de puntos de carga maximos que devuelve la respuesta (por defecto 10)
- **onSuccess**: callback de respuesta exitosa. Se devuelve un listado de objetos ChargingPoint que contiene la siguiente información:
```kotlin
@Parcelize
data class ChargingPoint(
    val id: String,
    val location: ChargingPointLocation,
    val connectors: List<ChargingPointConnector>
) : Parcelable
```

```kotlin
@Parcelize
data class ChargingPointLocation(
    val type: String,
    val coordinates: List<Double>
) : Parcelable
```

```kotlin
@Parcelize
data class ChargingPointConnector(
    val id: Int,
    val status: String,
    val power: Int,
    val type: String,
    val format: String,
    val speed: String,
) : Parcelable
```
- **onError**: callback de respuesta fallida. Se devuelve un mesaneje con la descripción del error

