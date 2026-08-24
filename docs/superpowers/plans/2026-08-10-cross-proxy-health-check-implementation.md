# CrossProxy Health Check Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Build local and Windows-server PowerShell diagnostics that distinguish CrossProxy log evidence from actual source-server proxy reachability.

**Architecture:** A pure PowerShell module parses log lines into a structured result and classifies evidence without network access. A local wrapper reports replay results; a runtime wrapper adds `Test-NetConnection` from the source game server and maps network plus log evidence to an operational status.

**Tech Stack:** Windows PowerShell 5.1+, Pester when installed, JSON via `ConvertTo-Json`, built-in `Test-NetConnection`.

## Global Constraints

- All scripts are read-only except an explicit `-ReportPath` JSON output.
- TCP reachability must never be presented as proof of ZeroMQ handshake or target-server availability.
- Missing target-server logs are `EvidenceInsufficient`, unless the source log explicitly records a local send block.
- Exit codes: `0` healthy, `1` confirmed unhealthy, `2` unavailable/insufficient evidence, `3` parameter or output error.

---

### Task 1: Shared Parser Tests and Fixtures

**Files:**
- Create: `script/crossproxy/tests/CrossProxyHealth.Tests.ps1`
- Create: `script/crossproxy/tests/fixtures/healthy.log`
- Create: `script/crossproxy/tests/fixtures/recovering-tiberium.log`
- Create: `script/crossproxy/tests/fixtures/insufficient.log`

**Interfaces:**
- Consumes: `Get-CrossProxyLogHealth -LogPath <string> [-SinceMinutes <int>]` from the module created in Task 2.
- Produces: repeatable fixture inputs covering healthy, locally blocked Tiberium, and insufficient-evidence classifications.

- [ ] **Step 1: Write failing Pester tests**

```powershell
Describe 'Get-CrossProxyLogHealth' {
    It 'classifies a validated heartbeat as Healthy' {
        $result = Get-CrossProxyLogHealth -LogPath $healthyFixture
        $result.Status | Should -Be 'Healthy'
    }

    It 'classifies a locally blocked Tiberium request as CrossProxyRecovering' {
        $result = Get-CrossProxyLogHealth -LogPath $recoveringFixture
        $result.Status | Should -Be 'CrossProxyRecovering'
        $result.Counts.TiberiumPrepareTimeout | Should -Be 1
        $result.Evidence.LocalBusinessSendBlocked | Should -BeTrue
    }

    It 'does not infer target-server failure from missing markers' {
        $result = Get-CrossProxyLogHealth -LogPath $insufficientFixture
        $result.Status | Should -Be 'EvidenceInsufficient'
    }
}
```

- [ ] **Step 2: Run the tests to verify the expected missing-command failure**

Run: `Invoke-Pester .\script\crossproxy\tests\CrossProxyHealth.Tests.ps1`

Expected: Pester reports that `Get-CrossProxyLogHealth` is unavailable.

### Task 2: Shared Log Parser and Local Replay Checker

**Files:**
- Create: `script/crossproxy/CrossProxyHealth.Common.psm1`
- Create: `script/crossproxy/Check-CrossProxyLog.ps1`
- Modify: `script/crossproxy/tests/CrossProxyHealth.Tests.ps1`

**Interfaces:**
- Produces: `Get-CrossProxyLogHealth` returning a PSCustomObject with `Status`, `Reasons`, `Evidence`, `Counts`, `LatestMonitor`, and `ProxyAddresses`.
- Produces: `Write-CrossProxyHealthReport -Result <object>` for consistent output.
- Produces: `Get-CrossProxyExitCode -Status <string>`.

- [ ] **Step 1: Implement the minimum parser to satisfy each fixture test**

```powershell
function Get-CrossProxyLogHealth {
    param([Parameter(Mandatory)][string]$LogPath, [int]$SinceMinutes = 0)
    # Count explicit state and Tiberium markers, parse the latest monitor line,
    # and classify only the evidence present in the supplied file.
}
```

- [ ] **Step 2: Implement the local wrapper**

```powershell
param([Parameter(Mandatory)][string]$LogPath, [string]$ExpectedProxyAddress,
      [string]$SourceServerId, [string]$TargetServerId, [string]$ReportPath)
Import-Module "$PSScriptRoot\CrossProxyHealth.Common.psm1" -Force
$result = Get-CrossProxyLogHealth -LogPath $LogPath
Write-CrossProxyHealthReport -Result $result
exit (Get-CrossProxyExitCode -Status $result.Status)
```

