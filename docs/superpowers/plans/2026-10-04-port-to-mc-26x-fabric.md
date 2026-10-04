# Port miku-plushie to MC 26.x (Fabric) Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build the miku-plushie Fabric mod against all stable MC 26.x releases (26.1, 26.1.1, 26.1.2, 26.2, 26.3) on Mojang official mappings, with minimum dependencies.

**Architecture:** Three staged phases. Phase 0 swaps yarn→mojmap at 1.21.1 (isolate the rename). Phase 1 bumps to 26.3 single-target (isolate version/API drift). Phase 2 adds Stonecutter to fan out to the full 26.x branch. Each phase ends with a green `gradlew build` + in-game smoke test.

**Tech Stack:** Fabric Loom 1.18.2, fabric-loader 0.19.5, fabric-api (per-version), Mojang official mappings, GeckoLib 5.5.x (modrinth maven), Stonecutter, Java 21.

**Spec:** `docs/superpowers/specs/2026-10-04-port-to-mc-26x-fabric-design.md`

## Global Constraints

- Fabric only. No Forge/NeoForge.
- Mappings: `loom.officialMojangMappings()` — no yarn dependency.
- Minimum deps: drop `net.fabricmc:yarn` and `com.eliotlash.mclib:mclib:20`. Runtime deps = fabric-loader + fabric-api + GeckoLib only.
- Freshest versions: loom `1.18.2`, loader `0.19.5`, fabric-api/GeckoLib latest per MC version (table below).
- GeckoLib via modrinth maven (`https://api.modrinth.com/maven` → `maven.modrinth:geckolib:<ver>`). Remove the dead cloudsmith geckolib3 repo.
- No behavior/content/feature changes — this is a port.
- No test framework introduced. Verification = `gradlew build` green + in-game smoke test.
- Java release target 21 unless the 26.x toolchain demands higher (verify Task 3).

### Per-version matrix (verified live 2026-10-04)

| MC | fabric-api | GeckoLib (fabric) |
|----|------------|-------------------|
| 26.1   | `0.145.1+26.1`   | `5.5` |
| 26.1.1 | `0.145.4+26.1.1` | verify at build |
| 26.1.2 | `0.155.3+26.1.2` | `5.5.2` |
| 26.2   | `0.161.0+26.2`   | `5.5.6` |
| 26.3   | `0.161.0+26.3`   | `5.5.7` |

## Review Focus

- **Untyped mapping refs** (mixin `@At`/method-descriptor strings, accesswidener entries) — `migrateMappings` only rewrites typed source; these stay yarn-named and fail silently at runtime, not compile time. Task 2 converts and the smoke test (mixin-dependent behavior fires) pins them.
- **GeckoLib 5.x API shape change** beyond simple renames (animation controller / renderer registration) — Task 4 smoke test must confirm an entity actually animates, not just that it compiles.
- **Datagen output drift** — regenerated JSON under `src/main/generated` must be committed or recipes/tags/worldgen silently go missing in-game. Task 5 owns this.
- **Cross-version API divergence 26.1↔26.3** — only surfaces at Phase 2 compile; Task 7 adds guards and must build the lowest (26.1) and highest (26.3) targets.
- **Java version floor** — if 26.x requires >21, build fails obscurely; Task 3 verifies before the fix loop.

---

### Task 1: Phase 0 — swap to mojmap at 1.21.1 + auto-migrate source

**Files:**
- Modify: `gradle.properties` (remove `yarn_mappings`)
- Modify: `build.gradle` (mappings block, remove mclib + cloudsmith repo not yet — keep geckolib working at 1.21.1), `loom_version`
- Modify: all `src/main/java/**` touched by `migrateMappings`

**Interfaces:**
- Produces: a 1.21.1 build on mojmap. Later phases assume source is mojmap-named.

