# The Corrupted (hellgauntletboss) - NeoForge 1.21.1

Open this folder in IntelliJ / VS Code as a Gradle project (ModDevGradle) or run `gradle build` with Gradle 8.10+ and JDK 21.
`gradle wrapper` creates the wrapper if you want one. Adjust `neo_version` in `gradle.properties` to the NeoForge 21.1.x you use.

Textures were generated with Pillow (see `tools/ (run `python3 paint.py` then `python3 misc.py` inside tools/; needs Pillow + numpy)`); the model quads in
`client/CorruptedMesh.java` are baked from `tools/prototype_the_corrupted.bbmodel`.

Spawn: `/summon hellgauntletboss:the_corrupted` or the spawn egg in the "The Corrupted" creative tab.

## Knight armor
Steel / Corrupted Knight armor is enchantable (enchantability 15 / 18; all 8 pieces are in the `minecraft:enchantable/*` item tags).
Worn pieces are drawn by `KnightArmorLayer` with the boss atlas (`the_corrupted_normal`, or `the_corrupted_awakened` + `the_corrupted_glow`);
the vanilla `*_layer_1/2.png` armor textures are 1x1 transparent on purpose (they only carry the glint). Regenerate the armor mesh with `tools/gen_armor.py`.
