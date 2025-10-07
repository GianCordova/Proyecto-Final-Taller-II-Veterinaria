drop database veterinariadb;
create database veterinariadb;
use veterinariadb;

-- Entidades Independientes

create table ClientesDueños(
	idClienteDueño int auto_increment,
    nombre varchar (64) not null,
    apellido varchar(64) not null,
    telefono varchar (16) not null,
    correo varchar(128) not null,
    nitClienteDueño varchar(16) not null,
    estado varchar(32) not null,
    constraint pk_clientes_dueños primary key (idClienteDueño)
);

create table Medicamentos(
	idMedicamento int auto_increment,
    nombreMedicamento varchar(64) not null,
    finalidad varchar(128) not null,
    descripcion varchar(128) not null,
    fechaVencimiento date not null,
    constraint pk_medicamentos primary key (idMedicamento)
);

create table Proveedores(
	idProveedor int auto_increment,
    nombreProveedor varchar(64) not null,
    nitProveedor varchar(16) not null,
    telefono varchar(16) not null,
    correo varchar(128) not null,
    constraint pk_proveedores primary key (idProveedor)
);

create table Empleados(
	idEmpleado int auto_increment,
    nombreEmpleado varchar(64) not null,
    apellido varchar(64) not null,
    constraint pk_empleados primary key (idEmpleado)
);

create table Servicios(
	idServicio int auto_increment,
    nombreServicio varchar(32) not null,
    descripcion varchar(128) not null,
    constraint pk_servicios primary key (idServicio)

);

create table Veterinarios(
	idVeterinario int auto_increment,
    nombreVeterinario varchar(64) not null,
    apellidoVeterinario varchar(64) not null,
    idEmpleado int not null,
    sueldo decimal (10,2) not null,
    constraint pk_veterinarios primary key (idVeterinario),
    constraint fk_veterinarios_empleados foreign key (idEmpleado)
		references Empleados (idEmpleado)
);

create table Mascotas(
	idMascota int auto_increment,
    idClienteDueño int not null,
    nombreMascota varchar(64) not null,
    sexo varchar(32) not null,
    edad int not null,
    raza varchar(64) not null,
    tratamientoPrevio varchar(64) not null,
    constraint pk_mascotas primary key (idMascota),
    constraint fk_mascotas_clientes_dueños foreign key (idClienteDueño)
		references ClientesDueños (idClienteDueño)
);

create table Tratamientos(
	idTratamiento int auto_increment,
    idServicio int not null,
    idMedicamento int not null,
    nombreTratamiento varchar(64) not null,
    descripcion varchar(128) not null,
    constraint pk_tratamientos primary key (idTratamiento),
    constraint fk_tratamientos_servicios foreign key (idServicio)
		references Servicios (idServicio),
	constraint fk_tratamientos_medicamentos foreign key (idMedicamento)
		references Medicamentos (idMedicamento)
);

create table Consultas(
	idConsulta int auto_increment,
    idServicio int not null,
    idClienteDueño int not null,
    idMascota int not null,
    idTratamiento int not null,
    idVeterinario int not null,
    fechaConsulta date not null,
    constraint pk_consultas primary key (idConsulta),
    constraint fk_consultas_servicios foreign key (idServicio)
		references Servicios (idServicio),
	constraint fk_consultas_clientes_dueños foreign key (idClienteDueño)
		references ClientesDueños (idClienteDueño),
	constraint fk_consultas_mascotas foreign key (idMascota)
		references Mascotas (idMascota),
	constraint fk_consultas_tratamientos foreign key (idTratamiento)
		references Tratamientos (idTratamiento),
	constraint fk_consultas_veterinarios foreign key (idVeterinario)
		references Veterinarios (idVeterinario)
);

create table Recetas(
	idReceta int auto_increment,
    idMedicamento int not null,
    idMascota int not null,
    idVeterinario int not null,
    constraint pk_recetas primary key (idReceta),
    constraint fk_recetas_medicamentos foreign key (idMedicamento)
		references Medicamentos (idMedicamento),
	constraint fk_recetas_mascotas foreign key (idMascota)
		references Mascotas (idMascota),
	constraint fk_recetas_veterinarios foreign key (idVeterinario)
		references Veterinarios (idVeterinario)
);

create table Compras(
	idCompra int auto_increment,
    idServicio int not null,
    fechaCompra date not null,
    idMedicamento int not null,
    idProveedor int not null,
    idReceta int not null,
    subtotal decimal (10,2) not null,
    constraint pk_compras primary key (idCompra),
    constraint fk_compras_servicios foreign key (idServicio)
		references Servicios (idServicio),
	constraint fk_compras_medicamentos foreign key (idMedicamento)
		references Medicamentos (idMedicamento),
	constraint fk_compras_proveedores foreign key (idProveedor)
		references Proveedores (idProveedor),
	constraint fk_compras_recetas foreign key (idReceta)
		references Recetas (idReceta)
);
create table Vacunaciones(
	idVacunacion int auto_increment,
    idServicio int not null,
    idMascota int not null,
    idMedicamento int not null,
    constraint pk_vacunaciones primary key (idVacunacion),
	constraint fk_vacunaciones_servicios foreign key (idServicio)
		references Servicios (idServicio),
	constraint fk_vacunaciones_mascotas foreign key (idMascota)
		references Mascotas (idMascota),
	constraint fk_vacunaciones_medicamentos foreign key (idMedicamento)
		references Medicamentos (idMedicamento)
);

create table Citas(
	idCita int auto_increment,
    idServicio int not null,
    fechaCita date not null,
    idClienteDueño int not null,
    idMascota int not null,
    constraint pk_citas primary key (idCita),
    constraint fk_citas_servicios foreign key (idServicio)
		references Servicios (idServicio),
	constraint fk_citas_clientes_dueños foreign key (idClienteDueño)
		references ClientesDueños (idClienteDueño),
	constraint fk_citas_mascotas foreign key (idMascota)
		references Mascotas (idMascota)
);
create table Facturas(
	idFactura int auto_increment,
    idCompra int not null,
    idVeterinario int not null,
    idMascota int not null,
    nitClienteDueño varchar(16) not null,
    total decimal (10,2) not null,
    constraint pk_facturas primary key (idFactura),
    constraint fk_facturas_compras foreign key (idCompra)
		references Compras (idCompra),
	constraint fk_facturas_veterinarios foreign key (idVeterinario)
		references Veterinarios (idVeterinario),
	constraint fk_facturas_mascotas foreign key (idMascota)
		references Mascotas (idMascota)
);

