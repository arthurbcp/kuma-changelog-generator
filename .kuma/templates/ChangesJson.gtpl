{{- $newRelease := dict "type" .data.changeType "description" .data.changeDescription -}}
{{- $releases := .data.releases -}}
{{- if eq .data.isRelease "n" -}}
    {{- $unreleased := ($releases | get "[Unreleased]") | append $newRelease -}}
    {{- $releases = $releases | set "[Unreleased]" $unreleased -}}
{{- else -}}
   {{- $releaseKey := print "[" .data.changeVersion "] - "  (now | date "2006-01-02")  -}}
   {{- $curReleases := list -}}
   {{- if $releases | hasKey $releaseKey -}}
      {{- $curReleases = ($releases | get $releaseKey)}}
   {{- end -}}
   {{- $curReleases = $curReleases | append $newRelease -}}
   {{- $releases = $releases | set $releaseKey $curReleases -}}
{{- end -}}
{
{{- $i := 0 -}}
 {{range $key, $versionReleases := $releases}}
    "{{$key}}": [
       {{- range $index, $release := $versionReleases }}
       {{toJson $release}}{{if not (eq $index (sub (len $versionReleases) 1))}},{{end}}
       {{- end}}
    ]{{if not (eq $i (sub (len $releases) 1))}},
    {{end}}
    {{- $i = add $i 1 -}}
 {{- end}}
}