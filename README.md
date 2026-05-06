# 🎓 Student Event Portal - Multilingual Spring 6 Web Application

> A comprehensive Spring 6 / Jakarta EE web application demonstrating modern Java web development practices with multi-language support, session management, and event discovery.

## 📋 Project Overview

This university TP (Travaux Pratiques) project creates a **Student Event Portal** where students can:
- ✅ **Register & Login** - Create accounts with secure authentication and session management
- ✅ **Search Events** - Discover conferences, parties, and workshops
- ✅ **Switch Languages** - Instant language switching (French, English, Arabic)

### 🎯 Learning Objectives (7 Technical Demonstrations)

This project demonstrates 7 key Spring 6 + Jakarta EE concepts:

| Example | Feature | Technology | Status |
|---------|---------|-----------|--------|
| **Ex 1** | Controller + Internal View Resolver (JSP) | JSP + InternalResourceViewResolver | ✅ |
| **Ex 2** | Controller + Thymeleaf View Resolver | Thymeleaf + Spring 6 | ✅ |
| **Ex 3** | Controller + XML View Resolver | XmlViewResolver + views.xml | ✅ |
| **Ex 4** | Multilingual Support (i18n) | MessageSource + LocaleResolver | 🔧 **Belkis's Task** |
| **Ex 5** | Form Validation | @Email, @NotBlank, @Pattern | ⏳ Coming |
| **Ex 6** | Hibernate ORM + MySQL | JPA Entities, Repositories | ⏳ Coming |
| **Ex 7** | Spring 6 ProblemDetail | Error Handling | ⏳ Coming |

---

## 🏗️ Architecture (Restaurant Model)

The application follows a **layered architecture**:

```
┌─────────────────────────────────────────┐
│         VUE (JSP / Thymeleaf)           │
│    What the student sees (Menu)         │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│        CONTROLLER                       │
│   Takes the order (HTTP Request)        │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│        SERVICE (Métier)                 │
│  Processes logic (validation, search)   │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│      REPOSITORY / DAO                   │
│    Queries the database                 │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│      DATABASE (MySQL)                   │
│    Stores users & events                │
└─────────────────────────────────────────┘
```

---

## 📁 Project Structure

```
Projet-Spring/
│
├── pom.xml                                 # Maven dependencies (Spring 6, Jakarta EE, Hibernate)
├── README.md                               # This file
│
├── src/main/java/com/tp3/portailetudiants/
│   │
│   ├── config/                             # Configuration classes
│   │   ├── AppInitializer.java            # Entry point (replaces web.xml)
│   │   └── WebConfig.java                 # Spring MVC + i18n + View Resolvers config
│   │
│   ├── controller/                         # HTTP Request handlers
│   │   ├── TestController.java            # Test endpoint
│   │   └── EventController.java           # (To be created)
│   │
│   ├── model/                              # JPA Entities
│   │   ├── User.java                      # (To be created)
│   │   └── Event.java                     # (To be created)
│   │
│   ├── repository/                         # Database queries (Spring Data)
│   │   ├── UserRepository.java            # (To be created)
│   │   └── EventRepository.java           # (To be created)
│   │
│   └── service/                            # Business logic
│       ├── UserService.java               # (To be created)
│       └── EventService.java              # (To be created)
│
├── src/main/resources/
│   │
│   ├── application.properties              # Database & Hibernate config
│   ├── views.xml                           # XML View Resolver mappings (Example 3)
│   │
│   └── messages/                           # Translation files (i18n) ← **Belkis's Main Task**
│       ├── messages_fr.properties          # 🇫🇷 French translations
│       ├── messages_en.properties          # 🇬🇧 English translations
│       └── messages_ar.properties          # 🇸🇦 Arabic translations
│
└── src/main/webapp/WEB-INF/
    │
    ├── jsp/                                # JSP templates (Example 1)
    │   ├── welcome.jsp                    # Test JSP page
    │   └── test-xml.jsp                   # XML resolver test
    │
    ├── templates/                          # Thymeleaf templates (Example 2)
    │   ├── welcome.html                   # Test Thymeleaf page
    │   └── index.html                     # (To be created)
    │
    └── web.xml                             # Minimal web descriptor (Spring 6 uses code config)
```

