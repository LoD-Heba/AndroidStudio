package com.sistemas.androidgrupog.Models

data class Product(
    val id: Int,
    val name: String,
    val price: Double,
    val createdAt: String,
    val updatedAt: String,
    val categoryId: Int,
    val urlImage: String
)