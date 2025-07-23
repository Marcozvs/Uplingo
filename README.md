<p align="center">
  <a href="https://marcozvs.github.io/UplingoPublicPage/" target="_blank" rel="noopener noreferrer">
    <img src="./images/logo.png" alt="Uplingo Logo" width="200"/>
  </a>
</p>

<p align="center">
  <img src="https://img.shields.io/badge/Maintained-yes-brightgreen">
  <img src="https://img.shields.io/badge/License-CC%20BY--NC%204.0-lightgrey">
  <img src="https://img.shields.io/badge/PRs-not%20allowed-red">
</p>

<p>
<strong>Definition</strong><br>
Uplingo is an engaging, personalized, and effective way to learn languages. Through interactive stories, you practice vocabulary and grammar naturally, with AI adapting themes and challenges to your level, interests, and goals.
</p>

<p>
<strong>Motivation</strong><br>
Uplingo began as a volunteer project in a public library where people gathered to practice foreign languages like English and Spanish. It was created for my Computer Engineering degree at São Judas Tadeu University, driven by the UN goal of Quality Education.
</p>

<p>
<strong>Target Audience</strong><br>
Designed for students, professionals, and self-learners seeking practical, flexible, and motivating language learning. Lessons fit into short daily breaks, keeping study light and continuous.
</p>

<p>
<strong>Problem it Solves</strong><br>
Traditional lessons can be monotonous and disconnected. Uplingo turns learning into immersive stories that make studying engaging, practical, and easy to integrate into your routine.
</p>

<p align="center">
  <a href="https://youtu.be/LUQ8-kdwiQs?si=cn_Y9tPbIfkQMZ_F">
    <img src="https://img.youtube.com/vi/LUQ8-kdwiQs/0.jpg" alt="Watch the video" width="400"/>
  </a>
</p>
<p align="center"><em>Click to watch the full presentation video on YouTube</em></p>

## Technologies Used

### Front-End:

<p align="center">
  <img src="https://img.shields.io/badge/Angular-DD0031?logo=angular&logoColor=white" alt="Angular Badge">
  <img src="https://img.shields.io/badge/NgRx-BA2BD2?logo=ngrx&logoColor=white" alt="NgRx Badge">
  <img src="https://img.shields.io/badge/RxJS-B7178C?logo=reactivex&logoColor=white" alt="RxJS Badge">
  <img src="https://img.shields.io/badge/Jest-C21325?logo=jest&logoColor=white" alt="Jest Badge">
  <img src="https://img.shields.io/badge/Playwright-45ba4b?logo=microsoftedge&logoColor=white" alt="Playwright Badge">
  <img src="https://img.shields.io/badge/TypeScript-3178C6?logo=typescript&logoColor=white" alt="TypeScript Badge">
  <img src="https://img.shields.io/badge/JavaScript-F7DF1E?logo=javascript&logoColor=black" alt="JavaScript Badge">
  <img src="https://img.shields.io/badge/Tailwind_CSS-38B2AC?logo=tailwind-css&logoColor=white" alt="Tailwind CSS Badge">
  <img src="https://img.shields.io/badge/Sass-CC6699?logo=sass&logoColor=white" alt="Sass Badge">
  <img src="https://img.shields.io/badge/HTML-E34F26?logo=html5&logoColor=white" alt="HTML Badge">
</p>

### Back-End:

<p align="center">
  <img src="https://img.shields.io/badge/Spring%20Boot-6DB33F?logo=springboot&logoColor=white">
  <img src="https://img.shields.io/badge/Java-007396?logo=openjdk&logoColor=white">
  <img src="https://img.shields.io/badge/PostgreSQL-4169E1?logo=postgresql&logoColor=white">
  <img src="https://img.shields.io/badge/SQL-003B57?logo=mysql&logoColor=white">
  <img src="https://img.shields.io/badge/Hibernate-59666C?logo=hibernate&logoColor=white">
  <img src="https://img.shields.io/badge/MapStruct-FF6F00">
  <img src="https://img.shields.io/badge/Lombok-BC322C?logo=java">
  <img src="https://img.shields.io/badge/Ollama-000000">