- [ ] **Step 1: Replace mappings in `build.gradle`** — change `mappings "net.fabricmc:yarn:${project.yarn_mappings}:v2"` to `mappings loom.officialMojangMappings()`. Remove the `yarn_mappings` line from `gradle.properties`.
- [ ] **Step 2: Remove the `mclib` dependency line** from `build.gradle` (`implementation("com.eliotlash.mclib:mclib:20")`). Leave GeckoLib + cloudsmith repo untouched for now (still on 1.21.1).
- [ ] **Step 3: Run `./gradlew migrateMappings`** to auto-rewrite typed source yarn→mojmap at 1.21.1.
- [ ] **Step 4: Move migrated sources into place** if Loom wrote them to `remappedSrc/` — replace `src/main/java` with the migrated tree per the task's console output.
- [ ] **Step 5: Run `./gradlew compileJava`** Expected: compiles, OR fails only on untyped refs handled in Task 2 (note them).
- [ ] **Step 6: Commit** `git add -A && git commit -m "refactor: swap yarn->mojmap at 1.21.1, drop mclib"`

### Task 2: Phase 0 — convert untyped refs (accesswidener + mixins), build green

**Files:**
- Modify: `src/main/resources/miku-plushie.accesswidener`
- Modify: `src/main/java/com/any/mikuplushie/mixin/LivingEntityMixin.java`, `.../PlayerMixin.java`

**Interfaces:**
- Consumes: mojmap source from Task 1.
- Produces: fully green 1.21.1 mojmap build — baseline for Phase 1.

- [ ] **Step 1: Convert accesswidener entries** — for each line, look up the yarn name in Linkie (`linkie.shedaniel.dev`, Yarn→Mojang, MC 1.21.1) and replace class/method/field names + descriptors with mojmap equivalents.
- [ ] **Step 2: Convert mixin targets** — in both mixins, translate `@At`/`@Inject` `method =` descriptors and any referenced field/method names to mojmap via Linkie.
- [ ] **Step 3: Run `./gradlew build`** Expected: BUILD SUCCESSFUL on 1.21.1.
- [ ] **Step 4: Smoke test** — launch `./gradlew runClient`, spawn a plush entity, confirm it renders + animates, mixin behavior (LivingEntity/Player hooks) fires, blocks/items/commands present.
- [ ] **Step 5: Commit** `git add -A && git commit -m "refactor: convert accesswidener + mixins to mojmap"`

### Task 3: Phase 1 — bump toolchain + metadata to 26.3

**Files:**
- Modify: `gradle.properties`, `build.gradle`, `src/main/resources/fabric.mod.json`

**Interfaces:**
- Produces: project configured for 26.3; compile errors in Task 4 are now genuine API drift.

- [ ] **Step 1: Bump `gradle.properties`** — `minecraft_version=26.3`, `loader_version=0.19.5`, `loom_version=1.18.2`, `fabric_version=0.161.0+26.3`, `geckolib_version=5.5.7`, `mod_version=2.0.1-26.3` (or equivalent).
- [ ] **Step 2: Update `build.gradle`** — remove the cloudsmith GeckoLib repo block; add modrinth maven (`https://api.modrinth.com/maven`); change GeckoLib dep to `modImplementation "maven.modrinth:geckolib:${geckolib_version}"`.
- [ ] **Step 3: Update `fabric.mod.json` `depends`** — `minecraft`: `~26.3`, `fabricloader`: `>=0.19.5`, `geckolib`: `>=5.5.7`, `java`: match verified floor.
- [ ] **Step 4: Verify Java floor** — run `./gradlew compileJava` once; if it reports a required Java > 21, raise `options.release`/`sourceCompatibility`/`targetCompatibility` in `build.gradle` and the `java` field in `fabric.mod.json`. Otherwise leave at 21.
- [ ] **Step 5: Commit** `git add -A && git commit -m "build: bump toolchain + metadata to MC 26.3"`

### Task 4: Phase 1 — compile-driven fix loop to 26.3

**Files:**
- Modify: any `src/main/java/**` the compiler flags; expected hotspots `entity/client/model/AbstractPlushModel.java`, `entity/client/render/AbstractPlushRender.java`, registry classes under `registry/`, `worldgen/**`.

