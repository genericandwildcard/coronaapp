import kotlin.math.pow

typealias VersionName = String
typealias VersionCode = Int

fun VersionName.toVersionCode(): VersionCode {
    val versions = this.split("-")
    val versionCode = versions.first().split(".")
        .map { it.toInt() }
        .reversed()
        .mapIndexed { index, value -> (value * 10.0.pow(index * 2)).toInt() }
        .reduce { acc, i -> acc + i }
    // is there a buildversion at the end?
    return if (versions.size == 2) {
        versionCode * 10000 + versions[1].toInt()
    } else {
        versionCode * 10000
    }
}
