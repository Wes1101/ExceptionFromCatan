# Installation and Setup Guide for the Existing JavaFX Project

This document explains how to clone the existing JavaFX Maven project from the GitHub repository and run it in IntelliJ IDEA using Java 21 and JavaFX 21.0.7 LTS.

---

## 1. Prerequisites

Make sure the following software is installed on your computer:

* **Java 21 JDK**
    * Download and installation guide: [https://www.oracle.com/de/java/technologies/downloads/#java21](https://www.oracle.com/de/java/technologies/downloads/#java21)

* **JavaFX 21.0.7 LTS** (only required for manual configuration)
    * Download (Gluon): [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)

* **Apache Maven**
    * Download and installation guide: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
    * Add the `bin` folders of Maven and JDK to your system's `PATH` environment variable.

---

## 2. Clone the Repository

1. Open a terminal or Git Bash.
2. Navigate to the directory where you want to work:

   ```bash
   cd /path/to/working-directory
   ```

3. Clone the GitHub repository:

   ```bash
   git clone https://github.com/Wes1101/ExecptionFromCatan.git
   ```

4. Change to the project directory:

   ```bash
   cd ExecptionFromCatan
   ```

---

## 3. Open the Project in IntelliJ IDEA and Configure the JDK

1. Start IntelliJ IDEA.
2. Go to **File → Open...** and select the cloned project directory (`JavaFX-Projekt`).
3. IntelliJ will recognize it as a Maven project and automatically load the `pom.xml`.
4. Open **File → Project Structure...** (`Ctrl+Alt+Shift+S`).
5. Under **Platform Settings → SDKs**, click the plus icon (`+`) and choose **JDK**.
6. Select the installation folder of your Java 21 JDK (e.g., `C:\Program Files\Java\jdk-21` or `/usr/lib/jvm/jdk-21`).
7. Under **Project Settings → Project**, set the **Project SDK** to your newly added JDK.
8. Optional: Under **Modules**, make sure the correct SDK is selected for each module.

---

## 4. Integrate JavaFX into the Project

### a) Recommended: via Maven (assuming `pom.xml` already includes dependencies)

The project uses Maven and should already include the following dependencies in the `pom.xml`:

```xml
<properties>
  <java.version>21</java.version>
  <javafx.version>21.0.7</javafx.version>
</properties>

<dependencies>
  <dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-controls</artifactId>
    <version>${javafx.version}</version>
  </dependency>
  <dependency>
    <groupId>org.openjfx</groupId>
    <artifactId>javafx-fxml</artifactId>
    <version>${javafx.version}</version>
  </dependency>
</dependencies>

<build>
  <plugins>
    <plugin>
      <groupId>org.openjfx</groupId>
      <artifactId>javafx-maven-plugin</artifactId>
      <version>0.0.8</version>
      <configuration>
        <mainClass>de.beispiel.MainApp</mainClass>
      </configuration>
    </plugin>
  </plugins>
</build>
```

IntelliJ will automatically reload the dependencies once you save the `pom.xml`.

### b) Alternatively: manual configuration

If you do not want to use Maven to include JavaFX, add it as a Global Library:

1. Open **File → Project Structure...**.
2. Under **Platform Settings → Global Libraries**, click the plus icon and choose **Java**.
3. Navigate to the JavaFX SDK folder (`.../javafx-sdk-21.0.7/lib`) and select all `.jar` files.
4. Under **Project Settings → Modules → Dependencies**, add the new library.
5. In the Run/Debug configurations, set the following in **VM options**:

   ```text
   --module-path "<PATH>/javafx-sdk-21.0.7/lib" --add-modules javafx.controls,javafx.fxml
   ```

---

## 5. Build the Project and Run the Application

In the terminal (or IntelliJ terminal), navigate to the project directory and run the following commands:

1. **Build and run tests**

   ```bash
   mvn clean install
   ```

2. **Start the application**

   ```bash
   mvn javafx:run
   ```

If configured correctly, Maven will compile the project, download all dependencies, and launch your JavaFX application.

---
