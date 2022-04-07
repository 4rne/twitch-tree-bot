FROM openjdk:16

COPY lib/ /app/lib
COPY  src/ /app/src

RUN javac -cp /app/lib/pircbot.jar:/app/lib/snakeyaml-1.30.jar /app/src/com/bot/tree/*.java

CMD ["java", "-cp", "/app/lib/pircbot.jar:/app/lib/snakeyaml-1.30.jar:/app/src/:/app/", "com.bot.tree.Bot"]
