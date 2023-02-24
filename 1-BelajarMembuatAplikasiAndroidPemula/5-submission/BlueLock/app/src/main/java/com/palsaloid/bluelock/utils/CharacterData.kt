package com.palsaloid.bluelock.utils

import com.palsaloid.bluelock.R
import com.palsaloid.bluelock.model.CharacterModel

object CharacterData {

    private val name = arrayOf(
        "Itoshi Rin",
        "Shidou Ryusei",
        "Bachira Meguru",
        "Chigiri Hyoma",
        "Nagi Seishiro",
        "Isagi Yoichi",
        "Karasu Tabito",
        "Mikage Reo",
        "Barou Shouei",
        "Kunigami Rensuke"
    )

    private val images = intArrayOf(
        R.drawable.itoshi_rin,
        R.drawable.shidou_ryusei,
        R.drawable.bachira_meguru,
        R.drawable.chigiri_hyoma,
        R.drawable.nagi_seishiro,
        R.drawable.isagi_yoichi,
        R.drawable.karasu_tabito,
        R.drawable.mikage_reo,
        R.drawable.barou_shouei,
        R.drawable.kunigami_rensuke
    )

    val listData: ArrayList<CharacterModel>
        get() {
            val list = arrayListOf<CharacterModel>()
            for (position in name.indices) {
                val character = CharacterModel()
                character.name = name[position]
                character.image = images[position]
                list.add(character)
            }
            return list
        }
}