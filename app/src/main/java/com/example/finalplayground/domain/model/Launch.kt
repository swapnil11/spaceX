package com.example.finalplayground.domain.model

import kotlinx.datetime.Instant
import kotlinx.datetime.toJavaInstant
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.ZoneOffset

@Serializable
data class Launch(
    val fairings: Fairings? = null,
    val links: Links,

    @SerialName("static_fire_date_utc")
    val staticFireDateUTC: Instant? = null,

    @SerialName("static_fire_date_unix")
    val staticFireDateUnix: Long? = null,

    val net: Boolean,
    val window: Long? = null,
    val rocket: String,
    val success: Boolean? = null,
    val failures: List<Failure>,
    val details: String? = null,
    val crew: List<String>,
    val ships: List<String>,
    val capsules: List<String>,
    val payloads: List<String>,
    val launchpad: String,

    @SerialName("flight_number")
    val flightNumber: Long,

    val name: String,

    @SerialName("date_utc")
    val dateUTC: Instant,

    @SerialName("date_unix")
    val dateUnix: Long,

    @SerialName("date_local")
    val dateLocal: Instant,

    @SerialName("date_precision")
    val datePrecision: DatePrecision,

    val upcoming: Boolean,
    val cores: List<Core>,

    @SerialName("auto_update")
    val autoUpdate: Boolean,

    val tbd: Boolean,

    @SerialName("launch_library_id")
    val launchLibraryID: String? = null,
    val id: String,

    var launchYear: String? = null
) : java.io.Serializable {
    init {
        launchYear = dateUTC.toJavaInstant().atZone(ZoneOffset.UTC).year.toString()
    }
}

@Serializable
data class Core(
    val core: String? = null,
    val flight: Long? = null,
    val gridfins: Boolean? = null,
    val legs: Boolean? = null,
    val reused: Boolean? = null,

    @SerialName("landing_attempt")
    val landingAttempt: Boolean? = null,

    @SerialName("landing_success")
    val landingSuccess: Boolean? = null,

    @SerialName("landing_type")
    val landingType: LandingType? = null,

    val landpad: String? = null
) : java.io.Serializable

@Serializable
enum class LandingType {
    ASDS,
    @SerialName("Ocean")
    OCEAN,
    RTLS;
}

@Serializable
enum class DatePrecision(val value: String) {
    @SerialName("day")
    DAY("day"),
    @SerialName("hour")
    HOUR("hour"),
    @SerialName("month")
    MONTH("month"),
    @SerialName("quarter")
    QUARTER("quarter");
}

@Serializable
data class Failure(
    val time: Long,
    val altitude: Long? = null,
    val reason: String
) : java.io.Serializable

@Serializable
data class Fairings(
    val reused: Boolean? = null,

    @SerialName("recovery_attempt")
    val recoveryAttempt: Boolean? = null,

    val recovered: Boolean? = null,
    val ships: List<String>
) : java.io.Serializable

@Serializable
data class Links(
    val patch: Patch,
    val reddit: Reddit,
    val flickr: Flickr,
    val presskit: String? = null,
    val webcast: String? = null,

    @SerialName("youtube_id")
    val youtubeID: String? = null,

    val article: String? = null,
    val wikipedia: String? = null
) : java.io.Serializable

@Serializable
data class Flickr(
    val original: List<String>
) : java.io.Serializable

@Serializable
data class Patch(
    val small: String? = null,
    val large: String? = null
) : java.io.Serializable

@Serializable
data class Reddit(
    val campaign: String? = null,
    val launch: String? = null,
    val media: String? = null,
    val recovery: String? = null
) : java.io.Serializable
