DC_VER          := 1.27.4
OS              := $(shell uname)
ARCH            := x86_64
DC_ARCHIVE      := docker-compose-$(OS)-$(ARCH)
DC_URL          := https://github.com/docker/compose/releases/download/$(DC_VER)/$(DC_ARCHIVE)
DOCKER_COMPOSE  := ./docker-compose

default: build

# Base
init:
	@[ -e $(DOCKER_COMPOSE) ] || curl -L -o docker-compose $(DC_URL)
	@chmod +x $(DOCKER_COMPOSE)
	$(DOCKER_COMPOSE) version

test: init
	$(DOCKER_COMPOSE) -f docker-compose.it.yml up --detach
	sleep 20
	./gradlew test flywayMigrate integrationTest
	$(DOCKER_COMPOSE) -f docker-compose.it.yml down -v
