# Ejercicio práctico: Flujo de code review con fork y Pull Request

**Asignatura:** Infraestructuras y Procesos de Soporte · Curso 2025/2026  
**Tema:** 4.4. Flujo de trabajo colaborativo y code review  
**Duración estimada:** 30 minutos  

---

## Objetivo

Practicar el flujo completo de colaboración en GitHub mediante fork, code review y Pull Request, aplicando las técnicas vistas en clase: checklist de revisión, comentarios con prefijos, y ciclo de revisión.

## Roles

El ejercicio se realiza en **parejas**:

| Rol | Responsabilidad |
|---|---|
| **Propietario** (Estudiante A) | Crea el repositorio base con el proyecto Maven y revisa la PR del colaborador |
| **Colaborador** (Estudiante B) | Hace fork, implementa los cambios solicitados y propone una PR |

> **Nota:** Una vez completada la primera ronda, se intercambian los roles y se repite el ejercicio.

---

## Parte 1 — Preparación del repositorio (Propietario)

### Paso 1.1. Crear el repositorio en GitHub

1. Crear un repositorio público en GitHub con el nombre `code-review-exercise`
2. Añadir un fichero `README.md` con la descripción del proyecto
3. Clonar el repositorio localmente:

```bash
git clone https://github.com/<tu-usuario>/code-review-exercise.git
cd code-review-exercise
```

### Paso 1.2. Crear el proyecto Maven

Crear la siguiente estructura de proyecto Maven:

```
code-review-exercise/
├── pom.xml
├── README.md
└── src/
    ├── main/java/org/example/
    │   ├── Calculator.java
    │   └── MathUtils.java
    └── test/java/org/example/
        └── CalculatorTest.java
```

#### `pom.xml`

```xml
<?xml version="1.0" encoding="UTF-8"?>
<project xmlns="http://maven.apache.org/POM/4.0.0"
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:schemaLocation="http://maven.apache.org/POM/4.0.0
         http://maven.apache.org/xsd/maven-4.0.0.xsd">
    <modelVersion>4.0.0</modelVersion>

    <groupId>org.example</groupId>
    <artifactId>code-review-exercise</artifactId>
    <version>1.0-SNAPSHOT</version>
    <packaging>jar</packaging>

    <properties>
        <maven.compiler.source>21</maven.compiler.source>
        <maven.compiler.target>21</maven.compiler.target>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <dependencies>
        <dependency>
            <groupId>org.junit.jupiter</groupId>
            <artifactId>junit-jupiter</artifactId>
            <version>5.11.4</version>
            <scope>test</scope>
        </dependency>
    </dependencies>
</project>
```

#### `Calculator.java`

```java
package org.example;

/**
 * Basic calculator with arithmetic operations.
 */
public class Calculator {

    public int add(int a, int b) {
        return a + b;
    }

    public int subtract(int a, int b) {
        return a - b;
    }

    public int multiply(int a, int b) {
        return a * b;
    }

    public double divide(int a, int b) {
        return a / b;
    }
}
```

#### `MathUtils.java`

```java
package org.example;

/**
 * Auxiliary math utilities.
 */
public class MathUtils {

    public static boolean isEven(int number) {
        return number % 2 == 0;
    }

    public static boolean isPositive(int number) {
        return number > 0;
    }
}
```

#### `CalculatorTest.java`

```java
package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CalculatorTest {

    private Calculator calculator;

    @BeforeEach
    void setUp() {
        calculator = new Calculator();
    }

    @Test
    @DisplayName("Adding two positive numbers returns their sum")
    void addTwoPositiveNumbers() {
        assertEquals(5, calculator.add(2, 3));
    }

    @Test
    @DisplayName("Subtracting two numbers returns the difference")
    void subtractTwoNumbers() {
        assertEquals(1, calculator.subtract(3, 2));
    }
}
```

### Paso 1.3. Subir el proyecto al repositorio

```bash
git add .
git commit -m "feat: initial project with Calculator and MathUtils"
git push origin main
```

### Paso 1.4. Verificar que el proyecto compila

Antes de continuar, verificar que todo funciona:

```bash
mvn test
```