create table FacturaCompra(
	idFacturaCompra int auto_increment,
    idFactura int not null,
    idCompra int not null,
    cantidad int not null,
    subtotal decimal (10,2) not null,
    constraint pk_facturas_compras primary key (idFacturaCompra),
	constraint fk_facturas_compras_facturas foreign key (idFactura)
		references Facturas (idFactura),
	constraint fk_facturas_compras_compras foreign key (idCompra)
		references Compras (idCompra)
);

create table RecetasMedicamentos(
	idRecetaMedicamento int auto_increment,
    idReceta int not null,
    idMedicamento int not null,
    cantidad int not null,
    dosis varchar (32) not null,
    frecuencia varchar(64) not null,
    observaciones varchar (128) not null,
    constraint pk_recetas_medicamentos primary key (idRecetaMedicamento),
    constraint fk_recetas_medicamentos_recetas foreign key (idReceta)
		references Recetas (idReceta),
	constraint fk_recetas_medicamentos_medicamentos foreign key (idMedicamento)
		references Medicamentos (idMedicamento)
);
create table TratamientosMedicamentos(
	idTratamientoMedicamento int auto_increment,
    idTratamiento int not null,
    idMedicamento int not null,
    nombreTratamiento varchar(64) not null,
    descripcion varchar (128) not null,
    constraint pk_tratamientos_medicamentos primary key (idTratamientoMedicamento),
	constraint fk_tratamientos_medicamentos_tratamientos foreign key (idTratamiento)
		references Tratamientos (idTratamiento),
	constraint fk_tratamientos_medicamentos_medicamentos foreign key (idMedicamento)
		references Medicamentos (idMedicamento)
);

create table Seguros (
    idseguro int auto_increment primary key,
    nombreseguro varchar(64) not null,     
    tiposeguro varchar(64) not null,     
    cobertura text,                     
    fechainicio date not null,           
    fechafin date not null,               
    costo decimal(10,2) not null,          
    idclientedueño int,                    
    foreign key (idclientedueño) references clientesdueños(idclientedueño)
);

-- __________________________Procedimientos almacenados____________________________________________
-- __________________________Procedimineto de ClientesDueños_______________________________________
delimiter //
	create procedure sp_agregarClienteDueños(
		in p_nombre varchar(64),
		in p_apellido varchar(64),
		in p_telefono varchar(16),
		in p_correo varchar(128),
		in p_nitclientedueño varchar(16),
		in p_estado varchar(32)
	)
	begin
		insert into ClientesDueños (nombre, apellido, telefono, correo, nitclientedueño, estado)
		values (p_nombre, p_apellido, p_telefono, p_correo, p_nitclientedueño, p_estado);
	end //
delimiter ;
 
delimiter //
	create procedure sp_listarClientesDueños()
	begin
		select * from ClientesDueños;
	end //
delimiter ;
 
delimiter //
	create procedure sp_buscarClientesDueños(in p_idClienteDueño int)
	begin
		select * from ClientesDueños 
		where idClienteDueño = p_idClienteDueño;
	end //
delimiter ;
 
delimiter //
	create procedure sp_actualizarClientesDueños(
		in p_idClienteDueño int,
		in p_nombre varchar(64),
		in p_apellido varchar(64),
		in p_telefono varchar(16),
		in p_correo varchar(128),
		in p_nitclientedueño varchar(16),
		in p_estado varchar(32)
	)
	begin
		update ClientesDueños
		set nombre = p_nombre,
			apellido = p_apellido,
			telefono = p_telefono,
			correo = p_correo,
			nitclientedueño = p_nitClienteDueño,
			estado = p_estado
		where idClienteDueño = p_idClienteDueño;
	end //
delimiter ;
 
delimiter //
	create procedure sp_eliminarClienteDueño(in p_idClienteDueño int)
	begin
		update ClientesDueños
		set estado = 'inactivo' 
		where idClienteDueño = p_idClienteDueño;
	end //
delimiter ;
 
 
-- __________________________Procedimineto de Medicamentos_______________________________________
delimiter //
	create procedure sp_agregarMedicamentos(
		in p_nombremedicamento varchar(64),
		in p_finalidad varchar(128),
		in p_descripcion varchar(128),
		in p_fechavencimiento date
	)
	begin
		insert into medicamentos (nombremedicamento, finalidad, descripcion, fechavencimiento)
		values (p_nombremedicamento, p_finalidad, p_descripcion, p_fechavencimiento);
	end //
delimiter ;
 
delimiter //
	create procedure sp_listarMedicamentos()
	begin
		select * from medicamentos;
	end //
delimiter ;
 
delimiter //
	create procedure sp_buscarMedicamentos(in p_idmedicamento int)
	begin
		select * from medicamentos where idmedicamento = p_idmedicamento;
	end //
delimiter ;
 
delimiter //
	create procedure sp_actualizarMedicamentos(
		in p_idmedicamento int,
		in p_nombremedicamento varchar(64),
		in p_finalidad varchar(128),
		in p_descripcion varchar(128),
		in p_fechavencimiento date
	)
	begin
		update medicamentos
		set nombremedicamento = p_nombremedicamento,
			finalidad = p_finalidad,
			descripcion = p_descripcion,
			fechavencimiento = p_fechavencimiento
		where idmedicamento = p_idmedicamento;
	end //
delimiter ;
 
delimiter //
	create procedure sp_eliminarMedicamentos(in p_idmedicamento int)
	begin
		delete from medicamentos where idmedicamento = p_idmedicamento;
	end //
