# CrossProxy Health Check

These Windows PowerShell scripts separate historical-log diagnosis from a real source-server network check. They are read-only unless `-ReportPath` is supplied.

## Files

- `script/crossproxy/Check-CrossProxyLog.ps1`: local log replay.
- `script/crossproxy/Check-CrossProxyRuntime.ps1`: execute on the actual source game server.
- `script/crossproxy/CrossProxyHealth.Common.psm1`: shared parser and status rules.

## Local Replay

Run from the repository root:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\script\crossproxy\Check-CrossProxyLog.ps1 `
  -LogPath 'E:\RDLog\2026-08-09_11\2026-08-09\Server.log.2026-08-09' `
  -ExpectedProxyAddress '192.168.10.20' `
  -SourceServerId '10003' `
  -TargetServerId '10005'
```

For the August 9 Tiberium incident, the expected status is `CrossProxyRecovering`. It should show no validated heartbeat, the `10003 -> 10005` local send block, and Tiberium prepare-enter timeouts.

The local script does not test the production network. Its result is evidence about the supplied log only.

## Source-Server Runtime Check

Copy the `script/crossproxy` directory to the actual source game server, for example server `10003`, then run it from that server:

```powershell
powershell.exe -NoProfile -ExecutionPolicy Bypass -File .\Check-CrossProxyRuntime.ps1 `
  -LogPath 'D:\GameServer\log\Server.log' `
  -ProxyAddress '192.168.10.20' `
  -ProxyPort 8280 `
  -SinceMinutes 15 `
  -ReportPath 'D:\GameServer\log\crossproxy-health.json'
```

Use the actual active log path. `-SinceMinutes 15` analyzes only events in the latest fifteen-minute log window; use `0` to analyze the full file.

## Statuses

| Status | Interpretation | Next action |
| --- | --- | --- |
| `Healthy` | TCP is reachable and recent CrossProxy heartbeats are validated. | Retry the activity; investigate target business logs only if it still fails. |
| `TcpUnreachable` | This source server cannot open TCP to the requested proxy address and port. | Check proxy process/listener, firewall, route, and security rules. |
| `TcpReachableButNoHeartbeat` | TCP works, but CrossProxy is still recovering or does not validate heartbeats. | Check proxy-side Dealer identity, protocol version, Redis selection, and heartbeat handling. Add focused Java logs before changing recovery behavior. |
| `CrossProxyRecovering` | Logs show recovery or local CrossProxy business-send blocking; TCP is unavailable or inconclusive. | Re-run with a valid proxy endpoint from the actual source server. |
| `LogUnavailable` | The active log file cannot be read. | Correct the path and permissions. |
| `EvidenceInsufficient` | The requested log window contains no usable CrossProxy monitor evidence. | Increase `-SinceMinutes`, use the active server log, or wait for a monitor cycle. |

TCP success does not prove a valid ZeroMQ handshake, accepted Dealer identity, correct Redis proxy selection, or target-server availability.

## Exit Codes

| Code | Meaning |
| --- | --- |
| `0` | `Healthy` |
| `1` | Confirmed unhealthy state, including `TcpUnreachable`, `TcpReachableButNoHeartbeat`, and `CrossProxyRecovering` |
| `2` | `LogUnavailable` or `EvidenceInsufficient` |
| `3` | Invalid parameters, unavailable PowerShell capability, or report-output failure |

## Java Follow-up Gate

Do not change CrossProxy recovery logic based only on a local replay or TCP test.

1. Run the runtime script on the source game server during the incident.
2. If it reports `TcpUnreachable`, repair deployment/network/proxy availability first.
3. If it reports `TcpReachableButNoHeartbeat`, collect the JSON report and matching proxy-server logs; then add focused logs around CrossProxy heartbeat role, identity, generation, and rejection reason.
4. Add a Java state-machine repair only after those logs show a reproducible source-side defect. The repair must include a failing regression test before implementation.
