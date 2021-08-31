# Lakkie Config Engine

This is a versatile configuration engine which allows deserialization, serialization of LConfig files (extension is .cfg).

To see an example config, look in: `src/main/resources/ExampleConfig.cfg`

## Applications

Because LConfig files can read successfully with or without whitespace they can follow most formats. Applications for LConfig can be in command line applications, servers, games, etc.

## Implementations

Currently, the only implementation is in Scala.

## LConfig Extension - LConfigX (`*.cfgx`)

LConfig extension files allow for variables, function calls, etc. These function calls can be grouped into packages and are called using function pointers, or in the case of certain implementations of LConfigX, accessed using annotations and reflection.

Example usage can be found in `src/main/resources/ExampleConfig.cfgx`