# Diseño de Sistemas - 1er Parcial 

### Integrantes
Capomolla, Luciano\
D'Antoni G., Priscila S.

### Link
[Google Drive](https://drive.google.com/drive/u/1/folders/1LmEufCj5gzect8zBlHH6bKDe1CgIHYDt)

## Cinema :clapper: :popcorn:	

![Imagen](https://cooltivarte.com/portal/wp-content/uploads/2020/08/por-que-se-comen-palomitas-en-el-cine-en-vez-de-otros-039-snacks-039.jpg)

>Nos pidieron implementar un sistema de ventas de entradas de cine de forma online. 
>
>Los usuarios podrán ver el listado de películas que actualmente se están dando en el establecimiento. Para comprar las entradas deberán registrarse en el sistema, a su vez, creándose un usuario para futuras compras. Se le solicitará el nombre y apellido, documento, fecha de nacimiento, un email y contraseña. Estos datos deberán persistir en la base de datos.
>
>El cliente podrá solicitar las entradas seleccionando la película, el horario y la cantidad de asientos. El sistema consulta la butaca disponible para la función que eligió y mostrará el mapa de la sala permitiendo que marque la que desee. Una vez elegido el número de asiento, el mismo no estará disponible para la función del horario elegido.
>El sistema calcula el monto de la compra consultando la tarifa que se encuentra en la base de datos, esta podrá variar según el día, y se le informa al cliente el costo. Si se trata del día del estreno, el precio se duplica, pero si se trata de los días miércoles, habrá un 50% de descuento. 
Las películas se estrenan los días jueves y duran en cartelera 3 semanas.
>
>La página contará con una opción para comprar consumibles, pudiendo tratarse de pochoclos o alguna bebida. Los primeros vienen en tres tamaños diferentes, una bolsita de 100 gr, un cartón que contiene 250 gr y el balde de 500 gr. El precio base está definido por la bolsita. A medida que aumenta el peso, el precio se duplica y cuatriplica (por ejemplo, si el precio de la bolsita es de $100, el del cartón será de $200 y el del balde de $400). La bebida viene en un único tamaño estándar de 500 ml y el precio es el mismo para todas. 
>El cliente podrá elegir entre los dos productos o ambos, en caso de elegir tres o más en un combo se le otorgará un descuento del 10%.

Requerimientos no funcionales: 
- Cuando el cliente esté por efectuar la compra aparecerá un cartel preguntadole si está seguro que desea realizarla o si quisiera quitar algún producto del carrito. 
- La sesión del usuario deberá cerrarse luego de 5 minutos de inactividad.
- Si existieran ya asientos tomados, estos aparecerán en rojo y no permitirá seleccionarlos. De no haber más butacas, se mostrará el mapa deshabilitado y una leyenda: “Función agotada”.
