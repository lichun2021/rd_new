# rd_new CrossProxy and Heroes Integration Plan

**Goal:** Preserve and separately commit the local CrossProxy work, reconcile the equivalent local/upstream hotfix histories, then add the missing hero 1121-1123 configuration and the hero 1116 soldier-adjustment fix.

**Target:** `https://github.com/lichun2021/rd_new.git` (`upstream/main`)

## Task 1: CrossProxy production code

- Commit only `CrossProxy.java`, `ProxyHelper.java`, `CrossProxyHealth.java`, and `CrossProxyHealthTest.java`.
- Prove the standalone health test fails without the production class, then compile and run it against the real class.
- Exclude IDE settings, copied projects, archives, and class files.

## Task 2: CrossProxy diagnostics and documentation

- Commit `script/crossproxy`, its Pester fixtures/tests, the operational and incident documentation, and this plan.
- Run Pester when available; otherwise run the wrappers against their fixed fixtures and record the limitation.

## Task 3: Reconcile upstream history

- Fetch `upstream`.
- Merge `upstream/main` without rewriting either side.
- Resolve the line-ending-only overlap while retaining the current enhanced CrossProxy implementation and the upstream `CrossService` timeout response.

## Task 4: Hero 1121-1123 configuration closure

- Create an isolated worktree from the integrated branch.
- Add a failing configuration-closure test for heroes 1121-1123.
- Add the trusted XML rows from the completed `rd_server` configuration commit: hero, item, collect, star, skill, soul, and effect definitions.
- Preserve all existing `rd_new` hero 1115-1120 rows and runtime implementations.
- Do not invent hero 1122 Java runtime behavior; record the external Protocol dependency.

## Task 5: Hero 1116 regression fix

- Add a failing behavior test showing effect 12724 applies the configured soldier-type coefficient.
- Apply the minimum battle calculation change and rerun the focused and closure tests.

## Task 6: Integration and delivery

- Run XML parse/uniqueness/reference closure, focused Java tests, available script tests, and `git diff --check`.
- Merge the hero worktree branch into the integration branch, then merge the integration branch into local `main`.
- Push `main` to `upstream`; if permission is denied, push to `origin` and report the exact result.

## Explicit exclusions

- `GameAcitivity/`, `GameActivity.zip`, `crossproxy-health-check.zip`
- `hawkjar/`, `HawkTime.class`, all generated `.class` files
- `.settings/org.eclipse.buildship.core.prefs` and other machine-specific IDE state
- generated/binary protocol JARs
