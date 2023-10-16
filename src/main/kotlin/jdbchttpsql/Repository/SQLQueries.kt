package jdbchttpsql.Repository

import jdbchttpsql.Data.SLEDATA
import jdbchttpsql.Data.DatabaseConnector
import org.ktorm.dsl.*

class SQLQueries {

    private val currentguid = SLEDATA.RadioMetaData.guid
    val database = DatabaseConnector.databaseConnection

    /*
//prints Metadata
println("${queries.td}")
println(database)
println(queries.td)
println(queries.queryaa)
println(database)
println(queries.query)
println(database.dialect.toString())
println(database.getScopeName())
*/

    val queryaa = database.useTransaction {
        database
            .from(SLEDATA.RadioMetaData)
            .select(SLEDATA.RadioMetaData.album)
            .union(
                database.from(SLEDATA.RadioMetaData).select(SLEDATA.RadioMetaData.album)
            )
            .unionAll(
                database.from(SLEDATA.RadioMetaData).select(SLEDATA.RadioMetaData.album)
            )
            .orderBy(SLEDATA.RadioMetaData.album.desc())
    }

    val query = database.useTransaction {
        database
            .from(SLEDATA.RadioMetaData)
            .select(SLEDATA.RadioMetaData.album)
            .union(
                database.from(SLEDATA.RadioMetaData).select(SLEDATA.RadioMetaData.album)
            )
            .unionAll(
                database.from(SLEDATA.RadioMetaData).select(SLEDATA.RadioMetaData.album)
            )
            .orderBy(SLEDATA.RadioMetaData.album.desc())
    }

    val query2 = database.useTransaction {
        database
        .from(SLEDATA.RadioMetaData)
        .select(SLEDATA.RadioMetaData.album)
    }

    val query3 = database.useTransaction {
        database
            .from(SLEDATA.RadioMetaData)
            .select(SLEDATA.RadioMetaData.guid)
            .where(currentguid eq SLEDATA.RadioMetaData.guid)
    }
    /*.where { (SLEDATA.Staffs.id eq 1) and (SLEDATA.Staffs.artist like "%vince%") }
    database.deleteAll(SLEDATA.RadioMetaData)*/

    /*database.let{
        it.update(SLEDATA.RadioMetaData){
            set(it.album, "${td.album}")
        }
        it.useConnection { database }
    }*/

    /*
    for (row in database
        .from(SLEDATA.RadioMetaData)
        .select()) {
        println(row[SLEDATA.RadioMetaData.album])
        println(row[SLEDATA.RadioMetaData.sku])
        println(row[SLEDATA.RadioMetaData.thumb])
        println(row[SLEDATA.RadioMetaData.artist])
        println(row[SLEDATA.RadioMetaData.title])
    }*/
}