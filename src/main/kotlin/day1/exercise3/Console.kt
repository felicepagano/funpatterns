package day1.exercise3

import day1.exercise1.andThen
import java.io.BufferedReader
import java.io.InputStreamReader


data class Console<T>(val exec: () -> T) {

    fun <B> andThen(other: Console<B>): Console<B> = Console {
        this.exec()
        other.exec()
    }

    fun <B> of (t: B): Console<B> = Console { t }
    // chain dovrebbe essere un sinonimo di flatMap

    // Console accetta un thunk quindi per questo andThen non viene eseguito subito
    fun <B> chain(f: (T) -> Console<B>): Console<B> = Console { (exec andThen f)()() }

    fun <B> chainNonLazy(f: (T) -> Console<B>): Console<B> = (exec andThen f)()

    // fun <B> map(f: (T) -> B): Console<B> = this.chain { of(f(this.exec())) }

    operator fun invoke() = exec()
}


fun printIO(msg: String) = Console { println(msg)}

val reader = BufferedReader(InputStreamReader(System.`in`))

fun readlineIO() = Console<String> { reader.readLine() }