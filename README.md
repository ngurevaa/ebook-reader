# :notebook: EBook Reader - приложение для скачивания и чтения книг. 

## Технологии:
* Kotlin
* Jetpack Compose
* Clean Architecture + MVI(Orbit)
* Koin
* Firebase Authentication
* Firebase Firestore
* Supabase Storage
* Coil

## Основной функционал:
### Экран загрузки книги
* Поддержка книг в формате .txt, .epub или .pdf
* Загрузка файла книги в Supabase Storage
* Сохранение метаданных в Firebase Firestore
* Локальное кэширование загруженной книги для офлайн-доступа
* Фоновая загрузка через WorkManager с уведомлением о прогрессе

<p>
  <img width="30%" alt="Screenshot_2026-02-15-19-38-54-362_ru gureva ebookreader" src="https://github.com/user-attachments/assets/dcf9029d-9684-4afe-8dfc-38af99a06522" />
  <img width="30%" alt="Screenshot_2026-02-15-19-41-43-276_ru gureva ebookreader" src="https://github.com/user-attachments/assets/017f331c-aacb-47a8-9353-5243205ca445" />
  <img width="30%" alt="Screenshot_2026-02-15-19-58-14-463_ru gureva ebookreader" src="https://github.com/user-attachments/assets/420cc8fb-605f-4afc-ab29-a15cf0355e03" />
</p>

### Экран скачанных книг
* Отображение локальных и облачных книг
* Автоматическая синхронизация с облаком при открытии экрана
* Поиск книги по названию или автору
* Загрузка книг из Supabase Storage
* Возможность локального удаления книг

<p>
  <img width="30%" alt="Screenshot_2026-02-15-23-43-11-309_ru gureva ebookreader" src="https://github.com/user-attachments/assets/368d2f4f-e74b-4177-aac6-8f07b6f32b7b" />
  <img width="30%" alt="Screenshot_2026-02-15-23-45-30-412_ru gureva ebookreader" src="https://github.com/user-attachments/assets/5dad53cb-5501-4ffd-bfbb-dceb1332642c" />
  <img width="30%" alt="Screenshot_2026-02-15-23-45-16-557_ru gureva ebookreader" src="https://github.com/user-attachments/assets/ed8447b2-c754-41dc-9f74-b3eadd70a10d" />
</p>

### Экран чтения книги
* Настройка размера шрифта
* Настройка темы (светлая и темная)
* Сохранение позиции чтения в Shared Preferences
* Отображение процента прочтения

<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-38-26-612_ru gureva ebookreader" src="https://github.com/user-attachments/assets/f423c9af-3a4f-46d5-9c92-788691e2288d" />
  <img width="30%" height="2160" alt="Screenshot_2025-11-23-19-38-36-999_ru gureva ebookreader" src="https://github.com/user-attachments/assets/60bda63e-d881-40ef-b421-9e0e5e6fc894" />
  <img width="30%" height="2160" alt="Screenshot_2025-11-23-19-38-45-854_ru gureva ebookreader" src="https://github.com/user-attachments/assets/ac6a5e2b-956a-4712-8094-3d25585cda33" />
</p>

### Экран Входа/Регистрации
* Вход через Firebase Authentication с email и паролем
* Валидация email/пароля по мере ввода
* Обработка исключений Firebase
* Поддержка подсказок и перехода фокуса между полями 
 
<p>
  <img width="30%" alt="Screenshot_2025-11-23-17-49-13-690_ru gureva ebookreader" src="https://github.com/user-attachments/assets/95249b4e-bd2e-482e-866b-31f9a6d40a66" />
  <img width="30%" alt="Screenshot_2025-11-23-19-03-00-357_ru gureva ebookreader" src="https://github.com/user-attachments/assets/d57099b5-7865-4356-ad09-f3aedfc9fc21" />
  <img width="30%" height="2160" alt="Screenshot_2025-11-23-19-03-53-243_ru gureva ebookreader" src="https://github.com/user-attachments/assets/33ccee6b-a5e9-489f-bb96-f01a6ff12adb" />
</p>

### Экран профиля
* Просмотр базовой информации об аккаунте в Firebase Authentication
* Редактирование имени пользователя, почты и изображения
* Выход из аккаунта
* Загрузка изображения в Supabase Storage

<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-46-11-179_ru gureva ebookreader" src="https://github.com/user-attachments/assets/066895da-86a4-4db6-ab98-5e1821203830" />
  <img width="30%" alt="Screenshot_2025-11-23-19-46-18-920_ru gureva ebookreader" src="https://github.com/user-attachments/assets/69c75ca2-0a79-4a89-b458-7c60f9fc00c2" />
  <img width="30%" alt="Screenshot_2025-11-23-21-34-24-820_ru gureva ebookreader" src="https://github.com/user-attachments/assets/fbdf2191-8cb5-40ce-bc86-2843e03e51af" />
</p>

### Скринкасты
<p>
  <img src="https://github.com/user-attachments/assets/b80b1f9c-c674-4674-8efc-e4af9bad51dc" width="30%" alt="book_upload">
</p>


## Запуск проекта:
1. Создать проект в Firebase Console
2. Добавить Android приложение
3. Скачать google-services.json и разместить в app/
4. Включить Firebase Authentication(Email/Password) и Firestore
5. Создать проект в Supabase
6. В Supabase Storage создать bucket 'books' и настроить к нему публичный доступ для чтения/изменения хранилища
7. Создать файл secrets.properties в core/network/ и добавить ключи SUPABASE_URL и SUPABASE_KEY из настроек проекта
8. Готово!
