package com.vajda.spockExamples.datadriven

import groovy.sql.Sql
import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Unroll


class SqlSpec extends Specification {

    @Shared
    def sql
    
    def setupSpec() {
        sql = Sql.newInstance("jdbc:h2:mem:testDB", "org.h2.Driver")
        sql.execute """
            create table sumData (
                id int primary key,
                a int,
                b int,
                c int,
                d int)
            """
        sql.execute("insert into sumData values(1, 2, 3, 0, 5), (2, 1, 1, 1, 2), (3, 8, 3, 5, 10)")
    }
    
    @Unroll
    def "#x + #y should equal #z"() {
        expect:
        x + y == z
        
        where:
        [x, y, z] << sql.rows("select a, b, d from sumData")
    }
    
    @Unroll
    def "#a + #b should equal #d"() {
        expect:
        a + b == d
        
        where:
        [_, a, b, _, d] << sql.rows("select * from sumData")
    }
    
    @Unroll
    def "second test: #row.a + #row.b should equal #row.d"() {
        expect:
        a + b == d
        
        where:
        row << sql.rows("select * from sumData")
        a = row.a
        b = row.b
        d = row.d
    }
    
    def cleanupSpec() {
        sql.close()
    }
}
