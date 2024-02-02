package com.example.mynewsapplication

import android.os.Parcel
import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "News_Table")
data class NewsModel(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "headline")
    val headLine: String,

    @ColumnInfo(name = "imgurl")
    val image: String?,

    @ColumnInfo(name = "description")
    val description: String?,

    @ColumnInfo(name = "url")
    val url: String?,

    @ColumnInfo(name = "source")
    val source: String?,

    @ColumnInfo(name = "time")
    val time: String?,

    @ColumnInfo(name = "content")
    val content: String?

) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readString() ?: "",
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(headLine)
        parcel.writeString(image)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(source)
        parcel.writeString(time)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsModel> {
        override fun createFromParcel(parcel: Parcel): NewsModel {
            return NewsModel(parcel)
        }

        override fun newArray(size: Int): Array<NewsModel?> {
            return arrayOfNulls(size)
        }
    }
}