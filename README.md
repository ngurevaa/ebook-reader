# :notebook: EBook Reader - приложение для скачивания и чтения книг. 
Реализовано как тестовое задание для стажировки Android (осенняя волна 2025). 

## Технологии:
* Kotlin
* Jetpack Compose
* Clean Architecture + MVI(Orbit)
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
* Загрузка книг в Supabase Storage, так как Firebase Storage требует Billing Account
* Локальное сохранение файла после загрузки в Suapbase Storage
* Сохранение метаданных в Firebase Firestore

Скринкаст: https://github.com/user-attachments/assets/a8394b0a-f706-4fed-a885-5f6f1bcf5892

<p>
  <img width="30%" alt="Screenshot_2025-11-23-19-20-15-739_ru gureva ebookreader" src="https://github.com/user-attachments/assets/666f9c24-d79e-4b72-9f11-ce19d66094fc" />
  <img width="30%" alt="Screenshot_2025-11-23-19-20-52-206_ru gureva ebookreader" src="https://github.com/user-attachments/assets/7a60f2f9-fd06-42cb-83c3-8a756e5b6146" />
  <img width="30%" alt="Screenshot_2025-11-23-19-21-58-916_ru gureva ebookreader" src="https://github.com/user-attachments/assets/aac3de78-61e6-4aca-a17b-a0ececfdc4ba" />
</p>
