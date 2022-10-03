
CREATE TABLE public.customer (
	id int4 NULL,
	phone varchar NULL,
	address varchar NULL,
	created_date timestamp NULL DEFAULT now()
);