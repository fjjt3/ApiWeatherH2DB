# Render Deployment Guide 🚀

This guide will help you upload your application to **Render.com** for free. We will use Docker to keep it simple.

## Step 1: Prepare your GitHub Repository
1. Ensure all your code is uploaded to a **GitHub** repository.
2. Verify that you have saved the recent changes I made to `WebConfig.java` and `environment.prod.ts`.

## Step 2: Create the Backend on Render
1. Go to [dashboard.render.com](https://dashboard.render.com/) and log in with GitHub.
2. Click **New +** > **Web Service**.
3. Select your GitHub repository.
4. Configure the Backend Web Service:
   - **Name**: `weather-api-backend` (or any name you prefer).
   - **Environment**: `Docker`.
   - **Root Directory**: `backend` (important).
   - **Dockerfile Path**: `Dockerfile`.
   - **Plan**: `Free`.
5. Click **Create Web Service**.
6. **Copy the URL** assigned by Render (e.g., `https://weather-api-backend.onrender.com`).

## Step 3: Update the Frontend Locally
1. Open the file `frontend/src/environments/environment.prod.ts` in your editor.
2. Replace `https://YOUR-BACKEND-URL.onrender.com/clima` with your newly created backend URL, keeping the `/clima` at the end.
   - Example: `apiUrl: 'https://weather-api-backend.onrender.com/clima'`
3. Save, **commit**, and **push** to GitHub.

## Step 4: Create the Frontend on Render
1. In the Render Dashboard, click **New +** > **Web Service**.
2. Select the same GitHub repository.
3. Configure the Frontend Web Service:
   - **Name**: `weather-api-frontend`.
   - **Environment**: `Docker`.
   - **Root Directory**: `frontend` (important).
   - **Dockerfile Path**: `Dockerfile`.
   - **Plan**: `Free`.
4. Click **Create Web Service**.

## Step 5: Test! 🎉
Once the frontend deployment is complete (it may take a few minutes), Render will provide a URL for your web app. Share it with your friends!

---

### Important Notes
- **H2 Database**: When using the free plan, the H2 database will reset every time the service sleeps or restarts (since it is in-memory).
- **Cold Start**: Render's free instances "sleep" after 15 minutes of inactivity. The first load after some time may take about 30 seconds.
- **CORS**: I have configured the backend to accept requests from anywhere (`*`), so you should not have connection issues.
