# Port miku-plushie to Minecraft 26.x (Fabric) — Design

## Intent

Port the `miku-plushie` Fabric mod from Minecraft 1.21.1 to the 26.x release
branch. Target **all stable 26.x releases**: `26.1`, `26.1.1`, `26.1.2`,
`26.2`, `26.3`. Fabric only. Behavior unchanged — this is a port, not a
feature change.

Secondary goal (explicit user constraint): **minimum dependencies, freshest
versions, no dep where none is needed.**

## Current state (source of port)

- MC `1.21.1`, Java 21, Fabric Loom `1.14-SNAPSHOT`
- Mappings: **yarn** (`net.fabricmc:yarn:1.21.1+build.3`)
- fabric-loader `0.18.4`, fabric-api `0.116.7+1.21.1`
- GeckoLib `4.8.2` via dead `cloudsmith/geckolib3` repo
- Legacy dep `com.eliotlash.mclib:mclib:20`
- ~50 Java source files: 12 GeckoLib-animated plush entities, blocks, items,
  worldgen, datagen, 2 mixins, particles, commands, villager trades
- `src/main/resources/miku-plushie.accesswidener` (yarn-named entries)
- `miku-plushie.mixins.json` (2 mixins: `LivingEntityMixin`, `PlayerMixin`)

## Two hard facts that shape the work

1. **No yarn mappings exist for any 26.x** (verified live:
   `meta.fabricmc.net/v2/versions/yarn/26.3` → `[]`, same for 26.1/26.1.2/26.2).
   The 26.x ecosystem uses **Mojang official mappings (mojmap)**. Porting
   therefore requires a mapping migration (yarn → mojmap) on top of the version
   bump. This renames every MC class/method/field across all source, mixins,
   and the accesswidener.

2. **Offline rename tables are not needed** — the rename is tool-driven:
   - Loom `migrateMappings` auto-rewrites typed source.
   - Linkie (`linkie.shedaniel.dev`) handles untyped stragglers (mixin `@At`
     target descriptors, accesswidener entries, reflection/string refs).
   - The compiler surfaces the rest (genuine 26.x API drift, not renames).

## Dependency decisions (minimize + freshest)

| Dep | Decision |
|-----|----------|
| yarn | **Drop** → `loom.officialMojangMappings()` (no dep) |
| `com.eliotlash.mclib:mclib:20` | **Drop** — legacy transitive of dead geckolib3 repo; not needed by GeckoLib 5.x |
| fabric-loader | Keep (required) — freshest `0.19.5` |
| fabric-api | Keep (required: datagen/registries/events) — freshest per MC version |
| GeckoLib | **Keep** — freshest `5.5.7`; via modrinth maven `maven.modrinth:geckolib:<ver>` |
| Stonecutter | Add — build-time plugin only, not shipped; needed for 5-version build |

**GeckoLib removal is explicitly out of scope** for this port. Removing it means
reauthoring all 12 plush entities as vanilla `EntityModel` + `AnimationDefinition`
— a large, behavior-affecting rewrite orthogonal to the port. If zero-extra-deps
is later desired, that is a separate project (its own spec), not part of this one.

### Confirmed toolchain / versions (verified live)

- loom `1.18.2`, loader `0.19.5`, mappings = mojmap

| MC | fabric-api (latest) | GeckoLib (fabric) |
|----|---------------------|-------------------|
| 26.1   | `0.145.1+26.1`   | `5.5` |
| 26.1.1 | `0.145.4+26.1.1` | verify at build (likely `5.5.x`) |
| 26.1.2 | `0.155.3+26.1.2` | `5.5.2` |
| 26.2   | `0.161.0+26.2`   | `5.5.6` |
| 26.3   | `0.161.0+26.3`   | `5.5.7` |

- Java version for 26.x: assumed 21 — **verify in Phase 1** against the actual
  requirement (may have risen). Adjust `options.release` / `sourceCompatibility`
  if the compiler/loom demands it.

