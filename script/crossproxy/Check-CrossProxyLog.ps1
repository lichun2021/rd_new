[CmdletBinding()]
param(
    [Parameter(Mandatory)]
    [string]$LogPath,
    [string]$ExpectedProxyAddress,
    [string]$SourceServerId,
    [string]$TargetServerId,
    [string]$ReportPath
)

Set-StrictMode -Version Latest
Import-Module (Join-Path $PSScriptRoot 'CrossProxyHealth.Common.psm1') -Force

try {
    $result = Get-CrossProxyLogHealth -LogPath $LogPath
    if ($ExpectedProxyAddress) {
        $result.Evidence.ExpectedProxyAddress = $ExpectedProxyAddress
        $result.Evidence.ExpectedProxyAddressMatched = $result.ProxyAddresses -contains "tcp://$ExpectedProxyAddress`:8280"
        if (-not $result.Evidence.ExpectedProxyAddressMatched) {
            $result.Reasons += "Expected proxy address was not observed: $ExpectedProxyAddress"
        }
    }
    if ($SourceServerId -and $result.Evidence.SourceServerId -and $SourceServerId -ne $result.Evidence.SourceServerId) {
        $result.Reasons += "Observed source server $($result.Evidence.SourceServerId) differs from expected $SourceServerId."
    }
    if ($TargetServerId -and $result.Evidence.TargetServerId -and $TargetServerId -ne $result.Evidence.TargetServerId) {
        $result.Reasons += "Observed target server $($result.Evidence.TargetServerId) differs from expected $TargetServerId."
    }
    Write-CrossProxyHealthReport -Result $result
    if ($ReportPath) {
        Export-CrossProxyHealthReport -Result $result -ReportPath $ReportPath
    }
    exit (Get-CrossProxyExitCode -Status $result.Status)
}
catch {
    Write-Error "CrossProxy log check failed: $($_.Exception.Message)"
    exit 3
}
