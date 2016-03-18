# Práctica de Padel

##ECP1. Persistencia (30%)

###A (+2) Modificar la capa de persistencia para que los tokens caduquen en 1 hora. Se deberá ofrecer una funcionalidad de eliminación de tokens caducados

En éste apartado se ha añadido un atributo más a la entidad de Token en el que se guarda la fecha de creación del mismo. También se ha añadido un método que comprueba si el token no ha expirado. Se ha añadido la funcionalidad para eliminar tokens caducados.

###B (+5) Ampliar la capa de persistencia para poder ofrecer un servicio de clases de padel. El diseño es abierto

En este apartado se ha optado por añadir la entidad Training, con un id, una fecha de inicio de entrenamiento, otra de fin, un entrenador y un listado de alumnos. Se ha pensado que un alumno puede estar apuntado a más de un entrenamiento, de modo que el atributo de alumnos queda ManyToMany.
También se ha añadido en la capa Dao la parte referida a los entrenamientos, así como ampliado la de usuarios, reservas y token.

###C (+3) Modificar o realizar los test de las mejorar anteriores

Se han realizado los test tanto de las entidades como de los daos. He ampliado el poblador con los entrenamientos para poder realizar los test.

##ECP1. Api (50%)
###A (+2) Modificar la capa de negocio para que la validación de tokens incluya la mejora de caducidad

Para este apartado se ha creado una query que devuelva sólo usuarios que tengan el token en vigor. Al ponerla en UserDetailsServiceImpl no dejan de pasar los test, a pesar de que la query creo que es correcta, por este motivo he dejado la consulta comentada en ese punto, para que la pueda ver pero no cause conflictos con el resto. No encuentro el fallo.


###B (+5) Ampliar la capa de negocio para poder ofrecer un servicio de clases de padel, incluyendo la seguridad. El diseño es abierto

Se ha creado un controlador de entrenamientos, TrainingController, un envolvente, TrainingWrapper, y un recurso especifico de los entrenamientos, TrainingResource. Además se han añadido las Uris correspondientes en el fichero de Uris y la seguridad en el fichero de SecurityConfig.

###C (+3) Modificar o realizar los test de las mejorar anteriores

He realizado los test del TrainingController y los de TrainingResource. He añadido en el fichero de populate un creador de entrenadores por defecto, que al lanzarlo da error por no crear una contraseña y no poder codificarla. Dejo esto también comentado. No encuentro el fallo.

##ECP1. Web (20%)
###Se puede realizar con JSP + JSTL o Thymeleaf
###A (+10) Realizar la capa Web de algunas de las operaciones de la aplicación de Paddle, sin seguridad

Se ha creado la parte web correspondiente a las pistas con JSP + JSTL + Bootstrap.