## Approach: staged, then fan out

Decompose the risk. Do the mapping rename once, against a known-good version,
*before* multiplexing across 5 versions.

### Phase 0 — mojmap swap at 1.21.1 (no version change)

Isolate the rename from the version bump.

- Replace `mappings "net.fabricmc:yarn:..."` with `loom.officialMojangMappings()`;
  remove the yarn dependency and the `mclib` dependency.
- Run `gradlew migrateMappings` (yarn → mojmap at 1.21.1).
- Convert `miku-plushie.accesswidener` entries (yarn → mojmap names) via Linkie.
- Convert mixin `@At`/`@Inject` target descriptors in `LivingEntityMixin` and
  `PlayerMixin` via Linkie.
- **Exit criteria:** `gradlew build` green on 1.21.1 + mojmap, zero behavior
  change. In-game smoke test still passes.

### Phase 1 — bump to 26.3 single target

- Bump `gradle.properties`: `minecraft_version=26.3`, `loader_version=0.19.5`,
  `fabric_version=0.161.0+26.3`, `loom_version=1.18.2`, `geckolib_version=5.5.7`.
- Switch GeckoLib dependency to modrinth maven; remove the cloudsmith repo block.
- Update `fabric.mod.json` `depends`: `minecraft ~26.3` (or `>=26.3`), `geckolib`
  version, `java` version, `fabricloader >=0.19.5`.
- Verify Java version requirement; adjust toolchain if needed.
- Compile-driven fix loop for genuine 26.x API drift. Expected hotspots:
  - **GeckoLib 5.x API** differs from 4.8.2 — renderer/model base classes in
    `AbstractPlushModel`, `AbstractPlushRender` likely changed.
  - Registry / entity-attribute / render-layer registration signatures.
  - Worldgen (`ConfiguredFeature`/`PlacedFeature`) and datagen provider APIs.
- Re-run datagen (`gradlew runDatagen`) and commit regenerated `src/main/generated`.
- **Exit criteria:** 26.3 jar builds and loads in-game; smoke test passes.

### Phase 2 — Stonecutter fan-out to the full 26.x branch

- Add the Stonecutter Gradle plugin; declare versions
  `26.1, 26.1.1, 26.1.2, 26.2, 26.3`.
- Move per-version values (fabric-api, GeckoLib) into Stonecutter version
  properties.
- Add `//? if >=...` conditional-comment guards **only where the compiler shows
  26.1 ↔ 26.3 API divergence** — nowhere else.
- **Exit criteria:** `gradlew chiseledBuild` produces 5 green jars; smoke test
  on the lowest (26.1) and highest (26.3) targets.

## Testing strategy

This mod has **no unit-test framework** and GeckoLib/registry/render behavior is
integration-bound. The check at each phase is:

1. `gradlew build` green (compile + remap + datagen).
2. In-game load smoke test: world loads; a plush entity spawns, renders, and
   plays its GeckoLib animation; block/item/worldgen/commands/villager-trades
   present.

No test framework is introduced — YAGNI; the smoke test is the runnable check.

## Risks

- GeckoLib 5.x migration may touch more than the two renderer/model files if its
  registration/animation-controller API changed shape.
- 26.1.1 GeckoLib version unconfirmed — resolve at build time.
- Java version for 26.x assumed 21 — verify Phase 1.
- Cross-version API drift (26.1↔26.3) unknown until Phase 2 compile; if large,
  Phase 2 may need more conditional guards than expected. Fallback: ship 26.3
  only (Phase 1 output) and defer older targets.
- `migrateMappings` can leave untyped refs unconverted — Linkie + compiler
  catch these; budget manual fixup in Phase 0.

## Out of scope

- GeckoLib removal / vanilla animation rewrite (separate project).
- Forge / NeoForge (Fabric only).
- Any feature, content, or balance change.