delimiter ;
 
 
-- __________________________Procedimineto de Proveedores_______________________________________
delimiter //
	create procedure sp_agregarProveedores(
		in p_nombreproveedor varchar(64),
		in p_nitproveedor varchar(16),
		in p_telefono varchar(16),
		in p_correo varchar(128)
	)
	begin
		insert into proveedores (nombreproveedor, nitproveedor, telefono, correo)
		values (p_nombreproveedor, p_nitproveedor, p_telefono, p_correo);
	end //
delimiter ;
 
delimiter //
	create procedure sp_listarProveedores()
	begin
		select * from proveedores;
	end //
delimiter ;
 
delimiter //
	create procedure sp_buscarProveedores(in p_idproveedor int)
	begin
		select * from proveedores where idproveedor = p_idproveedor;
	end //
delimiter ;
 
delimiter //
	create procedure sp_actualizarProveedores(
		in p_idproveedor int,
		in p_nombreproveedor varchar(64),
		in p_nitproveedor varchar(16),
		in p_telefono varchar(16),
		in p_correo varchar(128)
	)
	begin
		update proveedores
		set nombreproveedor = p_nombreproveedor,
			nitproveedor = p_nitproveedor,
			telefono = p_telefono,
			correo = p_correo
		where idproveedor = p_idproveedor;
	end //
delimiter ;
 
delimiter //
	create procedure sp_eliminarProveedores(in p_idproveedor int)
	begin
		delete from proveedores where idproveedor = p_idproveedor;
	end //
delimiter ;
 
 
-- __________________________Procedimineto de Empleados_______________________________________
delimiter //
	create procedure sp_agregarEmpleados(
		in p_nombreempleado varchar(64),
		in p_apellido varchar(64)
	)
	begin
		insert into empleados (nombreempleado, apellido)
		values (p_nombreempleado, p_apellido);
	end //
delimiter ;
 
delimiter //
	create procedure sp_listarEmpleados()
	begin
		select * from empleados;
	end //
delimiter ;
 
delimiter //
	create procedure sp_buscarEmpleados(in p_idempleado int)
	begin
		select * from empleados where idempleado = p_idempleado;
	end //
delimiter ;
 
delimiter //
	create procedure sp_actualizarEmpleados(
		in p_idempleado int,
		in p_nombreempleado varchar(64),
		in p_apellido varchar(64)
	)
	begin
		update empleados
		set nombreempleado = p_nombreempleado,
			apellido = p_apellido
		where idempleado = p_idempleado;
	end //
delimiter ;
 
delimiter //
	create procedure sp_eliminarEmpleados(in p_idempleado int)
	begin
		delete from empleados where idempleado = p_idempleado;
	end //
delimiter ;

-- ---------------------------------------Procedimineto Almacenado Servicios ---------------------------------------------
delimiter //
create procedure sp_AgreagrServicios(
    in p_nombre varchar(32),
    in p_descripcion varchar(128)
)
begin
    insert into Servicios (nombreServicio, descripcion)
    values (p_nombre, p_descripcion);
end //
delimiter ;

delimiter //
create procedure sp_ListarServicios()
begin
    select * from Servicios;
end //
delimiter ;

delimiter //
create procedure sp_EliminarServicios(in p_id int)
begin
    delete from Servicios where idServicio = p_id;
end //
delimiter ;


delimiter //
create procedure sp_ActualizarServicios(
    in p_id int,
    in p_nombre varchar(32),
    in p_descripcion varchar(128)
)
begin
    update Servicios
    set nombreServicio = p_nombre,
        descripcion = p_descripcion
    where idServicio = p_id;
end //
delimiter ;

delimiter //
create procedure sp_BuscarServicios(in p_id int)
begin
    select * from Servicios
    where Servicios.idServicio =  p_id;
end //
delimiter ;

-- -------------------------------------Procedimineto Almacenado Veterinarios -------------------------------------------

delimiter //
create procedure sp_AgregarVeterinarios(
    in p_nombre varchar(64),
    in p_apellido varchar(64),
    in p_id_empleado int,
    in p_sueldo decimal(10,2)
)
begin
    insert into Veterinarios (nombreVeterinario, apellidoVeterinario, idEmpleado, sueldo)
    values (p_nombre, p_apellido, p_id_empleado, p_sueldo);
end //
delimiter ;

delimiter //
create procedure sp_ListarVeterinarios()
begin
    select * from Veterinarios;
end //
delimiter ;

delimiter //
create procedure sp_EliminarVeterinarios(in p_id int)
begin
    delete from Veterinarios where idVeterinario = p_id;
end //
delimiter ;

delimiter //
create procedure sp_ActualizarVeterinarios(
    in p_id int,
    in p_nombre varchar(64),
    in p_apellido varchar(64),
    in p_id_empleado int,
    in p_sueldo decimal(10,2)
)
begin
    update Veterinarios
    set nombreVeterinario = p_nombre,
        apellidoVeterinario = p_apellido,
        idEmpleado = p_id_empleado,
        sueldo = p_sueldo
    where idVeterinario = p_id;
end //
delimiter ;

delimiter //
create procedure sp_BuscarVeterinarios(in p_id int)
begin
    select * from Veterinarios
    where Veterinarios.idVeterinario = p_id;
end //
delimiter ;

-- -----------------------------------------------Procedimineto Almacenado Tratamientos -----------------------------------------------

delimiter //
create procedure sp_AgregarTratamientos(
    in p_id_servicio int,
    in p_id_medicamento int,
    in p_nombre varchar(64),
    in p_descripcion varchar(128)
)
begin
    insert into Tratamientos (idServicio, idMedicamento, nombreTratamiento, descripcion)
    values (p_id_servicio, p_id_medicamento, p_nombre, p_descripcion);
end //
delimiter ;

delimiter //
create procedure sp_ListarTratamiento()
begin
    select * from Tratamientos;
end //
delimiter ;

delimiter //
create procedure sp_ElimarTratamientos(in p_id int)
begin
    delete from Tratamientos where idTratamiento = p_id;
end //
delimiter ;

delimiter //
create procedure sp_ActualizarTratamientos(
    in p_id int,
    in p_id_servicio int,
    in p_id_medicamento int,
    in p_nombre varchar(64),
    in p_descripcion varchar(128)
)
begin
    update Tratamientos
    set idServicio = p_id_servicio,
        idMedicamento = p_id_medicamento,
        nombreTratamiento = p_nombre,
        descripcion = p_descripcion
    where idTratamiento = p_id;
