# SmartPlant - README.md

## Introduction

SmartPlant is a plant management application that allows you to monitor and care for your indoor plants. The application uses sensors to measure temperature, humidity and luminosity, sending this data to a backend system, which stores plant information and sensor readings in a database.

### Key Features:
- **Frontend**: User interface to manage plants.
- **Backend**: Server that receives sensor data and stores plant information.
- **Embedded**: Sensors that track environmental data and send it to the backend via Wi-Fi.

## Requirements

### Software Requirements:
1. **Python 3**: Ensure Python 3 is installed on your system.
2. **SASS**: Used to compile SCSS files into CSS.
    - Install SASS using the following command:
      npm install -g sass
3. **A Web Browser**: To access the web interface.

### Hardware Requirements:
- **Temperature and Humidity Sensors**: These sensors need to be connected to Wi-Fi to send data to the backend.
- **Local Server**: A device such as a Raspberry Pi or your computer to run the backend server.

## Installation

### Step 1: Clone the repository

First, clone the repository to your local machine:

git clone https://github.com/username/smartplant.git
cd smartplant

### Step 2: Set up the environment

Make sure Python 3 and SASS are installed on your system as outlined in the requirements section.

### Step 3: Run the backend server

The backend is a Python HTTP server that listens for incoming data from the sensors and stores it.

To start the server, run the following command:

./run_server.py

This will start the server on `http://localhost:8000`. You can change the port by specifying it when running the command:

./run_server.py 8080

### Step 4: Compile SCSS to CSS

To compile the SCSS files into CSS, use the provided script:

./compile_css.sh

This will continuously monitor changes to SCSS files and compile them into CSS automatically.

### Step 5: Access the Web Application

Once the server is running and the styles are compiled, you can access the application at:

http://localhost:8000/index.html

The application consists of several pages:
- **Home Page**: `index.html`
- **About Page**: `about.html`
- **Add Plant Page**: `addplant.html`
- **Plant List Page**: `listplants.html`

## FrontEnd1

The FrontEnd encompasses all aspects of the website's interface, beginning with the homepage, which introduces the application. From the homepage and all other pages, there is a main header showing the philosophy and style of the site, as well as a secondary navigation bar containing different buttons to access the various pages. Among them are "Our Roots," which describes the company's origins, "Your Plant List" to view the full list of plants, "Add Your Plants" to add a plant to the application, and "Contact Us" for support in case of questions or problems.

Beyond these introductory sections, the FrontEnd includes pages that support the application's core functionalities. The "Add Plant" page enables users to register a new plant by providing details such as its name and type. The list of plant types is dynamically retrieved from the backend via an API call, ensuring accuracy and currency. After completing the form, users can submit their data, which is validated and sent to the backend through a POST request. Appropriate feedback is displayed based on the success or failure of the operation.

The "Your Plant List" page offers a comprehensive overview of all registered plants, presented in a dynamic and interactive format. This information is fetched via a GET request to the backend's plant API endpoint. Users can view key details, such as the plants' names and types, and perform actions like viewing additional information or deleting a plant. The list updates automatically to reflect the latest data from the database.

The "Plant Details" page provides in-depth information about individual plants, supplemented with real-time data from sensors. This page retrieves the plant's basic information through a GET request to the plant API and gathers environmental data such as humidity, temperature, and luminosity from another sensor-specific API endpoint. The data is displayed in an intuitive format, allowing users to monitor and assess their plant's conditions effectively.

## Embedded

## Android

## How to Use

### Add a Plant

1. Go to the **Add Plant** page (`addplant.html`).
2. Fill in the plant name and select its type from the dropdown menu.
3. Submit the form, and the plant will be added to the backend.

### View and Manage Plants

- On the **Plant List** page (`listplants.html`), you can view all the plants you have added.
- You can click on a plant to view more details and delete it from the backend if needed.

### Sensor Data

The backend receives data from the sensors and stores it for each plant. Each plant has its own temperature and humidity readings, which can be displayed or used for alerts.

## File Structure

smartplant/
├── assets/
│   ├── img/              # Images for the website
│   └── stylesheets/      # Compiled CSS
├── integration/          # SCSS and CSS source files
│   ├── style.scss        # SCSS source
│   └── output.css        # Compiled CSS
├── assets/js/            # JavaScript for frontend functionality
│   ├── list-plant.js     # Script for plant list interactions
│   └── plantdisplay.js   # Script for displaying plant details
├── run_server.py         # Python server to handle backend requests
├── compile_css.sh        # Script to compile SCSS into CSS
└── README.md             # Project documentation

## Conclusion

SmartPlant is an easy-to-use application for managing indoor plants using real-time data from sensors. Whether you're a plant enthusiast or just looking to keep track of your plants, this application will help you maintain the optimal conditions for your plants.

For any questions or contributions, feel free to open an issue or submit a pull request.

Made by SmartPlant Team.
