{{- define "microservice.name" -}}
{{- /* allow override via values.name, otherwise fall back to chart name */ -}}
{{- default .Chart.Name .Values.name -}}
{{- end }}

{{- define "microservice.fullname" -}}
{{- /* prefer the Helm release name, but if values.name is set and release has been
      auto-generated we can append/prepend it for clarity */ -}}
{{- $name := .Release.Name -}}
{{- if and (not (hasKey .Release "Name")) .Values.name }}
{{- $name = .Values.name }}
{{- end }}
{{- $name -}}
{{- end }}
