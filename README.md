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

---

## 🛠️ Technologies Used

### Core
- **Java 17+** - Modern Java with records, pattern matching
- **Spring Framework 6.1.6** - Latest Spring release
- **Spring MVC** - Web framework
- **Jakarta EE 10** - New standard (replaces javax.*)

### View Layer (3 Resolvers)
- **JSP** (JavaServer Pages) - Classic server-side rendering
- **Thymeleaf 3.1.2** - Modern template engine
- **XML View Resolver** - View mapping via XML configuration

### Data Access
- **Hibernate 6.4.4** - Object-Relational Mapping (ORM)
- **Jakarta Persistence API (JPA)** - Standard ORM interface
- **MySQL 8.3** - Relational database
- **Jakarta Validation API** - Bean validation

### Server
- **Apache Tomcat 10/11** - Servlet container (Jakarta EE compatible)

### Build & Dependencies
- **Maven 3.9+** - Build automation
- **Jakarta Servlet 6.0** - Servlet API

---

## ⚙️ How It Works: The 3 View Resolvers

When a controller returns a view name like `"welcome"`, Spring uses **resolvers** to find the actual file:

### **Priority Order (set by `setOrder()`)**

```
Request: return "welcome";
         ↓
    ┌────────────────────────────────────────────┐
    │ 1️⃣ XML View Resolver (Order: 1)            │
    │ Looks in: src/main/resources/views.xml    │
    │ Example: "vueXmlTest" → /WEB-INF/jsp/...  │
    └────────────────────────────────────────────┘
         Not found? Continue ↓
    ┌────────────────────────────────────────────┐
    │ 2️⃣ Thymeleaf View Resolver (Order: 2)     │
    │ Looks in: /WEB-INF/templates/*.html       │
    │ Example: "welcome" → welcome.html         │
    └────────────────────────────────────────────┘
         Not found? Continue ↓
    ┌────────────────────────────────────────────┐
    │ 3️⃣ JSP View Resolver (Order: 3)           │
    │ Looks in: /WEB-INF/jsp/*.jsp              │
    │ Example: "welcome" → welcome.jsp          │
    └────────────────────────────────────────────┘
         Not found? ❌ HTTP 404 Error
```

---

## 🌍 Internationalization (i18n) Flow

**How language switching works:**

```
Student clicks: "🇬🇧 English"
         ↓
URL: /hello?lang=en
         ↓
LocaleChangeInterceptor catches ?lang=en
         ↓
SessionLocaleResolver stores locale in session
         ↓
MessageSource loads messages_en.properties
         ↓
All ${key} variables get translated values
         ↓
HTML rendered in English! 🎉
```

### Translation File Format

**Example: `messages_fr.properties`**
```properties
# Login Page
login.title=Se Connecter
login.email=Adresse Email
login.password=Mot de Passe
login.button=Connexion

# Navigation
nav.home=Accueil
nav.events=Événements
nav.logout=Déconnexion

# Events
event.search=Chercher un Événement
event.type=Type d'Événement
```

**Example: `messages_en.properties`**
```properties
# Login Page
login.title=Sign In
login.email=Email Address
login.password=Password
login.button=Sign In

# Navigation
nav.home=Home
nav.events=Events
nav.logout=Logout

# Events
event.search=Search Event
event.type=Event Type
```

---

## 🚀 Getting Started

### Prerequisites
- **Java 17+** installed (`java -version`)
- **Maven 3.9+** installed (`mvn -version`)
- **MySQL 8.0+** server running
- **Tomcat 10/11** configured in your IDE (IntelliJ/Eclipse)

### Installation

#### 1️⃣ Clone the Repository
```bash
git clone https://github.com/KhadraouiAmani/Projet-Spring.git
cd Projet-Spring
```

#### 2️⃣ Create the Database
```bash
mysql -u root -p
```

```sql
CREATE DATABASE student_events_db CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE student_events_db;
```

#### 3️⃣ Configure Database Connection
Edit `src/main/resources/application.properties`:
```properties
db.driver=com.mysql.cj.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/student_events_db?createDatabaseIfNotExist=true&useUnicode=true&characterEncoding=UTF-8
db.user=root
db.password=YOUR_PASSWORD
```

