name: 🐞 Bug Report
description: Log a defect or regression observed in production or non-prod environments.
labels: [bug, needs-triage]
title: "[BUG] <brief summary>"
assignees: ""
body:
  - type: markdown
    attributes:
      value: |
        ## Bug Report
        Please provide a clear and concise summary of the issue.
  - type: input
    id: environment
    attributes:
      label: Environment
      description: Where did this occur (e.g., DEV, QA, UAT, PROD)?
      placeholder: DEV
  - type: textarea
    id: steps
    attributes:
      label: Steps to Reproduce
      description: List the exact steps to reproduce the issue.
      placeholder: |
        1. Go to ...
        2. Click on ...
        3. Observe error ...
  - type: textarea
    id: expected
    attributes:
      label: Expected Behavior
      description: What should have happened?
  - type: textarea
    id: actual
    attributes:
      label: Actual Behavior
      description: What actually happened?
  - type: textarea
    id: logs
    attributes:
      label: Logs / Screenshots
      description: Attach relevant logs, screenshots, or stack traces.
  - type: dropdown
    id: severity
    attributes:
      label: Severity
      options:
        - Critical
        - High
        - Medium
        - Low
  - type: input
    id: component
    attributes:
      label: Affected Module / Microservice
      description: Which logical component is impacted?
  - type: textarea
    id: notes
    attributes:
      label: Additional Context
      description: Any other relevant information?
