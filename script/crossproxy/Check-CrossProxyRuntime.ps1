[CmdletBinding()]
param(
    [Parameter(Mandatory)]
    [string]$LogPath,
    [Parameter(Mandatory)]
    [string]$ProxyAddress,
    [ValidateRange(1, 65535)]
    [int]$ProxyPort = 8280,
    [ValidateRange(0, 525600)]
    [int]$SinceMinutes = 15,
    [string]$ReportPath
)

Set-StrictMode -Version Latest
Import-Module (Join-Path $PSScriptRoot 'CrossProxyHealth.Common.psm1') -Force

try {
    $tcp = Test-NetConnection -ComputerName $ProxyAddress -Port $ProxyPort -WarningAction SilentlyContinue
    $tcpSucceeded = [bool]$tcp.TcpTestSucceeded
    $latency = $null
    if ($tcp.PingSucceeded -and $null -ne $tcp.PingReplyDetails) {
        $latency = [int]$tcp.PingReplyDetails.RoundtripTime
    }

    $logResult = Get-CrossProxyLogHealth -LogPath $LogPath -SinceMinutes $SinceMinutes
    $logResult.Evidence.ExpectedProxyAddress = "tcp://$ProxyAddress`:$ProxyPort"
    $logResult.Evidence.ExpectedProxyAddressMatched = $logResult.ProxyAddresses -contains $logResult.Evidence.ExpectedProxyAddress
    if ($logResult.ProxyAddresses.Count -gt 0 -and -not $logResult.Evidence.ExpectedProxyAddressMatched) {
        $logResult.Reasons += "Observed proxy address differs from requested endpoint $($logResult.Evidence.ExpectedProxyAddress)."
    }

    $result = New-CrossProxyRuntimeResult -LogResult $logResult -TcpSucceeded $tcpSucceeded -ProxyAddress $ProxyAddress -ProxyPort $ProxyPort -TcpLatencyMilliseconds $latency
    Write-CrossProxyRuntimeReport -Result $result
    if ($ReportPath) {
        Export-CrossProxyHealthReport -Result $result -ReportPath $ReportPath
    }
    exit (Get-CrossProxyExitCode -Status $result.Status)
}
catch {
    Write-Error "CrossProxy runtime check failed: $($_.Exception.Message)"
    exit 3
}