end //
delimiter ;

delimiter //
create procedure sp_BuscarTratamientos(in p_id int)
begin
    select * from Tratamientos
    where Tratamientos.idTratamiento =p_id;
end //
delimiter ;

-- -----------------------------------------Procedimineto Almacenado Mascotas -----------------------------------------------------

delimiter //
create procedure sp_AgregarMascotas(
    in p_id_cliente_duenio int,
    in p_nombre varchar(64),
    in p_sexo varchar(32),
    in p_edad int,
    in p_raza varchar(64),
    in p_tratamiento_previo varchar(64)
)
begin
    insert into Mascotas (idClienteDueño, nombreMascota, sexo, edad, raza, tratamientoPrevio)
    values (p_id_cliente_duenio, p_nombre, p_sexo, p_edad, p_raza, p_tratamiento_previo);
end //
delimiter ;

delimiter //
create procedure sp_ListarMascotas()
begin
    select * from Mascotas;
end //
delimiter ;

delimiter //
create procedure sp_EliminarMascotas(in p_id int)
begin
    delete from Mascotas where idMascota = p_id;
end //
delimiter ;

delimiter //
create procedure sp_ActualizarMscotas(
    in p_id int,
    in p_id_cliente_duenio int,
    in p_nombre varchar(64),
    in p_sexo varchar(32),
    in p_edad int,
    in p_raza varchar(64),
    in p_tratamiento_previo varchar(64)
)
begin
    update Mascotas
    set idClienteDueño = p_id_cliente_duenio,
        nombreMascota = p_nombre,
        sexo = p_sexo,
        edad = p_edad,
        raza = p_raza,
        tratamientoPrevio = p_tratamiento_previo
    where idMascota = p_id;
end //
delimiter ;

delimiter //
create procedure sp_BuscarMascotas(in p_id int)
begin
    select * from Mascotas
    where Mascotas.idMascota = p_id;
end //
delimiter ;

-- ------------------------------------------------- Consultas----------------------------------------------------
delimiter //
	Create procedure sp_listarConsultas()
		begin
			Select
				C.idConsulta,
				C.idClienteDueño,
                C.idServicio,
				C.idMascota, 
				C.idTratamiento, 
				C.idVeterinario, 
				C.fechaConsulta
                from Consultas C;
			end//
delimiter ;
 
delimiter //
	Create procedure sp_agregarConsultas (
			in p_idServicio int,
			in p_idClienteDueño int,
			in p_idMascota int ,
			in p_idTratamiento int ,
			in p_idVeterinario int ,
			in p_fechaConsulta date)
            begin
				insert into Consultas (idServicio,idClienteDueño,idMascota,idTratamiento, idVeterinario,fechaConsulta)
					values (p_idServicio,p_idClienteDueño,p_idMascota,p_idTratamiento, p_idVeterinario,p_fechaConsulta);
			end//
delimiter ;
 
delimiter //
	Create procedure sp_editarConsultas( in p_idConsulta int,
			in p_idServicio int,
			in p_idClienteDueño int,
			in p_idMascota int ,
			in p_idTratamiento int ,
			in p_idVeterinario int ,
			in p_fechaConsulta date)
            begin
				update Consultas 
					set
						idServicio = p_idServicio,
						idClienteDueño =  p_idClienteDueño,
						idMascota = p_idMascota,
						idTratamiento = p_idTratamiento,
						idVeterinario = p_idVeterinario,
						fechaConsulta = p_fechaConsulta 
                        where idConsulta = p_idConsulta ;
			end//
delimiter ;
 
delimiter //
	Create procedure sp_buscarConsulta(in p_idConsulta int )
		begin
			select * from Consultas where idConsulta = p_idConsulta ;
		end//
delimiter ;
 
delimiter //
	Create procedure sp_eliminarConsultas ( in p_idConsulta int )
		begin 
			delete from Consultas where idConsulta = p_idConsulta;
		end//
delimiter ;
 
-- ------------------------------------------------ Recetas -------------------------------------------------
 
delimiter //
	Create procedure sp_listarRecetas()
	begin
		Select
			*
            from Recetas ;
	end//
delimiter ;
delimiter //
	Create procedure sp_agregarReceta(
		in p_idMedicamento int,
		in p_idMascota int,
		in p_idVeterinario int
	)
	begin
		insert into Recetas (idMedicamento, idMascota, idVeterinario)
		values (p_idMedicamento, p_idMascota, p_idVeterinario);
	end//
delimiter ;
 
delimiter //
	Create procedure sp_editarReceta(
		in p_idReceta int,
		in p_idMedicamento int,
		in p_idMascota int,
		in p_idVeterinario int
	)
	begin
		update Recetas
		set idMedicamento = p_idMedicamento,
			idMascota = p_idMascota,
			idVeterinario = p_idVeterinario
		where idReceta = p_idReceta;
	end//
delimiter ;
 
delimiter //
	Create procedure sp_buscarReceta(in p_idReceta int)
	begin
		select * from Recetas where idReceta = p_idReceta;
	end//
delimiter ;
 
delimiter //
	Create procedure sp_eliminarReceta(in p_idReceta int)
	begin
		delete from Recetas where idReceta = p_idReceta;
	end//
delimiter ;
 
-- -----------------------------------------Compras -----------------------------------------------------------
delimiter //
	Create procedure sp_listarCompras()
	begin
		Select
*
        from Compras ;
	end//
delimiter ;
 
delimiter //
	Create procedure sp_agregarCompra(
		in p_idServicio int,
		in p_fechaCompra date,
		in p_idMedicamento int,
		in p_idProveedor int,
		in p_idReceta int,
		in p_subtotal decimal(10,2)
	)
	begin
		insert into Compras (idServicio, fechaCompra, idMedicamento, idProveedor, idReceta, subtotal)
		values (p_idServicio, p_fechaCompra, p_idMedicamento, p_idProveedor, p_idReceta, p_subtotal);
	end//
