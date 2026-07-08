.PHONY: build run tools-up tools-down generate

build:
	./mvnw clean install

run:
	./mvnw spring-boot:run

generate:
	./mvnw clean compile -Pgenerate-openapi-common,generate-jooq

tools-up:
	cd tools && docker-compose up -d

tools-down:
	cd tools && docker-compose down
