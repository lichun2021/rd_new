# Hero 1122 (Lexa) Runtime Skill Design

## Goal and provenance

Implement a server-side compatibility version of local hero 1122's battle skill. Local 1122 was remapped from official client hero 1123 (Lexa), so its authoritative client records are `hero_skill_conf[112301..112305]` and `HeroSkillDesWithValue112301`; local server identifiers remain 112201–112205.

The implementation is derived from client configuration and text because matching official server Java is unavailable. It must not be described as an exact official formula.

## Scope

- Register `Skill1122` for 112201–112205 and proficiency effect 12981.
- Implement the provable air-supremacy mechanics for effects 12961–12967 and 12970.
- Implement 12968/12969 only if a source-backed trigger is found. Their short labels alone—electromagnetic-interception attack/defense/HP increase and incoming-damage reduction—are insufficient to invent a trigger.
- Do not add `Skill1123` or change staff-officer heroes 1121/1123.
- Reuse existing battle tuple stages and immutable march-count snapshots.

## Client-derived behavior

Hero 1122 qualifies to provide air supremacy in rally battles when the owner's deployed helicopters (soldier type 4) exceed 50% of that owner's deployed troops and are at least 5% of the rally's total deployed troops. Only the owner's largest helicopter battle unit qualifies. Mirage/phantom units do not contribute. In a rally, the two highest qualifying friendly player contributions are summed.

The contribution is derived from the qualifying helicopter unit's average attack, defense, and HP bonuses multiplied by effect 12961. Friendly and enemy air-supremacy totals are calculated symmetrically and cached from immutable pre-battle state.

When friendly air supremacy exceeds enemy air supremacy:

- 12962 reduces incoming damage for friendly air units by `difference × configured coefficient`, capped at 12%.
- 12963 increases friendly all-unit attack and decreases enemy all-unit attack by `difference × configured coefficient`, capped at the client-stated limit.

Synchronized-overload thresholds last until battle end and each contribution stacks at most twice:

- air supremacy ≥ 3: 12966 friendly all-unit incoming-damage reduction;
- air supremacy ≥ 5: 12965 friendly all-unit outgoing-damage increase;
- air supremacy ≥ 7: 12964 friendly all-unit super-attack increase.

Rotor interference (12967) requires Lexa to deploy with the configured partner hero. Every fifth round it raises that owner's 12961 coefficient by 8% and, for that round, applies attack deflection to enemy bomber units (soldier type 3). Its effective value is based on 12967 and the number of enemy bomber battle units, capped at two Lexa layers.

Air-control field (12970) triggers once per battle at round 40 or when friendly air supremacy reaches 10. It causes enemy units to take the configured additional damage and doubles friendly air supremacy for five rounds. Additional damage must use a non-recursive existing damage hook.

Proficiency effect 12981 applies only while `Skill1122` is active and only to the owner's largest deployed helicopter unit when receiving attack damage. The configured third field is duration in seconds.

Soul effects 12991/12992 add to synchronized-overload super attack and outgoing damage, 12993 adds synchronized-overload incoming reduction while the combat skill is active, and 12994 extends the combat-skill duration. These are sourced from `hero_soul_skill.xml` and `HeroSoulSkillDes112306`.

## Architecture

- `Skill1122`: parse and expose 12981 using the existing `ISSSHeroSkill` lifecycle.
- `Hero1122Rules`: pure functions for 50%/5% qualification, top-two contribution selection, air-supremacy difference, threshold layers, caps, round predicates, and type predicates.
- Battle state: cache friendly/enemy supremacy, the one-shot field trigger, and the five-round doubling window on existing battle leader/player extra-state maps; no global mutable state.
- One annotated Checker per implemented effect, delegating arithmetic to the pure rules and retaining explicit owner/type/war-mode guards.
- A minimal battle-class hook is allowed only for round-bound effects or non-recursive additional damage that cannot be represented by current tuple evaluation.

## Scaling and safety

- Use existing effect units (`GsConst.EFF_PER`) exactly once at the tuple boundary.
- Calculate ratios with zero guards and without integer truncation.
- Select the top two values, not the first two players encountered.
- Never use casualty-mutated live counts for initial qualification.
- Attacker and defender supremacy are computed independently; enemy counts never increase friendly values.
- Threshold effects use the friendly absolute supremacy value; difference-based effects use `max(0, friendly - enemy)`.
- The 12% cap applies only to 12962. Other caps follow the explicit client limit or existing engine-wide clamp.
- 12970 is one-shot and cannot trigger itself recursively.
- Malformed or missing configuration fails closed to zero and is logged through existing exception handling.

## Tests

Tests are written and observed failing before production code:

- exact skill and Checker registration closure;
- 50% is not enough because the text says “exceeds”; 50% plus one qualifies;
- exactly 5% qualifies; below 5% fails;
- top-two contributions are selected by value and exclude phantom units;
- friendly/enemy difference never becomes negative;
- thresholds 3/5/7, two-layer cap, every-fifth-round predicate, round-40 predicate, and five-round window boundaries;
- helicopter/type and largest-unit ownership guards;
- 12981 active/inactive duration behavior;
- one-shot and recursion guards for 12970;
- control battles without hero 1122 remain unchanged;
- no runtime implementation is introduced for local hero 1123.

## Adversarial review

Review the final diff for source/target-ID confusion, wrong partner ID, bomber/helicopter inversion, `>=50%` instead of `>50%`, `<5%` acceptance, first-two rather than top-two selection, phantom contribution, attacker/defender inversion, percent scaling twice, live-count leakage, threshold/difference confusion, incorrect stacking, repeated 12970 activation, recursive additional damage, and unsupported 12968/12969 behavior.

Critical and important findings require a failing regression test before correction and a fresh full verification run.

## Delivery boundary

Deliver source and tests in `rd_new` main as explicitly authorized. Do not deploy or restart a server. Report any effects held back for lack of evidence.