delimiter ;
 
delimiter //
	Create procedure sp_editarCompra(
		in p_idCompra int,
		in p_idServicio int,
		in p_fechaCompra date,
		in p_idMedicamento int,
		in p_idProveedor int,
		in p_idReceta int,
		in p_subtotal decimal(10,2)
	)
	begin
		update Compras
		set idServicio = p_idServicio,
			fechaCompra = p_fechaCompra,
			idMedicamento = p_idMedicamento,
			idProveedor = p_idProveedor,
			idReceta = p_idReceta,
			subtotal = p_subtotal
		where idCompra = p_idCompra;
	end//
delimiter ;
 
delimiter //
	Create procedure sp_buscarCompra(in p_idCompra int)
	begin
		select * from Compras where idCompra = p_idCompra;
	end//
delimiter ;
 
delimiter //
	Create procedure sp_eliminarCompra(in p_idCompra int)
	begin
		delete from Compras where idCompra = p_idCompra;
	end//
delimiter ;
 
-- -------------------------------------------Vacunaciones ----------------------
delimiter //
	Create procedure sp_listarVacunaciones()
	begin
    Select
		*
        from Vacunaciones ;
	end//
delimiter ;
 
delimiter //
	Create procedure sp_agregarVacunacion(
		in p_idServicio int,
		in p_idMascota int,
		in p_idMedicamento int
	)
	begin
		insert into Vacunaciones (idServicio, idMascota, idMedicamento)
		values (p_idServicio, p_idMascota, p_idMedicamento);
	end//
delimiter ;
 
delimiter //
	Create procedure sp_editarVacunacion(
		in p_idVacunacion int,
		in p_idServicio int,
		in p_idMascota int,
		in p_idMedicamento int
	)
	begin
		update Vacunaciones
		set idServicio = p_idServicio,
			idMascota = p_idMascota,
			idMedicamento = p_idMedicamento
		where idVacunacion = p_idVacunacion;
	end//
delimiter ;
 
delimiter //
	create procedure sp_buscarVacunacion(in p_idVacunacion int)
	begin
		select * from Vacunaciones where idVacunacion = p_idVacunacion;
	end//
delimiter ;
 
delimiter //
	Create procedure sp_eliminarVacunacion(in p_idVacunacion int)
	begin
		delete from Vacunaciones where idVacunacion = p_idVacunacion;
	end//
delimiter ;

-- _____________________________________________Procedimientos Almacenados Citas____________________________________________________

delimiter //
	create procedure sp_listarCitas()
		begin
			select 
*
            from Citas C;
        end//
delimiter ;

delimiter //
	create procedure sp_agregarCitas(
		in p_idServicio int,
        in p_fechaCita date,
        in p_idClienteDueño int,
        in p_idMascota int)
		begin
			insert into Citas(idServicio, fechaCita, idClienteDueño, idMascota)
            values(p_idServicio, p_fechaCita, p_idClienteDueño, p_idMascota);
        end//
delimiter ;

delimiter //
	create procedure sp_buscarCitas(p_idCita int)
		begin
			select 
				C.idCita,
                C.idServicio,
                C.fechaCita,
                C.idClienteDueño,
                C.idMascota
			from Citas C where C.idCita = p_idCita;
        end//
delimiter ;

delimiter //
	create procedure sp_actualizarCitas(
		in p_idCita int,
        in p_idServicio int,
        in p_fechaCita date,
        in p_idClienteDueño int,
        in p_idMascota int)
		begin
			update Citas C
            set
				C.idServicio = p_idServicio,
                C.fechaCita = p_fechaCita,
                C.idClienteDueño = p_idClienteDueño,
                C.idMascota = p_idMascota
            where C.idCita = p_idCita;
        end//
delimiter ;

delimiter //
	create procedure sp_eliminarCitas(
		in p_idCita int)
		begin
			delete
            from Citas  where C.idCita = p_idCita;
        end//
delimiter ;

-- _____________________________________________Procedimientos Almacenados Facturas____________________________________________________

delimiter //
	create procedure sp_listarFacturas()
		begin
			select 
			*
            from Facturas F;
        end//
delimiter ;

delimiter //
	create procedure sp_agregarFacturas(
		in p_idCompra int,
        in p_idVeterinario int,
        in p_idMascota int,
        in p_nitClienteDueño decimal(10,2),
        in p_total decimal(10,2))
			begin
				insert into Facturas(idCompra, idVeterinario, idMascota, nitClienteDueño, total)
				values(p_idCompra, p_idVeterinario, p_idMascota, p_nitClienteDueño, p_total);
			end//
delimiter ;

delimiter //
	create procedure sp_buscarFacturas(
		in p_idFactura int)
		begin
			select
				F.idFactura,
                F.idCompra,
                F.idVeterinario,
                F.idMascota,
                F.nitClienteDueño,
                F.total
			from Facturas F where F.idFactura = p_idFactura;
        end//
delimiter ;

delimiter //
	create procedure sp_actualizarFacturas(
		in p_idFactura int)
		begin
			update Facturas F
            set
				F.idCompra = p_idCompra,
                F.idVeterinario = p_idVeterinario,
                F.idMascota = p_idMascota,
                F.nitClienteDueño = p_nitClienteDueño,
                F.total = p_total
                where F.idFactura = p_idFactura;
        end//
delimiter ;

delimiter //
	create procedure sp_eliminarFacturas(
		in p_idFactura int)
		begin
			delete
            from Facturas 
            where F.idFactura = p_idFactura;
        end//
delimiter ;

-- _____________________________________________Procedimientos Almacenados FacturaCompra____________________________________________________

delimiter //
	create procedure sp_listarFacturasCompras()
		begin
			select 
			*
            from FacturaCompra FC;
        end//
delimiter ;

delimiter //
	create procedure sp_agregarFacturasCompras(
		in p_idFactura int,
        in p_idCompra int,
        in p_cantidad int,
        in p_subtotal decimal(10,2))
			begin
				insert into FacturaCompra(idFactura, idCompra, cantidad, subtotal)
				values(p_idFactura, p_idCompra, p_cantidad, p_subtotal);
			end//