- [ ] **Step 3: Run the Pester suite and the real August 9 replay**

Run: `Invoke-Pester .\script\crossproxy\tests\CrossProxyHealth.Tests.ps1`

Run: `.\script\crossproxy\Check-CrossProxyLog.ps1 -LogPath 'E:\RDLog\2026-08-09_11\2026-08-09\Server.log.2026-08-09' -ExpectedProxyAddress '192.168.10.20' -SourceServerId '10003' -TargetServerId '10005'`

Expected: test fixtures pass; the August 9 replay reports `CrossProxyRecovering` and local Tiberium send evidence.

### Task 3: Windows Runtime Connectivity Checker

**Files:**
- Create: `script/crossproxy/Check-CrossProxyRuntime.ps1`
- Modify: `script/crossproxy/tests/CrossProxyHealth.Tests.ps1`

**Interfaces:**
- Consumes: the shared parser and report helpers from Task 2.
- Produces: a result whose `Status` is one of `Healthy`, `TcpUnreachable`, `TcpReachableButNoHeartbeat`, `CrossProxyRecovering`, `LogUnavailable`, or `EvidenceInsufficient`.

- [ ] **Step 1: Add failing status-mapping tests**

```powershell
It 'reports TcpReachableButNoHeartbeat when TCP works but logs are recovering' {
    $result = Resolve-CrossProxyRuntimeStatus -TcpSucceeded $true -LogStatus 'CrossProxyRecovering'
    $result | Should -Be 'TcpReachableButNoHeartbeat'
}
```

- [ ] **Step 2: Implement deterministic status mapping and runtime wrapper**

```powershell
$tcp = Test-NetConnection -ComputerName $ProxyAddress -Port $ProxyPort -WarningAction SilentlyContinue
$logResult = Get-CrossProxyLogHealth -LogPath $LogPath -SinceMinutes $SinceMinutes
$runtimeStatus = Resolve-CrossProxyRuntimeStatus -TcpSucceeded $tcp.TcpTestSucceeded -LogStatus $logResult.Status
```

- [ ] **Step 3: Run Pester and a local dry run**

Run: `Invoke-Pester .\script\crossproxy\tests\CrossProxyHealth.Tests.ps1`

Run: `.\script\crossproxy\Check-CrossProxyRuntime.ps1 -LogPath 'E:\RDLog\2026-08-09_11\2026-08-09\Server.log.2026-08-09' -ProxyAddress '192.168.10.20' -ProxyPort 8280 -SinceMinutes 0`

Expected: Pester passes. The local dry run demonstrates script mechanics only; its network result must not be used as source-server evidence.

### Task 4: Operational Documentation and Verification

**Files:**
- Create: `docs/cross_proxy_health_check.md`

**Interfaces:**
- Documents exact local replay and source-server commands, statuses, exit codes, and evidence limitations.

- [ ] **Step 1: Document commands and interpretation**

```powershell
.\script\crossproxy\Check-CrossProxyLog.ps1 -LogPath 'E:\RDLog\...\Server.log.2026-08-09'
.\script\crossproxy\Check-CrossProxyRuntime.ps1 -LogPath 'D:\server\log\Server.log' -ProxyAddress '192.168.10.20' -ProxyPort 8280
```

- [ ] **Step 2: Verify formatting and working tree diff**

Run: `git diff --check -- script/crossproxy docs/cross_proxy_health_check.md`

Expected: no whitespace errors.

### Task 5: Evidence-Gated Java Follow-up

**Files:**
- Modify only after runtime evidence identifies a Java-state-machine defect: `src/main/java/com/hawk/game/crossproxy/CrossProxy.java`
- Test only if source changes: `src/test/java/com/hawk/game/crossproxy/CrossProxyHealthTest.java`

**Interfaces:**
- Consumes: reports showing TCP success with persistent heartbeat validation failure, plus matching CrossProxy logs.
- Produces: a narrowly scoped diagnostic log or a tested state-machine repair.

- [ ] **Step 1: Do not edit Java code before runtime evidence exists**

Expected: scripts are the deliverable for this execution; Java work remains conditional rather than speculative.