</p>

### Infrastructure:

<p align="center">
  <img src="https://img.shields.io/badge/Docker-2496ED?logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/Docker%20Compose-384d54?logo=docker&logoColor=white">
  <img src="https://img.shields.io/badge/Kubernetes-326CE5?logo=kubernetes&logoColor=white">
</p>

## Machine Requirements

The minimum machine configuration to run the application in containers depends directly on the model used in Ollama. The default selected model is **llama3.1:8b**, which requires more robust resources for stable and efficient operation.

For this model, the recommended minimum would be:

- **CPU:** 1800% (equivalent to about 18 virtual CPUs)
- **Processor:** Intel(R) Core(TM) i9-14900HX (2.20 GHz)
- **RAM:** 16 GB installed (15.7 GB usable)
- **System:** 64-bit operating system, x64-based processor

## Installation Steps

Follow the steps below to get Uplingo up and running locally using Docker Compose.

1. Clone the repository:

```bash
git clone https://github.com/Marcozvs/Uplingo.git
```

```bash
cd Uplingo
```

2. Configure your environment variables:
   Create or edit the `.env.dev` file with the necessary variables.

3. Start the containers with Docker Compose:

```bash
docker compose --env-file .env.dev -f docker-compose.yml -p uplingo-dev up -d
```

## Usage Instructions

The complete step-by-step guide on how to use Uplingo is available in the presentation video on YouTube: [Click here](https://www.youtube.com/watch?v=LUQ8-kdwiQs&t=165s)

## Contribution

This project does not accept contributions as it serves solely as a portfolio piece. Pull requests and external contributions are not allowed.

## Contributors

### About Me

I’m a front-end developer with 3+ years of experience, skilled in Angular, React, and Vue, creating efficient, user-focused interfaces and systems.

- [LinkedIn](https://www.linkedin.com/in/marcoz-silva/)
- [GitHub](https://github.com/Marcozvs)
- [YouTube](https://www.youtube.com/@MarcozDev)
- [Medium](https://medium.com/@marcoz-silva)

## Software architecture

<p>
In this section, we present the software architecture using the C4 model and UML diagrams to clearly illustrate the system's structure, components, and interactions, making it easier to understand and maintain.
</p>

### Context

<p align="center">
  <a href="https://drive.google.com/file/d/1HtnzPQ1rlSPXeVrr2APA7okR7eaecAbh/view?usp=sharing" target="_blank" rel="noopener noreferrer">
    <img src="./images/context.jpeg" alt="Uplingo Context"/>
  </a>
</p>
<p><em>
This context diagram highlights the interaction between the user and the learning system, which delivers personalized educational content.
</em></p>

### Container

<p align="center">
  <a href="https://drive.google.com/file/d/1cJIk6l87DJfN3UDrMwME3oMiS5JvGfNY/view?usp=sharing" target="_blank" rel="noopener noreferrer">
    <img src="./images/container.jpeg" alt="Uplingo Container"/>
  </a>
</p>
<p><em>
This diagram represents Uplingo's container architecture using the C4 model. It shows how the user interacts with the Angular web application, which communicates with two backend services: the Resource Service and the Authorization Service, both built with Spring Boot. The Resource Service integrates with the Cognitive Service powered by Ollama for AI capabilities and connects to a PostgreSQL database for data storage. Database administration is managed through PgAdmin. This structure organizes responsibilities, improves scalability, and ensures secure access to resources.
</em></p>

### Entity Relationship Diagram

<p align="center">
  <a href="https://drive.google.com/file/d/1251UUj4co4V5hMMHCUZte7U8i1i4s7cl/view?usp=sharing" target="_blank" rel="noopener noreferrer">
    <img src="./images/entity-relationship-diagram.png" alt="Entity Relationship Diagram"/>
  </a>
</p>
<p><em>
This diagram provides a detailed explanation of the relationships between entities in the platform, offering a clear view of the database structure. It helps visualize how data is organized and how the tables interact with each other.
</em></p>