delimiter ;

delimiter //
	create procedure sp_buscarFacturasCompras(
		in p_idFacturaCompra int)
		begin
			select
				FC.idFacturaCompra,
                FC.idFactura,
                FC.idCompra,
                FC.cantidad,
                FC.subtotal
			from FacturaCompra FC where FC.idFacturaCompra = p_idFacturaCompra;
        end//
delimiter ;

delimiter //
	create procedure sp_actualizarFacturasCompras(
		in p_idFacturaCompra int,
		in p_idFactura int,
        in p_idCompra int,
        in p_cantidad int,
        in p_subtotal decimal(10,2))
		begin
			update FacturaCompra FC
            set
				FC.idFactura = p_idFactura,
                FC.idCompra = p_idCompra,
                FC.cantidad = p_cantidad,
                FC.subtotal = p_subtotal
            where FC.idFacturaCompra = p_idFacturaCompra;
        end//
delimiter ;

delimiter //
	create procedure sp_eliminarFacturasCompras(
		in p_idFacturaCompra int)
		begin
			delete
            from FacturaCompra 
            where idFacturaCompra = p_idFacturaCompra;
        end//
delimiter ;
-- _____________________________________________Procedimientos Almacenados RecetasMedicamentos____________________________________________________

delimiter //
	create procedure sp_listarRecetasMedicamentos()
		begin
			select
				*
			from RecetasMedicamentos RM;
        end//
delimiter ;

delimiter //
	create procedure sp_agregarRecetasMedicamentos(
		in p_idReceta int,
        in p_idMedicamento int,
        in p_cantidad int,
        in p_dosis varchar(32),
        in p_frecuencia varchar(64),
        in p_observaciones varchar(128))
			begin
				insert into RecetasMedicamentos(idReceta, idMedicamento, cantidad, dosis, frecuencia, observaciones)
				values(p_idReceta, p_idMedicamento, p_cantidad, p_dosis, p_frecuencia, p_observaciones);
			end//
delimiter ;

delimiter //
	create procedure sp_buscarRecetasMedicamentos(
		in p_idRecetaMedicamento int)
		begin
			select
				RM.idRecetaMedicamento,
				RM.idReceta,
                RM.idMedicamento,
                RM.cantidad,
                RM.dosis,
                RM.frecuencia,
                RM.observaciones
			from RecetasMedicamentos RM
            where RM.idRecetaMedicamento = p_idRecetaMedicamento;
        end//
delimiter ;

delimiter //
	create procedure sp_actualizarRecetasMedicamentos(
		in p_idRecetaMedicamento int,
        in p_idReceta int,
        in p_idMedicamento int,
        in p_cantidad int,
        in p_dosis varchar(32),
        in p_frecuencia varchar(64),
        in p_observaciones varchar(128))
			begin
				update RecetasMedicamentos RM
					set
						RM.idReceta = p_idReceta,
						RM.idMedicamento = p_idMedicamento,
						RM.cantidad = p_cantidad,
						RM.dosis = p_dosis,
						RM.frecuencia = p_frecuencia,
						RM.observaciones = p_observaciones
					where RM.idRecetaMedicamento = p_idRecetaMedicamento;
			end//
delimiter ;

delimiter //
	create procedure sp_eliminarRecetasMedicamentos(in p_idRecetaMedicamento int)
		begin
			delete
            from RecetasMedicamentos 
            where idRecetaMedicamento = p_idRecetaMedicamento;
        end//
delimiter ;

-- _____________________________________________Procedimientos Almacenados TratamientosMedicamentos____________________________________________________

delimiter //
	create procedure sp_listarTratamientosMedicamentos()
    begin
		select
			*
        from TratamientosMedicamentos TM;
        end//
delimiter ;

delimiter //
	create procedure sp_agregarTratamientosMedicamentos(
		in p_idTratamiento int,
        in p_idMedicamento int,
        in p_nombreTratamiento varchar(64),
        in p_descripcion varchar(128))
			begin
				insert into TratamientosMedicamentos(idTratamiento, idMedicamento, nombreTratamiento, descripcion)
                values(p_idTratamiento, p_idMedicamento, p_nombreTratamiento, p_descripcion);
            end//
delimiter ;

delimiter //
	create procedure sp_buscarTratamientosMedicamentos(in p_idTratamientoMedicamento int)
		begin
			select
            TM.idTratamientoMedicamento,
            TM.idTratamiento,
            TM.idMedicamento,
            TM.nombreTratamiento,
            TM.descripcion
            from TratamientosMedicamentos TM
            where  TM.idTratamientoMedicamento = p_idTratamientoMedicamento;
        end//
delimiter ;

delimiter //
	create procedure sp_actualizarTratamientosMedicamentos(
		in p_idTratamientoMedicamento int,
        in p_idTratamiento int,
        in p_idMedicamento int,
        in p_nombreTratamiento varchar(64),
        in p_descripcion varchar(128))
			begin
				update TratamientosMedicamentos TM
					set
						TM.idTratamiento = p_idTratamiento,
						TM.idMedicamento = p_idMedicamento,
						TM.nombreTratamiento = p_nombreTratamiento,
						TM.descripcion = p_descripcion
					where TM.idTratamientoMedicamento = p_idTratamientoMedicamento;
            end//
delimiter ;

delimiter //
	create procedure sp_eliminarTratamientosMedicamentos(in p_idTratamientoMedicamento int)
		begin
			delete 
            from TratamientosMedicamentos
            where idTratamientoMedicamento = p_idTratamientoMedicamento;
        end//
delimiter ;

DELIMITER $$
CREATE PROCEDURE sp_cambiarEstadoClienteDueño(IN idClienteDueño INT)
BEGIN
    DECLARE current_estado VARCHAR(50);
    SELECT estado INTO current_estado FROM Clientes WHERE idClienteDueño = cliId;

    IF current_estado = 'Activo' THEN
        UPDATE ClienteDueño SET estado = 'Inactivo' WHERE idClienteDueño = cliId;
    ELSE
        -- Opcional: si quieres que 'Inactivo' pueda volver a 'Activo'
        UPDATE ClienteDueño SET estado = 'Activo' WHERE idClienteDueño = cliId;
    END IF;
