# Installations- und Setup-Anleitung für das bestehende JavaFX-Projekt

Dieses Dokument beschreibt, wie Ihr das vorhandene JavaFX-Maven-Projekt aus dem GitHub-Repository klont und in IntelliJ IDEA mit Java 21 und JavaFX 21.0.7 LTS zum Laufen bringt.

---

## 1. Voraussetzungen

Stellt sicher, dass folgende Software auf Eurem Rechner installiert ist:

* **Java 21 JDK**

    * Download und Installationshinweis: [https://www.oracle.com/de/java/technologies/downloads/#java21](https://www.oracle.com/de/java/technologies/downloads/#java21)
    * 

* **JavaFX 21.0.7 LTS** (nur bei manueller Konfiguration erforderlich)

    * Download (Gluon): [https://gluonhq.com/products/javafx/](https://gluonhq.com/products/javafx/)


* **Apache Maven**

    * Download und Installationshinweis: [https://maven.apache.org/download.cgi](https://maven.apache.org/download.cgi)
    * Fügt die Ordner `bin` von Maven und JDK in Eure Umgebungsvariable `PATH` ein.

---

## 2. Repository klonen

1. Öffnet ein Terminal oder Git-Bash.
2. Wechselt in das Verzeichnis, in dem Ihr arbeiten möchtet:

   ```bash
   cd /pfad/zum/arbeitsverzeichnis
   ```
3. Klont das GitHub-Repository:

   ```bash
   git clone https://github.com/Wes1101/ExecptionFromCatan.git
   ```
4. Wechselt in das Projektverzeichnis:

   ```bash
   cd ExecptionFromCatan
   ```j

---

## 3. Projekt in IntelliJ IDEA öffnen und JDK einbinden

1. IntelliJ IDEA starten.
2. **File → Open...** und das geklonte Projekt-Verzeichnis auswählen (`JavaFX-Projekt`).
3. IntelliJ erkennt das Projekt als Maven-Projekt und lädt automatisch die `pom.xml`.
4. **File → Project Structure...** (`Ctrl+Alt+Shift+S`) öffnen.
5. Unter **Platform Settings → SDKs** auf das Pluszeichen (`+`) klicken und **JDK** wählen.
6. Den Installationsordner Eures Java 21 JDK auswählen (z. B. `C:\Program Files\Java\jdk-21` oder `/usr/lib/jvm/jdk-21`).
7. Unter **Project Settings → Project** als **Project SDK** Eure neu hinzugefügte JDK-Version auswählen.
8. Optional: Unter **Modules** sicherstellen, dass bei jedem Modul das korrekte SDK eingetragen ist.

---

## 4. JavaFX in das Projekt einbinden

### a) Empfohlen: via Maven (vorausgesetzt `pom.xml` enthält bereits Dependencies)

Das Projekt nutzt Maven und sollte in der `pom.xml` bereits folgende Abhängigkeiten haben:

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

IntelliJ lädt die Abhängigkeiten automatisch neu, sobald Ihr die `pom.xml` speichert.

### b) Alternativ: manuelle Konfiguration

Falls Ihr JavaFX nicht über Maven einbinden möchtet, fügt es als Global Library hinzu:

1. **File → Project Structure...** öffnen.
2. Unter **Platform Settings → Global Libraries** auf das Pluszeichen klicken und **Java** wählen.
3. In den JavaFX-SDK-Ordner (`.../javafx-sdk-21.0.7/lib`) wechseln und alle `.jar`-Dateien auswählen.
4. Unter **Project Settings → Modules → Dependencies** die neue Library hinzufügen.
5. In den Run/Debug-Konfigurationen bei **VM-Options** folgende Werte setzen:

   ```text
   --module-path "<PFAD>/javafx-sdk-21.0.7/lib" --add-modules javafx.controls,javafx.fxml
   ```

---

## 5. Projekt bauen und Anwendung starten

Wechselt im Terminal (oder in IntelliJ-Terminal) in das Projektverzeichnis und führt folgende Befehle aus:

1. **Build und Tests ausführen**

   ```bash
   mvn clean install
   ```
2. **Anwendung starten**

   ```bash
   mvn javafx:run
   ```

Bei korrekter Konfiguration kompiliert Maven das Projekt, lädt alle Abhängigkeiten und startet Eure JavaFX-Anwendung.

---
