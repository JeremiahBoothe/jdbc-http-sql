package jdbchttpsql.Repository

import jdbchttpsql.Data.SLEDATA
import org.ktorm.dsl.insert
import java.time.LocalDateTime

class InsertMethods {

    private val httpRequests = HttpRequests()
    private val td = httpRequests.td
    private val sqlQueries = SQLQueries()
    //get local timestamp
    private val database = sqlQueries.database
    private val localtime: LocalDateTime = LocalDateTime.now()

    /*insertSLERadio checks if the entry guid exists, if so it keeps skipping try until it comes to an entry that doesn't exist
    it then adds the entry and continues to skip try because the entry now exists*/

    fun insertSLERadio() {
        if (SLEDATA.RadioMetaData.guid != null) {
            try {
                database.useTransaction {
                    database.insert(SLEDATA.RadioMetaData) {
                        set(SLEDATA.RadioMetaData.album, "${td.album}")
                        set(SLEDATA.RadioMetaData.sku, "${td.sku}")
                        set(SLEDATA.RadioMetaData.thumb, "${td.thumb}")
                        set(SLEDATA.RadioMetaData.artist, "${td.artist}")
                        set(SLEDATA.RadioMetaData.title, "${td.title}")
                        set(SLEDATA.RadioMetaData.played_show, "${td.played_show}")
                        set(SLEDATA.RadioMetaData.buy_urls, "${td.buy_urls}")
                        set(SLEDATA.RadioMetaData.info_urls, "${td.info_urls}")
                        set(SLEDATA.RadioMetaData.duration, "${td.duration}")
                        set(SLEDATA.RadioMetaData.guid, "${td.guid}")
                        set(SLEDATA.RadioMetaData.timestamp, "${localtime}")
                    }
                }
                println("Entry Added!")
                return
            } catch (e: Exception) {
                println("Exceptions!!!")
                return
            } catch (e: StackOverflowError) {
                println("StackOverflowError")
                return
            } finally {
                println(td)
                println("${localtime}")
                println("Always prints(finally)")
                return
            }
        } else {
            println("OrElse!")
            return
        }
    }
    /*
    fun insertSLERadio() {
        try {
            database.useTransaction {
                database.insert(SLEDATA.RadioMetaData) {
                    set(SLEDATA.RadioMetaData.album, "${td.album}")
                    set(SLEDATA.RadioMetaData.sku, "${td.sku}")
                    set(SLEDATA.RadioMetaData.thumb, "${td.thumb}")
                    set(SLEDATA.RadioMetaData.artist, "${td.artist}")
                    set(SLEDATA.RadioMetaData.title, "${td.title}")
                    set(SLEDATA.RadioMetaData.played_show, "${td.played_show}")
                    set(SLEDATA.RadioMetaData.buy_urls, "${td.buy_urls}")
                    set(SLEDATA.RadioMetaData.info_urls, "${td.info_urls}")
                    set(SLEDATA.RadioMetaData.duration, "${td.duration}")
                    set(SLEDATA.RadioMetaData.guid, "${td.guid}")
                    set(SLEDATA.RadioMetaData.timestamp, "${localtime}")
                }
            }
        } catch (e: Exception) {
            TODO("update timestamp when guid is present in the database")
            /* database.useTransaction {
             database.update(SLEDATA.RadioMetaData){
                 while (i in SLEDATA.RadioMetaData.set(SLEDATA.RadioMetaData.timestamp, "${localtime}")
                 where {
                     it.guid eq it.guid
                 }
             }
         }*/
            return
        } finally {
            println(td)
            println("${localtime}")
            return
        }
    }

        fun insertSLERadio3() {
            database.insert(SLEDATA.RadioMetaData) {
                set(SLEDATA.RadioMetaData.album, "${td.album}")
                set(SLEDATA.RadioMetaData.sku, "${td.sku}")
                set(SLEDATA.RadioMetaData.thumb, "${td.thumb}")
                set(SLEDATA.RadioMetaData.artist, "${td.artist}")
                set(SLEDATA.RadioMetaData.title, "${td.title}")
                set(SLEDATA.RadioMetaData.played_show, "${td.played_show}")
                set(SLEDATA.RadioMetaData.buy_urls, "${td.buy_urls}")
                set(SLEDATA.RadioMetaData.info_urls, "${td.info_urls}")
                set(SLEDATA.RadioMetaData.duration, "${td.duration}")
                set(SLEDATA.RadioMetaData.guid, "${td.guid}")
                set(SLEDATA.RadioMetaData.timestamp, "${localtime}")
            }
        }*/
}