import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable, catchError, throwError } from 'rxjs';
import { CiudadClima } from '../models/ciudad-clima.model';
import { environment } from '../../environments/environment';

@Injectable({
    providedIn: 'root'
})
export class WeatherService {
    private apiUrl = environment.apiUrl;

    constructor(private http: HttpClient) { }

    /**
     * Get list of configured cities
     */
    getCities(): Observable<string[]> {
        return this.http.get<string[]>(`${this.apiUrl}/ciudades`)
            .pipe(
                catchError(this.handleError)
            );
    }

    /**
     * Get all weather records
     */
    getWeather(): Observable<CiudadClima[]> {
        return this.http.get<CiudadClima[]>(this.apiUrl)
            .pipe(
                catchError(this.handleError)
            );
    }

    /**
     * Refresh weather data for all cities
     */
    refreshWeather(): Observable<CiudadClima[]> {
        return this.http.post<CiudadClima[]>(`${this.apiUrl}/actualizar`, {})
            .pipe(
                catchError(this.handleError)
            );
    }

    /**
     * Get weather history for a specific city
     */
    getWeatherByCity(city: string): Observable<CiudadClima[]> {
        return this.http.get<CiudadClima[]>(`${this.apiUrl}/ciudad/${city}`)
            .pipe(
                catchError(this.handleError)
            );
    }

    private handleError(error: any) {
        console.error('Error occurred:', error);
        return throwError(() => new Error('Error al obtener datos del clima. Por favor, intenta de nuevo.'));
    }
}
