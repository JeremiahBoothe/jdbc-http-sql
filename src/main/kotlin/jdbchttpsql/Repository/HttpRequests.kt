package jdbchttpsql.Repository

import jdbchttpsql.Data.SLEDATA
import com.google.gson.Gson
import java.net.URL

class HttpRequests {

    //ReadText from webpage and use data.class to map the webpage data to gson.
    private val webPage = URL("https://www.radiojar.com/api/stations/umg0tz9sywzuv/now_playing/")
    private val gson = Gson()
    val data = webPage.readText()
    val td: SLEDATA.MetaData = gson.fromJson(data, SLEDATA.MetaData::class.java)

    val webPage2 = URL("https://n0d.radiojar.com/umg0tz9sywzuv?rj-ttl=5&rj-tok=AAABhOmgoR0ArwAOO-mNnWqdtw")

    /*
        //Attempt to read stream?
        val webPage2 = URL("https://n0d.radiojar.com/umg0tz9sywzuv?rj-ttl=5&rj-tok=AAABhOmgoR0ArwAOO-mNnWqdtw")
        val data2 = webPage2.readText()
        val data3 = webPage2.hashCode()
        val td2 = data2.hashCode()
        println(td2)

        val reader = data.bufferedReader()
        val iterator = reader.linesSequences().iterator()
        while(iterator.hasNext()) {
            val line = iterator.next()
            // do something with line...
        }
        reader.close()*/
}