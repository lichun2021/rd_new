# Hero 1122 Runtime Skill Design

## Goal

Implement a server-side compatibility version of hero 1122's runtime battle skill in `rd_new`, using the shipped client description and configuration as the behavioral contract and existing same-quality hero implementations as engine integration references.

The implementation must make effects 12961–12970 and proficiency effect 12981 participate in battle. It must not claim byte-for-byte parity with unavailable official server source.

## Scope

- Add runtime registration for skills 112201–112205.
- Add battle consumers for effects 12961–12970 and 12981.
- Add narrowly scoped battle state only where an effect cannot be expressed through an existing tuple/checker.
- Reuse the existing `EffType` entries and XML values without changing their identifiers or scaling.
- Add automated structural and behavior tests before production code.
- Do not change hero 1123 in this delivery. Hero 1123 is a staff officer (`staffOfficer=1`, `marchUsed=0`) and its effects 12995–12997 belong to the staff-officer attribute path rather than hero 1122's expedition skill path.
- Do not change hero 1121 merely because effects 12941–12944 have no Checker classes; it is also a staff officer.

## Evidence and Behavioral Contract

`hero_skill.xml` defines skills 112201–112205 with the same effect topology and progressively increasing values. The client description defines these mechanics:

1. The hero's core theme is dynamic battlefield command allocation.
2. During preparation, troop super attack, attack, defense, and HP use the ordinary configured effects already handled by the generic effect system.
3. Before a rally battle starts, if melee units are at least 40% of total deployed units, all allied melee units gain attack, defense, and HP. Multiple copies can stack up to seven layers.
4. Before a rally battle starts, if ranged units are at least 40% of total deployed units, all allied ranged units gain attack, defense, and HP. Multiple copies can stack up to seven layers.
5. For every 200,000 deployed melee units, the hero owner's melee troops receive reduced incoming attack damage, stacking up to twenty layers.
6. For every 200,000 deployed ranged units, the hero owner's ranged troops receive reduced incoming attack damage, stacking up to twenty layers.
7. Proficiency effect 12981 reduces damage received by helicopter units while they are attacked, for the configured duration.

The named effects provide the remaining semantic labels:

- 12961: air-current anchoring air-supremacy coefficient.
- 12962: air-current anchoring incoming-damage reduction.
- 12963: air-current anchoring attack increase.
- 12964: synchronized overload super-attack increase.
- 12965: synchronized overload outgoing-damage increase.
- 12966: synchronized overload incoming-damage reduction.
- 12967: rotor interference attack-deflection rate.
- 12968: electromagnetic interception attack/defense/HP increase.
- 12969: electromagnetic interception incoming-damage reduction.
- 12970: air-control field additional damage.
- 12981: helicopter incoming-damage reduction during proficiency.

Where the long client description and short effect name overlap, the long description controls trigger and stacking behavior while the XML value controls magnitude.

## Architecture

### Skill registration

Create `Skill1122` extending `ISSSHeroSkill`, registered for 112201–112205. It parses the three-part `proficiencyEffect` value exactly as `Skill1120` does and exposes effect 12981 only while the proficiency skill is active. Duration comes from the third configured field in seconds.

Parsing must fail closed: malformed or absent configuration returns no proficiency effect and records the exception through the existing server exception path.

### Pure rule layer

Create `Hero1122Rules` as a small, deterministic helper for calculations that are easy to get subtly wrong:

- qualifying whether a troop category reaches 40% of the total;
- computing `floor(categoryCount / 200000)` with a maximum of 20;
- limiting rally-wide duplicate-hero amplification to seven layers;
- converting configured floating coefficients through the same military-value scaling convention used by existing hero Checkers;
- identifying melee, ranged, and helicopter targets through existing soldier-type classifications rather than hard-coded unit IDs.

This layer contains no mutable battle state and is tested directly.

### Checker and battle integration

Each new effect receives one discoverable `@EffectChecker` entry. A Checker may delegate to a shared implementation, but annotations remain one-to-one with `EffType` so startup discovery and audits can prove closure.

Existing tuple types and battle phases are reused by semantic role:

