package com.geekbrains.model.dto

import com.google.gson.annotations.SerializedName

class TranslationDto(
    @field:SerializedName("text") val text: String?
)