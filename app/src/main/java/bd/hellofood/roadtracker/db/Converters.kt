package bd.hellofood.roadtracker.db

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.room.TypeConverter
import com.google.gson.Gson
import java.io.ByteArrayOutputStream

class Converters {

    @TypeConverter
    fun toBitmap(bytes: ByteArray): Bitmap {
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    @TypeConverter
    fun fromBitmap(bmp: Bitmap): ByteArray {
        val outputStream = ByteArrayOutputStream()
        bmp.compress(Bitmap.CompressFormat.PNG, 100, outputStream)
        return outputStream.toByteArray()
    }

    @TypeConverter
    fun listToJson(value: List<Point>?) = Gson().toJson(value)

    /**
     * if null value retun placeholder Point
     */
    @TypeConverter
    fun jsonToList(value: String ?):List<Point>? {
        val a  =  Gson().fromJson(value , Array<Point>::class.java)
        if(a!= null)
            return a.toList()
        return listOf()
    }





}