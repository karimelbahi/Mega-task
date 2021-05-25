package com.karim.task.data.entity

import android.os.Parcelable
import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "Jobs")
@Parcelize
data class JobsItem(
    @SerializedName("company")
    @Expose
    val company: String,
    @SerializedName("company_logo")
    @Expose
    @NonNull
    val company_logo: String? = "",
    @SerializedName("company_url")
    @Expose
    val company_url: String? = "",
    @SerializedName("created_at")
    @Expose
    val created_at: String,
    @SerializedName("description")
    @Expose
    val description: String,
    @SerializedName("how_to_apply")
    @Expose
    val how_to_apply: String,
    @PrimaryKey
    @SerializedName("id")
    @NonNull
    @Expose
    val id: String,
    @SerializedName("location")
    @Expose
    val location: String,
    @SerializedName("title")
    @Expose
    val title: String,
    @SerializedName("type")
    @Expose
    val type: String,
    @SerializedName("url")
    @Expose
    val url: String,

    var favorite: Boolean = false
) : Parcelable