package com.palsaloid.bluelock.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterModel(
    var name: String = "",
    var image: Int = 0
) : Parcelable
