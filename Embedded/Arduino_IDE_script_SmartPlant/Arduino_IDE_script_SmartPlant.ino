#include "DHT.h"               // Pour le capteur DHT
#include <Wire.h>
#include <HTTPClient.h>        // Pour envoyer des requêtes HTTP
#include <WiFi.h>              // Pour la connexion WiFi

// Définir les broches et le type de capteur
#define DHTPIN 22
#define DHTTYPE DHT22
#define TEMT6000_PIN A1

// WiFi credentials
const char* ssid = "YOUR WI-FI NAME";
const char* pass = "YOUR WI-FI PASSWORD";

// Initialisation du capteur DHT
DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
  dht.begin();

  // Connexion au WiFi
  WiFi.begin(ssid, pass);
  while (WiFi.status() != WL_CONNECTED) {
    delay(500);
    Serial.print(".");
  }
  Serial.println("WiFi connecté !");
}

void sendData(String sensorType, String name, double value) {
  if (WiFi.status() == WL_CONNECTED) {
    HTTPClient http;
    String serverUrl = "https://app-3861dd22-bcbc-49fb-a17d-9e71a5501d1b.cleverapps.io/api/sensors";

    // Construire le payload JSON
    char payload[200];
    snprintf(payload, sizeof(payload), 
             "{\"sensorType\": \"%s\", \"name\": \"%s\", \"value\": %.2f}", 
             sensorType.c_str(), name.c_str(), value);

    // Configurer la requête POST
    http.begin(serverUrl);
    http.addHeader("Content-Type", "application/json");

    // Envoyer la requête POST
    int httpResponseCode = http.POST(payload);

    // Afficher la réponse
    if (httpResponseCode > 0) {
      Serial.print("Code réponse HTTP : ");
      Serial.println(httpResponseCode);
    } else {
      Serial.print("Erreur lors de l'envoi : ");
      Serial.println(httpResponseCode);
    }

    // Fermer la connexion
    http.end();
  } else {
    Serial.println("WiFi non connecté !");
  }
}

void loop() {
  delay(2000);

  // Lire les données du capteur DHT
  float humidity = dht.readHumidity();
  float temperature = dht.readTemperature();

  // Lire la luminosité du capteur TEMT6000
  int lightValue = analogRead(TEMT6000_PIN);
  float luminosity = map(lightValue, 0, 1023, 0, 1000);

  // Afficher les données sur le port série
  Serial.print("Humidité : ");
  Serial.print(humidity);
  Serial.println(" %");
  Serial.print("Température : ");
  Serial.print(temperature);
  Serial.println(" °C");
  Serial.print("Luminosité : ");
  Serial.print(luminosity);
  Serial.println(" lux");

  // Envoyer les données au backend
  Serial.println("Envoi des données...");

  sendData("HUMIDITY", "Humidity Bruno", humidity);
  sendData("TEMPERATURE", "Temperature Bruno", temperature);
  sendData("LIGHT", "Light Bruno", luminosity);

  delay(10000);  // Attendre avant la prochaine boucle
}
