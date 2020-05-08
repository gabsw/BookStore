# Instructions

## Up & Down

For starters, use `docker-compose up -d`. Then you will be able to continue using the command line, since everything will be running in detached mode.

If you want to stop containers and remove containers, networks, volumes, and images created by `docker-compose up -d` . This will be only done if the infrastructure needs an update.

## Building

To build, use `docker-compose up --build`. The build will force building the images.

## Start & Stop (Use this if you do not need to rebuild the containers)

To run, use `docker-compose start`. To stop, use `docker-compose stop`

## Application Dockerfile

The Dockerfile for the application is located in the root directory of the code (next to the project's pom.xml).

This is due to docker-compose not allowing the Dockerfile to be outside of the build context, despite docker allowing it.

## Connecting to the database

### OPTION 1

`psql postgres://user:user@172.19.0.2:5432/book_store`

You can find the correct IP address by running `docker inspect postgresql`. The IP address can be found
under "IPAddress". It's usually near the end.

### OPTION 2

Run `docker exec -it postgresql bash`.

Then, run `psql -d book_store -U user -W`.

You'll be prompted for a password. After that, you'll be connected to the database.

## Updating Postgresql Init Files

If you want to update the init sql files and you have already executed `docker-compose up --build`,
then you must delete the postgresql volume that holds the pgdata.

To do that:
1. Destroy the containers with `docker-compose rm`
2. Destroy the `postgres` volume with `docker volume rm postgres`

After that, you can simply run `docker-compose up` again.

Note that erasing the volume will remove all data.