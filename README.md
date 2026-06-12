Guía de Pruebas API Cootramixtol (Bruno)

Esta guía contiene la configuración y todos los ejemplos de peticiones (POST, GET, PUT, PATCH) necesarios para probar el CRUD completo de la API de Cootramixtol utilizando Bruno (o Postman).

1. Configuración de Entorno (Environment)

Antes de empezar, crea un nuevo entorno (Environment) en Bruno llamado Local y añade las siguientes variables:

baseUrl = http://localhost:8080
token   = 


(La variable token se llenará con el valor devuelto tras hacer el Login).

2. Configuración de Headers

Para todas las peticiones, excepto las de la carpeta de Auth (/register y /login), debes configurar los siguientes Headers en tu request:

Authorization: Bearer {{token}}
Content-Type: application/json


3. Ejemplos de Peticiones por Recurso

Auth

Registrar Gerente

POST {{baseUrl}}/api/auth/register


{
  "username": "gerente",
  "password": "123456",
  "identificacion": "1001",
  "rol": "GERENTE"
}


Login

POST {{baseUrl}}/api/auth/login


{
  "username": "gerente",
  "password": "123456"
}


Nota: Copia el valor de accessToken de la respuesta y pégalo en la variable de entorno token.

Tarifas

Crear Tarifa Normal

POST {{baseUrl}}/api/tarifas


{
  "aportesGastosVarios": 10000,
  "fondoReposicion": 5000,
  "aporteSocialPorSocio": 3000,
  "activo": true
}


Crear Tarifa Cero (Para Taller)

POST {{baseUrl}}/api/tarifas


{
  "aportesGastosVarios": 0,
  "fondoReposicion": 0,
  "aporteSocialPorSocio": 0,
  "activo": true
}


Consultas GET de Tarifas

Listar tarifas: GET {{baseUrl}}/api/tarifas

Buscar tarifa por ID: GET {{baseUrl}}/api/tarifas/1

Filtrar tarifas activas: GET {{baseUrl}}/api/tarifas?activo=true

Inactivar Tarifa

PATCH {{baseUrl}}/api/tarifas/1/inactivar


Asociados

Crear Asociado

POST {{baseUrl}}/api/asociados


{
  "identificacion": "2001",
  "nombres": "Carlos Perez",
  "activo": true,
  "celular": "3001112233",
  "correo": "carlos@correo.com",
  "fechaNacimiento": "1985-05-10",
  "fechaIngreso": "2026-06-11"
}


Consultas GET de Asociados

Buscar por identificación: GET {{baseUrl}}/api/asociados/2001

Listar asociados: GET {{baseUrl}}/api/asociados

Filtrar por nombre: GET {{baseUrl}}/api/asociados?nombres=Carlos

Filtrar activos: GET {{baseUrl}}/api/asociados?activo=true

Actualizar Asociado

PUT {{baseUrl}}/api/asociados/2001


{
  "nombres": "Carlos Andres Perez",
  "activo": true,
  "celular": "3001112299",
  "correo": "carlos.actualizado@correo.com",
  "fechaNacimiento": "1985-05-10",
  "fechaIngreso": "2026-06-11"
}


Desactivar Asociado

PUT {{baseUrl}}/api/asociados/2001


{
  "activo": false
}


Conductores

Crear Conductor

POST {{baseUrl}}/api/conductores


{
  "identificacion": "3001",
  "nombres": "Luis Martinez",
  "fechaNacimiento": "1990-04-15",
  "activo": true,
  "numeroLicencia": "LIC3001",
  "categoriaLicencia": "C1",
  "vigenciaLicencia": "2027-12-31",
  "celular": "3004445566",
  "correo": "luis@correo.com"
}


Consultas GET de Conductores

Buscar por identificación: GET {{baseUrl}}/api/conductores/3001

Listar conductores: GET {{baseUrl}}/api/conductores

Filtrar por nombre: GET {{baseUrl}}/api/conductores?nombres=Luis

Filtrar activos: GET {{baseUrl}}/api/conductores?activo=true

Actualizar Conductor

PUT {{baseUrl}}/api/conductores/3001


{
  "nombres": "Luis Fernando Martinez",
  "fechaNacimiento": "1990-04-15",
  "activo": true,
  "numeroLicencia": "LIC3001-A",
  "categoriaLicencia": "C2",
  "vigenciaLicencia": "2028-12-31",
  "celular": "3004445577",
  "correo": "luis.actualizado@correo.com"
}


Desactivar Conductor

PUT {{baseUrl}}/api/conductores/3001


{
  "activo": false
}


Vehículos

Crear Vehículo

POST {{baseUrl}}/api/vehiculos


