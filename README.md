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
* Поиск по названию книги и автору
* Загрузка новых книг из Supabase Storage
* Возможность локального удаления книг

Скринкаст: https://github.com/user-attachments/assets/899b3196-771a-4ae9-8565-b6846dd356d1
<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-29-11-340_ru gureva ebookreader" src="https://github.com/user-attachments/assets/52dd1ca0-0d17-4843-a59a-b768d6cb02ea" />
  <img width="30%" alt="Screenshot_2025-11-23-19-29-20-140_ru gureva ebookreader" src="https://github.com/user-attachments/assets/24317ad9-9c8f-4cb6-9435-3389bdf48b70" />
</p>

### Экран чтения книги
* Настройка размера шрифта
* Настройка темы (светлая и темная)
* Сохранение позиции чтения в Shared Preferences
* Отображение процента прочтения

Скринкаст: https://github.com/user-attachments/assets/2646ce09-12b4-41e7-a828-01c5533f7860
<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-38-26-612_ru gureva ebookreader" src="https://github.com/user-attachments/assets/f423c9af-3a4f-46d5-9c92-788691e2288d" />
  <img width="30%" height="2160" alt="Screenshot_2025-11-23-19-38-36-999_ru gureva ebookreader" src="https://github.com/user-attachments/assets/60bda63e-d881-40ef-b421-9e0e5e6fc894" />
  <img width="30%" height="2160" alt="Screenshot_2025-11-23-19-38-45-854_ru gureva ebookreader" src="https://github.com/user-attachments/assets/ac6a5e2b-956a-4712-8094-3d25585cda33" />
</p>

### Экран профиля
* Просмотр базовой информации об аккаунте в Firebase Authentication
* Редактирование имени пользователя, почты и изображения
* Выход из аккаунта
* Загрузка изображения в Supabase Storage

Скринкаст: https://github.com/user-attachments/assets/53aa37f5-2c2c-45ea-b612-aa480df1e853
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
