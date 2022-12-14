INSERT INTO public.users(id_user, enabled, name_user, password, email_user, address_user, district_user, phone_number_user)
	VALUES (0, true, 'administrador', '$2a$10$rw2CpFnh1htrY1wmypdES.VgbKv/Lhh3dpKglTFi7exXji5gkElf.', 'administrador@gmail.com', 'administradoradministradoradministrador', 'San Miguel', '999999999');
	
INSERT INTO public.role(id, authority, user_id) VALUES (0, 'ROLE_ADMIN', 0);