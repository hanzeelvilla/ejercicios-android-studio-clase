const express = require('express');
const { Sequelize, DataTypes, Op } = require('sequelize');
require('dotenv').config()

const app = express();
app.use(express.json());

// Configurar la conexión a la base de datos
const sequelize = new Sequelize(process.env.BD_NAME, process.env.BD_USERNAME, process.env.BD_PSWD, {
  host: 'localhost',
  dialect: 'mysql'
});

// Definir el modelo de Usuario
const User = sequelize.define('User', {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  nombre: {
    type: DataTypes.STRING,
    allowNull: false
  },
  usuario: {
    type: DataTypes.STRING,
    allowNull: false,
    unique: true
  },
  contrasena: {
    type: DataTypes.STRING,
    allowNull: false
  }
});

// Sincronizar el modelo con la base de datos
// { force: false } creará la tabla si no existe, pero no la sobrescribirá si ya existe
sequelize.sync({ force: false }).then(() => {
  console.log("Base de datos y tablas creadas!");
});

// Ruta única para todas las operaciones CRUD
app.get('/users', async (req, res) => {
  const { action, id, nombre, usuario, contrasena } = req.query;

  try {
    switch(action) {
      case 'create':
        if (!nombre || !usuario || !contrasena) {
          return res.status(400).json({ error: 'Faltan datos requeridos' });
        }
       // const hashedPassword = await bcrypt.hash(contrasena, 10);
        const newUser = await User.create({ nombre, usuario, contrasena});
        return res.status(201).json({ message: 'Usuario creado exitosamente', id: newUser.id });

      case 'read':
        if (id) {
          const user = await User.findByPk(id, { attributes: ['id', 'nombre', 'usuario','contrasena'] });
          if (user) {
            return res.json(user);
          } else {
            return res.status(404).json({ message: 'Usuario no encontrado' });
          }
        } else {
          const users = await User.findAll({ attributes: ['id', 'nombre', 'usuario','contrasena'] });
          return res.json(users);
        }

      case 'update':
        if (!id) {
          return res.status(400).json({ error: 'Se requiere ID para actualizar' });
        }
        const user = await User.findByPk(id);
        if (user) {
          if (nombre) user.nombre = nombre;
          if (usuario) user.usuario = usuario;
          if (contrasena) user.contrasena = contrasena;
          await user.save();
          return res.json({ message: 'Usuario actualizado exitosamente',id:id });
        } else {
          return res.status(404).json({ message: 'Usuario no encontrado' });
        }

      case 'delete':
        if (!id) {
          return res.status(400).json({ error: 'Se requiere ID para eliminar' });
        }
        const deletedUser = await User.findByPk(id);
        if (deletedUser) {
          await deletedUser.destroy();
          return res.json({ message: 'Usuario eliminado exitosamente' });
        } else {
          return res.status(404).json({ message: 'Usuario no encontrado' });
        }

      default:
        return res.status(400).json({ error: 'Acción no válida' });
    }
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

const PORT = process.env.PORT || 3000;
app.listen(PORT, () => {
  console.log(`Servidor corriendo en el puerto ${PORT}`);
});