package jdbchttpsql.repository

import jdbchttpsql.data.FormatData
import jdbchttpsql.data.DatabaseConnector
import org.ktorm.dsl.*

class SQLQueries {

    private val currentguid = FormatData.RadioMetaData.guid
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
            .from(FormatData.RadioMetaData)
            .select(FormatData.RadioMetaData.album)
            .union(
                database.from(FormatData.RadioMetaData).select(FormatData.RadioMetaData.album)
            )
            .unionAll(
                database.from(FormatData.RadioMetaData).select(FormatData.RadioMetaData.album)
            )
            .orderBy(FormatData.RadioMetaData.album.desc())
    }

    val query = database.useTransaction {
        database
            .from(FormatData.RadioMetaData)
            .select(FormatData.RadioMetaData.album)
            .union(
                database.from(FormatData.RadioMetaData).select(FormatData.RadioMetaData.album)
            )
            .unionAll(
                database.from(FormatData.RadioMetaData).select(FormatData.RadioMetaData.album)
            )
            .orderBy(FormatData.RadioMetaData.album.desc())
    }

    val query2 = database.useTransaction {
        database
        .from(FormatData.RadioMetaData)
        .select(FormatData.RadioMetaData.album)
    }

    val query3 = database.useTransaction {
        database
            .from(FormatData.RadioMetaData)
            .select(FormatData.RadioMetaData.guid)
            .where(currentguid eq FormatData.RadioMetaData.guid)
    }

    /*.where { (FormatData.Staffs.id eq 1) and (FormatData.Staffs.artist like "%vince%") }
    database.deleteAll(FormatData.RadioMetaData)*/

    /*database.let{
        it.update(FormatData.RadioMetaData){
            set(it.album, "${td.album}")
        }
        it.useConnection { database }
    }*/

    /*
    for (row in database
        .from(FormatData.RadioMetaData)
        .select()) {
        println(row[FormatData.RadioMetaData.album])
        println(row[FormatData.RadioMetaData.sku])
        println(row[FormatData.RadioMetaData.thumb])
        println(row[FormatData.RadioMetaData.artist])
        println(row[FormatData.RadioMetaData.title])
    }*/
}