> Los dos tests existentes deben pasar correctamente.

---

## Parte 2 — Fork y desarrollo (Colaborador)

### Paso 2.1. Hacer fork del repositorio

1. Ir al repositorio del propietario en GitHub: `https://github.com/<propietario>/code-review-exercise`
2. Hacer clic en **Fork** (esquina superior derecha)
3. Clonar el fork localmente:

```bash
git clone https://github.com/<tu-usuario>/code-review-exercise.git
cd code-review-exercise
```

### Paso 2.2. Crear una rama de trabajo

```bash
git checkout -b feature/add-divide-validation-and-tests
```

### Paso 2.3. Implementar los cambios solicitados

El colaborador debe realizar las siguientes modificaciones:

#### a) Corregir el método `divide` en `Calculator.java`

El método `divide` tiene un problema: realiza una división entera y no maneja la división por cero. Corregirlo:

```java
public double divide(int a, int b) {
    if (b == 0) {
        throw new IllegalArgumentException("Divisor cannot be zero");
    }
    return (double) a / b;
}
```

#### b) Añadir un nuevo método `power` en `Calculator.java`

```java
public double power(int base, int exponent) {
    return Math.pow(base, exponent);
}
```

#### c) Añadir tests para los métodos `divide`, `multiply` y `power`

Añadir los siguientes tests en `CalculatorTest.java`:

```java
@Test
@DisplayName("Multiplying two numbers returns the product")
void multiplyTwoNumbers() {
    assertEquals(6, calculator.multiply(2, 3));
}

@Test
@DisplayName("Dividing two numbers returns the quotient")
void divideTwoNumbers() {
    assertEquals(2.5, calculator.divide(5, 2));
}

@Test
@DisplayName("Dividing by zero throws IllegalArgumentException")
void divideByZeroThrowsException() {
    assertThrows(IllegalArgumentException.class,
            () -> calculator.divide(5, 0));
}

@Test
@DisplayName("Raising a number to a power returns the correct result")
void powerReturnsCorrectResult() {
    assertEquals(8.0, calculator.power(2, 3));
}
```

### Paso 2.4. Verificar que los tests pasan

```bash
mvn test
```

> Todos los tests (6 en total) deben pasar.

### Paso 2.5. Hacer commit y push

```bash
git add .
git commit -m "feat: add divide validation, power method, and missing tests"
git push origin feature/add-divide-validation-and-tests
```

### Paso 2.6. Abrir un Pull Request

1. Ir al fork en GitHub
2. GitHub mostrará un banner para crear una PR; hacer clic en **Compare & pull request**
3. Rellenar la PR con la siguiente información:

**Título:**
```
feat: add divide validation, power method, and tests
```

**Descripción (usar como plantilla):**
```markdown
## Description

Fixes the `divide` method to handle division by zero and adds the
`power` method to `Calculator`. Includes unit tests for the `divide`,
`multiply`, and `power` methods.

## Changes

- Fixed `divide`: now throws `IllegalArgumentException` when divisor is 0
- Fixed `divide`: now returns `double` correctly (explicit cast)
- Added `power(int base, int exponent)` method
- Added 4 new unit tests

## How to test

```bash
mvn test
```

## Author checklist

- [x] Tests pass locally (`mvn test`)
- [x] Code follows project conventions
- [x] PR description explains the context of the change
```

4. Hacer clic en **Create pull request**

---

## Parte 3 — Revisión del Pull Request (Propietario)

### Paso 3.1. Leer la descripción del PR

Antes de mirar el código, revisar la descripción:

- [ ] ¿Se entiende qué hace el PR sin mirar el diff?
- [ ] ¿Incluye instrucciones para probarlo?
- [ ] ¿El título es claro y sigue una convención?

### Paso 3.2. Revisar el código con el checklist

Aplicar el checklist de revisión visto en clase sobre el diff del PR:

#### Corrección
- [ ] ¿El método `divide` lanza la excepción esperada al dividir por cero?
- [ ] ¿El cast a `double` está correctamente aplicado?
- [ ] ¿El método `power` funciona para exponentes negativos y cero?

