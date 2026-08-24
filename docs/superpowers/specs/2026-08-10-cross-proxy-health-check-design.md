# CrossProxy Health Check Design

## Goal

Provide two Windows PowerShell diagnostics for cross-server incidents:

- A local replay checker that validates log evidence and diagnostic rules before release.
- A runtime checker executed on the real source game server that verifies proxy reachability and recent CrossProxy health.

The scripts diagnose the path used by Tiberium and other CrossProxy activities. They do not change game configuration, restart services, or claim a remote root cause without supporting evidence.

## Files

```text
script/crossproxy/
  CrossProxyHealth.Common.psm1
  Check-CrossProxyLog.ps1
  Check-CrossProxyRuntime.ps1
  tests/
    CrossProxyHealth.Tests.ps1
```

## Shared Module

`CrossProxyHealth.Common.psm1` parses a supplied server log without depending on a running game process. It returns a structured result containing:

- proxy addresses found in `crossProxy ten seconds` records;
- counts for `HEALTHY`, `RECOVERING`, heartbeat validation, recovery socket creation, blocked sends, RPC timeouts, and target-side prepare records;
- Tiberium source validation, blocked-send, prepare-timeout, and rollback counts;
- the newest monitor record and its `configuredNodeCount`, `keepaliveAge`, and traffic counters;
- a `Status`, `Reasons`, and `Evidence` collection.

The parser must classify an absent log marker as `EvidenceInsufficient`, not as proof that a remote server did not receive a request. A blocked send on the source server is sufficient evidence that this request did not leave the local CrossProxy send path.

## Local Replay Checker

`Check-CrossProxyLog.ps1` accepts `-LogPath`, optional `-ExpectedProxyAddress`, optional `-SourceServerId`, and optional `-TargetServerId`.

It calls the shared parser and writes a readable console report plus an optional JSON file selected with `-ReportPath`. It never performs network checks or writes outside an explicit report path.

Exit codes:

| Code | Meaning |
| --- | --- |
| 0 | Recent healthy heartbeat evidence exists and no requested failure condition is present. |
| 1 | Logs show `RECOVERING`, missing heartbeat validation, or blocked CrossProxy business traffic. |
| 2 | Log input cannot be read or does not contain enough CrossProxy evidence. |
| 3 | Script parameter or report-output error. |

## Runtime Checker

`Check-CrossProxyRuntime.ps1` accepts `-LogPath`, `-ProxyAddress`, optional `-ProxyPort` (default `8280`), optional `-SinceMinutes` (default `15`), and optional `-ReportPath`.

It invokes `Test-NetConnection` from the actual source game server, restricts log analysis to the requested recent interval where timestamps are parseable, and combines the network and log results:

| Runtime Status | Meaning |
| --- | --- |
| `Healthy` | TCP is reachable and recent heartbeat validation/healthy-state evidence exists. |
| `TcpUnreachable` | The source server cannot open a TCP connection to the requested proxy endpoint. |
| `TcpReachableButNoHeartbeat` | TCP is reachable, but current CrossProxy logs remain recovering or lack heartbeat validation. |
| `CrossProxyRecovering` | Logs show active recovery; TCP evidence is unavailable or inconclusive. |
| `LogUnavailable` | The requested server log cannot be read. |
| `EvidenceInsufficient` | The log window has no usable CrossProxy monitor evidence. |

TCP success proves only source-to-port reachability. It does not prove a successful ZeroMQ handshake, correct proxy identity, Redis selection, or target-server business availability. The report must state this limitation.

Runtime exit codes are `0` for `Healthy`, `1` for a confirmed unhealthy status, `2` for unavailable or insufficient evidence, and `3` for parameter/report-output errors.

## Testing

Pester tests use fixed synthetic log lines. They must cover:

1. Healthy heartbeat input is classified as `Healthy`.
2. Recovering input with a blocked Tiberium request is classified as unhealthy and retains the source/target evidence.
3. A missing marker is classified as `EvidenceInsufficient` instead of a target-server assertion.
4. Unreadable input and invalid parameter paths produce the documented error result.

The local checker is first run against `E:\RDLog\2026-08-09_11\2026-08-09\Server.log.2026-08-09`; expected result is unhealthy because the source is continuously recovering and Tiberium sends are blocked. The runtime checker is validated on the actual source game server only after a real endpoint is supplied.

## Follow-up Code Work

Do not modify Java recovery code merely because a script reports unhealthy. First collect runtime evidence. If TCP is reachable but the process never validates a heartbeat, add focused CrossProxy logging around connection role/identity and heartbeat acceptance. If the state machine identifies a reproducible defect, add a failing Java test before implementing a repair.