- attack, defense, HP, and super-attack modifiers use their existing attribute tuple stages;
- outgoing and additional damage use the established attacker-side damage stages;
- incoming-damage reduction uses defender-side reduction stages and remains multiplicative with independent reductions where the engine already treats that family multiplicatively;
- deflection uses the existing deflection stage and probability scale;
- pre-battle composition checks use immutable original deployed counts, not counts after casualties, cloning, healing, or in-battle conversion;
- rally-wide bonuses read all participating hero copies, cap the effective layer count at seven, and apply only to the qualifying melee or ranged category;
- per-200,000 reductions use only the owning player's original deployed category count and cap at twenty layers;
- proficiency effect 12981 applies only to the hero owner's helicopter units and only while `Skill1122.isEffecting()` is true.

No effect is allowed to apply globally merely because its ID is present in `effectid.xml`.

## Scaling and Composition Rules

- XML effect values remain decimal coefficients; the implementation converts them at the same boundary as comparable existing Checkers.
- Percentage values are never multiplied by 100 twice.
- Stack counts multiply the configured per-layer effect before entering the battle tuple.
- A seven-layer rally cap and a twenty-layer unit-count cap are independent.
- Attack/defense/HP bonuses are additive inside their existing attribute family.
- Damage increase and damage reduction follow the composition policy of the matching existing tuple family; the new implementation does not introduce a new damage pipeline.
- Additional damage from 12970 must go through an existing additional-damage hook so it cannot recursively trigger itself.
- All category predicates are evaluated independently for attacker and defender ownership; enemy counts must never contribute to friendly stacks.

## Failure and Compatibility Behavior

- Missing hero skill configuration produces zero custom effect rather than a startup crash.
- Zero or negative total troop count never qualifies for a 40% bonus.
- Negative category counts are treated as zero by the pure rule layer.
- Duplicate Checkers or duplicate hero registration are rejected by structural tests.
- Battles without hero 1122 retain their prior results.
- Hero 1120's existing behavior remains the regression baseline.

## Test Strategy

Tests are added before implementation and must be observed failing for the missing feature.

1. Structural closure test:
   - skills 112201–112205 resolve to `Skill1122`, not `CommonSkill`;
   - effects 12961–12970 and 12981 each have one Checker registration;
   - no production implementation is added for 1123 in this change.
2. Pure rule tests:
   - 39.999% fails and exactly 40% qualifies;
   - 199,999 units produce zero layers, 200,000 produces one, and counts above 4,000,000 remain capped at twenty;
   - duplicate hero layers cap at seven;
   - zero and negative inputs fail safely.
3. Skill tests:
   - all five skill IDs parse their own configured values;
   - effect 12981 is zero outside the active window and nonzero inside it;
   - configured duration uses seconds-to-milliseconds conversion exactly once.
4. Battle behavior harness:
   - melee and ranged thresholds affect only their matching categories;
   - helicopter proficiency reduction affects only helicopters owned by the skill owner;
   - attacker and defender effects are not reversed;
   - additional damage does not recursively re-enter its own trigger;
   - a control battle without hero 1122 is unchanged.
5. Regression and build:
   - existing hero runtime tests pass;
   - `rd_new` compiles with its configured Java/Gradle toolchain.

## Adversarial Review

After implementation, review the complete diff from the perspective of a hostile integrator. The review explicitly searches for:

- unsupported assumptions presented as official behavior;
- wrong troop-category mappings;
- enemy troop counts leaking into friendly calculations;
- integer division or rounding errors at 40% and 200,000 boundaries;
- duplicated percentage scaling;
- incorrect additive versus multiplicative damage composition;
- attacker/defender inversion;
- mutable casualty counts used instead of original deployment counts;
- seven-layer and twenty-layer caps applied to the wrong mechanic;
- proficiency effects active outside their duration;
- extra damage recursively triggering itself;
- accidental changes to heroes 1120, 1121, or 1123;
- tests that only assert source text rather than executable behavior.

Critical and important findings must be fixed and the full verification rerun before the work is reported complete.

## Delivery

The delivery consists of source and tests in `rd_new`. It will be reported as a client-derived compatibility implementation, not an authenticated official implementation. No server deployment, process restart, or repository push is included unless separately requested.
