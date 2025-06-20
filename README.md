# 🎬 Streaming Service

Zamonaviy video streaming platformasi - Spring Boot va PostgreSQL asosida yaratilgan professional streaming xizmati.

## 📋 Loyiha haqida

Bu loyiha zamonaviy streaming platformalarining barcha asosiy funksiyalarini o'z ichiga olgan to'liq streaming xizmatidir. Netflix, YouTube va boshqa mashhur platformalardek, foydalanuvchilar filmlar, seriallar, multfilmlar va anime-larni tomosha qilish imkoniyatiga ega.

## ✨ Asosiy imkoniyatlar

### 👥 Foydalanuvchi boshqaruvi
- **Ro'yxatdan o'tish va autentifikatsiya**
- **Shaxsiy profil yaratish va boshqarish**
- **Obuna tizimi va premium status**
- **Tomosha qilish tarixi**

### 🎭 Kontent boshqaruvi
- **Filmlar, seriallar, anime va multfilmlar**
- **Janr bo'yicha kategoriyalash**
- **Yosh cheklovlari tizimi**
- **Premium va bepul kontent**
- **Aktyorlar va rejissyorlar ma'lumotlari**

### ⭐ Reyting va baho tizimi
- **Foydalanuvchilar baholari**
- **Yulduzcha reytingi**
- **Izohlar va fikrlar**

### 🔍 Qidiruv va filtrlash
- **Kontent nomini qidirish**
- **Janr bo'yicha filtrlash**
- **Yil va davomiylik bo'yicha saralash**
- **Premium status bo'yicha filtrlash**

## 🛠️ Texnologiyalar

### Backend
- **Java 17** - Zamonaviy Java versiyasi
- **Spring Boot 3.5.0** - Mikroservis arxitekturasi
- **Spring Data JPA** - Ma'lumotlar bazasi bilan ishlash
- **Spring Web** - REST API yaratish
- **Spring Validation** - Ma'lumotlarni tekshirish
- **Spring Actuator** - Monitoring va health check

### Ma'lumotlar bazasi
- **PostgreSQL** - Asosiy ma'lumotlar bazasi
- **Hibernate** - ORM (Object-Relational Mapping)

### Development Tools
- **Lombok** - Boilerplate kodlarni kamaytirish
- **Maven** - Dependency management
- **Spring Boot DevTools** - Development rejimida hot reload

## 🏗️ Loyiha strukturasi

```
src/
├── main/
│   ├── java/uz/dev/streamingservice/
│   │   ├── controller/          # REST API endpointlar
│   │   ├── service/            # Business logic
│   │   ├── repository/         # Ma'lumotlar bazasi access layer
│   │   ├── entity/             # JPA entitylar
│   │   ├── dto/                # Data Transfer Objects
│   │   ├── mapper/             # Entity va DTO mapper
│   │   ├── enums/              # Enum tiplar
│   │   ├── config/             # Spring konfiguratsiya
│   │   ├── exceptions/         # Custom exceptionlar
│   │   ├── handlers/           # Exception handlerlar
│   │   └── utils/              # Utility classlar
│   └── resources/
│       ├── static/             # Frontend assetlar
│       ├── templates/          # Thymeleaf shablonlar
│       ├── application.yaml    # Konfiguratsiya
│       └── banner.txt          # Application banner
└── test/                       # Unit va integration testlar
```

## 🗃️ Ma'lumotlar modeli

### Asosiy entitylar:
- **User** - Foydalanuvchilar
- **Content** - Video kontent (filmlar, seriallar, etc.)
- **Actor** - Aktyorlar va rejissyorlar
- **Genre** - Janrlar
- **Rating** - Foydalanuvchi baholari
- **Subscription** - Obuna ma'lumotlari
- **WatchHistory** - Tomosha qilish tarixi

### Enum tiplar:
- **ContentTypeEnum**: FILM, SERIAL, CARTOON, ANIME
- **AgeLimitEnum**: Yosh cheklovlari
- **PremiumStatus**: Premium va bepul kontent
- **SubscriptionStatusEnum**: Obuna statuslari

