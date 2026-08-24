$modulePath = Join-Path $PSScriptRoot '..\CrossProxyHealth.Common.psm1'
if (Test-Path -LiteralPath $modulePath) {
    Import-Module $modulePath -Force
}

$fixturePath = Join-Path $PSScriptRoot 'fixtures'
$healthyFixture = Join-Path $fixturePath 'healthy.log'
$recoveringFixture = Join-Path $fixturePath 'recovering-tiberium.log'
$insufficientFixture = Join-Path $fixturePath 'insufficient.log'

Describe 'Get-CrossProxyLogHealth' {
    It 'classifies a validated heartbeat as Healthy' {
        $result = Get-CrossProxyLogHealth -LogPath $healthyFixture

        $result.Status | Should Be 'Healthy'
        $result.Counts.HeartbeatValidated | Should Be 1
    }

    It 'classifies a locally blocked Tiberium request as CrossProxyRecovering' {
        $result = Get-CrossProxyLogHealth -LogPath $recoveringFixture

        $result.Status | Should Be 'CrossProxyRecovering'
        $result.Counts.TiberiumPrepareTimeout | Should Be 1
        $result.Evidence.LocalBusinessSendBlocked | Should Be $true
        $result.Evidence.SourceServerId | Should Be '10003'
        $result.Evidence.TargetServerId | Should Be '10005'
    }

    It 'does not infer target-server failure from missing markers' {
        $result = Get-CrossProxyLogHealth -LogPath $insufficientFixture

        $result.Status | Should Be 'EvidenceInsufficient'
        $result.Evidence.TargetServerReceived | Should BeNullOrEmpty
    }

    It 'returns LogUnavailable for a missing log file' {
        $result = Get-CrossProxyLogHealth -LogPath (Join-Path $fixturePath 'missing.log')

        $result.Status | Should Be 'LogUnavailable'
    }
}

Describe 'Resolve-CrossProxyRuntimeStatus' {
    It 'reports TcpReachableButNoHeartbeat when TCP works but logs are recovering' {
        $result = Resolve-CrossProxyRuntimeStatus -TcpSucceeded $true -LogStatus 'CrossProxyRecovering'

        $result | Should Be 'TcpReachableButNoHeartbeat'
    }
}
