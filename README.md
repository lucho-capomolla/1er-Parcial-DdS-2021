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
>El día de estreno de las películas es el jueves, el tiempo que durará cada una en cartelera será variable.
>
>La página contará con una opción para comprar consumibles, pudiendo tratarse de pochoclos, nachos o alguna bebida. Los primeros vienen en tres tamaños diferentes, una bolsita de 100 gr, un cartón que contiene 250 gr y el balde de 500 gr. El precio base está definido por la bolsita. A medida que aumenta el peso, el precio se duplica y cuatriplica (por ejemplo, si el precio de la bolsita es de $100, el del cartón será de $200 y el del balde de $400). La bebida viene en un único tamaño estándar de 500 ml y el precio es el mismo para todas. Existe una variedad de marcas que ofrece el establecimiento para estas, entre ellas: Sprite, Manaos, Pepsi, Agua, Jugo, Mirinda.
>El cliente podrá elegir entre cualquiera de los tres productos o todos. Para elegir varios, deberá armar un combo. En caso de agregar tres o más dentro de un combo, se le otorgará un descuento del 10%.
>
>El usuario poseerá en el sistema una billetera virtual que podrá recargar con un cierto monto de dinero. Esta será utilizada para pagar las compras que realice en el sitio.
>
>Se obtendrá un ticket por cada compra que se efectúe. Es decir, si se compra primero una entrada, luego una gaseosa y por último un combo, serán tres tickets diferentes.

Requerimientos no funcionales:
- Cuando el cliente esté por efectuar la compra, aparecerá un cartel preguntando si está seguro que desea realizarla, o si quisiera quitar algún producto del carrito. Esto servirá para evitar que compre cosas de las que luego se arrepintió o agregó por error.
- La sesión del usuario deberá cerrarse luego de 5 minutos de inactividad, para así, evitar que si este se olvidará de cerrarla, otra persona no pueda utilizarla.
- Si existieran ya asientos tomados, estos aparecerán en rojo y no se permitirá seleccionarlos. Esta funcionalidad es muy útil, ya que evitará que el usuario deba seleccionar asiento por asiento para saber cuáles están disponibles y cuáles no.
- Aquel usuario que posea el rol administrador tendrá la capacidad de agregar nuevos administradores o quitar a otro ya existente.
