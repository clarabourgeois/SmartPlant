// Fonction pour récupérer les paramètres de l'URL
const getPlantIdFromUrl = () => {
    const params = new URLSearchParams(window.location.search);
    return params.get('plant_id'); // Retourne l'ID de la plante
};

// Fonction pour récupérer les données du type de plante à partir de l'API
const fetchPlantTypeDetails = async (plantTypeId) => {
    const API_URL = `https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/types/${plantTypeId}`;
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des détails du type de plante.");
        }
        return await response.json();
    } catch (error) {
        console.error("Erreur :", error);
        return null;
    }
};

// Fonction pour récupérer les données des capteurs
const fetchSensorData = async () => {
    const API_URL = `https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/sensors`;
    try {
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des données des capteurs.");
        }
        const data = await response.json();
        return data;
    } catch (error) {
        console.error("Erreur lors de la récupération des données des capteurs :", error);
        return [];
    }
};

// Fonction principale à exécuter au chargement de la page
document.addEventListener("DOMContentLoaded", async () => {
    const plantId = parseInt(getPlantIdFromUrl());
    const PLANT_API_URL = `https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/plants/${plantId}`;

    // Vérifie si un ID est présent dans l'URL
    if (!plantId) {
        document.getElementById("plant-name").textContent = "Plant not found!";
        return;
    }

    try {
        // Récupération des données de la plante
        const plantResponse = await fetch(PLANT_API_URL);
        if (!plantResponse.ok) {
            throw new Error("Erreur lors de la récupération des détails de la plante.");
        }
        const plant = await plantResponse.json();

        // Mise à jour des champs dynamiques dans le DOM
        document.getElementById("plant-name").textContent = plant.name || "No name available";
        document.getElementById("plant-type").textContent = plant.plantType || "Unknown type";

        // Récupérer les données des capteurs
        const sensors = await fetchSensorData();

        // Trouver les capteurs associés à la plante
        const humiditySensor = sensors.find(sensor => sensor.sensorType === "HUMIDITY" && sensor.plantId === plantId);
        const temperatureSensor = sensors.find(sensor => sensor.sensorType === "TEMPERATURE" && sensor.plantId === plantId);
        const lightSensor = sensors.find(sensor => sensor.sensorType === "LIGHT" && sensor.plantId === plantId);

        // Afficher les valeurs des capteurs
        document.getElementById("plant-humidity").textContent = humiditySensor ? `${humiditySensor.value} %` : "N/A";
        document.getElementById("plant-temperature").textContent = temperatureSensor ? `${temperatureSensor.value} °C` : "N/A";
        document.getElementById("plant-luminosity").textContent = lightSensor ? `${lightSensor.value} lux` : "N/A";

        // Récupérer les détails du type de plante pour afficher les messages de température et d'humidité
        const plantTypeDetails = await fetchPlantTypeDetails(plant.plantType);

        if (plantTypeDetails) {
            // Logique de gestion de la température
            const temperatureMessage = document.getElementById("temperature-message");
            if (temperatureSensor && temperatureSensor.value < plantTypeDetails.min_temperature) {
                temperatureMessage.textContent = "It's too cold! Move the plant to a warmer room.";
                temperatureMessage.style.color = "blue";
            } else if (temperatureSensor && temperatureSensor.value > plantTypeDetails.max_temperature) {
                temperatureMessage.textContent = "It's too hot! Move the plant to a cooler room.";
                temperatureMessage.style.color = "red";
            } else {
                temperatureMessage.textContent = "Perfect temperature for your plant!";
                temperatureMessage.style.color = "green";
            }

            // Logique de gestion de l'humidité
            const humidityMessage = document.getElementById("humidity-message");
            if (humiditySensor && humiditySensor.value < plantTypeDetails.min_humidity) {
                humidityMessage.textContent = "The humidity is too low! Water your plant.";
                humidityMessage.style.color = "blue";
            } else if (humiditySensor && humiditySensor.value > plantTypeDetails.max_humidity) {
                humidityMessage.textContent = "The humidity is too high! Do not water your plant for now.";
                humidityMessage.style.color = "red";
            } else {
                humidityMessage.textContent = "Perfect humidity for your plant!";
                humidityMessage.style.color = "green";
            }
        }
    } catch (error) {
        console.error("Erreur :", error);
        document.getElementById("plant-name").textContent = "Error loading plant details.";
    }
});
