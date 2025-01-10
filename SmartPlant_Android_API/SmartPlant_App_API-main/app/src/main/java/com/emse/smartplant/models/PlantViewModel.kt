package com.emse.smartplant.models

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.emse.smartplant.services.ApiServices
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class PlantViewModel: ViewModel() {
    var plant by mutableStateOf <PlantDto?>(null)
    val plantState = MutableStateFlow(PlantList())

    fun findAll() {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ApiServices.plantApiService.findAll().execute() }
                .onSuccess { response ->
                    val plant = response.body() ?: emptyList()
                    plantState.value = PlantList(plant)
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    plantState.value = PlantList(emptyList(), throwable.message)
                }
        }
    }

    fun findRoom(id: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ApiServices.plantApiService.findById(id).execute() }
                .onSuccess { response ->
                    plant = response.body()
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    plant = null
                }
        }
    }

    fun updateRoom(id: Long, plantDto: PlantDto) {
        val command = PlantCommand(
            name = plantDto.name,
            plantType = plantDto.plant_type
        )
        viewModelScope.launch(Dispatchers.IO) {
            runCatching { ApiServices.plantApiService.updatePlant(id, command).execute() }
                .onSuccess { response ->
                    plant = response.body()
                }
                .onFailure { throwable ->
                    throwable.printStackTrace()
                    plant = null
                }
        }
    }
}