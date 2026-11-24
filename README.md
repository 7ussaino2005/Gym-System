# Gym System

A university coursework project for managing gym memberships, employees, and personal trainers. The Java Swing desktop interface supports member and employee records, trainer assignments, and marketing reports. Data is persisted locally using Java object serialization.

## Run locally

Clone or download this repository. From its root, run the following on a machine with a JDK and a graphical desktop:

```sh
mkdir -p out
javac -d out src/gymsystem/*.java
java -cp out gymsystem.GymSystemMain
```

The original NetBeans/Ant project is also included. Open the repository folder in Apache NetBeans with JDK 20 configured and use **Run Project**.

The application starts with an empty database. Add fictional members and employees through the dashboard to try it out. Local data and generated reports are ignored by Git.

## Demo

This is a Java Swing desktop app. Run it locally to interact with the dashboard.
