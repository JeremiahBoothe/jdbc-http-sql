package jdbchttpsql.repository

import com.google.gson.Gson
import jdbchttpsql.data.SongData
import org.bson.conversions.Bson
import java.net.URL

class HttpRequests {

    //ReadText from webpage and use data.class to map the webpage data to gson.
    private val webPage = URL("https://www.radiojar.com/api/stations/umg0tz9sywzuv/now_playing/")
    //private val webPage = URL("https://www.radiojar.com/api/stations/2z8y97h1v8quv/now_playing/") // poor sucker that I'm using to test the http to SQL stream
    private val gson = Gson()
    private val data = webPage.readText()

    //private val bson = Bson()
    val td: SongData.SongData = gson.fromJson(data, SongData.SongData::class.java)
}