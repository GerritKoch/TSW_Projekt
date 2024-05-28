Feature: Java Version Kompatibilität
  Die Software muss als Software Bibliothek in Java 16 oder neuer umgesetzt werden.

  Scenario: Ensure the software library is compatible with Java 16 or newer
    Given the development environment is set up with Java 16 or newer
    When the software library is compiled
    Then the compilation should succeed without any version compatibility issues
    And the software library should be able to utilize features specific to Java 16 or newer
