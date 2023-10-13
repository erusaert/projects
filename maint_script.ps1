Import-Module $env:SyncroModule

# Weekly Computer Maintenance Script

 

# Define variables

$logfile = "C:\slogs\WeeklyMaintenance.log"

$date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"

 

# Creates the C:\slogs folder if it does not exist

Set-Location C:\

md slogs

Set-Location C:\slogs

 

# Start logging

Write-Output "Weekly Computer Maintenance Script started on $date" | Out-File $logfile

 

# Create restore point

Write-Output "Creating a Windows Restore Point" | Out-File $logfile -Append

Checkpoint-Computer -Description "Weekly Maintenance" -RestorePointType "MODIFY_SETTINGS"

 

# Clear event logs

Write-Output "Clearing Event Logs" | Out-File $logfile -Append

wevtutil el | Foreach-Object {wevtutil cl "$_"} | Out-File $logfile -Append
 

# Delete temp files

Write-Output "Deleting Temp files" | Out-File $logfile -Append

Get-ChildItem -Path "$env:TEMP\*" -Recurse -Force | Remove-Item -Force -Recurse | Out-File $logfile -Append

 

# Delete old downloads

Write-Output "Deleting old downloads" | Out-File $logfile -Append

$downloads = "$env:USERPROFILE\Downloads"

Get-ChildItem -Path $downloads -Recurse | Where-Object {$_.LastWriteTime -lt (Get-Date).AddDays(-7)} | Remove-Item -Force -Recurse | Out-File $logfile -Append

 

# Clear recycle bin

Write-Output "Clearing Recycle Bin" | Out-File $logfile -Append

Clear-RecycleBin -Force | Out-File $logfile -Append

 

# Clean Up the WinSxS Folder

# Based on https://docs.microsoft.com/en-us/windows-hardware/manufacture/desktop/clean-up-the-winsxs-folder

Write-Output "Cleaning up the WinSxS folder" | Out-File $logfile -Append

schtasks.exe /Run /TN "\Microsoft\Windows\Servicing\StartComponentCleanup"

 

# Run disk cleanup

Write-Output "Running Disk Cleanup" | Out-File $logfile -Append

#Start-Process -FilePath "cleanmgr.exe" -ArgumentList "/sagerun:1" -Wait | Out-File $logfile -Append

 

# Restart the computer

Write-Output "Restarting the computer" | Out-File $logfile -Append

#Restart-Computer -Force | Out-File $logfile -Append

 

# End logging

$date = Get-Date -Format "yyyy-MM-dd HH:mm:ss"

Write-Output "Weekly Computer Maintenance Script finished on $date" | Out-File $logfile -Append