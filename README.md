# The Corrupted (hellgauntletboss) - NeoForge 1.21.1

Open this folder in IntelliJ / VS Code as a Gradle project (ModDevGradle) or run `gradle build` with Gradle 8.10+ and JDK 21.
`gradle wrapper` creates the wrapper if you want one. Adjust `neo_version` in `gradle.properties` to the NeoForge 21.1.x you use.

Textures were generated with Pillow (see `tools/ (run `python3 paint.py` then `python3 misc.py` inside tools/; needs Pillow + numpy)`); the model quads in
`client/CorruptedMesh.java` are baked from `tools/prototype_the_corrupted.bbmodel`.

Spawn: `/summon hellgauntletboss:the_corrupted` or the spawn egg in the "The Corrupted" creative tab.
