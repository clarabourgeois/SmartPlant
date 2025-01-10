document.addEventListener("DOMContentLoaded", async () => {
    const GET_TYPES_URL = 'https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/types';
    const ADD_PLANT_URL = 'https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/plants';

    try {
        // 🔗 Récupérer les types de plantes depuis l'API
        const response = await fetch(GET_TYPES_URL);
        if (!response.ok) {
            throw new Error("Erreur lors de la récupération des types de plantes.");
        }

        const types = await response.json();
        console.log(types); // Vérifier la réponse de l'API

        const plantTypeSelect = document.getElementById('plant-type');

        // Vider le menu déroulant et ajouter une option par défaut
        plantTypeSelect.innerHTML = '';
        const defaultOption = document.createElement('option');
        defaultOption.value = '';
        defaultOption.disabled = true;
        defaultOption.selected = true;
        defaultOption.textContent = 'Select plant type';
        plantTypeSelect.appendChild(defaultOption);

        // ✅ Ajouter les types de plantes récupérés depuis l'API
        types.forEach(type => {
            const option = document.createElement('option');
            option.value = type.name;
            option.textContent = type.name;
            plantTypeSelect.appendChild(option);
        });
    } catch (error) {
        console.error("Erreur lors de la récupération des types de plantes :", error);
    }

    // Intercepter la soumission du formulaire
    const form = document.querySelector(".add-plant-form");
    form.addEventListener("submit", async (event) => {
        event.preventDefault(); // Empêcher le rechargement de la page

        const name = document.getElementById("plant-name").value;
        const plantType = document.getElementById("plant-type").value;

        try {
            const response = await fetch(ADD_PLANT_URL, {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({ name, plantType }), // Envoyer les données sous forme de JSON
            });

            if (!response.ok) {
                throw new Error("Erreur lors de l'ajout de la plante.");
            }

            const result = await response.json();
            console.log("Plante ajoutée avec succès :", result);

            // Afficher un message de succès ou rediriger
            alert("Plante ajoutée avec succès !");
            form.reset(); // Réinitialiser le formulaire
        } catch (error) {
            console.error("Erreur lors de l'ajout de la plante :", error);
            alert("Impossible d'ajouter la plante. Veuillez réessayer.");
        }
    });
});
