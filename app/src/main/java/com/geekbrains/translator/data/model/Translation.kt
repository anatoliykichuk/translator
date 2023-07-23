package com.geekbrains.translator.data.model

import com.google.gson.annotations.SerializedName

class Translation(
    @field:SerializedName("text") val text: String?
)