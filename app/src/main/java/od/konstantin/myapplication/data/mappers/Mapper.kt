package od.konstantin.myapplication.data.mappers

interface Mapper<T, V> {

    fun map(obj: T): V
}