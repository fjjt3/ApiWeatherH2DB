import { Component, Input } from '@angular/core';
import { CommonModule } from '@angular/common';
import { CiudadClima } from '../../models/ciudad-clima.model';

@Component({
  selector: 'app-weather-card',
  standalone: true,
  imports: [CommonModule],
  templateUrl: './weather-card.component.html',
  styleUrls: ['./weather-card.component.css']
})
export class WeatherCardComponent {
  @Input() city: string = '';
  @Input() weather: CiudadClima | null = null;

  /**
   * Get temperature color class based on temperature value
   */
  getTemperatureClass(): string {
    if (!this.weather) return '';

    if (this.weather.temperatura < 15) {
      return 'temp-cold';
    } else if (this.weather.temperatura > 25) {
      return 'temp-hot';
    }
    return 'temp-moderate';
  }

  /**
   * Get weather icon based on temperature
   */
  getWeatherIcon(): string {
    if (!this.weather) return 'bi-cloud';

    if (this.weather.temperatura < 10) {
      return 'bi-cloud-snow';
    } else if (this.weather.temperatura < 20) {
      return 'bi-cloud-sun';
    } else {
      return 'bi-sun';
    }
  }

  /**
   * Format date to local string
   */
  formatDate(dateString: string): string {
    if (!dateString) return 'N/A';
    const date = new Date(dateString);
    return date.toLocaleString('es-ES', {
      day: '2-digit',
      month: '2-digit',
      year: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  }
}
