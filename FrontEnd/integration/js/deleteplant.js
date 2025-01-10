document.addEventListener("DOMContentLoaded", async () => {
    const plantId = getPlantIdFromUrl(); // Récupère l'ID de la plante depuis l'URL
    const API_URL = `https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/plants/${plantId}`;
    const DELETE_PLANT_URL = `https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/plants`;

    // Vérifie si un ID est présent dans l'URL
    if (!plantId) {
        document.getElementById("plant-name").textContent = "Plant not found!";
        return;
    }

    try {
        // Envoi de la requête à l'API pour récupérer les détails de la plante avec l'ID
        const response = await fetch(API_URL);
        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des détails de la plante.");
        }

        // Récupération des données de la plante
        const plant = await response.json();

        // Mise à jour des champs dynamiques dans le DOM
        document.getElementById("plant-name").textContent = plant.name || "No name available";
        document.getElementById("plant-type").textContent = plant.plantType || "Unknown type";
        document.getElementById("plant-temperature").textContent = plant.current_temperature !== null ? `${plant.current_temperature}°C` : "N/A";
        document.getElementById("plant-humidity").textContent = plant.current_humidity !== null ? `${plant.current_humidity}%` : "N/A";
        document.getElementById("plant-luminosity").textContent = plant.current_Light !== null ? `${plant.current_Light} lux` : "N/A";

        // Gérer la suppression de la plante
        const deleteButton = document.getElementById("delete-plant-btn");
        deleteButton.addEventListener("click", async () => {
            const confirmDelete = confirm(`Are you sure you want to delete the plant "${plant.name}"?`);
            if (!confirmDelete) return;

            try {
                const deleteResponse = await fetch(`${DELETE_PLANT_URL}/${plantId}`, {
                    method: "DELETE",
                });

                if (!deleteResponse.ok) {
                    throw new Error("Erreur lors de la suppression de la plante.");
                }

                alert("Plante supprimée avec succès !");
                window.location.href = "listplants.html"; // Redirection après suppression
            } catch (error) {
                console.error("Erreur lors de la suppression de la plante :", error);
                alert("Impossible de supprimer la plante. Veuillez réessayer.");
            }
        });

    } catch (error) {
        console.error("Erreur :", error);
        document.getElementById("plant-name").textContent = "Error loading plant details.";
    }
});