END$$
DELIMITER ;
-- _____________________________________________Procedimientos Almacenados seguros____________________________________________________

delimiter //
create procedure sp_agregarSeguro(
    in p_nombreseguro varchar(64),
    in p_tiposeguro varchar(64),
    in p_cobertura text,
    in p_fechainicio date,
    in p_fechafin date,
    in p_costo decimal(10,2),
    in p_idclientedueño int
)
begin
    insert into seguros (nombreseguro, tiposeguro, cobertura, fechainicio, fechafin, costo, idclientedueño)
    values (p_nombreseguro, p_tiposeguro, p_cobertura, p_fechainicio, p_fechafin, p_costo, p_idclientedueño);
end //
delimiter ;

delimiter //
create procedure sp_listarSeguros()
begin
    select * from seguros;
end //
delimiter ;

delimiter //
create procedure sp_buscarSeguro(in p_idseguro int)
begin
    select * from seguros 
    where idseguro = p_idseguro;
end //
delimiter ;

delimiter //
create procedure sp_actualizarSeguro(
    in p_idseguro int,
    in p_nombreseguro varchar(64),
    in p_tiposeguro varchar(64),
    in p_cobertura text,
    in p_fechainicio date,
    in p_fechafin date,
    in p_costo decimal(10,2),
    in p_idclientedueño int
)
begin
    update seguros
    set nombreseguro = p_nombreseguro,
        tiposeguro = p_tiposeguro,
        cobertura = p_cobertura,
        fechainicio = p_fechainicio,
        fechafin = p_fechafin,
        costo = p_costo,
        idclientedueño = p_idclientedueño
    where idseguro = p_idseguro;
end //
delimiter ;


delimiter //
create procedure sp_eliminarsSeguro(in p_idseguro int)
begin
    update seguros
    set estado = 'inactivo'
    where idseguro = p_idseguro;
end //
delimiter ;

-- ClientesDueños
call sp_agregarClienteDueños('Carlos', 'Ramírez', '55551234', 'carlos@email.com', '1234567890', 'Activo');
call sp_agregarClienteDueños('mmm', 'rrr', '55551234', 'carlos@email.com', '1234567890', 'Activo');
call sp_agregarClienteDueños('aaa', 'sss', '55551234', 'carlos@email.com', '1234567890', 'Activo');
call sp_agregarClienteDueños('ddd', 'aaa', '55551234', 'carlos@email.com', '1234567890', 'inactivo');
-- call sp_eliminarClienteDueño(3);
call sp_listarClientesDueños();
call sp_actualizarClientesDueños(2,'javier', 'gomez', '55551234', 'carlos@email.com', '1234567890', 'inactivo');
-- call sp_cambiarEstadoClienteDueño();

-- Medicamentos
call sp_agregarMedicamentos('Antibiótico X', 'Tratar infecciones bacterianas', 'Uso veterinario únicamente', '2026-01-01');
call sp_agregarMedicamentos('sd X', 'Tratar infecciones bacterianas', 'Uso veterinario ', '2026-01-01');
call sp_agregarMedicamentos('adsx X', 'Tratar infecciones bacterianas', 'Uso  únicamente', '2026-01-01');
call sp_agregarMedicamentos('adsx X', 'Tratar infecciones bacterianas', 'Uso  únicamente', '2026-01-01');
-- call sp_eliminarMedicamentos(3);
call sp_listarMedicamentos();
call sp_actualizarMedicamentos(2,'fuflin ', 'Tratar infecciones bacterianas', 'Uso veterinario únicamente', '2026-01-01');


-- Proveedores
call sp_agregarProveedores('Distribuidora Animal Farma', '9876543210', '55443322', 'contacto@animalfarma.com');
call sp_agregarProveedores(' Animal Farma', '9876543210', '55443322', 'contacto@animalfarma.com');
call sp_agregarProveedores(' Animal ', '9876543210', '55443322', 'contacto@animalfarma.com');
call sp_agregarProveedores(' Animal ', '9876543210', '55443322', 'contacto@animalfarma.com');
call sp_agregarProveedores(' Animal ', '9876543210', '55443322', 'contacto@animalfarma.com');
-- call sp_eliminarProveedores(3);
call sp_actualizarProveedores(2,' bestia ', '9876543210', '55443322', 'contacto@animalfarma.com');
call sp_listarProveedores();

-- Empleados
call sp_agregarEmpleados('Laura', 'Gómez');
call sp_agregarEmpleados('dfg', 'Gómez');
call sp_agregarEmpleados('dfg', 'Gómez');
call sp_agregarEmpleados('dfg', 'Gómez');
call sp_agregarEmpleados('sdrdf', 'Gómez');
call sp_actualizarEmpleados(2,'buga', 'Gómez');
-- call sp_eliminarEmpleados(3);
call sp_listarEmpleados();

-- Agregar Mascotas
CALL sp_AgregarMascotas(1, 'Firulais', 'Macho', 3, 'Labrador', 'Ninguno');
CALL sp_AgregarMascotas(2, 'Mimi', 'Hembra', 2, 'Persa', 'Vacunación completa');
CALL sp_AgregarMascotas(3, 'Rocky', 'Macho', 5, 'Pitbull', 'Cirugía reciente');
CALL sp_AgregarMascotas(4, 'Luna', 'Hembra', 1, 'Chihuahua', 'Tratamiento antipulgas');
-- CALL sp_EliminarMascotas(3);

CALL sp_ActualizarMscotas(2, 2, 'Mimi', 'Hembra', 3, 'Persa', 'Desparasitación anual');
CALL sp_BuscarMascotas(2);
CALL sp_ListarMascotas();

call sp_AgreagrServicios('javier','hola como estas');
call sp_AgreagrServicios('pedro','hola como estas');
call sp_AgreagrServicios('juan','hola como estas');
call sp_AgreagrServicios('luis','hola como estas');
call sp_AgreagrServicios('diego','hola como estas');
-- call sp_ActualizarServicios(2,'diego','hola como estas');
-- call sp_EliminarServicios(3);
call sp_ListarServicios();

