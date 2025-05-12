# Maven JavaFX Project Structure

This document describes the standardized folder structure of a Maven project using JavaFX. It serves as a guideline for all developers to understand where files are located and where new files should be placed.

---

## Project Structure

```text
my-javafx-app/
├── pom.xml
└── src/
    ├── main/
    │   ├── java/
    │   │   └── de/example/           # Java packages and source code
    │   │       └── Main.java         # Entry point of the application
    │   ├── resources/                # Static resources (FXML, images, properties)
    │   │   └── layout.fxml
    │   └── filters/                  # (Optional) Build filter resources
    └── test/
        ├── java/                     # Unit and integration tests
        │   └── de/example/
        │       └── MainTest.java
        ├── resources/                # Test resources
        └── filters/                  # (Optional) Test filter resources

target/                               # Automatically created by Maven during build
```

---

## Key Files and Directories

### `pom.xml`
- Central configuration file of the Maven project.
- Contains dependencies, plugins, project information, and build configuration.

### `src/main/java/`
- Contains the **production source code**.
- Java files follow the structure of their package names.
- The entry point (`Main.java`) must be located here.

### `src/main/resources/`
- Contains **static resources** needed at runtime, such as:
  - `.fxml` files for JavaFX layout
  - `.properties` files for configuration
  - Images, icons, stylesheets

### `src/main/filters/`
- Optional directory for resources with placeholders (e.g. `${version}`).
- These are replaced during the build process.

### `src/test/java/`
- Contains **JUnit tests** and other test classes.
- Follows the same structure as `main/java/`.

### `src/test/resources/`
- Resources used specifically for testing, such as configurations or example data.

### `src/test/filters/`
- Optional directory for filtered test resources.

### `target/`
- Automatically generated directory created during Maven builds.
- Contains `.class` files, generated `.jar` files, etc.
- Should **not** be included in version control.