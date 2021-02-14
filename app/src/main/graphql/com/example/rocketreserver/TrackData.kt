// AUTO-GENERATED FILE. DO NOT MODIFY.
//
// This class was automatically generated by Apollo GraphQL plugin from the GraphQL queries it found.
// It should not be modified by hand.
//
package com.example.rocketreserver.type

import com.apollographql.apollo.api.Input
import com.apollographql.apollo.api.InputType
import com.apollographql.apollo.api.internal.InputFieldMarshaller
import com.apollographql.apollo.api.internal.InputFieldWriter
import kotlin.Double
import kotlin.Int
import kotlin.String
import kotlin.Suppress
import kotlin.collections.List

@Suppress("NAME_SHADOWING", "UNUSED_ANONYMOUS_PARAMETER", "LocalVariableName",
    "RemoveExplicitTypeArguments", "NestedLambdaShadowedImplicitParameter")
data class TrackData(
  val user: Input<String> = Input.absent(),
  val avgSpeedInKMH: Input<Double> = Input.absent(),
  val distanceInMeters: Input<Double> = Input.absent(),
  val points: Input<List<List<Double?>?>> = Input.absent(),
  val timeInMillis: Input<Int> = Input.absent(),
  val timestamp: Input<Int> = Input.absent()
) : InputType {
  override fun marshaller(): InputFieldMarshaller = InputFieldMarshaller.invoke { writer ->
    if (this@TrackData.user.defined) {
      writer.writeString("user", this@TrackData.user.value)
    }
    if (this@TrackData.avgSpeedInKMH.defined) {
      writer.writeDouble("avgSpeedInKMH", this@TrackData.avgSpeedInKMH.value)
    }
    if (this@TrackData.distanceInMeters.defined) {
      writer.writeDouble("distanceInMeters", this@TrackData.distanceInMeters.value)
    }
    if (this@TrackData.points.defined) {
      writer.writeList("points", this@TrackData.points.value?.let { value ->
        InputFieldWriter.ListWriter { listItemWriter ->
          value.forEach { value ->
            listItemWriter.writeList { listItemWriter ->
              value?.forEach { value ->
                listItemWriter.writeDouble(value)
              }
            }
          }
        }
      })
    }
    if (this@TrackData.timeInMillis.defined) {
      writer.writeInt("timeInMillis", this@TrackData.timeInMillis.value)
    }
    if (this@TrackData.timestamp.defined) {
      writer.writeInt("timestamp", this@TrackData.timestamp.value)
    }
  }
}
