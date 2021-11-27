package com.example.finalplayground.domain.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RocketDetails(
    val height: Diameter,
    val diameter: Diameter,
    val mass: Mass,

    @SerialName("first_stage")
    val firstStage: FirstStage,

    @SerialName("second_stage")
    val secondStage: SecondStage,

    val engines: Engines,

    @SerialName("landing_legs")
    val landingLegs: LandingLegs,

    @SerialName("payload_weights")
    val payloadWeights: List<PayloadWeight>,

    @SerialName("flickr_images")
    val flickrImages: List<String>,

    val name: String,
    val type: String,
    val active: Boolean,
    val stages: Long,
    val boosters: Long,

    @SerialName("cost_per_launch")
    val costPerLaunch: Long,

    @SerialName("success_rate_pct")
    val successRatePct: Long,

    @SerialName("first_flight")
    val firstFlight: String,

    val country: String,
    val company: String,
    val wikipedia: String,
    val description: String,
    val id: String
)

@Serializable
data class Diameter(
    val meters: Double,
    val feet: Double
)

@Serializable
data class Engines(
    val isp: ISP,

    @SerialName("thrust_sea_level")
    val thrustSeaLevel: Thrust,

    @SerialName("thrust_vacuum")
    val thrustVacuum: Thrust,

    val number: Long,
    val type: String,
    val version: String,
    val layout: String,

    @SerialName("engine_loss_max")
    val engineLossMax: Long,

    @SerialName("propellant_1")
    val propellant1: String,

    @SerialName("propellant_2")
    val propellant2: String,

    @SerialName("thrust_to_weight")
    val thrustToWeight: Double
)

@Serializable
data class ISP(
    @SerialName("sea_level")
    val seaLevel: Long,

    val vacuum: Long
)

@Serializable
data class Thrust(
    val kN: Long,
    val lbf: Long
)

@Serializable
data class FirstStage(
    @SerialName("thrust_sea_level")
    val thrustSeaLevel: Thrust,

    @SerialName("thrust_vacuum")
    val thrustVacuum: Thrust,

    val reusable: Boolean,
    val engines: Long,

    @SerialName("fuel_amount_tons")
    val fuelAmountTons: Double,

    @SerialName("burn_time_sec")
    val burnTimeSEC: Long
)

@Serializable
data class LandingLegs(
    val number: Long
)

@Serializable
data class Mass(
    val kg: Long,
    val lb: Long
)

@Serializable
data class PayloadWeight(
    val id: String,
    val name: String,
    val kg: Long,
    val lb: Long
)

@Serializable
data class SecondStage(
    val thrust: Thrust,
    val payloads: Payloads,
    val reusable: Boolean,
    val engines: Long,

    @SerialName("fuel_amount_tons")
    val fuelAmountTons: Double,

    @SerialName("burn_time_sec")
    val burnTimeSEC: Long
)

@Serializable
data class Payloads(
    @SerialName("composite_fairing")
    val compositeFairing: CompositeFairing,

    @SerialName("option_1")
    val option1: String
)

@Serializable
data class CompositeFairing(
    val height: Diameter,
    val diameter: Diameter
)
