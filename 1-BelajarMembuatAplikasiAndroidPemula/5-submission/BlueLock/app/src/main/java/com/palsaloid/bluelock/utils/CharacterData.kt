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
        "Barou Shoei",
        "Kunigami Rensuke"
    )

    private val description = arrayOf(
        "Rin Itoshi (糸師 凛 Itoshi Rin) is a contender of Blue Lock, who currently plays as a regular for France's Paris X Gen in the Neo Egoist League.\nRin is the younger brother of Sae Itoshi, who started playing football with him at a young age. He has played as a forward on every team he was on and has always been regarded as one of the top players.",
        "Ryusei Shido (世し道どう 龍りゅう聖せい Shidō Ryūsei) is a contender for the Blue Lock Project, who currently plays for France's Paris X Gen.\nShido, unlike most forwards, has no concrete dream of becoming the world's best striker, but rather seeks to enjoy life to the fullest by playing football. Out of the original 300 forwards chosen to be apart of the Blue Lock Project, Shido is an ideal candidate for the type of striker Ego strives to create.",
        "Meguru Bachira (蜂ばち楽ら廻めぐる Bachira Meguru) is a contender of Blue Lock , who currently plays as a center forward for Spain's FC Barcha in the Neo Egoist League.\nBachira is a whimsical forward who plays following his instincts and when first arriving at Blue Lock he was a member of Team Z during First Selection. His main goal was to find soccer rivals to play with, who also have a \"monster\" inside of them but that later changes to becoming the best striker in the world when he overcomes his childhood trauma.",
        "Hyoma Chigiri (千切 豹馬 Chigiri Hyōma) is a contender of Blue Lock, who currently plays as a leftwing for England's Manshine City during the Neo Egoist League.\nChigiri is a forward prodigy who was chained down by a leg injury in his past but he let go of his fears while at Blue Lock so he could continue playing football wholeheartedly. When first arriving at Blue Lock, Chigiri was member of Team Z.",
        "Seishiro Nagi (凪なぎ 誠せい士し郎ろう Nagi Seishirō) is a contender for the Blue Lock Project and the protagonist of the spin-off, Blue Lock - Episode Nagi, who currently plays as a rightwing for England's Manshine City in the Neo Egoist League.\nWhen first arriving at Blue Lock, Nagi was a member of Team V and was the best player in his designated stratum. After the Blue Lock Eleven played against the Japan U-20 team, Nagi ranked as one of the top players in the project.",
        "Yoichi Isagi (潔いさぎ世よ一いち Isagi Yoichi) is the protagonist of the Blue Lock series.\nIsagi is a second-year high school student, who previously played as a forward for the Ichinan High School football team. When he arrived at the Blue Lock facility, he became a member of Team Z for the First Selection. His main goal is to become the world's best striker.",
        "Tabito Karasu (烏からす旅たび 人と Karasu Tabito) is a contender of Blue Lock , who currently plays for France's Paris X Gen during the Neo Egoist League.\nWhen first introduced he was ranked #3 during the Third Selection and he later played as a defensive midfielder for the match between Blue Lock Eleven and the Japan U-20 team.",
        "Reo Mikage (御影玲王みかげれお Mikage Reo) is a contender for the Blue Lock Project, who currently plays for England's Manshine City.\nReo is the heir of the Mikage Corporation, with his total assets valued at 705.8 billion yen or \$6.8 billion dollars. Prior to entering Blue Lock, he was a student at an elite high school, where he met his first long-term friend, Seishiro Nagi, and he started playing soccer shortly after with him due to Nagi's amazing skills and Reo's craving to go further in football.",
        "Shoei Baro (馬狼照英 Barō Shōei) is a contender of the Blue Lock Project, who currently plays as a regular for Italy's Ubers during the Neo Egoist League.\nBaro is a very selfish and arrogant type of forward whose main goal is to be the star of the field at all times while he strives to become the world's best striker When first arriving at Blue Lock, he was a member of Team X during First Selection and tied for top scorer in his designated stratum.",
        "Rensuke Kunigami (國神 錬介 Kunigami Rensuke) is a contender of Blue Lock, who currently plays as a forward for Germany's Bastard München.\nHe is a hot blooded forward who's main goal is to become the best striker in the world and in turn a football superhero. When first arriving at Blue Lock he was member of Team Z during the First Selection. He was eliminated from Blue Lock's main route during the final games of Second Selection but instead of leaving the facility he walked into a mysterious \"Wild Card\" door that was open to him."
    )

    private val gender = arrayOf(
        "Male",
        "Male",
        "Male",
        "Male",
        "Male",
        "Male",
        "Male",
        "Male",
        "Male",
        "Male"
    )

    private val age = intArrayOf(
        16,
        18,
        17,
        16,
        17,
        17,
        18,
        17,
        18,
        17
    )

    private val birthday = arrayOf(
        "September 9",
        "July 7",
        "August 8",
        "December 23",
        "May 6",
        "April 1",
        "August 15",
        "August 12",
        "June 27",
        "March 11"
    )

    private val bloodType = arrayOf(
        "A",
        "AB",
        "AB",
        "A",
        "O",
        "B",
        "A",
        "B",
        "A",
        "O"
    )

    private val hairColor = arrayOf(
        "Dark green",
        "Blond",
        "Black and Yellow",
        "Reddish-pink",
        "White",
        "Dark Blue",
        "Purple and Dark Blue",
        "Purple",
        "Black",
        "Orange"
    )

    private val eyeColor = arrayOf(
        "Teal",
        "Pink",
        "Yellow",
        "Pink",
        "Gray",
        "Blue",
        "Blue",
        "Purple",
        "Red",
        "Auburn"
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
                character.description = description[position]
                character.gender = gender[position]
                character.age = age[position]
                character.birthday = birthday[position]
                character.bloodType = bloodType[position]
                character.hairColor = hairColor[position]
                character.eyeColor = eyeColor[position]
                character.image = images[position]
                list.add(character)
            }
            return list
        }
}