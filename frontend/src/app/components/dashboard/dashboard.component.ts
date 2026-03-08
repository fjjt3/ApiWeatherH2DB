import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { WeatherCardComponent } from '../weather-card/weather-card.component';
import { WeatherService } from '../../services/weather.service';
import { CiudadClima } from '../../models/ciudad-clima.model';
import { TranslateService, TranslateModule } from '@ngx-translate/core';

@Component({
  selector: 'app-dashboard',
  standalone: true,
  imports: [CommonModule, WeatherCardComponent, TranslateModule],
  templateUrl: './dashboard.component.html',
  styleUrls: ['./dashboard.component.css']
})
export class DashboardComponent implements OnInit {
  cities: string[] = [];
  weatherData: Map<string, CiudadClima> = new Map();
  loading = false;
  error: string | null = null;
  successMessage: string | null = null;

  constructor(
    private weatherService: WeatherService,
    private translate: TranslateService
  ) { }

  ngOnInit(): void {
    this.loadCities();
    this.loadWeatherData();
  }

  /**
   * Load configured cities from API
   */
  loadCities(): void {
    this.weatherService.getCities().subscribe({
      next: (cities) => {
        this.cities = cities;
      },
      error: (err) => {
        console.error('Error loading cities:', err);
      }
    });
  }

  /**
   * Load current weather data
   */
  loadWeatherData(): void {
    this.loading = true;
    this.error = null;

    this.weatherService.getWeather().subscribe({
      next: (data) => {
        // Get most recent weather for each city
        this.weatherData.clear();
        data.forEach(weather => {
          const existing = this.weatherData.get(weather.nombreCiudad);
          if (!existing || new Date(weather.fechaConsulta) > new Date(existing.fechaConsulta)) {
            this.weatherData.set(weather.nombreCiudad, weather);
          }
        });
        this.loading = false;
      },
      error: (err) => {
        this.error = this.translate.instant('DASHBOARD.ERROR_MSG');
        this.loading = false;
        console.error('Error:', err);
      }
    });
  }

  /**
   * Refresh weather data from API
   */
  refreshWeather(): void {
    this.loading = true;
    this.error = null;
    this.successMessage = null;

    this.weatherService.refreshWeather().subscribe({
      next: (data) => {
        this.weatherData.clear();
        data.forEach(weather => {
          this.weatherData.set(weather.nombreCiudad, weather);
        });
        this.loading = false;
        this.successMessage = this.translate.instant('DASHBOARD.SUCCESS_MSG');

        // Clear success message after 3 seconds
        setTimeout(() => {
          this.successMessage = null;
        }, 3000);
      },
      error: (err) => {
        this.error = this.translate.instant('DASHBOARD.ERROR_MSG');
        this.loading = false;
        console.error('Error:', err);
      }
    });
  }

  /**
   * Get weather for a specific city
   */
  getWeatherForCity(city: string): CiudadClima | null {
    return this.weatherData.get(city) || null;
  }
}