call sp_AgregarTratamientos(1,1,  'Tratamiento Antipulgas', 'Aplicación de antipulgas para perros pequeños');
call sp_AgregarTratamientos(2,2, 'Desparasitación Interna', 'Desparasitación para cachorros con medicamento oral');
call sp_AgregarTratamientos(3,2,  'Cuidado de Heridas', 'Limpieza y aplicación de antibióticos tópicos');
call sp_AgregarTratamientos(4,4,  'Cuidado de Heridas', 'Limpieza y aplicación de antibióticos tópicos');

-- call sp_ElimarTratamientos(3);
call sp_ActualizarTratamientos(3,2, 2, 'Desparasitación ', 'Desparasitación para cachorros con medicamento oral');
call sp_ListarTratamiento();

call sp_AgregarVeterinarios('javier','Gomez',1,10.50);
call sp_AgregarVeterinarios('pedro','Gomez',2,100);
call sp_AgregarVeterinarios('luis','Gomez',3,500);
call sp_AgregarVeterinarios('juan','Gomez',4,824);
call sp_AgregarVeterinarios('lusio','Gomez',4,94);

call sp_ActualizarVeterinarios(5,'jorge','Gomez',4,9478);
-- call sp_EliminarVeterinarios(3);
call sp_ListarVeterinarios();

call sp_agregarConsultas (1,1,1,1,1,'2025-05-16');
call sp_agregarConsultas (2,2,2,2,2,'2025-05-16');
call sp_agregarConsultas (3,3,3,3,3,'2025-05-16');
call sp_agregarConsultas (4,4,4,4,4,'2025-05-16');
call sp_editarConsultas(4,4,4,4,4,4,'2025-05-16');
call sp_eliminarConsultas (3);
call sp_listarConsultas();

call sp_agregarReceta(1,1,1);
call sp_agregarReceta(2,2,2);
call sp_agregarReceta(3,3,3);
call sp_agregarReceta(4,4,4);

-- call sp_editarReceta(4,4,4,4);
-- call sp_eliminarReceta(3);
call sp_listarRecetas();

call sp_agregarCompra(1,'2025-05-16',1,1,1,54756);
call sp_agregarCompra(2,'2025-05-16',2,2,2,4556);
call sp_agregarCompra(3,'2025-05-16',3,3,3,54441);
call sp_agregarCompra(4,'2025-05-16',4,4,4,785);

call sp_editarCompra(4,4,'2025-05-16',4,4,4,10);
-- call sp_eliminarCompra(3);
call sp_listarCompras();

call sp_agregarVacunacion(1,1,1);
call sp_agregarVacunacion(2,2,2);
call sp_agregarVacunacion(3,3,3);
call sp_agregarVacunacion(4,4,4);

call sp_editarVacunacion(4,4,4,4);
-- call sp_eliminarVacunacion(3);
call sp_listarVacunaciones();

call sp_agregarCitas(1,'2025-05-16',1,1);
call sp_agregarCitas(2,'2025-05-16',2,2);
call sp_agregarCitas(3,'2025-05-16',3,3);
call sp_agregarCitas(4,'2025-05-16',4,4);

call sp_actualizarCitas(4,4,'2025-05-16',4,4);
-- call sp_eliminarCitas(3);
call sp_listarCitas();

call sp_agregarFacturas(1,1,1,50,587);
call sp_agregarFacturas(2,2,2,55,755);
call sp_agregarFacturas(3,3,3,98,323);
call sp_agregarFacturas(4,4,4,56,835);

-- call sp_actualizarFacturas(3,3,3,65,98560);
-- call sp_eliminarFacturas(3);
call sp_listarFacturas();


call sp_agregarFacturasCompras(1,1,54,9746);
call sp_agregarFacturasCompras(2,2,56,5615);
call sp_agregarFacturasCompras(3,3,64,5656);
call sp_agregarFacturasCompras(4,4,6,654);

call sp_actualizarFacturasCompras(3,3,3,65,32);
-- call sp_eliminarFacturasCompras(3);
call sp_listarFacturasCompras();

call sp_agregarRecetasMedicamentos(1,1,14,'ds','dSG','dffs');
call sp_agregarRecetasMedicamentos(2,2,45,'ds','dSG','dffs');
call sp_agregarRecetasMedicamentos(3,3,45,'ds','dSG','dffs');
call sp_agregarRecetasMedicamentos(4,4,78,'ds','dSG','dffs');

call sp_actualizarRecetasMedicamentos(4,4,4,56,'ds','dSG','dffs');
-- call sp_eliminarRecetasMedicamentos(3);
call sp_listarRecetasMedicamentos();
 
call sp_agregarTratamientosMedicamentos(1,1,'gg','kaht');
call sp_agregarTratamientosMedicamentos(2,2,'gg','kaht');
call sp_agregarTratamientosMedicamentos(3,3,'gg','kaht');
call sp_agregarTratamientosMedicamentos(4,4,'gg','kaht');

call sp_actualizarTratamientosMedicamentos(4,4,4,'gg','kaht');
-- call sp_eliminarTratamientosMedicamentos(3);
call sp_listarTratamientosMedicamentos();


call sp_agregarSeguro('Seguro de Vida', 'Vida', 'Cobertura en caso de muerte o invalidez permanente', '2025-01-01', '2025-12-31', 1200.50, 1);
call sp_agregarSeguro('Seguro de Vida', 'Vida', 'Cobertura total en caso de emergencia', '2025-03-01', '2026-03-01', 800.00, 2);
call sp_agregarSeguro('Seguro de Vida', 'Vida', 'Cobertura total en caso de emergencia', '2025-06-01', '2025-12-01', 500.00, 3);
call sp_agregarSeguro('Seguro de Vida', 'Vida', 'Cobertura médica integral', '2025-05-01', '2026-05-01', 1500.75, 4);
call sp_actualizarSeguro(1, 'Seguro de Vida', 'Vida', 'Cobertura mejorada en caso de accidente o muerte', '2025-01-01', '2025-12-31', 1300.00, 1);
-- call sp_eliminarsSeguro(1);
call sp_listarSeguros();
