# Guía de Despliegue en Render 🚀

Esta guía te ayudará a subir tu aplicación a **Render.com** de forma gratuita. Usaremos Docker para que sea sencillo.

## Paso 1: Preparar tu repositorio en GitHub
1. Asegúrate de que todo tu código esté subido a un repositorio en **GitHub**.
2. Verifica que has guardado los cambios recientes que hice en `WebConfig.java` y `environment.prod.ts`.

## Paso 2: Crear el Backend en Render
1. Ve a [dashboard.render.com](https://dashboard.render.com/) e inicia sesión con GitHub.
2. Haz clic en **New +** > **Web Service**.
3. Selecciona tu repositorio de GitHub.
4. Configura el Web Service del Backend:
   - **Name**: `weather-api-backend` (o el que quieras).
   - **Environment**: `Docker`.
   - **Docker Context**: `backend` (importante).
   - **Dockerfile Path**: `Dockerfile`.
   - **Plan**: `Free`.
5. Haz clic en **Create Web Service**.
6. **Copia la URL** que te asigne Render (ej. `https://weather-api-backend.onrender.com`).

## Paso 3: Actualizar el Frontend Localmente
1. Abre el archivo `frontend/src/environments/environment.prod.ts` en tu editor.
2. Sustituye `https://YOUR-BACKEND-URL.onrender.com/clima` por la URL de tu backend recién creado, manteniendo el `/clima` al final.
   - Ejemplo: `apiUrl: 'https://weather-api-backend.onrender.com/clima'`
3. Guarda, haz **commit** y **push** a GitHub.

## Paso 4: Crear el Frontend en Render
1. En el Dashboard de Render, haz clic en **New +** > **Web Service**.
2. Selecciona el mismo repositorio de GitHub.
3. Configura el Web Service del Frontend:
   - **Name**: `weather-api-frontend`.
   - **Environment**: `Docker`.
   - **Docker Context**: `frontend` (importante).
   - **Dockerfile Path**: `Dockerfile`.
   - **Plan**: `Free`.
4. Haz clic en **Create Web Service**.

## Paso 5: ¡Probar! 🎉
Una vez que el despliegue del frontend termine (puede tardar unos minutos), Render te dará una URL para tu web. ¡Pásasela a tus amigos!

---

### Notas Importantes
- **H2 Database**: Al usar el plan gratuito, la base de datos H2 se reiniciará cada vez que el servicio se duerma o se reinicie (ya que es en memoria).
- **Cold Start**: Las instancias gratuitas de Render "se duermen" después de 15 minutos de inactividad. La primera carga después de un tiempo puede tardar unos 30 segundos.
- **CORS**: He configurado el backend para que acepte peticiones desde cualquier sitio (`*`), así que no deberías tener problemas de conexión.
