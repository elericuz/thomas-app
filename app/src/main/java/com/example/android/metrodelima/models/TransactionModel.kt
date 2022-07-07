package com.example.android.metrodelima.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

data class TransactionModel(val results: Result) {}

@Parcelize
data class Result(
    val data: List<Transaction>,
    val page: Int,
    val total: Int
) : Parcelable

@Parcelize
data class Transaction(
    val _id: String,
    val date: String,
    val name_station: String,
    val terminal: String,
    val operation_type: String,
    val external_number: String,
    val internal_number: Long,
    val card_transaction_number: Int,
    val former_purse: Double,
    val amount: Double,
    val purse: Double,
    val document_id: String,
    val fare: String
) : Parcelable
