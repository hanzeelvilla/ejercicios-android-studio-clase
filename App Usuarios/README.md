# Implementación de Retrofit para la Gestión de Usuarios en Android

## Configuración backend

### Abrir la carpeta apiUsuariosFeaDelProfe

```
cd apiUsuariosFeaDelProfe
```

### Descargar paquetes npm
```
npm i
```

### Crear el archivo .env
1. Cambia de nombre el archivo *template_env.txt* a **.env**
2. Agrega tus datos (minimo la pswd)

### Crea la base de datos si no la tienes creada en MySQL
```
CREATE DATABASE pmoviles;
```
### Ejecuta la api
```
node app.js
```

### Probar la API localmente

Puedes probar la api localmente con el archivo **test_endpoints_local.rest** si tienes instalada la extensión **Rest Client**

### Port Forwarding

1. Al lado de terminal ve a la opcion que dice PORTS
2. Da clic en Forward a Port
3. El puerto de la API es 3000 **a menos que se defina otro en las variables de entorno**
4. Aparecerá un link con la dirección de tu API, tienes que hacerla pública para que pueda Android Studio pueda usarla
5. Da clic en el número del puerto, **port visibility** y selecciona **public** 

Documentación de como hacer port forwarding desde VS
[Local Port Forwarding](https://code.visualstudio.com/docs/editor/port-forwarding)

### Probar la API en línea

Antes de pasar a Android Studio puedes probar la API con el archivo **test_endpoints_port_forwarding.rest** pero debes de modificar los endpoints para que usen tu link

## Configuración Android Studio

Modifica la línea 50 (BASE_URL) del archivo ApiService.kt con el link de tu API hosteada y abierta a internet (port forwarding)
