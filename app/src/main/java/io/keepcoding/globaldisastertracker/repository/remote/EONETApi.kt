
import io.keepcoding.globaldisastertracker.domain.EONETResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface EONETApi {

    @GET("/events")  // planetary/apod?api_key=DEMO_KEY
    @Headers("Content-Type: application/json")
    fun getEvents(@Query("api_key") apiKey: String): EONETResponse
    
}
