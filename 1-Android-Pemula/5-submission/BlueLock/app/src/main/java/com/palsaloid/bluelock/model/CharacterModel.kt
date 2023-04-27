package com.palsaloid.bluelock.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class CharacterModel(
    var name: String = "",
    var description: String = "",
    var gender: String = "",
    var age: Int = 0,
    var birthday: String = "",
    var bloodType: String = "",
    var hairColor: String = "",
    var eyeColor: String = "",
    var image: Int = 0
) : Parcelable
