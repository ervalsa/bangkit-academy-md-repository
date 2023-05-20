package com.palsaloid.my.data

import com.palsaloid.my.model.Hero
import com.palsaloid.my.model.HeroesData

class HeroRepository {

    fun getHeroes(): List<Hero> {
        return HeroesData.heroes
    }

    fun searchHeroes(query: String): List<Hero> {
        return HeroesData.heroes.filter {
            it.name.contains(query, ignoreCase = true)
        }
    }
}