#### Calidad
- [ ] ¿Los nombres de métodos y variables son descriptivos?
- [ ] ¿Hay lógica duplicada que podría extraerse?
- [ ] ¿El mensaje de la excepción es informativo?

#### Tests
- [ ] ¿Se testea el camino feliz de cada método?
- [ ] ¿Se testea el caso de error (división por cero)?
- [ ] ¿Faltan tests relevantes? (por ejemplo: exponente negativo, exponente cero)

#### Estilo
- [ ] ¿Los tests usan `@DisplayName` de forma consistente?
- [ ] ¿Los imports están organizados?

### Paso 3.3. Escribir comentarios en la PR

El propietario debe escribir al menos **tres comentarios** en la PR usando los prefijos vistos en clase:

**Ejemplos orientativos** (adaptar según lo que se encuentre):

```
blocker: missing tests for edge cases in the power method
         (negative exponent, zero exponent). Add them before
         merging.

suggestion: the exception message in divide could include
            the received values to make debugging easier:
            "Cannot divide " + a + " by zero"

nit: the @DisplayName of divideByZeroThrowsException could be
     more descriptive, e.g.: "Dividing by zero throws an
     exception"

question: has it been considered what happens with power(0, 0)?
          Mathematically it is indeterminate but Math.pow
          returns 1.0.
```

### Paso 3.4. Solicitar cambios

Si hay comentarios de tipo `blocker`, marcar la revisión como **Changes requested** en GitHub.

---

## Parte 4 — Respuesta y corrección (Colaborador)

### Paso 4.1. Leer y responder a los comentarios

Para cada comentario recibido:

- **Resolver** si se aplica el cambio → responder indicando el commit y marcar como resuelto
- **Explicar** si se mantiene la decisión original → responder con el razonamiento
- **Proponer alternativa** si hay un término medio

### Paso 4.2. Aplicar las correcciones solicitadas

Realizar los cambios en la misma rama y hacer push:

```bash
# Example: add tests for power with zero and negative exponent
git add .
git commit -m "test: add edge case tests for power method"
git push origin feature/add-divide-validation-and-tests
```

> La PR se actualiza automáticamente con los nuevos commits.

### Paso 4.3. Solicitar nueva revisión

Avisar al propietario (mediante un comentario en la PR) de que los cambios están listos para una nueva revisión.

---

## Parte 5 — Aprobación y merge (Propietario)

### Paso 5.1. Verificar las correcciones

1. Revisar los nuevos commits
2. Comprobar que los comentarios `blocker` han sido resueltos
3. Aprobar la PR marcando **Approve** en la revisión

### Paso 5.2. Hacer merge

1. Hacer clic en el botón de merge en la PR
2. Revisar el mensaje del commit resultante
3. Confirmar el merge

### Paso 5.3. Limpieza

Tras el merge, GitHub ofrecerá eliminar la rama. Hacer clic en **Delete branch**.

---

## Checklist final del ejercicio

### Para el propietario
- [ ] He creado el repositorio con el proyecto Maven completo
- [ ] He revisado la PR aplicando el checklist de la sesión
- [ ] He escrito al menos 3 comentarios usando los prefijos (`blocker`, `suggestion`, `nit`, `question`)
- [ ] He solicitado cambios o aprobado según correspondía
- [ ] He hecho merge de la PR tras la aprobación

### Para el colaborador
- [ ] He hecho fork del repositorio correctamente
- [ ] He creado una rama de feature con nombre descriptivo
- [ ] He implementado los cambios solicitados
- [ ] He verificado que los tests pasan antes de la PR
- [ ] He rellenado la descripción de la PR con contexto suficiente
- [ ] He respondido a todos los comentarios de la revisión
- [ ] He aplicado las correcciones solicitadas

---

## Preguntas de reflexión

Una vez completado el ejercicio, discutir en el grupo:

1. ¿La descripción de la PR era suficiente para entender el cambio sin leer el código?
2. ¿Qué blocker se ha encontrado que podría haber llegado a producción sin revisión?
3. ¿El tamaño de la PR era adecuado? ¿Cómo se dividiría si fuera más grande?
4. ¿Qué habría ocurrido si no existieran tests para la validación de la división por cero?
