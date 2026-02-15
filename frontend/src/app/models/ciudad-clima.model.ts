export interface CiudadClima {
  id: number;
  nombreCiudad: string;
  latitud: number;
  longitud: number;
  temperatura: number;
  humedad: number;
  velocidadViento: number;
  fechaConsulta: string; // ISO 8601 date string
  timezoneMeteo: string;
}
