package com.emse.smartplant.services

import androidx.core.text.isDigitsOnly
import com.emse.smartplant.models.PlantDto
import java.util.UUID



object PlantService {
    val PLANT_KIND: List<String> = listOf("Succulent", "Monstera", "Orchid", "Bonzai", "Cactus")
    var plant_counter: Long = 0






    fun generatePlant(id: Long): PlantDto {





        val plantName = "Plant ${PlantService.plant_counter}"
        PlantService.plant_counter+=1

        return PlantDto(
            id = id,
            name = plantName,
            plant_type = PLANT_KIND.random(),
            current_temperature = (15..30).random().toDouble(),
            current_enlightment = (15..30).random().toDouble(),
            current_humidity = (0..100).random().toDouble(),
            max_humidity = 90.0,
            min_humidity = 30.0

        )
    }

    // Create 50 rooms
    val PLANTS = (1..50).map { generatePlant(it.toLong()) }.toMutableList()

    fun findAll(): List<PlantDto> {
        // return all plants sorted by name
        return PLANTS.sortedBy { it.name}
    }

    fun findById(id: Long): PlantDto? {
        // TODO return the room with the given id or null
        for (plants in PLANTS){
            if (plants.id == id){
                return plants
            }
        }
        return null
    }

    fun findByName(name: String): PlantDto? {
        // TODO return the room with the given name or null
        for (plants in PLANTS){
            if (plants.name == name){
                return plants
            }
        }
        return null
    }

    fun updatePlant(id: Long, plant: PlantDto): PlantDto {
        // TODO update an existing room with the given values
        val index = PLANTS.indexOfFirst { it.id == id }
        val updatedPlant = findById(id)?.copy(
            name = plant.name,
            plant_type = plant.plant_type,
            current_temperature = plant.current_temperature,
            current_enlightment = plant.current_enlightment,
            current_humidity = plant.current_humidity,
            max_humidity = plant.max_humidity,
            min_humidity = plant.min_humidity
        ) ?: throw IllegalArgumentException()
        return PLANTS.set(index, updatedPlant)
    }

    fun findByNameOrId(nameOrId: String?): PlantDto? {
        if (nameOrId != null) {
            return if (nameOrId.isDigitsOnly()) {
                findById(nameOrId.toLong())
            } else {
                findByName(nameOrId)
            }
        }
        return null
    }

}