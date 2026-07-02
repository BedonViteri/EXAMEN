# Sistema de Mercado Distribuidor - Mercado Quevedo

Este repositorio contiene el prototipo arquitectónico para la plataforma distribuida **Mercado Quevedo**, desarrollado como solución práctica para el Examen de Sistemas Distribuidos en la Universidad Técnica Estatal de Quevedo (UTEQ). La solución adopta una arquitectura basada en microservicios desacoplados bajo el patrón de diseño *Database per Service*.

---

## Arquitectura y Tecnologías
* **Lenguaje:** Java 21
* **Framework:** Spring Boot 3.3.x / 3.4.x
* **Persistencia:** PostgreSQL 16 (Bases de datos aisladas e independientes)
* **Proxy Inverso / Puerta de Enlace:** Nginx Alpine
* **Orquestador de Contenedores:** Docker Compose

---

## Justificación Técnica de la Imagen Base (Parte D)

Para optimizar el entorno de producción y cumplir con los requisitos rigurosos del examen, se seleccionó la imagen base **`eclipse-temurin:21-jre-alpine`** mediante una estrategia de construcción multi-etapa (*Multi-stage build*):

1. **Minimización del Peso (Eficiencia):** Al utilizar la distribución ligera basada en **Alpine Linux**, el tamaño final de la imagen en ejecución se reduce drásticamente (pasando de ~450MB a solo ~150MB), lo que acelera el despliegue en red.
2. **Seguridad Operativa (No-Root User):** Los contenedores no corren con privilegios de administrador. Se crea explícitamente un grupo y usuario seguro llamado `appuser` dentro del Dockerfile, impidiendo que una falla del sistema comprometa al sistema operativo host.
3. **Reducción de Superficie de Ataque:** Alpine no incluye herramientas de desarrollo, compiladores ni shells innecesarios, eliminando vulnerabilidades comunes (CVE) explotables en producción.
4. **Respaldo Institucional:** Eclipse Temurin es el estándar de la industria promovido por la Fundación Eclipse, garantizando parches de seguridad continuos para Java 21.

---

## Instrucciones de Despliegue Rápido

El sistema está diseñado para compilar todo el código fuente de Java y levantar el ecosistema completo con un **único comando unificado**.

### Requisitos Previos
* Tener activo el motor de **Docker Desktop** en el equipo de laboratorio.

### Paso a Paso para Arrancar
1. Abra una terminal en la raíz de la carpeta del proyecto.
2. Ejecute el comando global de orquestación:
   docker compose up --build