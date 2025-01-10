document.addEventListener("DOMContentLoaded", () => {
    // Fonction pour récupérer les plantes via l'API
    const API_URL = 'https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/plants';
    const fetchPlants = async () => {
        try {
            console.log('Envoi de la requête API...'); // Log pour vérifier si la requête est envoyée
            const response = await fetch(API_URL); // URL de l'API backend

            console.log('Réponse reçue:', response); // Log pour vérifier la réponse

            if (!response.ok) {
                throw new Error("Erreur lors de la récupération des plantes.");
            }

            const plants = await response.json(); // Récupération des données JSON

            console.log('Plantes récupérées:', plants); // Log pour vérifier les données des plantes

            renderPlantList(plants);
        } catch (error) {
            console.error("Erreur :", error);
            document.getElementById("plant-list").innerHTML = "<li>Impossible de charger la liste des plantes.</li>";
        }
    };

    // Fonction pour afficher la liste des plantes dans le DOM
    const renderPlantList = (plants) => {
        const plantList = document.getElementById("plant-list");
        plantList.innerHTML = ""; // Vide la liste actuelle

        plants.forEach((plant) => {
            const listItem = document.createElement("li");
            listItem.classList.add("plant-item");

            const link = document.createElement("a");
            // Lien dynamique vers la page détails
            link.href = `plant.html?plant_id=${plant.id}`;

            // Affiche le nom et le type de plante
            link.textContent = `${plant.name} (${plant.plantType})`;

            listItem.appendChild(link);
            plantList.appendChild(listItem);
        });
    };

    // Charger les plantes au chargement de la page
    fetchPlants();
});