{
  "placa": "ABC123",
  "conductorIdentificacion": "3001",
  "tipo": "CAMPERO",
  "marca": "Toyota",
  "modelo": 2020,
  "capacidadPasajeros": 7,
  "color": "Blanco",
  "tarjetaPropiedad": "TP123456",
  "tarifaId": 1,
  "activo": true,
  "vigenciaSoat": "2027-12-31",
  "vigenciaRtm": "2027-12-31",
  "fechaIngreso": "2026-06-11"
}


Consultas GET de Vehículos

Buscar por placa: GET {{baseUrl}}/api/vehiculos/ABC123

Listar vehículos: GET {{baseUrl}}/api/vehiculos

Filtrar por placa: GET {{baseUrl}}/api/vehiculos?placa=ABC

Filtrar por tipo: GET {{baseUrl}}/api/vehiculos?tipo=CAMPERO

Filtrar activos: GET {{baseUrl}}/api/vehiculos?activo=true

Actualizar Vehículo

PUT {{baseUrl}}/api/vehiculos/ABC123


{
  "conductorIdentificacion": "3001",
  "tipo": "CAMPERO",
  "marca": "Toyota",
  "modelo": 2021,
  "capacidadPasajeros": 7,
  "color": "Gris",
  "tarjetaPropiedad": "TP123456",
  "tarifaId": 1,
  "activo": true,
  "vigenciaSoat": "2028-12-31",
  "vigenciaRtm": "2028-12-31",
  "fechaIngreso": "2026-06-11"
}


Desactivar Vehículo

PUT {{baseUrl}}/api/vehiculos/ABC123


{
  "activo": false
}


Afiliaciones

Crear Afiliación

POST {{baseUrl}}/api/afiliaciones


{
  "asociadoIdentificacion": "2001",
  "vehiculoPlaca": "ABC123",
  "fechaAfiliacion": "2026-06-11",
  "fechaFinAfiliacion": null,
  "estado": "ACTIVA"
}


Consultas GET de Afiliaciones

Buscar por ID: GET {{baseUrl}}/api/afiliaciones/1

Listar afiliaciones: GET {{baseUrl}}/api/afiliaciones

Filtrar por asociado: GET {{baseUrl}}/api/afiliaciones?asociadoIdentificacion=2001

Filtrar por vehículo: GET {{baseUrl}}/api/afiliaciones?vehiculoPlaca=ABC123

Filtrar por estado: GET {{baseUrl}}/api/afiliaciones?estado=ACTIVA

Desafiliar

PATCH {{baseUrl}}/api/afiliaciones/1/desafiliar


{
  "fechaFinAfiliacion": "2026-06-11"
}


Planillas

Crear Planilla Operación

POST {{baseUrl}}/api/planillas


{
  "fecha": "2026-06-11",
  "hora": "08:00:00",
  "vehiculoPlaca": "ABC123",
  "tarifaId": 1,
  "tipoPlanilla": "OPERACION",
  "registradoPor": "1001"
}


Crear Planilla Taller

POST {{baseUrl}}/api/planillas


{
  "fecha": "2026-06-11",
  "hora": "10:00:00",
  "vehiculoPlaca": "ABC123",
  "tarifaId": null,
  "tipoPlanilla": "TALLER",
  "registradoPor": "1001"
}


Crear Planillas Masivas

POST {{baseUrl}}/api/planillas/masivas


{
  "fecha": "2026-06-11",
  "placas": ["ABC123"],
  "tipoPlanilla": "OPERACION",
  "registradoPor": "1001"
}


Consultas GET de Planillas

Buscar por número: GET {{baseUrl}}/api/planillas/1

Listar planillas: GET {{baseUrl}}/api/planillas

Filtrar por fecha: GET {{baseUrl}}/api/planillas?fecha=2026-06-11

Filtrar por vehículo: GET {{baseUrl}}/api/planillas?vehiculoPlaca=ABC123

Filtrar por estado: GET {{baseUrl}}/api/planillas?estado=ACTIVA

Actualizar Planilla

PATCH {{baseUrl}}/api/planillas/1


{
  "conductorIdentificacion": "3001",
  "tarifaId": 1,
  "estado": "FINALIZADA"
}


Anular Planilla

PATCH {{baseUrl}}/api/planillas/1


{
  "estado": "ANULADA"
}


Despachos

Crear Despacho

POST {{baseUrl}}/api/despachos


{
  "ruta": "Sincelejo-Toluviejo",
  "vehiculoPlaca": "ABC123",
  "hora": "09:00:00",
  "fecha": "2026-06-11",
  "numeroPasajeros": 5
}


Consultas GET de Despachos

Listar despachos: GET {{baseUrl}}/api/despachos

Filtrar por fecha: GET {{baseUrl}}/api/despachos?fecha=2026-06-11

Filtrar por vehículo: GET {{baseUrl}}/api/despachos?vehiculoPlaca=ABC123

Filtrar por ruta: GET {{baseUrl}}/api/despachos?ruta=Sincelejo
