FROM gradle:jdk17-alpine AS build

WORKDIR /appbuild
COPY . /appbuild

RUN gradle build

FROM openjdk:17

WORKDIR /app

COPY --from=build /appbuild/build/libs/TwitchChatBot-all.jar .

CMD ["java", "-jar", "TwitchChatBot-all.jar"]