## 🚀 O'rnatish va ishga tushirish

### Talablar
- Java 17 yoki undan yangi versiya
- Maven 3.6+
- PostgreSQL 12+
- Git

### 1. Loyihani klonlash
```bash
git clone https://github.com/MuminjonovAsrorbek/Streaming-Service.git
cd streaming-service
```

### 2. Ma'lumotlar bazasini sozlash
PostgreSQL da yangi database yarating:
```sql
CREATE DATABASE streaming_service;
```

### 3. Konfiguratsiya
`src/main/resources/application.yaml` faylida ma'lumotlar bazasi sozlamalarini o'zgartiring:
```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/streaming_service
    username: sizning_username
    password: sizning_password
```

### 4. Loyihani ishga tushirish
```bash
./mvnw spring-boot:run
```

yoki

```bash
mvn spring-boot:run
```

### 5. Brauzerda ochish
```
http://localhost:8080
```

## 📡 API Endpoints

### Foydalanuvchilar
- `GET /api/users` - Barcha foydalanuvchilar
- `POST /api/users` - Yangi foydalanuvchi yaratish
- `GET /api/users/{id}` - Foydalanuvchi ma'lumotlari
- `PUT /api/users/{id}` - Foydalanuvchi ma'lumotlarini yangilash
- `DELETE /api/users/{id}` - Foydalanuvchini o'chirish

### Kontent
- `GET /api/content` - Barcha kontent
- `POST /api/content` - Yangi kontent qo'shish
- `GET /api/content/{id}` - Kontent ma'lumotlari
- `PUT /api/content/{id}` - Kontent yangilash
- `DELETE /api/content/{id}` - Kontent o'chirish
- `GET /api/content/search?q={query}` - Kontent qidirish

### Janrlar
- `GET /api/genres` - Barcha janrlar
- `POST /api/genres` - Yangi janr qo'shish
- `GET /api/genres/{id}` - Janr ma'lumotlari

### Aktyorlar
- `GET /api/actors` - Barcha aktyorlar
- `POST /api/actors` - Yangi aktyor qo'shish
- `GET /api/actors/{id}` - Aktyor ma'lumotlari

## 🔧 Development

### Testlarni ishga tushirish
```bash
./mvnw test
```

### Production uchun build qilish
```bash
./mvnw clean package
```

### Docker bilan ishlatish
```bash
# Docker image yaratish
docker build -t streaming-service .

# Konteynerni ishga tushirish
docker run -p 8080:8080 streaming-service
```

## 📊 Monitoring

Spring Boot Actuator yordamida applicationning sog'lom holatini kuzatish mumkin:
- Health check: `http://localhost:8080/actuator/health`
- Metrics: `http://localhost:8080/actuator/metrics`
- Application info: `http://localhost:8080/actuator/info`

## 🤝 Hissa qo'shish

1. Loyihani fork qiling
2. Yangi branch yarating (`git checkout -b feature/yangi-funksiya`)
3. O'zgarishlaringizni commit qiling (`git commit -am 'Yangi funksiya qo'shildi'`)
4. Branch ga push qiling (`git push origin feature/yangi-funksiya`)
5. Pull Request yarating

## 📄 Litsenziya

Bu loyiha MIT litsenziyasi ostida tarqatiladi. Batafsil ma'lumot uchun [LICENSE](LICENSE) faylini ko'ring.

## 👨‍💻 Muallif

**Asrorbek** - [GitHub](https://github.com/MuminjonovAsrorbek)

## 📞 Aloqa

Savollar yoki takliflar uchun:
- Email: muminjonovasrorbek@gmail.com
- Telegram: @Kompyuterchi_aka
- LinkedIn: [Asrorbek](www.linkedin.com/in/asrorbek-muminjonov)

## 🙏 Minnatdorchilik

Bu loyihada quyidagi ochiq kodli loyihalardan foydalanilgan:
- Spring Boot Framework
- PostgreSQL
- Font Awesome
- va boshqa ajoyib ochiq kodli loyihalar

---

⭐ Agar bu loyiha sizga yoqqan bo'lsa, uni star qiling!
