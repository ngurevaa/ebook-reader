# :notebook: EBook Reader - приложение для скачивания и чтения книг. 
Реализовано как тестовое задание для стажировки Android (осенняя волна 2025). 

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
* Обработка отсутсвия сети
* Поддержка подсказок и перехода фокуса между полями 
* Авто-вход

Скринкаст https://github.com/user-attachments/assets/80d1fd43-1198-4703-8d66-2b0d9a53522b 
<p>
  <img width="30%" alt="Screenshot_2025-11-23-17-49-13-690_ru gureva ebookreader" src="https://github.com/user-attachments/assets/95249b4e-bd2e-482e-866b-31f9a6d40a66" />
  <img width="30%" alt="Screenshot_2025-11-23-17-49-07-865_ru gureva ebookreader" src="https://github.com/user-attachments/assets/335f9828-6f88-4dd4-a156-be182abb4b99" />
</p>
<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-03-00-357_ru gureva ebookreader" src="https://github.com/user-attachments/assets/d57099b5-7865-4356-ad09-f3aedfc9fc21" />
  <img width="30%" height="2160" alt="Screenshot_2025-11-23-19-03-53-243_ru gureva ebookreader" src="https://github.com/user-attachments/assets/33ccee6b-a5e9-489f-bb96-f01a6ff12adb" />
</p>

### Экран загрузки книги
* Поддержка книг в формате .txt, .epub или .pdf
* Загрузка книг в Supabase Storage (бесплатная альтернатива Firebase Storage)
* Локальное сохранение файла после загрузки
* Сохранение метаданных в Firebase Firestore

Скринкаст: https://github.com/user-attachments/assets/a8394b0a-f706-4fed-a885-5f6f1bcf5892

<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-20-15-739_ru gureva ebookreader" src="https://github.com/user-attachments/assets/666f9c24-d79e-4b72-9f11-ce19d66094fc" />
  <img width="30%" alt="Screenshot_2025-11-23-19-20-52-206_ru gureva ebookreader" src="https://github.com/user-attachments/assets/7a60f2f9-fd06-42cb-83c3-8a756e5b6146" />
  <img width="30%" alt="Screenshot_2025-11-23-19-21-58-916_ru gureva ebookreader" src="https://github.com/user-attachments/assets/aac3de78-61e6-4aca-a17b-a0ececfdc4ba" />
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
* Редактирование имени пользователя и почты
* Выход из аккаунта

Скринкаст: https://github.com/user-attachments/assets/53aa37f5-2c2c-45ea-b612-aa480df1e853
<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-46-11-179_ru gureva ebookreader" src="https://github.com/user-attachments/assets/066895da-86a4-4db6-ab98-5e1821203830" />
  <img width="30%" alt="Screenshot_2025-11-23-19-46-18-920_ru gureva ebookreader" src="https://github.com/user-attachments/assets/69c75ca2-0a79-4a89-b458-7c60f9fc00c2" />
</p>

### Гугл диск со всеми скринкастами и скриншотами
https://drive.google.com/drive/folders/1WbGeX7WwV3KG0LoORU80L9vNwvBZOHPk?usp=sharing

## Запуск проекта:
1. Создать проект в Firebase Console
2. Добавить Android приложение
3. Скачать google-services.json и разместить в app/
4. Включить Firebase Authentication(Email/Password) и Firestore
5. Создать проект в Supabase
6. В Supabase Storage создать bucket 'books' и настроить к нему публичный доступ для чтения/изменения хранилища
7. Создать файл secrets.properties в core/network/ и добавить ключи SUPABASE_URL и SUPABASE_KEY из настроек проекта
8. Готово!
