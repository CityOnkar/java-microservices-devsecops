{{- define "microservice.name" -}}
{{ .Values.name }}
{{- end }}

{{- define "microservice.fullname" -}}
{{ .Release.Name }}
{{- end }}