#### 4️⃣ Build the Project
```bash
mvn clean install
```

#### 5️⃣ Deploy to Tomcat
- In IntelliJ: Right-click project → Run → Edit Configurations → Add Tomcat Server
- Or package as WAR: `mvn package` then deploy to Tomcat `/webapps/` folder

#### 6️⃣ Test the Application
- **JSP Example**: http://localhost:8080/hello
- **French Version**: http://localhost:8080/hello?lang=fr
- **English Version**: http://localhost:8080/hello?lang=en
- **Arabic Version**: http://localhost:8080/hello?lang=ar

---

## 📝 Current Status

### ✅ Completed (Foundation)
- [x] Maven configuration with Spring 6 & Jakarta EE dependencies
- [x] `AppInitializer.java` - Spring boot initialization
- [x] `WebConfig.java` - View Resolvers configured (all 3 working)
- [x] `TestController.java` - Test endpoint at `/hello`
- [x] JSP view resolver setup
- [x] Thymeleaf view resolver setup
- [x] XML view resolver setup

### 🔧 **BELKIS'S TASKS** (Next Steps)

#### **STEP A: Complete the View Resolver Documentation** ✓ (Already explained)
- Understand why 3 resolvers exist
- Understand `setOrder()` priority
- See how each resolver finds files

#### **STEP B: Create Translation Files** (YOUR NEXT TASK!)
Tasks:
1. Create `messages_fr.properties` with French translations
2. Create `messages_en.properties` with English translations  
3. Create `messages_ar.properties` with Arabic translations

Key translations needed:
- Page titles
- Button labels
- Form labels
- Navigation menu items

#### **STEP C: Build the Language Switcher** (YOUR FINAL TASK!)
Tasks:
1. Update `welcome.html` (Thymeleaf template)
2. Add navigation bar with language links
3. Add "Welcome" message with Thymeleaf syntax: `th:text="#{welcome.message}"`
4. Create language switcher: `<a href="?lang=fr">🇫🇷 Français</a>`

#### **How the language switcher works:**
```html
<!-- Navigation Bar with Language Links -->
<nav>
    <a href="?lang=fr">🇫🇷 Français</a>
    <a href="?lang=en">🇬🇧 English</a>
    <a href="?lang=ar">🇸🇦 العربية</a>
</nav>

<!-- Content using translations -->
<h1 th:text="#{welcome.title}">Default Title</h1>
<p th:text="#{welcome.message}">Default Message</p>
```

### ⏳ Not Yet Started
- [ ] Ex 5: Form Validation (Hibernate Validator)
- [ ] Ex 6: Database entities & Hibernate mapping
- [ ] Ex 7: ProblemDetail error handling
- [ ] User registration form
- [ ] Login/Session management
- [ ] Event search functionality
- [ ] Database schema creation

---

## 🎓 Learning Path

### Week 1-2: View Layer (Your Current Phase - Belkis)
1. ✅ Understand 3 View Resolvers
2. 🔧 Create translation files (Step B)
3. 🔧 Build language switcher (Step C)

### Week 3-4: Data & Validation
4. Create User & Event entities
5. Add form validation with annotations
6. Create registration form

### Week 5-6: Database & Authentication
7. Setup Hibernate ORM
8. Implement user authentication
9. Add session management

### Week 7: Error Handling & Deployment
10. Implement ProblemDetail error handling
11. Deploy to production

---

## 📚 Reference Documentation

