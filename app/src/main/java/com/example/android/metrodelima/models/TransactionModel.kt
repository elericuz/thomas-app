package com.example.android.metrodelima.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

data class TransactionModel(val results: Result) {}

@Parcelize
data class Result(
    val data: List<Transaction>,
    val page: Int,
    val total: Int
) : Parcelable

@Parcelize
data class Transaction(
    val amount: Double,
    val purse: Double,
    val date: String,
    val name_station: String,
    val reference_id: String,
    val operation_type: String,
    val sw_serial_number: String,
    val media_serial_number: String,
    val fare: String
) : Parcelable
