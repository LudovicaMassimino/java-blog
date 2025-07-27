## 🇮🇹 Italiano

# Coding Café - Un Blog per Sviluppatori

## 📖 Descrizione
Coding Café è una piattaforma di blogging pensata per sviluppatori:  
– Registrazione, login/logout  
– Role‑based access (USER vs ADMIN)  
– CRUD sugli articoli e commenti  
– Upload di immagini e file  

## 🚀 Caratteristiche
- **Autenticazione** con BCrypt  
- **Ruoli**: ADMIN vs USER  
- **Gestione articoli**: creazione, modifica, cancellazione  
- **Interfaccia**: Thymeleaf + Bootstrap 5  
- **Database**: MySQL

## 🔧 Tecnologie Usate

- **Backend**: Java, Spring Boot
- **Frontend**: Thymeleaf, Bootstrap 5
- **Database**: MySQL
- **Security**: Spring Security, BCrypt for password encoding
- **Conteinerizzazione**: Docker & Docker Compose

## 👤 Utenti Demo

| Username | Password | Ruolo |
|----------|----------|-------|
| admin    | password | ADMIN |
| user8    | User8!   | USER  |

⚠️ Le credenziali sono pensate solo per i test in locale. Le password sono salvate in modo sicuro tramite crittografia BCrypt.

## 🛠 Prerequisiti 
- Docker & Docker Compose  

## 🐳 Avvio con Docker
- git clone https://github.com/LudovicaMassimino/java-blog.git
- cd java-blog
- docker-compose up
Una volta avviata, l'applicazione sarà disponibile su http://localhost:8080.




════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════════




## 🇬🇧 English

# Coding Café - A Blogging Platform for Programmers

Welcome to the **Coding Café**, a blogging platform where programmers can register, share articles, and interact with others! This project is designed to showcase various concepts such as user authentication, role-based access control, and CRUD operations.

## 🚀 Features

- **User Authentication**: Sign up, login, and logout with password encryption (using bcrypt).
- **Role-Based Access Control**: Different permissions for **Users** and **Admins**.
- **Article Management**: Admins can create, edit, delete articles, while users can view and comment on them.
- **Responsive Design**: Built with **Bootstrap 5** for a mobile-friendly interface.

## 🔧 Technologies Used

- **Backend**: Java, Spring Boot
- **Frontend**: Thymeleaf, Bootstrap 5
- **Database**: MySQL
- **Security**: Spring Security, BCrypt for password encoding
- **Containerization**: Docker & Docker Compose

## 👤 Demo Users

For testing purposes, you can log in with the following demo accounts:

| Username | Password | Role  |
|----------|----------|-------|
| admin    | password | ADMIN |
| user8    | User8!   | USER  |

⚠️ These credentials are for local testing only. Passwords are stored in an encrypted format using **bcrypt**.

To start the entire application using Docker:
Make sure Docker and Docker Compose are installed on your system.
git clone https://github.com/LudovicaMassimino/java-blog.git
cd java-blog
docker-compose up
Once started, the application will be available at http://localhost:8080.