**Interfaces:**
- Consumes: 26.3-configured project (Task 3).
- Produces: compiling + loading 26.3 mod.

- [ ] **Step 1: Run `./gradlew compileJava`** and fix the first batch of errors — GeckoLib 5.x renderer/model base-class changes, registry/attribute/render-layer signature changes, worldgen `ConfiguredFeature`/`PlacedFeature` API. Use Linkie for any remaining mojmap name shifts 1.21.1→26.3.
- [ ] **Step 2: Repeat compile→fix** until `./gradlew compileJava` is clean.
- [ ] **Step 3: Run `./gradlew build`** Expected: BUILD SUCCESSFUL.
- [ ] **Step 4: Smoke test** `./gradlew runClient` — world loads; plush entity spawns, renders, **animates** (confirms GeckoLib 5.x wiring, not just compile); block/item/worldgen/commands/villager-trades present; mixins fire.
- [ ] **Step 5: Commit** `git add -A && git commit -m "fix: port source to MC 26.3 API (mojmap + GeckoLib 5.x)"`

### Task 5: Phase 1 — regenerate datagen for 26.3

**Files:**
- Modify: `src/main/generated/**`

**Interfaces:**
- Consumes: compiling 26.3 mod (Task 4).
- Produces: committed regenerated data for 26.3.

- [ ] **Step 1: Run `./gradlew runDatagen`** (Fabric datagen entrypoint `MikuPlushieDataGenerator`).
- [ ] **Step 2: Diff `src/main/generated`** — confirm recipes/tags/worldgen/loot regenerated without unexpected loss.
- [ ] **Step 3: Run `./gradlew build`** Expected: SUCCESSFUL with regenerated data.
- [ ] **Step 4: Commit** `git add -A && git commit -m "chore: regenerate datagen for MC 26.3"`

### Task 6: Phase 2 — add Stonecutter + declare the 26.x branch

**Files:**
- Modify: `settings.gradle` (Stonecutter plugin + version declarations), `build.gradle` (per-version properties), `gradle.properties` (move per-version values), `stonecutter.gradle` (if required by the plugin layout)

**Interfaces:**
- Produces: a Stonecutter-managed project with 5 declared targets; 26.3 remains the active/default build.

- [ ] **Step 1: Apply Stonecutter** — add the plugin to `settings.gradle` and declare versions `26.1, 26.1.1, 26.1.2, 26.2, 26.3` per Stonecutter docs; set 26.3 as the active target.
- [ ] **Step 2: Parameterize deps** — move `fabric_version` and `geckolib_version` into Stonecutter per-version properties using the matrix in Global Constraints (resolve 26.1.1 GeckoLib at build).
- [ ] **Step 3: Run `./gradlew build`** on the active 26.3 target Expected: SUCCESSFUL (no regression from Task 5).
- [ ] **Step 4: Commit** `git add -A && git commit -m "build: add Stonecutter with 26.x version targets"`

### Task 7: Phase 2 — resolve cross-version drift + build all 5

**Files:**
- Modify: any `src/main/java/**` where 26.1↔26.3 APIs diverge (conditional comments only)

**Interfaces:**
- Consumes: Stonecutter project (Task 6).
- Produces: 5 green jars across the 26.x branch.

- [ ] **Step 1: Run `./gradlew chiseledBuild`** (all targets) and note per-version compile failures.
- [ ] **Step 2: Add `//? if >=<ver> {` guards** only at the exact divergent call sites — nowhere else. Re-run until all targets compile.
- [ ] **Step 3: Run `./gradlew chiseledBuild`** Expected: 5 jars built.
- [ ] **Step 4: Smoke test** lowest (26.1) and highest (26.3) jars — entity animates, core content present, mixins fire.
- [ ] **Step 5: Commit** `git add -A && git commit -m "feat: multiversion 26.x via Stonecutter conditional guards"`
