Pantalla de Bienvenida (Splash Screen)

<img width="216" height="472" alt="image" src="https://github.com/user-attachments/assets/cf20c309-a689-4673-bd8d-e1ace3c0fce2" />

Al abrir la aplicación por primera vez, verás una pantalla de bienvenida con el logo de SaboraApp. Esta pantalla se muestra durante un breve periodo (1.5 segundos).



Pantalla de Carga (Barra de Progreso)

<img width="238" height="472" alt="image" src="https://github.com/user-attachments/assets/9b0fabf4-6511-42d2-a282-a73daa77d99c" />
Inmediatamente después de la pantalla de bienvenida, aparece una pantalla con una barra de progreso que se va llenando. Esto indica que la aplicación está preparando los componentes iniciales.



Pantalla de Inicio de Sesión (MainActivity)

<img width="217" height="472" alt="image" src="https://github.com/user-attachments/assets/cc46d03b-b19c-4bb6-9b07-aff94d070eff" />

En la vista de ingreso se muestra:
  o	Un título en la barra de acción que dice "Ingresar".
  o	Campos para ingresar "Correo Electrónico" y "Contraseña".
  o	Un botón grande "INGRESAR".
  o	Un texto en la parte inferior que dice ¿“No tienes cuenta”? Regístrate.

  

Pantalla de Registro (Registro)

<img width="217" height="472" alt="image" src="https://github.com/user-attachments/assets/78f205b9-5940-4976-884d-5d83be70305c" />

En la vista de registro se muestra:
  o	Un título en la barra de acción que dice "Registrarse".
  o	Campos para ingresar "Usuario", "Correo Electrónico", "Contraseña" y "Confirmar Contraseña".
  o	Un botón grande "REGISTRARSE".
  o	Un texto en la parte inferior que dice ¿"Tienes una cuenta?” INGRESAR.


  
Vista General de la Pantalla Principal

<img width="550" height="180" alt="image" src="https://github.com/user-attachments/assets/ff01927f-bcb3-4c28-a2f6-9a6a460be7b0" />

Se puede destacar	una barra de herramientas (Toolbar) en la parte superior con el título de la sección actual (e.g., "Inicio") y un ícono de menú (hamburguesa) a la izquierda.



Abrir y Usar el Menú de Navegación Lateral (Navigation Drawer)

<img width="211" height="472" alt="image" src="https://github.com/user-attachments/assets/7094414d-cf0b-42dc-8b87-41bb9247eda5" />

El menú lateral se desliza mostrando:
  o	Encabezado (Header).
  o	Si el usuario está logueado: Su nombre y correo electrónico obtenidos de Firebase.
  o	Si no está logueado o hay un error: "Invitado" y "correo@ejemplo.com" (o los valores por defecto).

  

Sección "Inicio" (InicioFragment)

<img width="221" height="472" alt="image" src="https://github.com/user-attachments/assets/9a6639ce-c127-4250-831b-d006b6a7fe87" />

La información principal del restaurante (nombre, ubicación, número de mesas, horarios de servicio, redes sociales).



Sección "Nueva Reserva" (HomeFragment)

<img width="218" height="472" alt="image" src="https://github.com/user-attachments/assets/613ac9ac-fc0f-4ba3-97b4-0280cfafa70a" />

En la vista de rnueva reserva se muestra:
o	Un botón e ícono de calendario (binding.Calendario).
o	Un TextView para mostrar la fecha seleccionada (binding.Fecha).
o	Campos de texto para: "Nombre del Cliente", "Teléfono del Cliente", "Correo del Cliente", "Número de Personas", "Preferencias (Opcional)", "Notas Adicionales (Opcional)".
o	Un botón "GUARDAR CITA".



Sección "Reservas" (GalleryFragment)

<img width="220" height="472" alt="image" src="https://github.com/user-attachments/assets/044ad62b-3a51-4321-bb35-f2124d49bab7" />

En la vista de reservas se muestra:
o	Cargando: Una ProgressBar (progressBarGallery) visible.
o	Iniciado sesión, sin reservas: TextView (textViewNoCitas) con el mensaje de "No hay citas disponibles." (de R.string.no_citas_disponibles).
o	Iniciado sesión, con reservas: Un RecyclerView (recyclerViewCitas) mostrando una lista de sus reservas. Cada item de la lista con los detalles de una cita según el diseño de item_cita.xml (CitasAdapter).



Sección "Menú Digital" (SlideshowFragment)

<img width="218" height="472" alt="image" src="https://github.com/user-attachments/assets/e2928405-7a8d-4614-9ffd-5ad946c6d424" />

Sale la vista del menú digital mostrando una lista de platos. Cada item de la lista (definido por item_plato.xml para MenuAdapter) mostrará la imagen, nombre, descripción y precio del plato
