package jdbchttpsql.repository

import jdbchttpsql.data.FormatData
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
        if (FormatData.RadioMetaData.guid != null) {
            try {
                database.useTransaction {
                    database.insert(FormatData.RadioMetaData) {
                        set(FormatData.RadioMetaData.album, "${td.album}")
                        set(FormatData.RadioMetaData.sku, "${td.sku}")
                        set(FormatData.RadioMetaData.thumb, "${td.thumb}")
                        set(FormatData.RadioMetaData.artist, "${td.artist}")
                        set(FormatData.RadioMetaData.title, "${td.title}")
                        set(FormatData.RadioMetaData.played_show, "${td.played_show}")
                        set(FormatData.RadioMetaData.buy_urls, "${td.buy_urls}")
                        set(FormatData.RadioMetaData.info_urls, "${td.info_urls}")
                        set(FormatData.RadioMetaData.duration, "${td.duration}")
                        set(FormatData.RadioMetaData.guid, "${td.guid}")
                        set(FormatData.RadioMetaData.timestamp, "${localtime}")
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
                database.insert(FormatData.RadioMetaData) {
                    set(FormatData.RadioMetaData.album, "${td.album}")
                    set(FormatData.RadioMetaData.sku, "${td.sku}")
                    set(FormatData.RadioMetaData.thumb, "${td.thumb}")
                    set(FormatData.RadioMetaData.artist, "${td.artist}")
                    set(FormatData.RadioMetaData.title, "${td.title}")
                    set(FormatData.RadioMetaData.played_show, "${td.played_show}")
                    set(FormatData.RadioMetaData.buy_urls, "${td.buy_urls}")
                    set(FormatData.RadioMetaData.info_urls, "${td.info_urls}")
                    set(FormatData.RadioMetaData.duration, "${td.duration}")
                    set(FormatData.RadioMetaData.guid, "${td.guid}")
                    set(FormatData.RadioMetaData.timestamp, "${localtime}")
                }
            }
        } catch (e: Exception) {
            TODO("update timestamp when guid is present in the database")
            /* database.useTransaction {
             database.update(FormatData.RadioMetaData){
                 while (i in FormatData.RadioMetaData.set(FormatData.RadioMetaData.timestamp, "${localtime}")
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
            database.insert(FormatData.RadioMetaData) {
                set(FormatData.RadioMetaData.album, "${td.album}")
                set(FormatData.RadioMetaData.sku, "${td.sku}")
                set(FormatData.RadioMetaData.thumb, "${td.thumb}")
                set(FormatData.RadioMetaData.artist, "${td.artist}")
                set(FormatData.RadioMetaData.title, "${td.title}")
                set(FormatData.RadioMetaData.played_show, "${td.played_show}")
                set(FormatData.RadioMetaData.buy_urls, "${td.buy_urls}")
                set(FormatData.RadioMetaData.info_urls, "${td.info_urls}")
                set(FormatData.RadioMetaData.duration, "${td.duration}")
                set(FormatData.RadioMetaData.guid, "${td.guid}")
                set(FormatData.RadioMetaData.timestamp, "${localtime}")
            }
        }*/
}