### Spring Framework
- [Spring Framework 6 Documentation](https://docs.spring.io/spring-framework/docs/current/reference/html/)
- [Spring MVC Official Guide](https://docs.spring.io/spring-framework/docs/current/reference/html/web.html)

### Jakarta EE
- [Jakarta EE 10 Specification](https://jakarta.ee/)
- [Jakarta Servlet API](https://jakarta.ee/specifications/servlet/)

### View Technologies
- [Thymeleaf Documentation](https://www.thymeleaf.org/documentation.html)
- [Thymeleaf + Spring Integration](https://www.thymeleaf.org/doc/tutorials/3.1/thymeleafspring.html)

### Internationalization
- [Spring i18n Guide](https://spring.io/blog/2015/02/11/better-application-events-in-spring-4-2)
- [Java Locale & ResourceBundle](https://docs.oracle.com/en/java/javase/17/docs/api/java.base/java/util/Locale.html)

### Hibernate
- [Hibernate ORM 6.4 Documentation](https://hibernate.org/orm/documentation/6.4/)
- [JPA Specification](https://jakarta.ee/specifications/persistence/)

---

## 💡 Tips & Best Practices

### For Belkis (View & i18n specialist):
1. **Always use Thymeleaf namespaces**: `xmlns:th="http://www.thymeleaf.org"`
2. **Test each language**: After adding translations, test with `?lang=fr`, `?lang=en`, `?lang=ar`
3. **Keep translation keys consistent**: Use dot notation like `welcome.title`, `form.email`, etc.
4. **Use HTML entities for special characters**: `&copy;` for ©, `&reg;` for ®
5. **Store user language preference in session**: Don't use cookies for i18n (session is more secure)

### General Tips:
- Start simple: Get JSP working first, then Thymeleaf, then XML
- Test each layer independently
- Use browser DevTools (F12) to debug
- Check Tomcat logs for errors: `catalina.out`
- Use `System.out.println()` for quick debugging

---

## ❓ FAQ

### Q: What's the difference between JSP, Thymeleaf, and XML views?
**A:** 
- **JSP** - Classic, server-side rendering with scriptlets. Tightly coupled to Java.
- **Thymeleaf** - Modern template language, cleaner syntax, works standalone.
- **XML** - View names mapped to actual files via configuration. Useful when view logic needs external management.

### Q: Why do we need 3 view resolvers?
**A:** This project demonstrates Spring's flexibility. In production, you'd typically use ONE. We use all 3 to show:
1. Classic Java web development (JSP)
2. Modern best practices (Thymeleaf)
3. Advanced configuration patterns (XML)

### Q: How does Spring know which language to use?
**A:** 
1. User clicks a language link: `?lang=en`
2. `LocaleChangeInterceptor` intercepts the request
3. Stores locale in `HttpSession`
4. `SessionLocaleResolver` retrieves it from session on every request
5. `MessageSource` loads the correct `.properties` file

### Q: Can I use the same view with different languages?
**A:** Yes! That's the power of i18n. One Thymeleaf template uses `#{key}` syntax, and Spring swaps the values based on the user's locale.

---

## 🐛 Troubleshooting

### "View not found" Error
- Check spelling of view name (case-sensitive)
- Verify file path matches resolver configuration
- Use `/hello` not `/hello/` (trailing slash matters)

### Translation key shows as "??key??" 
- Message file has wrong name (must be `messages_xx.properties`)
- Key doesn't exist in the properties file
- Locale parameter is wrong: use `?lang=fr` not `?lang=FR`

### Tomcat won't start
- Check Java version: `java -version` (must be 17+)
- Check port 8080 is available: `netstat -an | grep 8080`
- Check Tomcat configuration in IDE
- Review Tomcat logs: `catalina.out`

### Database connection fails
- MySQL server is not running: `mysql -u root -p`
- Wrong password in `application.properties`
- Database doesn't exist: Create with SQL script above

---

## 📞 Support & Contribution

For questions about this project:
1. Check the FAQ above
2. Review Spring documentation
3. Ask your teacher or TP assistant
4. Create an Issue in this GitHub repository

---

## 📄 License

This project is for educational purposes as part of a university TP assignment.

---

## 👥 Contributors

- **Architecture & Backend Setup**: Foundation Team
- **View Resolvers & i18n (Belkis's Role)**: To be completed by Belkis
- **Database & Validation**: Team Member 2
- **Authentication & Error Handling**: Team Member 3

---

**Last Updated**: May 6, 2026  
**Spring Version**: 6.1.6  
**Java Version**: 17+  
**Status**: 🔄 In Progress (Step B Next)

